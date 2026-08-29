import React from 'react';

/**
 * Reusable Button component matching the Edgento Design System.
 */
export const Button = ({
  children,
  onClick,
  variant = 'primary', // primary, secondary, ghost
  size = 'md', // sm, md, lg
  className = '',
  disabled = false,
  type = 'button',
  ...props
}) => {
  const baseClass = 'btn';
  const variantClass = `btn--${variant}`;
  const sizeClass = size !== 'md' ? `btn--${size}` : '';
  const combinedClasses = [baseClass, variantClass, sizeClass, className].filter(Boolean).join(' ');

  return (
    <button
      type={type}
      className={combinedClasses}
      onClick={onClick}
      disabled={disabled}
      {...props}
    >
      {children}
    </button>
  );
};

export default Button;
