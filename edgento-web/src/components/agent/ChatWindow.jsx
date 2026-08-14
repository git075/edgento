/**
 * ChatWindow Component
 * The main conversation interface.
 */
import React, from 'react';
import MessageBubble from './MessageBubble';

const ChatWindow = ({ messages }) => (
  <div style={{ height: '300px', overflowY: 'auto', border: '1px solid #ccc', padding: '16px' }}>
    {messages.map((m, i) => <MessageBubble key={i} message={m.text} isUser={m.sender === 'user'} />)}
  </div>
);

export default ChatWindow;
