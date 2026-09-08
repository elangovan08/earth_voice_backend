import { motion } from 'framer-motion';
import {
  Leaf,
  Globe,
  Users,
  BookOpen,
  Shield,
  Heart,
  ArrowRight,
  Flame,
  TreePine,
  Droplets,
  Swords,
  Recycle,
  AlertTriangle,
} from 'lucide-react';
import { Link } from 'react-router-dom';

const fadeUp = {
  hidden: { opacity: 0, y: 40 },
  visible: (i = 0) => ({
    opacity: 1,
    y: 0,
    transition: { duration: 0.7, delay: i * 0.15 },
  }),
};

const scaleIn = {
  hidden: { opacity: 0, scale: 0.8 },
  visible: (i = 0) => ({
    opacity: 1,
    scale: 1,
    transition: { duration: 0.6, delay: i * 0.12 },
  }),
};

const values = [
  {
    icon: <Globe className="h-8 w-8" />,
    title: 'Global Awareness',
    desc: 'We believe ideas about sustainability should reach every corner of the world, inspiring action at every level.',
  },
  {
    icon: <Users className="h-8 w-8" />,
    title: 'Community Driven',
    desc: 'EarthVoice thrives on its community of writers, activists, scientists, and everyday people who care about our planet.',
  },
  {
    icon: <BookOpen className="h-8 w-8" />,
    title: 'Open Knowledge',
    desc: 'Every article, guide, and discussion is freely accessible — knowledge about sustainability should never be locked away.',
  },
  {
    icon: <Shield className="h-8 w-8" />,
    title: 'Trust & Transparency',
    desc: 'We uphold editorial integrity and encourage evidence-based writing so readers can trust what they read.',
  },
];

const crisisCards = [
  {
    icon: <Flame className="h-7 w-7" />,
    title: 'Climate Change',
    desc: 'Rising global temperatures are causing extreme weather, melting glaciers, and threatening ecosystems worldwide. Urgent action is needed to reduce emissions and protect our future.',
  },
  {
    icon: <Swords className="h-7 w-7" />,
    title: 'War & Environmental Damage',
    desc: 'Armed conflicts destroy forests, pollute water sources, increase carbon emissions, and leave long-lasting impacts on nature that persist for decades.',
  },
  {
    icon: <AlertTriangle className="h-7 w-7" />,
    title: 'Pollution',
    desc: 'Air, water, and soil pollution continue to harm wildlife, ecosystems, and millions of people every year, causing preventable diseases and environmental degradation.',
  },
  {
    icon: <TreePine className="h-7 w-7" />,
    title: 'Deforestation',
    desc: 'Millions of trees are lost annually, reducing biodiversity and accelerating climate change. Forests are the lungs of our planet and they need our protection.',
  },
  {
    icon: <Droplets className="h-7 w-7" />,
    title: 'Plastic & Waste Crisis',
    desc: 'Plastic waste and improper disposal threaten oceans, animals, and future generations. Every year, millions of tons of plastic end up in our seas.',
  },
  {
    icon: <Recycle className="h-7 w-7" />,
    title: 'Together We Can Make a Difference',
    desc: 'Small actions — planting trees, reducing plastic use, conserving water, and spreading awareness — can create lasting positive change. Your voice matters.',
  },
];

const stats = [
  { value: '8+ Billion', label: 'People sharing one planet' },
  { value: 'Rising', label: 'Global warming threatens ecosystems' },
  { value: 'Millions', label: 'Trees lost every year' },
  { value: 'Millions of Tons', label: 'Plastic enter oceans annually' },
  { value: 'Ongoing', label: 'Conflicts damage nature' },
  { value: 'Every Action', label: 'Counts toward a better world' },
];

