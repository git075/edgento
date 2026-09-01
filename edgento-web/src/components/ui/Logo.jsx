import React from 'react';

const Logo = ({ className = '', style = {} }) => {
  return (
    <div className={`logo-container ${className}`} style={{ display: 'flex', alignItems: 'center', gap: '12px', ...style }}>
      {/* Geometric 'Edge/Blade' Icon (Concept B) */}
      <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg" style={{ flexShrink: 0 }}>
        {/* Forward angular blade */}
        <path d="M4 4L16 16L4 28" stroke="var(--color-gold)" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round"/>
        {/* Intersecting secondary blade */}
        <path d="M12 8L20 16L12 24" stroke="var(--color-gold)" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round"/>
        {/* Abstract central line for momentum */}
        <path d="M4 16H16" stroke="var(--color-gold)" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round"/>
      </svg>
      {/* Wordmark */}
      <span className="font-heading" style={{
        fontSize: '26px',
        color: 'var(--color-white)',
        letterSpacing: '0.04em',
        fontWeight: '500'
      }}>
        Edgento
      </span>
    </div>
  );
};

export default Logo;
