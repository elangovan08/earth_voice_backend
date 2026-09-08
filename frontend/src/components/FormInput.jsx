export default function FormInput({ label, id, error, as = 'input', ...props }) {
  const Control = as;
  return (
    <div className="mb-3">
      <label htmlFor={id} className="form-label fw-semibold">{label}</label>
      <Control id={id} className={`form-control ${error ? 'is-invalid' : ''}`} {...props} />
      {error && <div className="invalid-feedback">{error}</div>}
    </div>
  );
}
