import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../ui/Button';

const Hero = () => {
  return (
    <section className="theme-dark" style={{ padding: 'var(--space-24) 0 var(--space-32)', borderBottom: '1px solid rgba(255,255,255,0.05)' }}>
      <div className="container grid-2 blueprint-border" style={{ position: 'relative' }}>
        <div style={{ maxWidth: '600px', display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
          
          <div className="status-badge" style={{ marginBottom: 'var(--space-8)' }}>
            <div className="status-dot"></div>
            System Status: Operational &nbsp;|&nbsp; Enterprise Grade
          </div>

          <h1 className="animate-fade-in-up display-tight" style={{
            fontSize: 'var(--text-6xl)',
            marginBottom: 'var(--space-6)',
            lineHeight: '1.1'
          }}>
            Software built for <span className="text-gold" style={{ fontStyle: 'italic', fontWeight: '400' }}>distinction.</span>
          </h1>
          
          <p className="animate-fade-in-up delay-100" style={{
            fontSize: 'var(--text-xl)',
            marginBottom: 'var(--space-12)',
            lineHeight: '1.6',
            color: 'var(--color-gray-200)',
            maxWidth: '500px'
          }}>
            Edgento engineers premium, bespoke technical solutions for modern enterprises, alongside launching and scaling our own suite of proprietary SaaS products.
          </p>

          <div className="flex-col-mobile animate-fade-in-up delay-200" style={{ display: 'flex', gap: 'var(--space-4)' }}>
            <Link to="/contact" className="w-full-mobile">
              <Button variant="primary" size="lg" className="w-full-mobile">Get Started</Button>
            </Link>
            <Link to="/services" className="w-full-mobile">
              <Button variant="outline-light" size="lg" className="w-full-mobile">Our Services</Button>
            </Link>
          </div>
        </div>

        {/* Right Side: Engineered Workflow Visual */}
        <div className="hidden-mobile animate-fade-in-up delay-300" style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-end', position: 'relative', zIndex: 1 }}>
          
          <div style={{
            position: 'relative',
            width: '100%',
            maxWidth: '480px',
            height: '320px',
            transform: 'perspective(1000px) rotateY(-5deg) rotateX(2deg)',
            transformStyle: 'preserve-3d'
          }}>
            
            {/* SVG Connecting Lines */}
            <svg style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%', zIndex: 0, overflow: 'visible' }}>
              <path d="M 120 80 Q 200 80 200 160" fill="none" stroke="rgba(255,255,255,0.15)" strokeWidth="2" strokeDasharray="4 4" />
              <path d="M 200 160 Q 200 240 280 240" fill="none" stroke="rgba(255,255,255,0.15)" strokeWidth="2" strokeDasharray="4 4" />
              <path d="M 200 160 Q 200 80 280 80" fill="none" stroke="rgba(212, 175, 55, 0.4)" strokeWidth="2" />
              <circle cx="200" cy="160" r="4" fill="var(--color-slate-900)" stroke="var(--color-gold)" strokeWidth="2" />
            </svg>

            {/* Node 1 */}
            <div style={{ position: 'absolute', top: '50px', left: '20px', zIndex: 1, padding: '12px 16px', background: 'rgba(15,23,42,0.8)', border: '1px solid rgba(255,255,255,0.1)', borderRadius: 'var(--radius-lg)', backdropFilter: 'blur(8px)', display: 'flex', alignItems: 'center', gap: '12px', boxShadow: '0 10px 30px rgba(0,0,0,0.5)' }}>
              <div style={{ width: '32px', height: '32px', borderRadius: '8px', background: 'rgba(255,255,255,0.05)', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--color-gray-400)' }}>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M21 12a9 9 0 01-9 9m9-9a9 9 0 00-9-9m9 9H3m9 9a9 9 0 01-9-9m9 9c1.657 0 3-4.03 3-9s-1.343-9-3-9m0 18c-1.657 0-3-4.03-3-9s1.343-9 3-9"></path></svg>
              </div>
              <div>
                <div style={{ fontSize: '11px', fontWeight: '600', color: 'var(--color-gray-400)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Trigger</div>
                <div style={{ fontSize: '14px', color: 'var(--color-white)', fontWeight: '500' }}>REST API Webhook</div>
              </div>
            </div>

            {/* Node 2 (The Core) */}
            <div style={{ position: 'absolute', top: '120px', left: '160px', zIndex: 2, padding: '16px 20px', background: 'rgba(15,23,42,0.9)', border: '1px solid rgba(212, 175, 55, 0.3)', borderRadius: 'var(--radius-lg)', backdropFilter: 'blur(12px)', display: 'flex', alignItems: 'center', gap: '16px', boxShadow: '0 20px 40px rgba(0,0,0,0.6), 0 0 0 1px rgba(212, 175, 55, 0.1) inset' }}>
              <div style={{ width: '40px', height: '40px', borderRadius: '10px', background: 'rgba(212, 175, 55, 0.1)', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--color-gold)' }}>
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M12 2v4M12 18v4M4.93 4.93l2.83 2.83M16.24 16.24l2.83 2.83M2 12h4M18 12h4M4.93 19.07l2.83-2.83M16.24 7.76l2.83-2.83"></path></svg>
              </div>
              <div>
                <div style={{ fontSize: '11px', fontWeight: '600', color: 'var(--color-gold)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Agent Core</div>
                <div style={{ fontSize: '15px', color: 'var(--color-white)', fontWeight: '600' }}>Semantic Reasoning Engine</div>
                <div style={{ fontSize: '12px', color: 'var(--color-gray-500)', marginTop: '2px' }}>Latency: 42ms</div>
              </div>
            </div>

            {/* Node 3 */}
            <div style={{ position: 'absolute', top: '50px', left: '300px', zIndex: 1, padding: '12px 16px', background: 'rgba(15,23,42,0.8)', border: '1px solid rgba(255,255,255,0.1)', borderRadius: 'var(--radius-lg)', backdropFilter: 'blur(8px)', display: 'flex', alignItems: 'center', gap: '12px', boxShadow: '0 10px 30px rgba(0,0,0,0.5)' }}>
              <div style={{ width: '32px', height: '32px', borderRadius: '8px', background: 'rgba(255,255,255,0.05)', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--color-gray-400)' }}>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><rect x="3" y="3" width="18" height="18" rx="2"></rect><path d="M3 9h18M9 21V9"></path></svg>
              </div>
              <div>
                <div style={{ fontSize: '11px', fontWeight: '600', color: 'var(--color-gray-400)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Action</div>
                <div style={{ fontSize: '14px', color: 'var(--color-white)', fontWeight: '500' }}>Postgres Sync</div>
              </div>
            </div>

            {/* Node 4 */}
            <div style={{ position: 'absolute', top: '210px', left: '260px', zIndex: 1, padding: '12px 16px', background: 'rgba(15,23,42,0.8)', border: '1px solid rgba(255,255,255,0.1)', borderRadius: 'var(--radius-lg)', backdropFilter: 'blur(8px)', display: 'flex', alignItems: 'center', gap: '12px', boxShadow: '0 10px 30px rgba(0,0,0,0.5)' }}>
              <div style={{ width: '32px', height: '32px', borderRadius: '8px', background: 'rgba(255,255,255,0.05)', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--color-gray-400)' }}>
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M22 12h-4l-3 9L9 3l-3 9H2"></path></svg>
              </div>
              <div>
                <div style={{ fontSize: '11px', fontWeight: '600', color: 'var(--color-gray-400)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Output</div>
                <div style={{ fontSize: '14px', color: 'var(--color-white)', fontWeight: '500' }}>Client WebSocket</div>
              </div>
            </div>

          </div>
          
          {/* Subtle glow behind workflow */}
          <div style={{ position: 'absolute', width: '300px', height: '300px', background: 'var(--color-gold)', filter: 'blur(100px)', opacity: 0.08, top: '50%', right: '10%', transform: 'translateY(-50%)', zIndex: -1 }}></div>
        </div>
      </div>
    </section>
  );
};

export default Hero;
