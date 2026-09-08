import { Component } from 'react';
import { AlertTriangle, RotateCcw } from 'lucide-react';

export default class ErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError() {
    return { hasError: true };
  }

  render() {
    if (!this.state.hasError) {
      return this.props.children;
    }

    return (
      <main className="flex min-h-screen items-center justify-center bg-[var(--surface)] px-6 text-[var(--text)]">
        <section className="glass-panel max-w-xl p-8 text-center">
          <div className="mx-auto flex h-14 w-14 items-center justify-center rounded-2xl bg-red-500/10 text-red-500">
            <AlertTriangle className="h-7 w-7" />
          </div>
          <h1 className="mt-6 text-3xl font-bold">Something broke</h1>
          <p className="mt-3 text-[var(--muted)]">
            The app hit an unexpected UI error. Refreshing the page will restore a clean state.
          </p>
          <button
            type="button"
            onClick={() => window.location.reload()}
            className="primary-button mt-6"
          >
            <RotateCcw className="h-4 w-4" />
            Refresh
          </button>
        </section>
      </main>
    );
  }
}
