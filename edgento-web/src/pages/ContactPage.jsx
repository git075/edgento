import React, { useState } from 'react';
import Input from '../components/ui/Input';
import Button from '../components/ui/Button';
import Card from '../components/ui/Card';
import leadService from '../services/leadService';

const ContactPage = () => {
  const [formData, setFormData] = useState({ name: '', email: '', message: '' });
  const [status, setStatus] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setStatus('submitting');
    try {
      await leadService.submitContact(formData);
      setStatus('success');
      setFormData({ name: '', email: '', message: '' });
    } catch (err) {
      setStatus('error');
    }
  };

  return (
    <div style={{ padding: 'var(--space-24) 0' }}>
      <div className="container" style={{ maxWidth: '600px' }}>
        <h1 className="font-heading" style={{ fontSize: 'var(--text-4xl)', letterSpacing: '-0.04em', marginBottom: 'var(--space-2)', textAlign: 'center' }}>
          Get in <span style={{ fontStyle: 'italic', fontWeight: 'normal' }}>touch</span>
        </h1>
        <p style={{ textAlign: 'center', color: 'var(--color-text-secondary)', marginBottom: 'var(--space-12)' }}>
          Have a project in mind? Let's discuss how we can help.
        </p>

        <Card>
          <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-6)' }}>
            <div>
              <label style={{ display: 'block', marginBottom: 'var(--space-2)', fontSize: 'var(--text-sm)', color: 'var(--color-text-primary)' }}>Name</label>
              <Input 
                required
                value={formData.name}
                onChange={e => setFormData({...formData, name: e.target.value})}
              />
            </div>
            <div>
              <label style={{ display: 'block', marginBottom: 'var(--space-2)', fontSize: 'var(--text-sm)', color: 'var(--color-text-primary)' }}>Email</label>
              <Input 
                type="email"
                required
                value={formData.email}
                onChange={e => setFormData({...formData, email: e.target.value})}
              />
            </div>
            <div>
              <label style={{ display: 'block', marginBottom: 'var(--space-2)', fontSize: 'var(--text-sm)', color: 'var(--color-text-primary)' }}>Message</label>
              <textarea 
                className="input"
                required
                rows={5}
                value={formData.message}
                onChange={e => setFormData({...formData, message: e.target.value})}
                style={{ resize: 'vertical' }}
              />
            </div>
            <Button type="submit" variant="primary" disabled={status === 'submitting'}>
              {status === 'submitting' ? 'Sending...' : 'Send Message'}
            </Button>

            {status === 'success' && <div style={{ color: 'var(--color-success)', fontSize: 'var(--text-sm)', textAlign: 'center' }}>Message sent successfully!</div>}
            {status === 'error' && <div style={{ color: 'var(--color-error)', fontSize: 'var(--text-sm)', textAlign: 'center' }}>Failed to send message.</div>}
          </form>
        </Card>
      </div>
    </div>
  );
};

export default ContactPage;
