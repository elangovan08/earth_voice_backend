package com.webApp.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class DefaultPostImageService {

    public GeneratedImage generateDefaultImage(String title, String category) {
        CategoryTheme theme = resolveTheme(category);
        String safeTitle = truncate(escapeXml(StringUtils.hasText(title) ? title.trim() : "EarthVoice"), 70);
        String safeCategory = escapeXml(theme.label());

        String svg = """
                <svg xmlns="http://www.w3.org/2000/svg" width="1200" height="675" viewBox="0 0 1200 675">
                  <defs>
                    <linearGradient id="bg" x1="0" x2="1" y1="0" y2="1">
                      <stop offset="0" stop-color="%s"/>
                      <stop offset="1" stop-color="%s"/>
                    </linearGradient>
                  </defs>
                  <rect width="1200" height="675" fill="url(#bg)"/>
                  <circle cx="970" cy="155" r="135" fill="#ffffff" opacity="0.18"/>
                  <circle cx="160" cy="560" r="210" fill="#ffffff" opacity="0.12"/>
                  <path d="%s" fill="#ffffff" opacity="0.82"/>
                  <rect x="76" y="72" width="1048" height="531" rx="34" fill="#0b1220" opacity="0.34"/>
                  <text x="120" y="185" font-family="Arial, sans-serif" font-size="30" font-weight="700" fill="#ffffff" opacity="0.86">%s</text>
                  <text x="120" y="326" font-family="Arial, sans-serif" font-size="58" font-weight="800" fill="#ffffff">%s</text>
                  <text x="120" y="407" font-family="Arial, sans-serif" font-size="28" fill="#ffffff" opacity="0.82">Community insight for a cleaner planet</text>
                </svg>
                """.formatted(theme.startColor(), theme.endColor(), theme.path(), safeCategory, safeTitle);

        return new GeneratedImage(svg.getBytes(StandardCharsets.UTF_8), "image/svg+xml");
    }

    private CategoryTheme resolveTheme(String category) {
        String normalized = category == null ? "" : category.trim().toLowerCase(Locale.ROOT);
        return switch (normalized) {
            case "climate" -> new CategoryTheme("Climate", "#0f766e", "#38bdf8",
                    "M820 438c75-155 210-246 330-224v211c-78-48-154-45-225 13-38 31-72 44-105 0z");
            case "energy" -> new CategoryTheme("Energy", "#ca8a04", "#22c55e",
                    "M864 154h116l-76 148h115L803 548l62-181H760z");
            case "wildlife" -> new CategoryTheme("Wildlife", "#166534", "#84cc16",
                    "M842 460c-25-112 33-229 134-270 91-37 169 2 197 66-99-11-170 26-209 104-27 53-67 87-122 100z");
            case "zero waste" -> new CategoryTheme("Zero waste", "#047857", "#14b8a6",
                    "M845 206c77-49 180-36 242 31l-57 1 34 69-85-48 22-43c-46-25-105-25-156-10zm249 223c-60 69-161 91-241 54l49-31-45-62 94 29-8 48c52 10 108-4 151-38zm-277 5c-25-88 15-184 94-230l-3 58 76 18-84 51-30-38c-36 39-56 92-53 141z");
            case "water" -> new CategoryTheme("Water", "#0369a1", "#06b6d4",
                    "M970 142c86 111 150 197 150 282 0 90-67 155-150 155s-150-65-150-155c0-85 64-171 150-282z");
            case "food", "agriculture" -> new CategoryTheme("Agriculture", "#4d7c0f", "#facc15",
                    "M816 504c39-156 147-257 305-311-22 175-130 284-305 311zm-12-4c-47-89-42-190 16-304 82 94 78 207-16 304z");
            default -> new CategoryTheme("General", "#15803d", "#0ea5e9",
                    "M806 494c38-152 146-245 302-279-22 167-129 270-302 279z");
        };
    }

    private String truncate(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    private String escapeXml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    private record CategoryTheme(String label, String startColor, String endColor, String path) {
    }
}
