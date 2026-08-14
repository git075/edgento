/**
 * MessageBubble Component
 * Renders individual chat messages.
 */
import React from 'react';

const MessageBubble = ({ message, isUser }) => (
  <div style={{ textAlign: isUser ? 'right' : 'left', margin: '8px 0' }}>
    <span style={{ background: isUser ? 'var(--primary)' : '#eee', color: isUser ? 'white' : 'black', padding: '8px 12px', borderRadius: '16px', display: 'inline-block' }}>
      {message}
    </span>
  </div>
);

export default MessageBubble;
