import { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext.jsx';
import { commentService } from '../services/commentService';
import { getApiError, required } from '../utils/validators';
import ErrorAlert from './ErrorAlert.jsx';
import LoadingState from './LoadingState.jsx';

export default function CommentSection({ postId, initialComments = [] }) {
  const { user, isAuthenticated } = useAuth();
  const [comments, setComments] = useState(initialComments);
  const [content, setContent] = useState('');
  const [loading, setLoading] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    let active = true;
    setLoading(true);
    commentService.getByPost(postId)
      .then(data => active && setComments(data))
      .catch(err => active && setError(getApiError(err, 'Unable to load comments.')))
      .finally(() => active && setLoading(false));
    return () => {
      active = false;
    };
  }, [postId]);

  async function handleSubmit(event) {
    event.preventDefault();
    if (!required(content)) {
      setError('Comment content is required.');
      return;
    }
    if (!isAuthenticated) {
      setError('Login is required to comment.');
      return;
    }
    setSubmitting(true);
    setError('');
    try {
      const saved = await commentService.add(postId, { content, userId: user.id });
      setComments(current => [saved, ...current]);
      setContent('');
    } catch (err) {
      setError(getApiError(err, 'Unable to add comment.'));
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <section className="section-panel p-4 mt-4">
      <h2 className="h4 fw-bold mb-3">Comments</h2>
      <ErrorAlert message={error} />
      <form onSubmit={handleSubmit} className="mb-4">
        <textarea
          className="form-control mb-2"
          rows="3"
          value={content}
          onChange={event => setContent(event.target.value)}
          placeholder={isAuthenticated ? 'Write a comment' : 'Login to write a comment'}
        />
        <button className="btn btn-eco" type="submit" disabled={submitting}>
          {submitting ? 'Posting...' : 'Post Comment'}
        </button>
      </form>
      {loading ? <LoadingState label="Loading comments..." /> : (
        <div className="vstack gap-3">
          {comments.length === 0 && <p className="text-muted-eco mb-0">No comments yet.</p>}
          {comments.map(comment => (
            <div className="border rounded-2 p-3 bg-light" key={comment.id}>
              <div className="d-flex justify-content-between mb-1">
                <strong>{comment.user?.username || 'Reader'}</strong>
                <small className="text-muted-eco">{comment.createdAt ? new Date(comment.createdAt).toLocaleString() : ''}</small>
              </div>
              <p className="mb-0">{comment.content}</p>
            </div>
          ))}
        </div>
      )}
    </section>
  );
}