export default function AboutPage() {
  return (
    <>
      {/* ── Hero ───────────────────────────────────────────── */}
      <section className="relative px-6 py-24">
        <div className="mx-auto grid max-w-7xl items-center gap-12 md:grid-cols-2">
          <motion.div initial="hidden" animate="visible" variants={fadeUp}>
            <span className="mb-4 inline-flex items-center gap-2 rounded-full border border-green-200 bg-green-50 px-4 py-1.5 text-sm font-medium text-green-700">
              <Leaf className="h-4 w-4" />
              Our Story
            </span>
            <h1 className="text-4xl font-extrabold leading-tight md:text-6xl">
              Building a Greener Future,{' '}
              <span className="text-green-700">One Post at a Time</span>
            </h1>
            <p className="mt-6 text-lg leading-relaxed text-gray-600">
              EarthVoice started as a simple idea: give people a space to share knowledge,
              spark conversations, and inspire real-world action on the environmental
              issues that matter most — from pollution and climate change to sustainable
              living and green technology.
            </p>
            <div className="mt-8 flex flex-wrap gap-4">
              <Link to="/posts" className="primary-button">
                Explore Blogs
                <ArrowRight className="h-5 w-5" />
              </Link>
              <Link to="/contact" className="secondary-button">
                Get in Touch
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
              src="https://images.unsplash.com/photo-1441974231531-c6227db76b6e?auto=format&fit=crop&w=1200&q=80"
              alt="Lush green forest"
              className="rounded-3xl shadow-2xl"
            />
            <div className="absolute -bottom-6 -left-2 rounded-2xl bg-white/70 p-6 shadow-xl backdrop-blur-xl sm:-left-6">
              <p className="text-2xl font-bold text-green-700">Since 2024</p>
              <p className="text-gray-600">Fostering Eco Awareness</p>
            </div>
          </motion.div>
        </div>
      </section>

      {/* ── Mission Statement ──────────────────────────────── */}
      <section className="px-6 py-20">
        <div className="mx-auto max-w-5xl">
          <motion.div
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true, margin: '-80px' }}
            variants={fadeUp}
            className="rounded-[2.5rem] border border-white/20 bg-white/40 p-10 text-center shadow-xl backdrop-blur-lg md:p-16"
          >
            <Leaf className="mx-auto h-12 w-12 text-green-700" />
            <h2 className="mt-6 text-3xl font-bold md:text-4xl">Our Mission</h2>
            <p className="mx-auto mt-6 max-w-3xl text-lg leading-relaxed text-gray-600">
              We are on a mission to democratise environmental awareness. By providing
              a free, open, and beautiful platform for eco-conscious writing, we empower
              individuals and communities to share solutions, challenge the status quo, and
              build a collective movement toward a more sustainable world. Every voice
              matters, and every story has the power to change minds.
            </p>
          </motion.div>
        </div>
      </section>

      {/* ── Stats ──────────────────────────────────────────── */}
      <section className="py-16">
        <div className="mx-auto grid max-w-6xl gap-6 px-6 sm:grid-cols-2 lg:grid-cols-3">
          {stats.map((stat, i) => (
            <motion.div
              key={stat.label}
              initial="hidden"
              whileInView="visible"
              viewport={{ once: true }}
              custom={i}
              variants={scaleIn}
              whileHover={{ y: -10 }}
              className="rounded-3xl border border-white/20 bg-white/40 p-6 text-center shadow-xl backdrop-blur-lg"
            >
              <h3 className="text-2xl font-bold text-green-700">{stat.value}</h3>
              <p className="mt-2 text-sm text-gray-600">{stat.label}</p>
            </motion.div>
          ))}
        </div>
      </section>

      {/* ── Values ─────────────────────────────────────────── */}
      <section className="px-6 py-24">
        <div className="mx-auto max-w-7xl">
          <motion.div
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true }}
            variants={fadeUp}
            className="mb-16 text-center"
          >
            <h2 className="text-4xl font-bold text-green-800 md:text-5xl">
              What We Stand For
            </h2>
            <p className="mx-auto mt-4 max-w-2xl text-gray-600">
              The principles that guide everything we build and every story we publish.
            </p>
          </motion.div>

          <div className="grid gap-8 sm:grid-cols-2 lg:grid-cols-4">
            {values.map((v, i) => (
              <motion.div
                key={v.title}
                initial="hidden"
                whileInView="visible"
                viewport={{ once: true }}
                custom={i}
                variants={fadeUp}
                whileHover={{ y: -8 }}
                className="glass-panel rounded-3xl p-8 transition-shadow hover:shadow-2xl"
              >
                <div className="flex h-14 w-14 items-center justify-center rounded-2xl bg-green-100 text-green-700">
                  {v.icon}
                </div>
                <h3 className="mt-5 text-xl font-bold">{v.title}</h3>
                <p className="mt-3 leading-relaxed text-gray-600">{v.desc}</p>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* ── Why This Website ──────────────────────────────── */}
      <section className="px-6 py-24">
        <div className="mx-auto max-w-7xl">
          <motion.div
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true }}
            variants={fadeUp}
            className="mb-16 text-center"
          >
            <h2 className="text-4xl font-bold text-green-800 md:text-5xl">
              Why This Website Exists
            </h2>
            <p className="mx-auto mt-4 max-w-2xl text-gray-600">
              Our planet faces unprecedented challenges. These are the crises driving our mission.
            </p>
          </motion.div>

          <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
            {crisisCards.map((card, i) => (
              <motion.div
                key={card.title}
                initial="hidden"
                whileInView="visible"
                viewport={{ once: true }}
                custom={i}
                variants={fadeUp}
                whileHover={{ y: -6 }}
                className="glass-panel rounded-3xl p-7 transition-shadow hover:shadow-2xl"
              >
                <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-green-600 text-white">
                  {card.icon}
                </div>
                <h3 className="mt-4 text-lg font-bold">{card.title}</h3>
                <p className="mt-2 text-gray-600">{card.desc}</p>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      {/* ── Our Planet Needs Us ───────────────────────────── */}
      <section className="px-6 py-20">
        <div className="mx-auto max-w-5xl">
          <motion.div
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true }}
            variants={fadeUp}
            className="glass-panel rounded-[2.5rem] p-10 md:p-14"
          >
            <h2 className="text-center text-3xl font-bold md:text-4xl">
              🌍 Our Planet Needs Us
            </h2>
            <p className="mx-auto mt-6 max-w-3xl text-center text-lg leading-relaxed text-gray-600">
              Every forest cleared, every river polluted, every ton of carbon emitted — it all adds up.
              But so does every tree planted, every piece of plastic refused, and every conversation started.
              The challenges are massive, but together, our collective actions can turn the tide.
              This platform exists because we believe awareness is the first step toward change.
            </p>
            <div className="mt-8 flex flex-wrap justify-center gap-6 text-sm text-gray-500">
              <span className="flex items-center gap-2"><Leaf className="h-4 w-4 text-green-600" /> Awareness</span>
              <span className="flex items-center gap-2"><Users className="h-4 w-4 text-green-600" /> Community</span>
              <span className="flex items-center gap-2"><Globe className="h-4 w-4 text-green-600" /> Action</span>
              <span className="flex items-center gap-2"><Heart className="h-4 w-4 text-green-600" /> Hope</span>
            </div>
          </motion.div>
        </div>
      </section>

      {/* ── CTA ────────────────────────────────────────────── */}
      <section className="px-6 py-24">
        <div className="mx-auto max-w-5xl rounded-[40px] bg-gradient-to-r from-green-600 to-emerald-500 p-10 text-center text-white shadow-2xl md:p-16">
          <div className="flex justify-center">
            <Leaf className="h-12 w-12" />
          </div>
          <h2 className="mt-4 text-4xl font-bold md:text-5xl">Ready to Make a Difference?</h2>
          <p className="mt-6 text-lg text-green-100">
            Join thousands of eco-conscious writers and readers. Start sharing your
            ideas and help build a more sustainable future.
          </p>
          <div className="mt-8 flex flex-wrap justify-center gap-4">
            <Link
              to="/signup"
              className="inline-flex rounded-2xl bg-white px-8 py-4 font-bold text-green-700 shadow-lg transition hover:scale-105"
            >
              Join Community
            </Link>
            <Link
              to="/posts/new"
              className="inline-flex rounded-2xl border border-white/40 px-8 py-4 font-bold text-white transition hover:bg-white/10"
            >
              Start Writing
            </Link>
          </div>
        </div>
      </section>
    </>
  );
}
