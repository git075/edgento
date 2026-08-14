/**
 * Badge Component
 * Small status indicator.
 */
import React from 'react';

const Badge = ({ text, color = 'var(--primary)' }) => (
  <span style={{ background: color, color: 'white', padding: '4px 8px', borderRadius: '12px', fontSize: '12px' }}>
    {text}
  </span>
);

export default Badge;
