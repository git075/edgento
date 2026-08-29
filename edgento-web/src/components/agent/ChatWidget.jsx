import React, { useState, useEffect, useRef } from 'react';
import { createPortal } from 'react-dom';
import ChatWindow from './ChatWindow';
import Input from '../ui/Input';
import Button from '../ui/Button';
import { useAgentChat } from '../../hooks/useAgentChat';
import AuditReport from './AuditReport';

const ChatWidget = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [visitorName, setVisitorName] = useState('');
  const [visitorEmail, setVisitorEmail] = useState('');
  const { messages, isLoading, isComplete, report, error, startChat, sendMessage } = useAgentChat();
  const [input, setInput] = useState('');

  const messagesEndRef = useRef(null);

  const toggleWidget = () => setIsOpen(!isOpen);

  const handleStart = (e) => {
    e.preventDefault();
    if (visitorName && visitorEmail) {
      startChat(visitorName, visitorEmail);
    }
  };

  const handleSend = (e) => {
    e.preventDefault();
    if(input.trim() && !isLoading) {
      sendMessage(input);
      setInput('');
    }
  };

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages, isLoading]);

  return (
    <div style={{ position: 'relative' }}>
      {/* Header Button Toggle */}
      <Button 
        onClick={toggleWidget}
        variant="primary"
        style={{ display: 'flex', alignItems: 'center', gap: '8px' }}
      >
        {isOpen ? (
          <svg width="18" height="18" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><line x1="18" y1="6" x2="6" y2="18"></line><line x1="6" y1="6" x2="18" y2="18"></line></svg>
        ) : (
          <svg width="18" height="18" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
        )}
        Executive Audit
      </Button>

      {/* Slide-out Drawer Panel rendered via Portal to escape Navbar stacking context */}
      {isOpen && createPortal(
        <>
          {/* Backdrop Overlay */}
          <div 
            className="animate-fade-in-backdrop"
            onClick={toggleWidget}
            style={{
              position: 'fixed',
              top: 0,
              left: 0,
              right: 0,
              bottom: 0,
              backgroundColor: 'rgba(15, 23, 42, 0.4)',
              zIndex: 9998,
              cursor: 'pointer'
            }}
          />

          <div className="animate-slide-in-right" style={{
            position: 'fixed',
            top: 0,
            right: 0,
            bottom: 0,
            width: '450px',
            maxWidth: '100vw',
            backgroundColor: 'var(--color-white)',
            boxShadow: '-10px 0 40px rgba(15, 23, 42, 0.1)',
            zIndex: 9999,
            display: 'flex',
            flexDirection: 'column',
            borderLeft: '1px solid var(--color-gray-200)'
          }}>
            {/* Header */}
            <div style={{
              padding: 'var(--space-4) var(--space-6)',
              borderBottom: '1px solid var(--color-gray-200)',
              backgroundColor: 'var(--color-gray-50)',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'space-between'
            }}>
              <div>
                <div style={{ fontWeight: '600', color: 'var(--color-slate-900)' }}>AI Strategy Consultant</div>
                <div style={{ fontSize: 'var(--text-xs)', color: 'var(--color-emerald)', display: 'flex', alignItems: 'center', gap: '4px' }}>
                  <span style={{ display: 'inline-block', width: '8px', height: '8px', borderRadius: '50%', backgroundColor: 'currentColor' }}></span>
                  Online
                </div>
              </div>
              <button onClick={toggleWidget} style={{ background: 'none', border: 'none', cursor: 'pointer', color: 'var(--color-gray-500)', padding: '4px' }}>
                <svg width="24" height="24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><line x1="18" y1="6" x2="6" y2="18"></line><line x1="6" y1="6" x2="18" y2="18"></line></svg>
              </button>
            </div>

            {/* Body Area */}
            <div className="no-scrollbar" style={{ flex: 1, overflowY: 'auto', padding: 'var(--space-6)', display: 'flex', flexDirection: 'column' }}>
              {error && (
                <div style={{ padding: 'var(--space-3)', backgroundColor: 'hsl(350, 100%, 95%)', color: 'hsl(350, 60%, 50%)', borderRadius: 'var(--radius-md)', marginBottom: 'var(--space-4)', fontSize: 'var(--text-sm)' }}>
                  {error}
                </div>
              )}
              
              {messages.length === 0 && !isComplete ? (
                // Lead Capture Form
                <div style={{ margin: 'auto', textAlign: 'center', maxWidth: '320px' }}>
                  <div style={{ 
                    display: 'inline-flex', 
                    alignItems: 'center', 
                    justifyContent: 'center',
                    width: '64px', 
                    height: '64px', 
                    borderRadius: 'var(--radius-full)', 
                    backgroundColor: 'rgba(212, 175, 55, 0.1)', 
                    color: 'var(--color-gold)',
                    marginBottom: 'var(--space-6)'
                  }}>
                    <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                      <path d="M12 2v4M12 18v4M4.93 4.93l2.83 2.83M16.24 16.24l2.83 2.83M2 12h4M18 12h4M4.93 19.07l2.83-2.83M16.24 7.76l2.83-2.83"></path>
                      <circle cx="12" cy="12" r="4"></circle>
                    </svg>
                  </div>
                  
                  <h3 className="font-heading" style={{ fontSize: 'var(--text-3xl)', marginBottom: 'var(--space-2)', color: 'var(--color-slate-900)' }}>Executive Diagnostic</h3>
                  <p style={{ fontSize: 'var(--text-base)', color: 'var(--color-gray-500)', marginBottom: 'var(--space-8)', lineHeight: '1.6' }}>
                    Initiate an AI-driven analysis of your operational architecture to identify scaling bottlenecks and revenue gaps.
                  </p>

                  <form onSubmit={handleStart} style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)', textAlign: 'left' }}>
                    <div>
                      <label style={{ display: 'block', fontSize: 'var(--text-xs)', fontWeight: '600', color: 'var(--color-slate-900)', textTransform: 'uppercase', letterSpacing: '0.05em', marginBottom: '8px' }}>Full Name</label>
                      <Input 
                        placeholder="e.g. Jane Doe" 
                        value={visitorName} 
                        onChange={e => setVisitorName(e.target.value)} 
                        required 
                        style={{ backgroundColor: 'var(--color-gray-50)' }}
                      />
                    </div>
                    <div>
                      <label style={{ display: 'block', fontSize: 'var(--text-xs)', fontWeight: '600', color: 'var(--color-slate-900)', textTransform: 'uppercase', letterSpacing: '0.05em', marginBottom: '8px' }}>Corporate Email</label>
                      <Input 
                        type="email" 
                        placeholder="jane@company.com" 
                        value={visitorEmail} 
                        onChange={e => setVisitorEmail(e.target.value)} 
                        required 
                        style={{ backgroundColor: 'var(--color-gray-50)' }}
                      />
                    </div>
                    
                    <label style={{ display: 'flex', gap: '8px', alignItems: 'flex-start', fontSize: '12px', color: 'var(--color-slate-900)' }}>
                      <input type="checkbox" required style={{ marginTop: '2px' }} />
                      <span>
                        I agree to the <a href="/privacy" target="_blank" style={{ color: 'var(--color-gold)', textDecoration: 'underline' }}>Privacy Policy</a> and 
                        understand my data will be used to generate a business audit report.
                      </span>
                    </label>
                    <Button type="submit" variant="primary" disabled={isLoading} style={{ marginTop: 'var(--space-4)', padding: 'var(--space-4)' }}>
                      {isLoading ? 'Connecting to Core...' : 'Initiate Diagnostic'}
                    </Button>
                    <p style={{ fontSize: '12px', color: 'var(--color-gray-500)', textAlign: 'center', marginTop: 'var(--space-2)' }}>
                      Secure, confidential, and automated.
                    </p>
                  </form>
                </div>
              ) : isComplete && report ? (
                // Final Report
                <AuditReport report={report} />
              ) : (
                // Chat Messages
                <ChatWindow messages={messages} isLoading={isLoading} />
              )}
              <div ref={messagesEndRef} />
            </div>

            {/* Input Area (only show when chatting) */}
            {messages.length > 0 && !isComplete && (
              <div style={{ padding: 'var(--space-4) var(--space-6)', borderTop: '1px solid var(--color-gray-200)', backgroundColor: 'var(--color-white)' }}>
                <form onSubmit={handleSend} style={{ display: 'flex', gap: 'var(--space-3)' }}>
                  <Input 
                    value={input} 
                    onChange={e => setInput(e.target.value)}
                    placeholder="Type your response..."
                    disabled={isLoading}
                    style={{ backgroundColor: 'var(--color-gray-50)' }}
                  />
                  <Button type="submit" variant="primary" disabled={isLoading || !input.trim()}>
                    <svg width="18" height="18" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><line x1="22" y1="2" x2="11" y2="13"></line><polygon points="22 2 15 22 11 13 2 9 22 2"></polygon></svg>
                  </Button>
                </form>
              </div>
            )}
          </div>
        </>,
        document.body
      )}
    </div>
  );
};

export default ChatWidget;
