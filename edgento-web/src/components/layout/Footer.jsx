/**
 * Footer Component
 * Global footer.
 */
import React from 'react';

const Footer = () => (
  <footer style={{ padding: '32px', textAlign: 'center', borderTop: '1px solid #eaeaea', marginTop: '64px' }}>
    <p>&copy; {new Date().getFullYear()} Edgento. All rights reserved.</p>
  </footer>
);

export default Footer;
