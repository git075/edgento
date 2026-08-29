import React from 'react';
import Section from '../components/layout/Section';
import Container from '../components/layout/Container';

const TermsPage = () => {
  return (
    <Section style={{ paddingTop: '120px', paddingBottom: '80px' }}>
      <Container maxWidth="800px">
        <h1 className="font-heading" style={{ fontSize: 'var(--text-4xl)', marginBottom: 'var(--space-6)' }}>
          Terms of Service
        </h1>
        <div style={{ lineHeight: '1.8', color: 'var(--color-slate-900)' }}>
          <p style={{ marginBottom: 'var(--space-4)' }}>Last updated: August 29, 2026</p>
          
          <h2 style={{ fontSize: 'var(--text-2xl)', margin: 'var(--space-6) 0 var(--space-4)' }}>1. Acceptance of Terms</h2>
          <p style={{ marginBottom: 'var(--space-4)' }}>
            By accessing and using the Edgento website and our AI diagnostic tools, you accept and agree to be bound by the terms and provision of this agreement.
          </p>

          <h2 style={{ fontSize: 'var(--text-2xl)', margin: 'var(--space-6) 0 var(--space-4)' }}>2. Use of AI Diagnostic Tool</h2>
          <p style={{ marginBottom: 'var(--space-4)' }}>
            The Edgento Executive Diagnostic provides automated business analysis based on user inputs. The generated reports are for informational purposes and should not be considered formal financial or legal advice.
          </p>
          
          <h2 style={{ fontSize: 'var(--text-2xl)', margin: 'var(--space-6) 0 var(--space-4)' }}>3. Intellectual Property</h2>
          <p style={{ marginBottom: 'var(--space-4)' }}>
            All content, features, and functionality on this website, including but not limited to text, graphics, logos, and software, are the exclusive property of Edgento.
          </p>
        </div>
      </Container>
    </Section>
  );
};

export default TermsPage;
