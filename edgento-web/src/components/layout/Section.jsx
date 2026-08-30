import React from 'react';

const Section = ({ children, className = '', id, style = {} }) => {
  return (
    <section 
      id={id}
      className={`section ${className}`}
      style={{
        padding: 'var(--space-16) 0',
        ...style
      }}
    >
      {children}
    </section>
  );
};

export default Section;
