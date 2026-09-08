import { createContext, useCallback, useContext, useMemo, useState } from 'react';

const NotificationContext = createContext(null);

export function NotificationProvider({ children }) {
  const [notifications, setNotifications] = useState([]);

  const notify = useCallback((message, type = 'success') => {
    const item = { id: crypto.randomUUID(), message, type };
    setNotifications(current => [item, ...current].slice(0, 4));
    window.setTimeout(() => {
      setNotifications(current => current.filter(notification => notification.id !== item.id));
    }, 3500);
  }, []);

  const dismiss = useCallback((id) => {
    setNotifications(current => current.filter(notification => notification.id !== id));
  }, []);

  const value = useMemo(() => ({ notifications, notify, dismiss }), [dismiss, notifications, notify]);

  return (
    <NotificationContext.Provider value={value}>
      {children}
      <div className="fixed right-4 top-24 z-[80] flex w-[min(24rem,calc(100vw-2rem))] flex-col gap-3">
        {notifications.map(notification => (
          <button
            type="button"
            key={notification.id}
            onClick={() => dismiss(notification.id)}
            className={`glass-panel px-4 py-3 text-left text-sm shadow-2xl ${
              notification.type === 'error' ? 'border-red-400/40 text-red-600' : 'border-emerald-400/40 text-emerald-700'
            }`}
          >
            {notification.message}
          </button>
        ))}
      </div>
    </NotificationContext.Provider>
  );
}

export function useNotifications() {
  const context = useContext(NotificationContext);
  if (!context) {
    throw new Error('useNotifications must be used inside NotificationProvider');
  }
  return context;
}
