param(
    [string]$MailUsername = "",
    [string]$NotificationRecipient = "elangovandev27@gmail.com",
    [string]$HfToken = "",
    [string]$JwtSecret = "",
    [switch]$SkipMailPrompt
)

$ErrorActionPreference = "Stop"
$mailPassword = ""

function Convert-SecureStringToPlainText {
    param([System.Security.SecureString]$SecureValue)

    if ($null -eq $SecureValue -or $SecureValue.Length -eq 0) {
        return ""
    }

    $bstr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($SecureValue)
    try {
        return [Runtime.InteropServices.Marshal]::PtrToStringBSTR($bstr)
    } finally {
        [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($bstr)
    }
}

function Set-ProcessEnvironment {
    param(
        [string]$Name,
        [string]$Value
    )

    if ($null -ne $Value) {
        [Environment]::SetEnvironmentVariable($Name, $Value, "Process")
    }
}

if (-not $JwtSecret) {
    $JwtSecret = ([guid]::NewGuid().ToString("N") + [guid]::NewGuid().ToString("N"))
}

if (-not $SkipMailPrompt) {
    if (-not $MailUsername) {
        $MailUsername = Read-Host "Sender Gmail address for EarthVoice notifications"
    }

    $mailPasswordSecure = Read-Host "Gmail App Password for $MailUsername" -AsSecureString
    $mailPassword = Convert-SecureStringToPlainText $mailPasswordSecure
}

if (-not $HfToken) {
    $HfToken = Read-Host "Hugging Face token (optional, press Enter to skip)"
}

$projectRoot = Resolve-Path (Join-Path $PSScriptRoot "..")

try {
    Set-ProcessEnvironment "JWT_SECRET" $JwtSecret
    Set-ProcessEnvironment "NOTIFICATIONS_ENABLED" "true"
    Set-ProcessEnvironment "NOTIFICATION_RECIPIENT" $NotificationRecipient

    if ($MailUsername -and $mailPassword) {
        Set-ProcessEnvironment "MAIL_HOST" "smtp.gmail.com"
        Set-ProcessEnvironment "MAIL_PORT" "587"
        Set-ProcessEnvironment "MAIL_USERNAME" $MailUsername
        Set-ProcessEnvironment "MAIL_PASSWORD" $mailPassword
        Set-ProcessEnvironment "MAIL_SMTP_AUTH" "true"
        Set-ProcessEnvironment "MAIL_SMTP_STARTTLS_ENABLE" "true"
        Set-ProcessEnvironment "NOTIFICATION_FROM" $MailUsername
    } else {
        Write-Warning "Mail credentials were not provided. The app will run, but email delivery will be skipped."
    }

    if ($HfToken) {
        Set-ProcessEnvironment "HF_TOKEN" $HfToken
    }

    Push-Location $projectRoot
    try {
        .\mvnw.cmd spring-boot:run
    } finally {
        Pop-Location
    }
} finally {
    foreach ($name in @(
        "JWT_SECRET",
        "NOTIFICATIONS_ENABLED",
        "NOTIFICATION_RECIPIENT",
        "MAIL_HOST",
        "MAIL_PORT",
        "MAIL_USERNAME",
        "MAIL_PASSWORD",
        "MAIL_SMTP_AUTH",
        "MAIL_SMTP_STARTTLS_ENABLE",
        "NOTIFICATION_FROM",
        "HF_TOKEN"
    )) {
        [Environment]::SetEnvironmentVariable($name, $null, "Process")
    }
}
