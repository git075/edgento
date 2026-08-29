import React from 'react';
import ProductGrid from '../products/ProductGrid';
import { products } from '../../data/products';

const ProductShowcase = () => (
  <section className="theme-white">
    <div className="container">
      <div style={{ textAlign: 'center', marginBottom: 'var(--space-16)', maxWidth: '600px', margin: '0 auto var(--space-12)' }}>
        <h2 className="font-heading" style={{ fontSize: 'var(--text-4xl)' }}>Our Portfolio</h2>
        <div style={{ width: '60px', height: '3px', backgroundColor: 'var(--color-gold)', margin: '0 auto var(--space-6)' }}></div>
        <p style={{ fontSize: 'var(--text-lg)' }}>
          Powerful, proprietary SaaS products built and scaled entirely in-house by the Edgento venture team.
        </p>
      </div>
      <ProductGrid products={products} />
    </div>
  </section>
);

export default ProductShowcase;
