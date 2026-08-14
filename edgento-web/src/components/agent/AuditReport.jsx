/**
 * AuditReport Component
 * Displays system audit logs.
 */
import React from 'react';

const AuditReport = ({ logs }) => (
  <div style={{ background: '#f4f4f4', padding: '16px', borderRadius: '8px' }}>
    <h4>Audit Report</h4>
    <ul>
      {logs?.map((log, idx) => <li key={idx}>{log}</li>)}
    </ul>
  </div>
);

export default AuditReport;
