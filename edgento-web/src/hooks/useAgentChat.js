/**
 * useAgentChat Hook
 * Manages state and logic for agent chat interactions.
 */
import { useState } from 'react';

export const useAgentChat = () => {
  const [messages, setMessages] = useState([]);

  const sendMessage = (msg) => {
    setMessages((prev) => [...prev, { text: msg, sender: 'user' }]);
    // Mock response
    setTimeout(() => {
      setMessages((prev) => [...prev, { text: 'Agent response...', sender: 'agent' }]);
    }, 1000);
  };

  return { messages, sendMessage };
};
