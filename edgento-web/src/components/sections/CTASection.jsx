import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../ui/Button';

const CTASection = () => (
  <section className="theme-dark" style={{ padding: 'var(--space-32) 0', textAlign: 'center', position: 'relative', overflow: 'hidden' }}>
    <div className="container">
      <h2 className="font-heading" style={{ fontSize: 'var(--text-5xl)', marginBottom: 'var(--space-6)' }}>
        Ready to build something <span className="text-gold">exceptional?</span>
      </h2>
      <p style={{ fontSize: 'var(--text-xl)', color: 'var(--color-gray-200)', marginBottom: 'var(--space-12)', maxWidth: '600px', margin: '0 auto var(--space-12)' }}>
        Partner with Edgento to engineer bespoke solutions that elevate your business.
      </p>
      <Link to="/contact">
        <Button variant="primary" size="lg">Start a Project</Button>
      </Link>
    </div>
  </section>
);

export default CTASection;
