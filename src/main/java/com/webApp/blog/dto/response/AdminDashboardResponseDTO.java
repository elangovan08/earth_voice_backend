package com.webApp.blog.dto.response;

import java.util.List;

public class AdminDashboardResponseDTO {

    private long userCount;
    private long postCount;
    private long commentCount;
    private long contactCount;
    private List<UserResponseDTO> recentUsers;

    public AdminDashboardResponseDTO() {
    }

    public AdminDashboardResponseDTO(long userCount, long postCount, long commentCount, long contactCount, List<UserResponseDTO> recentUsers) {
        this.userCount = userCount;
        this.postCount = postCount;
        this.commentCount = commentCount;
        this.contactCount = contactCount;
        this.recentUsers = recentUsers;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public long getPostCount() {
        return postCount;
    }

    public void setPostCount(long postCount) {
        this.postCount = postCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getContactCount() {
        return contactCount;
    }

    public void setContactCount(long contactCount) {
        this.contactCount = contactCount;
    }

    public List<UserResponseDTO> getRecentUsers() {
        return recentUsers;
    }

    public void setRecentUsers(List<UserResponseDTO> recentUsers) {
        this.recentUsers = recentUsers;
    }
}
