import React from 'react';

/**
 * Reusable Card component matching the Edgento Design System.
 */
export const Card = ({
  children,
  interactive = false,
  className = '',
  onClick,
  ...props
}) => {
  const baseClass = 'card';
  const interactiveClass = interactive || onClick ? 'card--interactive' : '';
  const combinedClasses = [baseClass, interactiveClass, className].filter(Boolean).join(' ');

  return (
    <div
      className={combinedClasses}
      onClick={onClick}
      {...props}
    >
      {children}
    </div>
  );
};

export default Card;
