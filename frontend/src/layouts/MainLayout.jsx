import { Outlet } from 'react-router-dom';
import Footer from '../components/Footer.jsx';
import Navbar from '../components/Navbar.jsx';
import PageTransition from '../components/PageTransition.jsx';
import { useReadingProgress } from '../hooks/useReadingProgress';

export default function MainLayout() {
  const readingProgress = useReadingProgress();

  return (
    <div className="page-shell">
      <div className="fixed left-0 top-0 z-[90] h-1 bg-gradient-to-r from-emerald-400 via-lime-400 to-teal-500 transition-all" style={{ width: `${readingProgress}%` }} />
      <Navbar />
      <main>
        <PageTransition>
          <Outlet />
        </PageTransition>
      </main>
      <Footer />
    </div>
  );
}
