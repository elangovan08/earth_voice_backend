import apiClient from './apiClient';

export const commentService = {
  getByPost(postId) {
    return apiClient.get(`/posts/${postId}/comments`).then(res => res.data);
  },
  add(postId, payload) {
    return apiClient.post(`/posts/${postId}/comments`, payload).then(res => res.data);
  },
  remove(commentId) {
    return apiClient.delete(`/comments/${commentId}`);
  }
};
