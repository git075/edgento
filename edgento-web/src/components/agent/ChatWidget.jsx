/**
 * ChatWidget Component
 * Floating chat widget leveraging useAgentChat.
 */
import React, { useState } from 'react';
import ChatWindow from './ChatWindow';
import Input from '../ui/Input';
import Button from '../ui/Button';
import { useAgentChat } from '../../hooks/useAgentChat';

const ChatWidget = () => {
  const { messages, sendMessage } = useAgentChat();
  const [input, setInput] = useState('');

  const handleSend = () => {
    if(input.trim()) {
      sendMessage(input);
      setInput('');
    }
  };

  return (
    <div style={{ position: 'fixed', bottom: 20, right: 20, width: '300px', background: 'white', border: '1px solid #ddd', borderRadius: '8px' }}>
      <ChatWindow messages={messages} />
      <div style={{ display: 'flex', padding: '8px' }}>
        <Input value={input} onChange={e => setInput(e.target.value)} placeholder="Type a message..." />
        <Button onClick={handleSend}>Send</Button>
      </div>
    </div>
  );
};

export default ChatWidget;
