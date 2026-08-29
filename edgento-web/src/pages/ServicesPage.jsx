import React from 'react';
import ServicesOverview from '../components/sections/ServicesOverview';
import CTASection from '../components/sections/CTASection';

const ServicesPage = () => (
  <div>
    <section className="theme-white" style={{ padding: 'var(--space-24) 0 0', textAlign: 'center' }}>
      <div className="container">
        <h1 className="font-heading" style={{ fontSize: 'var(--text-6xl)', marginBottom: 'var(--space-6)' }}>
          Expert <span className="text-gold" style={{ fontStyle: 'italic' }}>Services</span>
        </h1>
      </div>
    </section>
    <ServicesOverview />
    <CTASection />
  </div>
);

export default ServicesPage;
