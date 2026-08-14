/**
 * ProductsPage
 */
import React from 'react';
import ProductGrid from '../components/products/ProductGrid';
import { products } from '../data/products';

const ProductsPage = () => (
  <div>
    <h1>Our Products</h1>
    <ProductGrid products={products} />
  </div>
);

export default ProductsPage;
