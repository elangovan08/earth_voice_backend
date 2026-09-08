export function isEmail(value) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
}

export function required(value) {
  return value !== undefined && value !== null && String(value).trim().length > 0;
}

export function minLength(value, length) {
  return String(value || '').trim().length >= length;
}

export function getApiError(error, fallback = 'Something went wrong. Please try again.') {
  const data = error?.response?.data;
  if (data?.validationErrors) {
    return Object.values(data.validationErrors).join(' ');
  }
  return data?.message || fallback;
}
