/**
 * Button Component
 * Reusable UI button.
 */
import React from 'react';

// 📚 CONCEPT: Reusable UI components ensure consistent styling.
const Button = ({ children, onClick, variant = 'primary' }) => {
  const style = {
    padding: '8px 16px',
    background: variant === 'primary' ? 'var(--primary)' : 'transparent',
    color: variant === 'primary' ? 'white' : 'var(--text-color)',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer'
  };
  return <button style={style} onClick={onClick}>{children}</button>;
};

export default Button;
