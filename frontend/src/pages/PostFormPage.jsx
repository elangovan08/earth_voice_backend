import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ErrorAlert from '../components/ErrorAlert.jsx';
import FormInput from '../components/FormInput.jsx';
import LoadingState from '../components/LoadingState.jsx';
import { POST_CATEGORIES } from '../constants/appConstants';
import { useAuth } from '../context/AuthContext.jsx';
import { postService } from '../services/postService';
import { getApiError, minLength, required } from '../utils/validators';

export default function PostFormPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  const isEdit = Boolean(id);
  const [form, setForm] = useState({ title: '', content: '', category: POST_CATEGORIES[0], imageUrl: '' });
  const [imageFile, setImageFile] = useState(null);
  const [imagePreview, setImagePreview] = useState('');
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [loading, setLoading] = useState(isEdit);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    if (!isEdit) return;
    postService.getById(id)
      .then(post => {
        setForm({ title: post.title, content: post.content, category: post.category || POST_CATEGORIES[0], imageUrl: post.imageUrl || '' });
        setImagePreview(post.imageUrl || '');
      })
      .catch(err => setApiError(getApiError(err, 'Unable to load post.')))
      .finally(() => setLoading(false));
  }, [id, isEdit]);

  useEffect(() => () => {
    if (imagePreview.startsWith('blob:')) {
      URL.revokeObjectURL(imagePreview);
    }
  }, [imagePreview]);

  function update(field, value) {
    setForm(current => ({ ...current, [field]: value }));
  }

  function validate() {
    const next = {};
    if (!required(form.title)) next.title = 'Title is required.';
    if (!minLength(form.content, 10)) next.content = 'Content must be at least 10 characters.';
    if (imageFile && !imageFile.type.startsWith('image/')) next.image = 'Cover image must be an image file.';
    setErrors(next);
    return Object.keys(next).length === 0;
  }

  function handleImageChange(event) {
    const file = event.target.files?.[0];

    if (imagePreview.startsWith('blob:')) {
      URL.revokeObjectURL(imagePreview);
    }

    if (!file) {
      setImageFile(null);
      setImagePreview(form.imageUrl || '');
      return;
    }

    setImageFile(file);
    setImagePreview(URL.createObjectURL(file));
    setErrors(current => ({ ...current, image: undefined }));
  }

  function buildPostFormData(payload) {
    const formData = new FormData();
    formData.append('title', payload.title);
    formData.append('content', payload.content);
    formData.append('category', payload.category);
    formData.append('authorId', payload.authorId);
    if (payload.imageUrl) formData.append('imageUrl', payload.imageUrl);
    if (imageFile) formData.append('image', imageFile);
    return formData;
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!validate()) return;
    setSubmitting(true);
    setApiError('');
    try {
      const payload = { ...form, authorId: user.id };

      const saved = imageFile
        ? isEdit
          ? await postService.updateWithImage(id, buildPostFormData(payload))
          : await postService.createWithImage(buildPostFormData(payload))
        : isEdit
          ? await postService.update(id, payload)
          : await postService.create(payload);
      navigate(`/posts/${saved.id}`);
    } catch (err) {
      setApiError(getApiError(err, 'Unable to save post.'));
    } finally {
      setSubmitting(false);
    }
  }

  if (loading) return <LoadingState label="Loading form..." />;

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-lg-9">
          <h1 className="page-title mb-3">{isEdit ? 'Edit Post' : 'Create Post'}</h1>
          <div className="section-panel p-4">
            <ErrorAlert message={apiError} />
            <form onSubmit={handleSubmit}>
              <FormInput id="title" label="Title" value={form.title} error={errors.title} onChange={e => update('title', e.target.value)} />
              <div className="mb-3">
                <label htmlFor="category" className="form-label fw-semibold">Category</label>
                <select id="category" className="form-control" value={form.category} onChange={e => update('category', e.target.value)}>
                  {POST_CATEGORIES.map(category => <option key={category} value={category}>{category}</option>)}
                </select>
              </div>
              <FormInput id="content" label="Content" as="textarea" rows="10" value={form.content} error={errors.content} onChange={e => update('content', e.target.value)} />
              <div className="mb-3">
                <label htmlFor="image" className="form-label fw-semibold">Cover image</label>
                <input id="image" className={`form-control ${errors.image ? 'is-invalid' : ''}`} type="file" accept="image/*" onChange={handleImageChange} />
                {errors.image && <div className="invalid-feedback">{errors.image}</div>}
              </div>
              {imagePreview && (
                <div className="mb-4 overflow-hidden rounded-4 border border-success-subtle">
                  <img src={imagePreview} alt="" className="h-64 w-100 object-fit-cover" />
                </div>
              )}
              <button className="btn btn-eco" type="submit" disabled={submitting}>
                {submitting ? 'Saving...' : 'Save Post'}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
