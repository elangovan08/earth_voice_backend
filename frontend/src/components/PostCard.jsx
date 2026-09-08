import { memo } from 'react';
import { motion } from 'framer-motion';
import { Bookmark, Clock, ExternalLink, Heart } from 'lucide-react';
import { Link } from 'react-router-dom';

const images = [
  'https://images.unsplash.com/photo-1472214103451-9374bd1c798e?auto=format&fit=crop&w=900&q=80',
  'https://images.unsplash.com/photo-1466611653911-95081537e5b7?auto=format&fit=crop&w=900&q=80',
  'https://images.unsplash.com/photo-1497436072909-60f360e1d4b1?auto=format&fit=crop&w=900&q=80'
];

function PostCard({ post, index = 0 }) {
  const postUrl = typeof post.id === 'number' ? `/posts/${post.id}` : '/posts';
  const readingMinutes = Math.max(2, Math.ceil((post.content || '').split(/\s+/).length / 180));
  const imageUrl = post.imageUrl || images[index % images.length];

  return (
    <motion.article
      whileHover={{ y: -8, scale: 1.01 }}
      transition={{ type: 'spring', stiffness: 260, damping: 22 }}
      className="group overflow-hidden rounded-[2rem] border border-white/20 bg-[var(--card)] shadow-2xl shadow-emerald-950/10 backdrop-blur-2xl"
    >
      <div className="relative overflow-hidden">
        <img
          src={imageUrl}
          alt={post.title}
          loading="lazy"
          className="h-56 w-full object-cover transition duration-700 group-hover:scale-105"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-emerald-950/45 to-transparent" />
        <div className="absolute bottom-4 left-4 flex gap-2">
          <span className="rounded-full bg-white/80 px-3 py-1 text-xs font-bold text-emerald-800 backdrop-blur">
            {post.category || 'General'}
          </span>
          <span className="flex items-center gap-1 rounded-full bg-black/35 px-3 py-1 text-xs font-semibold text-white backdrop-blur">
            <Clock className="h-3 w-3" />
            {readingMinutes} min
          </span>
        </div>
      </div>

      <div className="p-6">
        <h3 className="text-2xl font-black leading-tight text-[var(--heading)]">
          <Link to={postUrl} className="transition hover:text-green-600">
            {post.title}
          </Link>
        </h3>

        <p className="mt-3 line-clamp-3 text-[var(--muted)]">{post.content}</p>

        <div className="mt-5 flex items-center justify-between gap-4">
          <span className="text-sm text-[var(--muted)]">By {post.author?.username || 'EarthVoice'}</span>
          <div className="flex items-center gap-3 text-sm text-[var(--muted)]">
            <span className="inline-flex items-center gap-1"><Heart className="h-4 w-4" />{post.likeCount || 0}</span>
            <span className="inline-flex items-center gap-1"><Bookmark className="h-4 w-4" />{post.bookmarkCount || 0}</span>
          </div>
        </div>

        <div className="mt-5">
          <Link to={postUrl} className="inline-flex items-center gap-2 font-bold text-[var(--brand)] transition hover:gap-3">
            Read article
            <ExternalLink className="h-4 w-4" />
          </Link>
        </div>
      </div>
    </motion.article>
  );
}

export default memo(PostCard);
