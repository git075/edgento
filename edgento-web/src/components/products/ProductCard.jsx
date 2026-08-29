import React from 'react';
import Card from '../ui/Card';
import ProductStatusBadge from './ProductStatusBadge';
import Button from '../ui/Button';

const ProductCard = ({ product }) => (
  <Card interactive={true} style={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: 'var(--space-6)' }}>
      <h3 className="font-heading" style={{ fontSize: 'var(--text-xl)', margin: 0, color: 'var(--color-slate-900)' }}>{product.name}</h3>
      <ProductStatusBadge status={product.status} />
    </div>
    
    <p style={{ flex: 1, fontSize: 'var(--text-base)', color: 'var(--color-gray-500)' }}>
      {product.description}
    </p>

    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: 'var(--space-8)' }}>
      <span style={{ fontWeight: '600', fontSize: 'var(--text-base)', color: 'var(--color-slate-900)' }}>
        {product.price}
      </span>
      <Button variant="ghost" size="sm" style={{ paddingRight: 0, color: 'var(--color-gold)' }}>Learn More &rarr;</Button>
    </div>
  </Card>
);

export default ProductCard;
