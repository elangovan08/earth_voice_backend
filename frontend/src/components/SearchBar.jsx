import { Search, X } from 'lucide-react';

export default function SearchBar({ value = '', onChange, onClear, suggestions = [] }) {
  return (
    <div className="relative">
      <div className="flex items-center gap-3 rounded-2xl border border-white/25 bg-[var(--card)] px-4 py-3 shadow-xl shadow-emerald-950/5 backdrop-blur-xl">
        <Search className="h-5 w-5 text-[var(--muted)]" />
        <input
          className="w-full bg-transparent text-[var(--text)] outline-none placeholder:text-[var(--muted)]"
          value={value}
          onChange={event => onChange(event.target.value)}
          placeholder="Search climate stories, authors, ideas..."
        />
        {value && (
          <button type="button" onClick={onClear} className="text-[var(--muted)] transition hover:text-[var(--text)]" aria-label="Clear search">
            <X className="h-5 w-5" />
          </button>
        )}
      </div>

      {suggestions.length > 0 && (
        <div className="glass-panel absolute left-0 right-0 top-full z-20 mt-3 overflow-hidden p-2">
          {suggestions.map(suggestion => (
            <button
              type="button"
              key={suggestion}
              onClick={() => onChange(suggestion)}
              className="block w-full rounded-xl px-3 py-2 text-left text-sm text-[var(--text)] transition hover:bg-emerald-500/10"
            >
              {suggestion}
            </button>
          ))}
        </div>
      )}
    </div>
  );
}
