import { useCallback, useEffect, useMemo, useState } from 'react';
import { Flame, Layers3, Sparkles } from 'lucide-react';
import PostCard from '../components/PostCard.jsx';
import SearchBar from '../components/SearchBar.jsx';
import ErrorAlert from '../components/ErrorAlert.jsx';
import Skeleton from '../components/ui/Skeleton.jsx';
import { POST_CATEGORIES, POST_SORT_OPTIONS } from '../constants/appConstants';
import { useDebounce } from '../hooks/useDebounce';
import { useRecentlyViewedPosts } from '../hooks/useRecentlyViewedPosts';
import { postService } from '../services/postService';
import { getApiError } from '../utils/validators';

const categoryFilters = ['All', ...POST_CATEGORIES];

export default function PostsPage() {
  const [posts, setPosts] = useState([]);
  const [keyword, setKeyword] = useState('');
  const [sort, setSort] = useState(POST_SORT_OPTIONS[0].value);
  const [category, setCategory] = useState('All');
  const [visibleCount, setVisibleCount] = useState(9);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const debouncedKeyword = useDebounce(keyword, 320);
  const { items: recentlyViewed } = useRecentlyViewedPosts();

  const loadPosts = useCallback(async () => {
    setLoading(true);
    setError('');
    try {
      const data = debouncedKeyword ? await postService.search(debouncedKeyword) : await postService.getAll();
      if (Array.isArray(data)) {
        setPosts(data);
      } else {
        console.error('Expected posts array but received:', data);
        setPosts([]);
      }
      setVisibleCount(9);
    } catch (err) {
      setError(getApiError(err, 'Unable to load posts.'));
    } finally {
      setLoading(false);
    }
  }, [debouncedKeyword]);

  useEffect(() => {
    loadPosts();
  }, [loadPosts]);

  const sortedPosts = useMemo(() => {
    const next = [...posts];
    if (sort === 'likeCount,desc') {
      next.sort((a, b) => (b.likeCount || 0) - (a.likeCount || 0));
    } else if (sort === 'createdAt,asc') {
      next.sort((a, b) => new Date(a.createdAt || 0) - new Date(b.createdAt || 0));
    } else {
      next.sort((a, b) => new Date(b.createdAt || 0) - new Date(a.createdAt || 0));
    }
    return category === 'All' ? next : next.filter(post => (post.category || 'General').toLowerCase() === category.toLowerCase());
  }, [category, posts, sort]);

  const visiblePosts = useMemo(() => sortedPosts.slice(0, visibleCount), [sortedPosts, visibleCount]);
  const trendingPosts = useMemo(() => [...posts].sort((a, b) => (b.likeCount || 0) - (a.likeCount || 0)).slice(0, 4), [posts]);
  const suggestions = useMemo(() => {
    if (!keyword || keyword.length < 2) {
      return [];
    }
    return posts
      .map(post => post.title)
      .filter(title => title?.toLowerCase().includes(keyword.toLowerCase()))
      .slice(0, 5);
  }, [keyword, posts]);

  return (
    <section className="px-6 py-12">
      <div className="mx-auto max-w-7xl">
        <div className="grid gap-8 lg:grid-cols-[minmax(0,1fr)_21rem]">
          <div>
            <div className="mb-8 flex flex-col gap-5 lg:flex-row lg:items-end lg:justify-between">
              <div>
                <span className="inline-flex items-center gap-2 rounded-full bg-emerald-500/10 px-4 py-2 text-sm font-bold text-[var(--brand)]">
                  <Sparkles className="h-4 w-4" />
                  Explore ideas
                </span>
                <h1 className="mt-4 text-4xl font-black tracking-tight text-[var(--heading)] md:text-6xl">Eco intelligence feed</h1>
                <p className="mt-3 max-w-2xl text-[var(--muted)]">
                  Search, filter, and sort community climate stories with a faster editorial browsing experience.
                </p>
              </div>

              <div className="min-w-60">
                <label className="mb-2 block text-sm font-bold text-[var(--muted)]">Sort by</label>
                <select value={sort} onChange={event => setSort(event.target.value)} className="form-field">
                  {POST_SORT_OPTIONS.map(option => <option key={option.value} value={option.value}>{option.label}</option>)}
                </select>
              </div>
            </div>

            <div className="mb-6">
              <SearchBar value={keyword} onChange={setKeyword} onClear={() => setKeyword('')} suggestions={suggestions} />
            </div>

            <div className="mb-8 flex flex-wrap gap-3">
              {categoryFilters.map(item => (
                <button
                  type="button"
                  key={item}
                  onClick={() => setCategory(item)}
                  className={`rounded-full px-4 py-2 text-sm font-bold transition ${
                    category === item ? 'bg-[var(--brand)] text-white shadow-lg shadow-emerald-500/25' : 'bg-[var(--card)] text-[var(--muted)] hover:text-[var(--text)]'
                  }`}
                >
                  {item}
                </button>
              ))}
            </div>

            <ErrorAlert message={error} />

            {loading ? (
              <div className="grid gap-8 md:grid-cols-2 xl:grid-cols-3">
                {Array.from({ length: 6 }).map((_, index) => (
                  <div className="glass-panel overflow-hidden" key={index}>
                    <Skeleton className="h-56 rounded-none" />
                    <div className="space-y-4 p-6">
                      <Skeleton className="h-7 w-4/5" />
                      <Skeleton className="h-4 w-full" />
                      <Skeleton className="h-4 w-2/3" />
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <>
                <div className="grid gap-8 md:grid-cols-2 xl:grid-cols-3">
                  {visiblePosts.length === 0 && (
                    <div className="glass-panel col-span-full p-8 text-[var(--muted)]">No posts matched this search.</div>
                  )}
                  {visiblePosts.map((post, index) => <PostCard post={post} index={index} key={post.id} />)}
                </div>

                {visibleCount < sortedPosts.length && (
                  <div className="mt-10 text-center">
                    <button type="button" className="secondary-button" onClick={() => setVisibleCount(count => count + 6)}>
                      Load more posts
                    </button>
                  </div>
                )}
              </>
            )}
          </div>

          <aside className="space-y-6 lg:sticky lg:top-28 lg:self-start">
            <section className="glass-panel p-6">
              <h2 className="flex items-center gap-2 text-xl font-black text-[var(--heading)]">
                <Flame className="h-5 w-5 text-orange-500" />
                Trending
              </h2>
              <div className="mt-5 space-y-4">
                {trendingPosts.map(post => (
                  <div key={post.id} className="rounded-2xl bg-white/10 p-4">
                    <p className="font-bold text-[var(--text)]">{post.title}</p>
                    <p className="mt-1 text-sm text-[var(--muted)]">{post.likeCount || 0} likes</p>
                  </div>
                ))}
              </div>
            </section>

            <section className="glass-panel p-6">
              <h2 className="flex items-center gap-2 text-xl font-black text-[var(--heading)]">
                <Layers3 className="h-5 w-5 text-emerald-500" />
                Recently viewed
              </h2>
              <div className="mt-5 space-y-3 text-sm text-[var(--muted)]">
                {recentlyViewed.length === 0 ? 'Open a post to build your reading history.' : recentlyViewed.map(post => (
                  <p key={post.id} className="font-semibold text-[var(--text)]">{post.title}</p>
                ))}
              </div>
            </section>
          </aside>
        </div>
      </div>
    </section>
  );
}
