/**
 * ProductCard Component
 * Displays individual product details.
 */
import React from 'react';
import Card from '../ui/Card';
import ProductStatusBadge from './ProductStatusBadge';

const ProductCard = ({ product }) => (
  <Card>
    <h3>{product.name}</h3>
    <p>Price: {product.price}</p>
    <ProductStatusBadge status={product.status} />
  </Card>
);

export default ProductCard;
