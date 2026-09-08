import { useEffect, useState } from 'react';
import { Bell, Check, CheckCheck, ChevronLeft, ChevronRight, Trash2 } from 'lucide-react';
import ErrorAlert from '../components/ErrorAlert.jsx';
import LoadingState from '../components/LoadingState.jsx';
import { adminService } from '../services/adminService';
import { getApiError } from '../utils/validators';

export default function AdminDashboardPage() {
  const [dashboard, setDashboard] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [notifications, setNotifications] = useState({ content: [], page: 0, totalPages: 0, last: true });
  const [unreadCount, setUnreadCount] = useState(0);
  const [notificationsLoading, setNotificationsLoading] = useState(true);
  const [notificationsError, setNotificationsError] = useState('');

  useEffect(() => {
    adminService.getDashboard()
      .then(setDashboard)
      .catch(err => setError(getApiError(err, 'Unable to load admin dashboard.')))
      .finally(() => setLoading(false));

    loadNotifications(0);
  }, []);

  async function loadNotifications(page) {
    setNotificationsLoading(true);
    setNotificationsError('');
    try {
      const [pageData, count] = await Promise.all([
        adminService.getNotifications({ page, size: 10, sortBy: 'createdAt', direction: 'desc' }),
        adminService.getUnreadCount()
      ]);
      setNotifications(pageData);
      setUnreadCount(count);
    } catch (err) {
      setNotificationsError(getApiError(err, 'Unable to load notifications.'));
    } finally {
      setNotificationsLoading(false);
    }
  }

  async function markAsRead(id) {
    try {
      await adminService.markNotificationAsRead(id);
      setNotifications(current => ({
        ...current,
        content: current.content.map(item => item.id === id ? { ...item, read: true } : item)
      }));
      setUnreadCount(current => Math.max(current - 1, 0));
    } catch (err) {
      setNotificationsError(getApiError(err, 'Unable to update notification.'));
    }
  }

  async function markAllAsRead() {
    try {
      await adminService.markAllNotificationsAsRead();
      setNotifications(current => ({
        ...current,
        content: current.content.map(item => ({ ...item, read: true }))
      }));
      setUnreadCount(0);
    } catch (err) {
      setNotificationsError(getApiError(err, 'Unable to update notifications.'));
    }
  }

  async function removeNotification(id) {
    const removed = notifications.content.find(item => item.id === id);
    try {
      await adminService.deleteNotification(id);
      setNotifications(current => ({
        ...current,
        content: current.content.filter(item => item.id !== id)
      }));
      if (removed && !removed.read) setUnreadCount(current => Math.max(current - 1, 0));
    } catch (err) {
      setNotificationsError(getApiError(err, 'Unable to delete notification.'));
    }
  }

  if (loading) return <LoadingState label="Loading dashboard..." />;

  return (
    <div className="container py-5">
      <h1 className="page-title mb-4">Admin Dashboard</h1>
      <ErrorAlert message={error} />
      {dashboard && (
        <>
          <div className="row g-4 mb-4">
            <StatCard label="Users" value={dashboard.userCount} />
            <StatCard label="Posts" value={dashboard.postCount} />
            <StatCard label="Comments" value={dashboard.commentCount} />
            <StatCard label="Contact Messages" value={dashboard.contactCount} />
          </div>
          <div className="section-panel p-4">
            <h2 className="h5 fw-bold mb-3">Recent Users</h2>
            <div className="table-responsive">
              <table className="table align-middle">
                <thead>
                  <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                  </tr>
                </thead>
                <tbody>
                  {dashboard.recentUsers.map(user => (
                    <tr key={user.id}>
                      <td>{user.username}</td>
                      <td>{user.email}</td>
                      <td><span className="badge text-bg-success">{user.role}</span></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
          <NotificationPanel
            notifications={notifications}
            unreadCount={unreadCount}
            loading={notificationsLoading}
            error={notificationsError}
            onMarkAsRead={markAsRead}
            onMarkAllAsRead={markAllAsRead}
            onDelete={removeNotification}
            onPageChange={loadNotifications}
          />
        </>
      )}
    </div>
  );
}

function NotificationPanel({
  notifications,
  unreadCount,
  loading,
  error,
  onMarkAsRead,
  onMarkAllAsRead,
  onDelete,
  onPageChange
}) {
  return (
    <section className="section-panel mt-4 p-4" aria-labelledby="notification-heading">
      <div className="mb-3 flex flex-wrap items-center justify-between gap-3">
        <div className="flex items-center gap-2">
          <Bell className="h-5 w-5 text-emerald-600" aria-hidden="true" />
          <h2 id="notification-heading" className="h5 mb-0 fw-bold">Notifications</h2>
          <span className="badge rounded-pill text-bg-success">{unreadCount} unread</span>
        </div>
        <button
          type="button"
          className="secondary-button"
          onClick={onMarkAllAsRead}
          disabled={loading || unreadCount === 0}
          title="Mark all notifications as read"
        >
          <CheckCheck className="h-4 w-4" aria-hidden="true" />
          Mark all read
        </button>
      </div>
      <ErrorAlert message={error} />
      {loading ? <LoadingState label="Loading notifications..." /> : (
        <>
          {notifications.content.length === 0 ? (
            <p className="text-muted-eco mb-0">No notifications yet.</p>
          ) : (
            <div className="table-responsive">
              <table className="table align-middle">
                <thead>
                  <tr>
                    <th>Status</th>
                    <th>Notification</th>
                    <th>Source</th>
                    <th>Created</th>
                    <th className="text-end">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {notifications.content.map(notification => (
                    <tr key={notification.id} className={notification.read ? '' : 'table-success'}>
                      <td>{notification.read ? 'Read' : 'Unread'}</td>
                      <td>
                        <div className="fw-semibold">{notification.title}</div>
                        <div className="small text-muted-eco">{notification.message}</div>
                      </td>
                      <td>{notification.username || 'System'}</td>
                      <td>{formatNotificationDate(notification.createdAt)}</td>
                      <td>
                        <div className="flex justify-end gap-2">
                          {!notification.read && (
                            <button
                              type="button"
                              className="icon-button"
                              onClick={() => onMarkAsRead(notification.id)}
                              aria-label="Mark notification as read"
                              title="Mark as read"
                            >
                              <Check className="h-4 w-4" aria-hidden="true" />
                            </button>
                          )}
                          <button
                            type="button"
                            className="icon-button"
                            onClick={() => onDelete(notification.id)}
                            aria-label="Delete notification"
                            title="Delete notification"
                          >
                            <Trash2 className="h-4 w-4" aria-hidden="true" />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
          <div className="mt-3 flex items-center justify-between">
            <button
              type="button"
              className="icon-button"
              onClick={() => onPageChange(Math.max(notifications.page - 1, 0))}
              disabled={loading || notifications.page === 0}
              aria-label="Previous notification page"
              title="Previous page"
            >
              <ChevronLeft className="h-5 w-5" aria-hidden="true" />
            </button>
            <span className="small text-muted-eco">
              Page {notifications.page + 1} of {Math.max(notifications.totalPages, 1)}
            </span>
            <button
              type="button"
              className="icon-button"
              onClick={() => onPageChange(notifications.page + 1)}
              disabled={loading || notifications.last}
              aria-label="Next notification page"
              title="Next page"
            >
              <ChevronRight className="h-5 w-5" aria-hidden="true" />
            </button>
          </div>
        </>
      )}
    </section>
  );
}

function formatNotificationDate(value) {
  if (!value) return 'Unknown';
  return new Date(value).toLocaleString();
}

function StatCard({ label, value }) {
  return (
    <div className="col-md-4">
      <div className="section-panel p-4">
        <div className="text-muted-eco">{label}</div>
        <div className="display-6 fw-bold">{value}</div>
      </div>
    </div>
  );
}
