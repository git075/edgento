import React, { useState, useEffect } from 'react';
import { NavLink, Link } from 'react-router-dom';
import Button from '../ui/Button';
import ChatWidget from '../agent/ChatWidget';

const Navbar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  // Close menu when route changes
  useEffect(() => {
    setIsMenuOpen(false);
  }, []);

  const navLinks = [
    { path: '/services', label: 'Services' },
    { path: '/products', label: 'Products' },
    { path: '/work', label: 'Work' },
    { path: '/about', label: 'About' },
  ];

  const getLinkStyle = ({ isActive }) => ({
    color: isActive ? 'var(--color-gold)' : 'var(--color-white)',
    fontWeight: isActive ? '500' : '400',
    textDecoration: 'none'
  });

  return (
    <nav className="glass" style={{
      position: 'sticky',
      top: 0,
      zIndex: 100,
      color: 'var(--color-white)'
    }}>
      <div className="container" style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        height: '80px'
      }}>
        {/* Logo */}
        <Link to="/" style={{ textDecoration: 'none', display: 'flex', alignItems: 'center', zIndex: 101 }}>
          <span className="font-heading" style={{
            fontSize: 'var(--text-3xl)',
            color: 'var(--color-white)',
            letterSpacing: '0.02em',
          }}>Edgento</span>
        </Link>

        {/* Mobile Menu Toggle */}
        <button 
          className="hidden-desktop"
          onClick={() => setIsMenuOpen(!isMenuOpen)}
          style={{ background: 'none', border: 'none', cursor: 'pointer', zIndex: 101, color: 'var(--color-white)' }}
        >
          {isMenuOpen ? (
            <svg width="28" height="28" fill="none" stroke="currentColor" strokeWidth="2"><line x1="18" y1="6" x2="6" y2="18"></line><line x1="6" y1="6" x2="18" y2="18"></line></svg>
          ) : (
            <svg width="28" height="28" fill="none" stroke="currentColor" strokeWidth="2"><line x1="3" y1="12" x2="21" y2="12"></line><line x1="3" y1="6" x2="21" y2="6"></line><line x1="3" y1="18" x2="21" y2="18"></line></svg>
          )}
        </button>

        {/* Desktop Links */}
        <div className="hidden-mobile" style={{ display: 'flex', gap: 'var(--space-8)', alignItems: 'center' }}>
          {navLinks.map((link) => (
            <NavLink key={link.path} to={link.path} style={getLinkStyle}>
              {link.label}
            </NavLink>
          ))}
        </div>

        {/* Desktop CTA */}
        <div className="hidden-mobile">
          <ChatWidget />
        </div>
      </div>

      {/* Mobile Menu Overlay */}
      {isMenuOpen && (
        <div className="hidden-desktop" style={{
          position: 'absolute',
          top: '80px',
          left: 0,
          right: 0,
          backgroundColor: 'var(--color-slate-900)',
          padding: 'var(--space-6)',
          display: 'flex',
          flexDirection: 'column',
          gap: 'var(--space-6)',
          boxShadow: 'var(--shadow-xl)',
          borderBottom: '1px solid rgba(255,255,255,0.1)'
        }}>
          {navLinks.map((link) => (
            <NavLink 
              key={link.path} 
              to={link.path} 
              style={({ isActive }) => ({
                color: isActive ? 'var(--color-gold)' : 'var(--color-white)',
                fontSize: 'var(--text-lg)',
                padding: 'var(--space-2) 0',
                textDecoration: 'none',
                borderBottom: '1px solid rgba(255,255,255,0.1)'
              })}
              onClick={() => setIsMenuOpen(false)}
            >
              {link.label}
            </NavLink>
          ))}
          <Link to="/contact" onClick={() => setIsMenuOpen(false)} style={{ marginTop: 'var(--space-4)' }}>
            <Button variant="primary" style={{ width: '100%' }}>Get Started</Button>
          </Link>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
