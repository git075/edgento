/**
 * Navbar Component
 * Global navigation.
 */
import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => (
  <nav style={{ padding: '16px', background: 'white', borderBottom: '1px solid #eaeaea', display: 'flex', gap: '16px' }}>
    <Link to="/">Home</Link>
    <Link to="/services">Services</Link>
    <Link to="/products">Products</Link>
    <Link to="/work">Work</Link>
    <Link to="/about">About</Link>
    <Link to="/contact">Contact</Link>
    <Link to="/blog">Blog</Link>
  </nav>
);

export default Navbar;
