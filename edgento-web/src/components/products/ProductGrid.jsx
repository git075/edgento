import React from 'react';
import ProductCard from './ProductCard';

const ProductGrid = ({ products }) => (
  <div style={{
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))',
    gap: 'var(--space-6)'
  }}>
    {products.map(p => <ProductCard key={p.id} product={p} />)}
  </div>
);

export default ProductGrid;
