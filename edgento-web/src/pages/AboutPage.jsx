import React from 'react';
import CTASection from '../components/sections/CTASection';

const AboutPage = () => (
  <div>
    <section className="theme-white" style={{ padding: 'var(--space-24) 0', textAlign: 'center' }}>
      <div className="container">
        <h1 className="font-heading" style={{ fontSize: 'var(--text-6xl)', marginBottom: 'var(--space-6)', color: 'var(--color-slate-900)' }}>
          About <span className="text-gold" style={{ fontStyle: 'italic' }}>Edgento</span>
        </h1>
        <p style={{ fontSize: 'var(--text-lg)', color: 'var(--color-gray-500)', maxWidth: '600px', margin: '0 auto' }}>
          We are a team of elite engineers and designers building the next generation of business software.
        </p>
      </div>
    </section>
    <CTASection />
  </div>
);

export default AboutPage;
