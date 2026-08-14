/**
 * ProductGrid Component
 * Grid layout for products.
 */
import React from 'react';
import ProductCard from './ProductCard';

const ProductGrid = ({ products }) => (
  <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '16px' }}>
    {products.map(p => <ProductCard key={p.id} product={p} />)}
  </div>
);

export default ProductGrid;
