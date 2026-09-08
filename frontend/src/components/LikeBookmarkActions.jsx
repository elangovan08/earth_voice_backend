import { useState } from 'react';
import { useAuth } from '../context/AuthContext.jsx';
import { postService } from '../services/postService';
import { getApiError } from '../utils/validators';

export default function LikeBookmarkActions({ post, onChange }) {
  const { user, isAuthenticated } = useAuth();
  const [loading, setLoading] = useState('');
  const [error, setError] = useState('');

  async function run(action) {
    if (!isAuthenticated) {
      setError('Login is required for this action.');
      return;
    }
    setLoading(action);
    setError('');
    try {
      const updated = action === 'like'
        ? await postService.like(post.id)
        : await postService.bookmark(post.id);
      onChange(updated);
    } catch (err) {
      setError(getApiError(err));
    } finally {
      setLoading('');
    }
  }

  return (
    <div>
      <div className="d-flex flex-wrap gap-2">
        <button className="btn btn-outline-eco" type="button" disabled={loading === 'like'} onClick={() => run('like')}>
          {loading === 'like' ? 'Liking...' : `Like (${post.likeCount || 0})`}
        </button>
        <button className="btn btn-outline-secondary" type="button" disabled={loading === 'bookmark'} onClick={() => run('bookmark')}>
          {loading === 'bookmark' ? 'Saving...' : `Bookmark (${post.bookmarkCount || 0})`}
        </button>
      </div>
      {error && <div className="text-danger small mt-2">{error}</div>}
    </div>
  );
}
