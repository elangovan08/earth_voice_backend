import { Link } from 'react-router-dom';

export default function NotFoundPage() {
  return (
    <div className="container py-5 text-center">
      <h1 className="page-title">Page Not Found</h1>
      <p className="text-muted-eco">The page you requested does not exist.</p>
      <Link className="btn btn-eco" to="/">Go Home</Link>
    </div>
  );
}
