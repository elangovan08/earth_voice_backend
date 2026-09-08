import { STORAGE_KEYS } from '../constants/appConstants';

export function getStoredUser() {
  const value = localStorage.getItem(STORAGE_KEYS.user);
  return value ? JSON.parse(value) : null;
}

export function getStoredToken() {
  return localStorage.getItem(STORAGE_KEYS.accessToken);
}

export function storeUser(user) {
  localStorage.setItem(STORAGE_KEYS.user, JSON.stringify(user));
}

export function storeAccessToken(token) {
  if (token) {
    localStorage.setItem(STORAGE_KEYS.accessToken, token);
    return;
  }
  localStorage.removeItem(STORAGE_KEYS.accessToken);
}

export function clearStoredUser() {
  localStorage.removeItem(STORAGE_KEYS.user);
  localStorage.removeItem(STORAGE_KEYS.accessToken);
}
