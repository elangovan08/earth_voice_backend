import { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import ErrorAlert from '../components/ErrorAlert.jsx';
import FormInput from '../components/FormInput.jsx';
import { useAuth } from '../context/AuthContext.jsx';
import { getApiError, required } from '../utils/validators';

export default function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [form, setForm] = useState({ username: '', password: '' });
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  function validate() {
    const next = {};
    if (!required(form.username)) next.username = 'Username is required.';
    if (!required(form.password)) next.password = 'Password is required.';
    setErrors(next);
    return Object.keys(next).length === 0;
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!validate()) return;
    setSubmitting(true);
    setApiError('');
    try {
      await login(form);
      navigate(location.state?.from?.pathname || '/posts', { replace: true });
    } catch (err) {
      setApiError(getApiError(err, 'Login failed.'));
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-7 col-lg-5">
          <div className="section-panel p-4">
            <h1 className="h3 fw-bold mb-3">Login</h1>
            <ErrorAlert message={apiError} />
            <form onSubmit={handleSubmit}>
              <FormInput id="username" label="Username" value={form.username} error={errors.username} onChange={e => setForm({ ...form, username: e.target.value })} />
              <FormInput id="password" label="Password" type="password" value={form.password} error={errors.password} onChange={e => setForm({ ...form, password: e.target.value })} />
              <button className="btn btn-eco w-100" type="submit" disabled={submitting}>
                {submitting ? 'Signing in...' : 'Login'}
              </button>
            </form>
            <p className="text-muted-eco mt-3 mb-0">New here? <Link to="/signup">Create an account</Link></p>
          </div>
        </div>
      </div>
    </div>
  );
}
