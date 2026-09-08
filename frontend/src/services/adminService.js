import apiClient from './apiClient';

export const adminService = {
  getDashboard() {
    return apiClient.get('/admin/dashboard').then(res => res.data);
  },
  getNotifications(params = {}) {
    return apiClient.get('/admin/notifications', { params }).then(res => res.data.data);
  },
  getUnreadNotifications(params = {}) {
    return apiClient.get('/admin/notifications/unread', { params }).then(res => res.data.data);
  },
  getUnreadCount() {
    return apiClient.get('/admin/notifications/unread/count').then(res => res.data.data);
  },
  markNotificationAsRead(id) {
    return apiClient.put(`/admin/notifications/${id}/read`).then(res => res.data);
  },
  markAllNotificationsAsRead() {
    return apiClient.put('/admin/notifications/read-all').then(res => res.data);
  },
  deleteNotification(id) {
    return apiClient.delete(`/admin/notifications/${id}`).then(res => res.data);
  }
};
