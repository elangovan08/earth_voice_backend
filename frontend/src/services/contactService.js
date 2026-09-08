import apiClient from './apiClient';

export const contactService = {
  submit(payload) {
    return apiClient.post('/contact', payload).then(res => res.data);
  }
};
