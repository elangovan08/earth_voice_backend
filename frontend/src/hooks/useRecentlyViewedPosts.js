import { useCallback, useMemo, useState } from 'react';
import { STORAGE_KEYS } from '../constants/appConstants';

function readRecentlyViewed() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE_KEYS.recentlyViewed) || '[]');
  } catch {
    return [];
  }
}

export function useRecentlyViewedPosts() {
  const [items, setItems] = useState(readRecentlyViewed);

  const addPost = useCallback((post) => {
    if (!post?.id) {
      return;
    }
    setItems(currentItems => {
      const next = [post, ...currentItems.filter(item => item.id !== post.id)].slice(0, 6);
      localStorage.setItem(STORAGE_KEYS.recentlyViewed, JSON.stringify(next));
      return next;
    });
  }, []);

  return useMemo(() => ({ items, addPost }), [addPost, items]);
}
