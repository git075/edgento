import React from 'react';

const Container = ({ children, className = '', maxWidth = '1200px', style = {} }) => {
  return (
    <div 
      className={`container ${className}`} 
      style={{ 
        maxWidth, 
        margin: '0 auto', 
        padding: '0 var(--space-4)',
        width: '100%',
        ...style 
      }}
    >
      {children}
    </div>
  );
};

export default Container;
