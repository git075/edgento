import React from 'react';
import MessageBubble from './MessageBubble';

const ChatWindow = ({ messages, isLoading }) => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)' }}>
      {messages.map((m, i) => (
        <MessageBubble key={i} message={m.text} isUser={m.sender === 'user'} />
      ))}
      {isLoading && (
        <div style={{ alignSelf: 'flex-start', color: 'var(--color-text-muted)', fontSize: 'var(--text-sm)', display: 'flex', gap: '4px', padding: 'var(--space-2)' }}>
          <span>Agent is typing</span>
          <span className="dot-anim">...</span>
        </div>
      )}
    </div>
  );
};

export default ChatWindow;
