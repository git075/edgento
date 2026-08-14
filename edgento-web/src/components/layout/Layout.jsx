/**
 * Layout Component
 * Wraps pages with Navbar and Footer.
 */
import React from 'react';
import Navbar from './Navbar';
import Footer from './Footer';
import ChatWidget from '../agent/ChatWidget';

const Layout = ({ children }) => (
  <div>
    <Navbar />
    <main style={{ minHeight: '80vh', padding: '32px' }}>
      {children}
    </main>
    <ChatWidget />
    <Footer />
  </div>
);

export default Layout;
