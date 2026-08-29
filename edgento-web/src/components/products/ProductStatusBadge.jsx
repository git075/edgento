import React from 'react';
import Badge from '../ui/Badge';

/**
 * Maps product status to the correct badge variant.
 */
const ProductStatusBadge = ({ status }) => {
  const displayStatus = status === 'live' ? 'Live' : status === 'beta' ? 'Beta' : 'Coming Soon';
  return <Badge status={status}>{displayStatus}</Badge>;
};

export default ProductStatusBadge;
