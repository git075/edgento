/**
 * Input Component
 * Reusable text input.
 */
import React from 'react';

const Input = ({ value, onChange, placeholder }) => (
  <input 
    type="text" 
    value={value} 
    onChange={onChange} 
    placeholder={placeholder}
    style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '4px' }}
  />
);

export default Input;
