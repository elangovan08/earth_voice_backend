import { useState } from 'react';
import ErrorAlert from '../components/ErrorAlert.jsx';
import FormInput from '../components/FormInput.jsx';
import { contactService } from '../services/contactService';
import { getApiError, isEmail, minLength, required } from '../utils/validators';

export default function ContactPage() {
  const [form, setForm] = useState({ name: '', email: '', subject: '', message: '' });
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [success, setSuccess] = useState('');
  const [submitting, setSubmitting] = useState(false);

  function update(field, value) {
    setForm(current => ({ ...current, [field]: value }));
  }

  function validate() {
    const next = {};
    if (!required(form.name)) next.name = 'Name is required.';
    if (!isEmail(form.email)) next.email = 'Valid email is required.';
    if (!required(form.subject)) next.subject = 'Subject is required.';
    if (!minLength(form.message, 10)) next.message = 'Message must be at least 10 characters.';
    setErrors(next);
    return Object.keys(next).length === 0;
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!validate()) return;
    setSubmitting(true);
    setApiError('');
    setSuccess('');
    try {
      const response = await contactService.submit(form);
      setSuccess(response.message);
      setForm({ name: '', email: '', subject: '', message: '' });
    } catch (err) {
      setApiError(getApiError(err, 'Unable to submit contact form.'));
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-lg-8">
          <h1 className="page-title mb-3">Contact</h1>
          <p className="text-muted-eco">Send feedback, collaboration ideas, or support requests.</p>
          <div className="section-panel p-4">
            <ErrorAlert message={apiError} />
            {success && <div className="alert alert-success">{success}</div>}
            <form onSubmit={handleSubmit}>
              <FormInput id="name" label="Name" value={form.name} error={errors.name} onChange={e => update('name', e.target.value)} />
              <FormInput id="email" label="Email" value={form.email} error={errors.email} onChange={e => update('email', e.target.value)} />
              <FormInput id="subject" label="Subject" value={form.subject} error={errors.subject} onChange={e => update('subject', e.target.value)} />
              <FormInput id="message" label="Message" as="textarea" rows="5" value={form.message} error={errors.message} onChange={e => update('message', e.target.value)} />
              <button className="btn btn-eco" type="submit" disabled={submitting}>
                {submitting ? 'Sending...' : 'Send Message'}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
