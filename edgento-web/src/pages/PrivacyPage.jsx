import React from 'react';
import Section from '../components/layout/Section';
import Container from '../components/layout/Container';

const PrivacyPage = () => {
  return (
    <Section style={{ paddingTop: '120px', paddingBottom: '80px' }}>
      <Container maxWidth="800px">
        <h1 className="font-heading" style={{ fontSize: 'var(--text-4xl)', marginBottom: 'var(--space-6)' }}>
          Privacy Policy
        </h1>
        <div style={{ lineHeight: '1.8', color: 'var(--color-slate-900)' }}>
          <p style={{ marginBottom: 'var(--space-4)' }}>Last updated: August 29, 2026</p>
          
          <h2 style={{ fontSize: 'var(--text-2xl)', margin: 'var(--space-6) 0 var(--space-4)' }}>1. Information We Collect</h2>
          <p style={{ marginBottom: 'var(--space-4)' }}>
            When you use the Edgento Executive Diagnostic tool or contact us, we collect personal information including 
            your name, corporate email address, and business details you provide.
          </p>

          <h2 style={{ fontSize: 'var(--text-2xl)', margin: 'var(--space-6) 0 var(--space-4)' }}>2. How We Use Your Information</h2>
          <p style={{ marginBottom: 'var(--space-4)' }}>
            We use this information to:
            <ul style={{ listStyleType: 'disc', paddingLeft: '20px', marginTop: '10px' }}>
              <li>Generate your customized business audit report.</li>
              <li>Communicate with you regarding our services.</li>
              <li>Improve our AI diagnostic models.</li>
            </ul>
          </p>

          <h2 style={{ fontSize: 'var(--text-2xl)', margin: 'var(--space-6) 0 var(--space-4)' }}>3. Data Security</h2>
          <p style={{ marginBottom: 'var(--space-4)' }}>
            We implement industry-standard security measures to protect your personal information. Your data is stored 
            securely on our servers and is not sold to third parties.
          </p>
          
          <p style={{ marginTop: 'var(--space-8)' }}>
            For any privacy-related concerns, please contact us at privacy@edgento.com.
          </p>
        </div>
      </Container>
    </Section>
  );
};

export default PrivacyPage;
