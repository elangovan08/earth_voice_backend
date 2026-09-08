import apiClient from './apiClient';

export const authService = {
  signup(payload) {
    return apiClient.post('/auth/signup', payload).then(res => res.data);
  },
  login(payload) {
    return apiClient.post('/auth/login', payload).then(res => res.data);
  }
};
