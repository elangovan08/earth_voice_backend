import { Link } from 'react-router-dom';
import { Leaf } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="mt-12 bg-green-950 py-10 text-white">
      <div className="mx-auto flex max-w-7xl flex-col items-center justify-between gap-6 px-6 md:flex-row">
        <div>
          <h3 className="flex items-center gap-2 text-3xl font-bold">
            <Leaf className="h-8 w-8" />
            EarthVoice
          </h3>
          <p className="mt-2 text-green-200">Protect Nature. Share Ideas. Inspire Change.</p>
        </div>

        <div className="flex gap-6">
          <Link to="/posts" className="transition hover:text-green-300">Posts</Link>
          <Link to="/about" className="transition hover:text-green-300">About</Link>
          <Link to="/contact" className="transition hover:text-green-300">Contact</Link>
        </div>
      </div>
    </footer>
  );
}
