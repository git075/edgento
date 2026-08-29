import React, { forwardRef } from 'react';

/**
 * Reusable Input component matching the Edgento Design System.
 */
export const Input = forwardRef(({
  className = '',
  type = 'text',
  ...props
}, ref) => {
  return (
    <input
      ref={ref}
      type={type}
      className={`input ${className}`}
      {...props}
    />
  );
});

Input.displayName = 'Input';

export default Input;
