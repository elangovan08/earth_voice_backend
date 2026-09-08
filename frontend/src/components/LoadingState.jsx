export default function LoadingState({ label = 'Loading...' }) {
  return (
    <div className="flex items-center justify-center py-16">
      <div className="mr-3 h-8 w-8 animate-spin rounded-full border-4 border-green-200 border-t-green-700" />
      <span className="font-medium text-[var(--muted)]">{label}</span>
    </div>
  );
}
