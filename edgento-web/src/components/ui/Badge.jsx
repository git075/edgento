import React from 'react';

/**
 * Reusable Badge component matching the Edgento Design System.
 */
export const Badge = ({
  children,
  status = 'info', // live, beta, coming-soon, info
  className = '',
  ...props
}) => {
  const baseClass = 'badge';
  const statusClass = `badge--${status}`;
  const combinedClasses = [baseClass, statusClass, className].filter(Boolean).join(' ');

  return (
    <span className={combinedClasses} {...props}>
      {children}
    </span>
  );
};

export default Badge;
