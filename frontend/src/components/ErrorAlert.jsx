export default function ErrorAlert({ message }) {
  if (!message) {
    return null;
  }
  return (
    <div className="mb-4 rounded-2xl border border-red-100 bg-red-50 px-4 py-3 text-red-700">
      {message}
    </div>
  );
}
