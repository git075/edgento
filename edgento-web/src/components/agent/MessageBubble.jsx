import React from 'react';

const MessageBubble = ({ message, isUser }) => {
  return (
    <div style={{
      alignSelf: isUser ? 'flex-end' : 'flex-start',
      maxWidth: '85%',
      backgroundColor: isUser ? 'var(--color-slate-900)' : 'var(--color-gray-50)',
      color: isUser ? 'var(--color-white)' : 'var(--color-slate-900)',
      padding: 'var(--space-3) var(--space-4)',
      borderRadius: 'var(--radius-xl)',
      borderBottomRightRadius: isUser ? '4px' : 'var(--radius-xl)',
      borderBottomLeftRadius: !isUser ? '4px' : 'var(--radius-xl)',
      fontSize: 'var(--text-sm)',
      lineHeight: '1.5',
      boxShadow: 'var(--shadow-sm)',
      border: isUser ? 'none' : '1px solid var(--color-gray-200)'
    }}>
      {message}
    </div>
  );
};

export default MessageBubble;
