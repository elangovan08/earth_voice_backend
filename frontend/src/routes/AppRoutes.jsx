import { lazy, Suspense } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import { AnimatePresence } from 'framer-motion';
import MainLayout from '../layouts/MainLayout.jsx';
import ProtectedRoute from './ProtectedRoute.jsx';
import LoadingState from '../components/LoadingState.jsx';

const HomePage = lazy(() => import('../pages/HomePage.jsx'));
const AboutPage = lazy(() => import('../pages/AboutPage.jsx'));
const ContactPage = lazy(() => import('../pages/ContactPage.jsx'));
const PostsPage = lazy(() => import('../pages/PostsPage.jsx'));
const PostDetailsPage = lazy(() => import('../pages/PostDetailsPage.jsx'));
const PostFormPage = lazy(() => import('../pages/PostFormPage.jsx'));
const LoginPage = lazy(() => import('../pages/LoginPage.jsx'));
const SignupPage = lazy(() => import('../pages/SignupPage.jsx'));
const AdminDashboardPage = lazy(() => import('../pages/AdminDashboardPage.jsx'));
const NotFoundPage = lazy(() => import('../pages/NotFoundPage.jsx'));

export default function AppRoutes() {
  const location = useLocation();

  return (
    <Suspense fallback={<LoadingState label="Preparing EarthVoice..." />}>
      <AnimatePresence mode="wait">
        <Routes location={location} key={location.pathname}>
          <Route element={<MainLayout />}>
            <Route index element={<HomePage />} />
            <Route path="/about" element={<AboutPage />} />
            <Route path="/contact" element={<ContactPage />} />
            <Route path="/posts" element={<PostsPage />} />
            <Route path="/posts/:id" element={<PostDetailsPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />

            <Route element={<ProtectedRoute />}>
              <Route path="/posts/new" element={<PostFormPage />} />
              <Route path="/posts/:id/edit" element={<PostFormPage />} />
            </Route>

            <Route element={<ProtectedRoute adminOnly />}>
              <Route path="/admin" element={<AdminDashboardPage />} />
            </Route>

            <Route path="*" element={<NotFoundPage />} />
          </Route>
        </Routes>
      </AnimatePresence>
    </Suspense>
  );
}
