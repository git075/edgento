import React from 'react';
import ProductShowcase from '../components/sections/ProductShowcase';
import CTASection from '../components/sections/CTASection';

const ProductsPage = () => (
  <div>
    <section className="theme-white" style={{ padding: 'var(--space-24) 0 0', textAlign: 'center' }}>
      <div className="container">
        <h1 className="font-heading" style={{ fontSize: 'var(--text-6xl)', marginBottom: 'var(--space-6)' }}>
          Our <span className="text-gold" style={{ fontStyle: 'italic' }}>Products</span>
        </h1>
      </div>
    </section>
    <ProductShowcase />
    <CTASection />
  </div>
);

export default ProductsPage;
