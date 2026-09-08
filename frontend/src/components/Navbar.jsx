import { useState } from 'react';
import { motion } from 'framer-motion';
import { Leaf, Menu, Moon, Sun, X } from 'lucide-react';
import { Link, NavLink } from 'react-router-dom';
import { useTheme } from '../app/providers/ThemeProvider.jsx';
import { useAuth } from '../context/AuthContext.jsx';

const navLinkClass = ({ isActive }) =>
  `nav-link ${isActive ? 'nav-link-active' : ''}`;

export default function Navbar() {
  const { isAuthenticated, isAdmin, user, logout } = useAuth();
  const { isDark, toggleTheme } = useTheme();
  const [open, setOpen] = useState(false);

  function closeMenu() {
    setOpen(false);
  }

  return (
    <motion.nav
      initial={{ y: -24, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      className="sticky top-0 z-50 border-b border-white/20 bg-[var(--nav)] shadow-lg shadow-emerald-950/5 backdrop-blur-2xl"
    >
      <div className="mx-auto flex max-w-7xl items-center justify-between px-6 py-4">
        <Link to="/" onClick={closeMenu} className="flex items-center gap-3 text-2xl font-black tracking-tight text-[var(--brand)]">
          <span className="flex h-11 w-11 items-center justify-center rounded-2xl bg-gradient-to-br from-emerald-500 to-lime-400 text-white shadow-xl shadow-emerald-500/25">
            <Leaf className="h-6 w-6" />
          </span>
          <span>EarthVoice</span>
        </Link>

        <div className="hidden items-center gap-8 font-semibold md:flex">
          <NavLink className={navLinkClass} to="/">Home</NavLink>
          <NavLink className={navLinkClass} to="/posts">Posts</NavLink>
          <NavLink className={navLinkClass} to="/about">About</NavLink>
          <NavLink className={navLinkClass} to="/contact">Contact</NavLink>
          {isAdmin && <NavLink className={navLinkClass} to="/admin">Admin</NavLink>}
        </div>

        <div className="hidden items-center gap-3 md:flex">
          <button type="button" onClick={toggleTheme} className="icon-button" aria-label="Toggle dark mode">
            {isDark ? <Sun className="h-5 w-5" /> : <Moon className="h-5 w-5" />}
          </button>
          {isAuthenticated ? (
            <>
              <Link to="/posts/new" className="primary-button px-5 py-2">Write</Link>
              <button type="button" onClick={logout} className="secondary-button px-5 py-2">
                {user.username}
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="nav-link">Login</Link>
              <Link to="/signup" className="primary-button px-5 py-2">Join Community</Link>
            </>
          )}
        </div>

        <button type="button" className="icon-button md:hidden" onClick={() => setOpen(current => !current)} aria-label="Toggle navigation">
          {open ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
        </button>
      </div>

      {open && (
        <div className="border-t border-white/20 bg-[var(--nav)] px-6 py-4 backdrop-blur-xl md:hidden">
          <div className="flex flex-col gap-4 font-medium">
            <button type="button" onClick={toggleTheme} className="secondary-button justify-start">
              {isDark ? <Sun className="h-5 w-5" /> : <Moon className="h-5 w-5" />}
              {isDark ? 'Light mode' : 'Dark mode'}
            </button>
            <NavLink onClick={closeMenu} className={navLinkClass} to="/">Home</NavLink>
            <NavLink onClick={closeMenu} className={navLinkClass} to="/posts">Posts</NavLink>
            <NavLink onClick={closeMenu} className={navLinkClass} to="/about">About</NavLink>
            <NavLink onClick={closeMenu} className={navLinkClass} to="/contact">Contact</NavLink>
            {isAdmin && <NavLink onClick={closeMenu} className={navLinkClass} to="/admin">Admin</NavLink>}
            {isAuthenticated ? (
              <>
                <Link onClick={closeMenu} to="/posts/new" className="primary-button">Write</Link>
                <button type="button" className="secondary-button" onClick={() => { logout(); closeMenu(); }}>
                  Logout {user.username}
                </button>
              </>
            ) : (
              <>
                <Link onClick={closeMenu} to="/login" className="secondary-button">Login</Link>
                <Link onClick={closeMenu} to="/signup" className="primary-button">Join Community</Link>
              </>
            )}
          </div>
        </div>
      )}
    </motion.nav>
  );
}
