import { BrowserRouter } from 'react-router-dom';
import ErrorBoundary from './app/ErrorBoundary.jsx';
import { ThemeProvider } from './app/providers/ThemeProvider.jsx';
import { AuthProvider } from './context/AuthContext.jsx';
import { NotificationProvider } from './context/NotificationContext.jsx';
import AppRoutes from './routes/AppRoutes.jsx';

export default function App() {
  return (
    <ErrorBoundary>
      <BrowserRouter>
        <ThemeProvider>
          <NotificationProvider>
            <AuthProvider>
              <AppRoutes />
            </AuthProvider>
          </NotificationProvider>
        </ThemeProvider>
      </BrowserRouter>
    </ErrorBoundary>
  );
}
