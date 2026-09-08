import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import ErrorAlert from '../components/ErrorAlert.jsx';
import FormInput from '../components/FormInput.jsx';
import { useAuth } from '../context/AuthContext.jsx';
import { getApiError, isEmail, minLength, required } from '../utils/validators';

const DEFAULT_SIGNUP_ROLE = 'POSTER';

export default function SignupPage() {
  const { signup } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ name: '', username: '', email: '', password: '' });
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  function validate() {
    const next = {};
    if (!required(form.username)) next.username = 'Username is required.';
    if (!isEmail(form.email)) next.email = 'Valid email is required.';
    if (!minLength(form.password, 6)) next.password = 'Password must be at least 6 characters.';
    setErrors(next);
    return Object.keys(next).length === 0;
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!validate()) return;
    setSubmitting(true);
    setApiError('');
    try {
      await signup({ ...form, role: DEFAULT_SIGNUP_ROLE });
      navigate('/posts');
    } catch (err) {
      setApiError(getApiError(err, 'Signup failed.'));
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <div className="section-panel p-4">
            <h1 className="h3 fw-bold mb-2">Join the EarthVoice Community <span aria-hidden="true">&#127793;</span></h1>
            <p className="text-muted-eco mb-4">Create your account and start sharing eco-friendly ideas with the world.</p>
            <ErrorAlert message={apiError} />
            <form onSubmit={handleSubmit}>
              <FormInput id="name" label="Name" value={form.name} error={errors.name} onChange={e => setForm({ ...form, name: e.target.value })} />
              <FormInput id="username" label="Username" value={form.username} error={errors.username} onChange={e => setForm({ ...form, username: e.target.value })} />
              <FormInput id="email" label="Email" value={form.email} error={errors.email} onChange={e => setForm({ ...form, email: e.target.value })} />
              <FormInput id="password" label="Password" type="password" value={form.password} error={errors.password} onChange={e => setForm({ ...form, password: e.target.value })} />
              <button className="btn btn-eco w-100" type="submit" disabled={submitting}>
                {submitting ? 'Joining...' : 'Join Community'}
              </button>
            </form>
            <p className="text-muted-eco mt-3 mb-0">Already have an account? <Link to="/login">Login</Link></p>
          </div>
        </div>
      </div>
    </div>
  );
}
