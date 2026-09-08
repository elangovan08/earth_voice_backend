import { forwardRef } from 'react';

const variants = {
  primary: 'primary-button',
  secondary: 'secondary-button',
  ghost: 'rounded-2xl px-4 py-2 font-semibold text-[var(--text)] transition hover:bg-white/20'
};

const Button = forwardRef(function Button({ as: Component = 'button', variant = 'primary', className = '', ...props }, ref) {
  return <Component ref={ref} className={`${variants[variant]} ${className}`.trim()} {...props} />;
});

export default Button;
