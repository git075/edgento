import React from 'react';
import Card from '../ui/Card';

const ServicesOverview = () => {
  return (
    <section className="theme-light" style={{ position: 'relative' }}>
      <div className="container blueprint-border" style={{ paddingBottom: 'var(--space-32)' }}>
        
        {/* Section Header */}
        <div style={{ textAlign: 'center', marginBottom: 'var(--space-16)', maxWidth: '600px', margin: '0 auto var(--space-16)' }}>
          <div style={{ fontSize: 'var(--text-xs)', textTransform: 'uppercase', letterSpacing: '0.1em', color: 'var(--color-gold)', marginBottom: 'var(--space-4)', fontWeight: '600' }}>Platform Capabilities</div>
          <h2 style={{ fontSize: 'var(--text-4xl)' }}>Engineered for Scale</h2>
          <p style={{ fontSize: 'var(--text-lg)' }}>
            We bridge the gap between complex engineering and elegant product design using a bento-style progressive disclosure architecture.
          </p>
        </div>

        {/* Bento Grid */}
        <div className="bento-grid">
          
          {/* Large Item (Primary Focus) */}
          <Card interactive={true} className="bento-item-large" style={{ display: 'flex', flexDirection: 'column', backgroundColor: 'var(--color-slate-900)', color: 'var(--color-white)', borderColor: 'var(--color-slate-800)' }}>
            <div style={{ color: 'var(--color-gold)', marginBottom: 'auto' }}>
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
                <path d="M2 17l10 5 10-5M2 12l10 5 10-5"></path>
              </svg>
            </div>
            <div style={{ marginTop: 'var(--space-12)' }}>
              <h3 className="font-heading" style={{ fontSize: 'var(--text-3xl)', color: 'var(--color-white)' }}>Bespoke Enterprise Software</h3>
              <p style={{ color: 'var(--color-gray-200)', fontSize: 'var(--text-lg)' }}>
                End-to-end custom SaaS, scalable web platforms, and critical business infrastructure. Built with uncompromising precision for visionary agencies and modern enterprises.
              </p>
            </div>
          </Card>

          {/* Wide Item (Secondary Focus) */}
          <Card interactive={true} className="bento-item-wide" style={{ display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <h3 className="font-heading" style={{ fontSize: 'var(--text-2xl)', color: 'var(--color-slate-900)' }}>AI & Agentic Workflows</h3>
              <div style={{ color: 'var(--color-slate-900)' }}>
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                  <rect x="3" y="11" width="18" height="10" rx="2" ry="2"></rect>
                  <circle cx="12" cy="5" r="2"></circle>
                  <path d="M12 7v4"></path>
                </svg>
              </div>
            </div>
            <p style={{ margin: 0, marginTop: 'var(--space-6)', fontSize: 'var(--text-base)' }}>
              Deploy secure, LLM-driven agents directly into your business logic to automate complex, high-value tasks.
            </p>
          </Card>

          {/* Small Items */}
          <Card interactive={true} className="bento-item-small" style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center', textAlign: 'center' }}>
            <div className="font-heading" style={{ fontSize: 'var(--text-4xl)', color: 'var(--color-slate-900)', lineHeight: 1 }}>99.9%</div>
            <div style={{ fontSize: 'var(--text-xs)', color: 'var(--color-gray-500)', textTransform: 'uppercase', letterSpacing: '0.05em', marginTop: 'var(--space-2)' }}>Uptime SLA</div>
          </Card>

          <Card interactive={true} className="bento-item-small" style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center', textAlign: 'center' }}>
            <div style={{ color: 'var(--color-gold)', marginBottom: 'var(--space-2)' }}>
              <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path></svg>
            </div>
            <div style={{ fontWeight: '500', color: 'var(--color-slate-900)' }}>SOC2 Ready</div>
          </Card>

          {/* Wide Item Bottom Left */}
          <Card interactive={true} className="bento-item-wide" style={{ display: 'flex', alignItems: 'center', gap: 'var(--space-6)' }}>
            <div style={{ flex: 1 }}>
              <h3 className="font-heading" style={{ fontSize: 'var(--text-2xl)', color: 'var(--color-slate-900)' }}>Technical Consulting</h3>
              <p style={{ margin: 0, fontSize: 'var(--text-base)' }}>
                Architecture reviews, security audits, and strategic technical roadmaps. We solve the hard engineering problems so you can scale.
              </p>
            </div>
          </Card>

          {/* Wide Item Bottom Right (In-House Products) */}
          <Card interactive={true} className="bento-item-wide" style={{ display: 'flex', alignItems: 'center', gap: 'var(--space-6)', backgroundColor: 'var(--color-slate-900)', color: 'var(--color-white)' }}>
            <div style={{ flex: 1 }}>
              <h3 className="font-heading" style={{ fontSize: 'var(--text-2xl)', color: 'var(--color-white)' }}>In-House Ventures</h3>
              <p style={{ margin: 0, fontSize: 'var(--text-base)', color: 'var(--color-gray-200)' }}>
                We don't just build for clients. We continuously launch, scale, and maintain our own proprietary SaaS products and AI solutions.
              </p>
            </div>
            <div style={{ color: 'var(--color-gold)' }}>
              <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path>
                <polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline>
                <line x1="12" y1="22.08" x2="12" y2="12"></line>
              </svg>
            </div>
          </Card>

        </div>
      </div>
    </section>
  );
};

export default ServicesOverview;
