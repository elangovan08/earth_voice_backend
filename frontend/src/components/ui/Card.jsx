import { motion } from 'framer-motion';

export default function Card({ children, className = '', hover = false }) {
  const Component = hover ? motion.article : 'article';
  const motionProps = hover ? { whileHover: { y: -8, scale: 1.01 }, transition: { type: 'spring', stiffness: 260, damping: 24 } } : {};
  return (
    <Component className={`glass-panel ${className}`.trim()} {...motionProps}>
      {children}
    </Component>
  );
}
