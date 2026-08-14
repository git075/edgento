/**
 * ProductStatusBadge Component
 * Specific badge for product status.
 */
import React from 'react';
import Badge from '../ui/Badge';

const ProductStatusBadge = ({ status }) => {
  const color = status === 'Active' ? 'green' : 'orange';
  return <Badge text={status} color={color} />;
};

export default ProductStatusBadge;
