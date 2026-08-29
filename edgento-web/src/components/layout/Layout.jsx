import React from 'react';
import Navbar from './Navbar';
import Footer from './Footer';

/**
 * Main Layout wrapper ensuring consistent structure across pages.
 */
const Layout = ({ children }) => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh', position: 'relative' }}>
      
      {/* --- GLOBAL ARCHITECTURAL GRID OVERLAY --- */}
      <div className="hidden-mobile" style={{
        position: 'fixed',
        top: 0,
        left: '50%',
        transform: 'translateX(-50%)',
        width: '100%',
        maxWidth: '1280px',
        height: '100vh',
        pointerEvents: 'none',
        zIndex: 50,
        borderLeft: '1px solid rgba(255,255,255,1)',
        borderRight: '1px solid rgba(255,255,255,1)',
        opacity: 0.04,
        mixBlendMode: 'difference'
      }}>
        {/* Corner Crosshairs */}
        <div style={{ position: 'absolute', top: '120px', left: '-5px', width: '9px', height: '1px', background: 'rgba(255,255,255,1)' }}></div>
        <div style={{ position: 'absolute', top: '116px', left: '-1px', width: '1px', height: '9px', background: 'rgba(255,255,255,1)' }}></div>
        
        <div style={{ position: 'absolute', top: '120px', right: '-5px', width: '9px', height: '1px', background: 'rgba(255,255,255,1)' }}></div>
        <div style={{ position: 'absolute', top: '116px', right: '-1px', width: '1px', height: '9px', background: 'rgba(255,255,255,1)' }}></div>
      </div>

      {/* --- LEFT MARGIN TYPOGRAPHY --- */}
      <div className="hidden-mobile" style={{
        position: 'fixed',
        left: 'var(--space-6)',
        bottom: 'var(--space-12)',
        writingMode: 'vertical-rl',
        transform: 'rotate(180deg)',
        fontSize: '10px',
        fontWeight: '500',
        letterSpacing: '0.25em',
        color: '#fff',
        textTransform: 'uppercase',
        zIndex: 99,
        pointerEvents: 'none',
        mixBlendMode: 'difference',
        opacity: 0.4
      }}>
        EST. 2026 &nbsp;&nbsp;—&nbsp;&nbsp; BESPOKE ENGINEERING
      </div>

      {/* --- RIGHT MARGIN TYPOGRAPHY --- */}
      <div className="hidden-mobile" style={{
        position: 'fixed',
        right: 'var(--space-6)',
        top: '50%',
        transform: 'translateY(-50%)',
        writingMode: 'vertical-rl',
        fontSize: '10px',
        fontWeight: '500',
        letterSpacing: '0.25em',
        color: '#fff',
        textTransform: 'uppercase',
        zIndex: 99,
        pointerEvents: 'none',
        mixBlendMode: 'difference',
        opacity: 0.4,
        display: 'flex',
        alignItems: 'center',
        gap: '24px'
      }}>
        <div style={{ width: '1px', height: '60px', background: '#fff', opacity: 0.5 }}></div>
        SCROLL TO EXPLORE
      </div>

      <Navbar />
      <main style={{ flex: '1 0 auto' }}>
        {children}
      </main>
      <Footer />
    </div>
  );
};

export default Layout;
