/**
 * ContactPage
 */
import React from 'react';
import Input from '../components/ui/Input';
import Button from '../components/ui/Button';

const ContactPage = () => (
  <div>
    <h1>Contact Us</h1>
    <div style={{ display: 'flex', flexDirection: 'column', gap: '16px', maxWidth: '300px' }}>
      <Input placeholder="Your Email" />
      <Input placeholder="Message" />
      <Button>Send</Button>
    </div>
  </div>
);

export default ContactPage;
