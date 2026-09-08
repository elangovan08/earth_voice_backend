import { useEffect, useState } from 'react';

export function useReadingProgress() {
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    function updateProgress() {
      const scrollTop = window.scrollY;
      const scrollable = document.documentElement.scrollHeight - window.innerHeight;
      setProgress(scrollable <= 0 ? 0 : Math.min(100, Math.round((scrollTop / scrollable) * 100)));
    }

    updateProgress();
    window.addEventListener('scroll', updateProgress, { passive: true });
    return () => window.removeEventListener('scroll', updateProgress);
  }, []);

  return progress;
}
