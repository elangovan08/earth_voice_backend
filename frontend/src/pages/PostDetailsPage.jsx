import { Link, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { CalendarDays, Edit3, Share2, Tag, UserRound } from 'lucide-react';
import CommentSection from '../components/CommentSection.jsx';
import ErrorAlert from '../components/ErrorAlert.jsx';
import LikeBookmarkActions from '../components/LikeBookmarkActions.jsx';
import LoadingState from '../components/LoadingState.jsx';
import Button from '../components/ui/Button.jsx';
import { useAuth } from '../context/AuthContext.jsx';
import { useNotifications } from '../context/NotificationContext.jsx';
import { useRecentlyViewedPosts } from '../hooks/useRecentlyViewedPosts';
import { postService } from '../services/postService';
import { getApiError } from '../utils/validators';

const fallbackImage = 'https://images.unsplash.com/photo-1518531933037-91b2f5f229cc?auto=format&fit=crop&w=1400&q=80';

export default function PostDetailsPage() {
  const { id } = useParams();
  const { user, isAdmin } = useAuth();
  const { notify } = useNotifications();
  const { addPost } = useRecentlyViewedPosts();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    postService.getById(id)
      .then(data => {
        setPost(data);
        addPost({ id: data.id, title: data.title });
      })
      .catch(err => setError(getApiError(err, 'Unable to load post.')))
      .finally(() => setLoading(false));
  }, [addPost, id]);

  async function sharePost() {
    const shareData = { title: post.title, text: post.content?.slice(0, 140), url: window.location.href };
    if (navigator.share) {
      await navigator.share(shareData);
      return;
    }
    await navigator.clipboard.writeText(window.location.href);
    notify('Post link copied to clipboard');
  }

  if (loading) return <LoadingState label="Loading post..." />;

  return (
    <section className="px-6 py-12">
      <ErrorAlert message={error} />
      {post && (
        <article className="mx-auto max-w-4xl">
          <div className="glass-panel overflow-hidden">
            <img src={post.imageUrl || fallbackImage} alt={post.title} className="h-72 w-full object-cover" />
            <div className="p-6 md:p-10">
              <div className="mb-6 flex flex-wrap items-center justify-between gap-4 text-sm text-[var(--muted)]">
                <div className="flex flex-wrap gap-4">
                  <span className="inline-flex items-center gap-2"><UserRound className="h-4 w-4" />{post.author?.username || 'EarthVoice'}</span>
                  <span className="inline-flex items-center gap-2"><Tag className="h-4 w-4" />{post.category || 'General'}</span>
                  <span className="inline-flex items-center gap-2"><CalendarDays className="h-4 w-4" />{post.createdAt ? new Date(post.createdAt).toLocaleDateString() : 'Draft'}</span>
                </div>
                <div className="flex gap-2">
                  <Button type="button" variant="secondary" className="px-4 py-2" onClick={sharePost}>
                    <Share2 className="h-4 w-4" />
                    Share
                  </Button>
                  {(isAdmin || user?.id === post.author?.id) && (
                    <Button as={Link} variant="secondary" className="px-4 py-2" to={`/posts/${post.id}/edit`}>
                      <Edit3 className="h-4 w-4" />
                      Edit
                    </Button>
                  )}
                </div>
              </div>
              <h1 className="text-4xl font-black leading-tight text-[var(--heading)] md:text-6xl">{post.title}</h1>
              <div className="prose-eco mt-8 whitespace-pre-line text-lg leading-9 text-[var(--text)]">{post.content}</div>
              <div className="mt-8">
                <LikeBookmarkActions post={post} onChange={setPost} />
              </div>
            </div>
          </div>
          <CommentSection postId={post.id} initialComments={post.comments || []} />
        </article>
      )}
    </section>
  );
}
