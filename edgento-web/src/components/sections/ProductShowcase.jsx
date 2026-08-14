/**
 * ProductShowcase Section
 * Highlights key products.
 */
import React from 'react';
import ProductGrid from '../products/ProductGrid';
import { products } from '../../data/products';

const ProductShowcase = () => (
  <section style={{ padding: '32px 0' }}>
    <h2>Featured Products</h2>
    <ProductGrid products={products} />
  </section>
);

export default ProductShowcase;
