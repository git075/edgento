import { useState, useEffect } from 'react';
import agentService from '../services/agentService';
import leadService from '../services/leadService';

// 📚 CONCEPT: Session Persistence
// Before this fix, if a user accidentally hit refresh during the 5-step chat,
// React would wipe all local state and they would have to start over.
// By persisting the state to the browser's sessionStorage, the chat survives
// refreshes but is cleanly wiped when they close the browser tab.
export const useAgentChat = () => {
  // Initialize state from sessionStorage if it exists, otherwise use defaults
  const [conversationId, setConversationId] = useState(() => sessionStorage.getItem('edgento_conversationId') || null);
  const [messages, setMessages] = useState(() => JSON.parse(sessionStorage.getItem('edgento_messages')) || []);
  const [isComplete, setIsComplete] = useState(() => JSON.parse(sessionStorage.getItem('edgento_isComplete')) || false);
  const [report, setReport] = useState(() => JSON.parse(sessionStorage.getItem('edgento_report')) || null);
  
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  // Auto-save to sessionStorage whenever these state variables change
  useEffect(() => {
    if (conversationId) sessionStorage.setItem('edgento_conversationId', conversationId);
    if (messages.length > 0) sessionStorage.setItem('edgento_messages', JSON.stringify(messages));
    sessionStorage.setItem('edgento_isComplete', JSON.stringify(isComplete));
    if (report) sessionStorage.setItem('edgento_report', JSON.stringify(report));
  }, [conversationId, messages, isComplete, report]);

  const startChat = async (visitorName, visitorEmail) => {
    setIsLoading(true);
    setError(null);
    try {
      const res = await agentService.startAudit({ visitorName, visitorEmail });
      setConversationId(res.conversationId);
      setMessages([{ text: res.message, sender: 'agent' }]);
    } catch (err) {
      setError('Failed to start chat. ' + (err.response?.data?.message || err.message));
    } finally {
      setIsLoading(false);
    }
  };

  const sendMessage = async (text) => {
    if (!conversationId) return;

    // Optimistically add user message
    setMessages(prev => [...prev, { text, sender: 'user' }]);
    setIsLoading(true);
    setError(null);

    try {
      const res = await agentService.processMessage(conversationId, { content: text });
      
      // Add agent's response
      setMessages(prev => [...prev, { text: res.message, sender: 'agent' }]);

      if (res.status === 'COMPLETED') {
        setIsComplete(true);
        const reportRes = await agentService.getReport(conversationId);
        setReport(reportRes);
      }
    } catch (err) {
      setError('Failed to send message. ' + (err.response?.data?.message || err.message));
    } finally {
      setIsLoading(false);
    }
  };

  return {
    conversationId,
    messages,
    isLoading,
    isComplete,
    report,
    error,
    startChat,
    sendMessage
  };
};

export default useAgentChat;
