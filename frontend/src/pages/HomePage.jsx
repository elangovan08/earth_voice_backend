import { motion } from 'framer-motion';
import { ArrowRight, Heart, Leaf, MessageCircle, Users } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import PostCard from '../components/PostCard.jsx';
import LoadingState from '../components/LoadingState.jsx';
import ErrorAlert from '../components/ErrorAlert.jsx';
import { postService } from '../services/postService';
import { getApiError } from '../utils/validators';

const fallbackPosts = [
  {
    id: 'sample-water',
    title: "Save Water Before It's Too Late",
    content: 'Simple lifestyle changes can preserve thousands of liters yearly.',
    author: { username: 'Neo Green' }
  },
  {
    id: 'sample-tech',
    title: 'Green Technology in 2026',
    content: 'AI and sustainability are transforming our future together.',
    author: { username: 'Elangovan' }
  },
  {
    id: 'sample-plastic',
    title: 'Plastic-Free Villages',
    content: 'Communities are reducing waste with inspiring local movements.',
    author: { username: 'Eco Warrior' }
  }
];

export default function HomePage() {
  const [posts, setPosts] = useState(fallbackPosts);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  useEffect(() => {
    postService.getAll()
        .then(data => {
          console.log("Posts API Response:", data);

          if (Array.isArray(data)) {
            setPosts(data.length > 0 ? data.slice(0, 3) : fallbackPosts);
          } else {
            console.error("Expected array but received:", data);
            setPosts(fallbackPosts);
          }
        })
        .catch(err => {
          console.error("Posts API Error:", err);
          setError(getApiError(err, 'Unable to load latest posts. Showing sample posts.'));
          setPosts(fallbackPosts);
        })
        .finally(() => setLoading(false));
  }, []);

  return (
    <>
      <section className="relative px-6 py-24">
        <div className="mx-auto grid max-w-7xl items-center gap-12 md:grid-cols-2">
          <motion.div
            initial={{ opacity: 0, y: 40 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
          >
            <h1 className="text-5xl font-extrabold leading-tight md:text-6xl">
              Speak for the <span className="text-green-700">Planet</span>
            </h1>

            <p className="mt-6 text-lg leading-relaxed text-gray-600">
              EarthVoice is a modern eco-conscious blogging platform where people share ideas,
              awareness, and solutions for a greener future.
            </p>

            <div className="mt-8 flex flex-wrap gap-4">
              <Link to="/posts" className="primary-button">
                Explore Blogs
                <ArrowRight className="h-5 w-5" />
              </Link>

              <Link to="/about" className="secondary-button">
                Learn More
              </Link>
            </div>
          </motion.div>

          <motion.div
            initial={{ opacity: 0, scale: 0.7 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 1 }}
            className="relative"
          >
            <img
              src="https://images.unsplash.com/photo-1472214103451-9374bd1c798e?auto=format&fit=crop&w=1200&q=80"
              alt="Nature landscape"
              className="rounded-3xl shadow-2xl"
            />

            <div className="absolute -bottom-6 -left-2 rounded-2xl bg-white/70 p-6 shadow-xl backdrop-blur-xl sm:-left-6">
              <p className="text-2xl font-bold text-green-700">1200+</p>
              <p className="text-gray-600">Eco Ideas Shared</p>
            </div>
          </motion.div>
        </div>
      </section>

      <section className="py-16">
        <div className="mx-auto grid max-w-6xl gap-8 px-6 md:grid-cols-3">
          {[
            { icon: <Users className="h-10 w-10" />, value: '15K+', label: 'Community Members' },
            { icon: <MessageCircle className="h-10 w-10" />, value: '3K+', label: 'Blog Posts' },
            { icon: <Heart className="h-10 w-10" />, value: '25K+', label: 'Eco Supporters' }
          ].map((item) => (
            <motion.div
              key={item.label}
              whileHover={{ y: -10 }}
              className="rounded-3xl border border-white/20 bg-white/40 p-8 text-center shadow-xl backdrop-blur-lg"
            >
              <div className="flex justify-center text-green-700">{item.icon}</div>
              <h2 className="mt-4 text-4xl font-bold">{item.value}</h2>
              <p className="mt-2 text-gray-600">{item.label}</p>
            </motion.div>
          ))}
        </div>
      </section>

      <section className="px-6 py-20">
        <div className="mx-auto max-w-7xl">
          <div className="mb-16 text-center">
            <h2 className="text-4xl font-bold text-green-800 md:text-5xl">Trending Eco Posts</h2>
            <p className="mx-auto mt-4 max-w-2xl text-gray-600">
              Fresh ideas from the EarthVoice community.
            </p>
          </div>

          <ErrorAlert message={error} />
          {loading ? <LoadingState label="Loading posts..." /> : (
            <div className="grid gap-10 md:grid-cols-3">
              {posts.map((post, index) => (
                <PostCard post={post} index={index} key={post.id} />
              ))}
            </div>
          )}
        </div>
      </section>

      <section className="px-6 py-24">
        <div className="mx-auto max-w-5xl rounded-[40px] bg-gradient-to-r from-green-600 to-emerald-500 p-10 text-center text-white shadow-2xl md:p-16">
          <div className="flex justify-center">
            <Leaf className="h-12 w-12" />
          </div>
          <h2 className="mt-4 text-4xl font-bold md:text-5xl">Join The Green Movement</h2>
          <p className="mt-6 text-lg text-green-100">
            Start blogging, inspire others, and create awareness for a better future.
          </p>
          <Link
            to="/posts/new"
            className="mt-8 inline-flex rounded-2xl bg-white px-8 py-4 font-bold text-green-700 transition hover:scale-105"
          >
            Start Writing
          </Link>
        </div>
      </section>
    </>
  );
}
