import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../ui/Button';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="theme-dark" style={{
      padding: 'var(--space-24) 0 var(--space-8) 0',
      borderTop: '1px solid rgba(255,255,255,0.05)',
      overflow: 'hidden',
      position: 'relative'
    }}>
      <div className="container">
        <div className="grid-4" style={{ marginBottom: 'var(--space-16)' }}>
          {/* Brand Col */}
          <div style={{ gridColumn: 'span 1' }}>
            <div className="font-heading" style={{ fontSize: 'var(--text-2xl)', marginBottom: 'var(--space-4)', color: 'var(--color-white)' }}>
              Edgento<span style={{ color: 'var(--color-gold)' }}>.</span>
            </div>
            <p style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-200)', maxWidth: '250px' }}>
              Technology with an edge. We build bespoke software solutions for modern businesses.
            </p>
          </div>

          {/* Links Col 1 */}
          <div>
            <h4 style={{ fontSize: 'var(--text-sm)', color: 'var(--color-white)', marginBottom: 'var(--space-6)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Company</h4>
            <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-3)' }}>
              <Link to="/about" style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-200)' }}>About Us</Link>
              <Link to="/work" style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-200)' }}>Our Work</Link>
              <Link to="/blog" style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-200)' }}>Blog</Link>
            </div>
          </div>

          {/* Links Col 2 */}
          <div>
            <h4 style={{ fontSize: 'var(--text-sm)', color: 'var(--color-white)', marginBottom: 'var(--space-6)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Services</h4>
            <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-3)' }}>
              <Link to="/services" style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-200)' }}>AI Automation</Link>
              <Link to="/services" style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-200)' }}>Web Applications</Link>
              <Link to="/services" style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-200)' }}>Consulting</Link>
            </div>
          </div>

          {/* Newsletter */}
          <div>
            <h4 style={{ fontSize: 'var(--text-sm)', color: 'var(--color-white)', marginBottom: 'var(--space-6)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Newsletter</h4>
            <p style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-200)', marginBottom: 'var(--space-4)' }}>
              Insights and engineering deep-dives, delivered monthly.
            </p>
            <form style={{ display: 'flex', gap: 'var(--space-2)' }}>
              <input 
                type="email" 
                placeholder="Email address" 
                style={{
                  width: '100%',
                  padding: 'var(--space-2) var(--space-3)',
                  borderRadius: 'var(--radius-md)',
                  border: '1px solid rgba(255,255,255,0.2)',
                  backgroundColor: 'rgba(255,255,255,0.05)',
                  color: 'var(--color-white)',
                  outline: 'none',
                  fontSize: 'var(--text-sm)'
                }}
              />
              <Button type="button" variant="primary" style={{ padding: 'var(--space-2) var(--space-4)' }}>Join</Button>
            </form>
          </div>
        </div>

        {/* Bottom */}
        <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'space-between', alignItems: 'center', paddingTop: 'var(--space-8)', borderTop: '1px solid rgba(255,255,255,0.05)', position: 'relative', zIndex: 2 }}>
          <div style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-500)' }}>
            &copy; {currentYear} Edgento Inc. All rights reserved.
          </div>
          <div style={{ display: 'flex', gap: 'var(--space-6)', fontSize: 'var(--text-sm)' }}>
            <Link to="/privacy" style={{ color: 'var(--color-gray-500)' }}>Privacy Policy</Link>
            <Link to="/terms" style={{ color: 'var(--color-gray-500)' }}>Terms of Service</Link>
          </div>
        </div>

        {/* Massive Watermark */}
        <div style={{ 
          textAlign: 'center', 
          marginTop: 'var(--space-8)', 
          fontSize: 'min(18vw, 250px)', 
          fontWeight: '900', 
          lineHeight: '0.85', 
          color: 'rgba(255,255,255,0.02)', 
          userSelect: 'none', 
          pointerEvents: 'none',
          letterSpacing: '-0.05em',
          fontFamily: 'var(--font-sans)',
          textTransform: 'uppercase'
        }}>
          EDGENTO
        </div>
      </div>
    </footer>
  );
};

export default Footer;
