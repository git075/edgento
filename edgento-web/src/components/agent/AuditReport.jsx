import React from 'react';
import Card from '../ui/Card';
import Badge from '../ui/Badge';

const AuditReport = ({ report }) => {
  if (!report) return null;

  const scoreColor = report.healthScore > 80 ? 'var(--color-emerald)' : report.healthScore > 50 ? 'var(--color-warning)' : 'hsl(350, 60%, 50%)';

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 'var(--space-4)', padding: 'var(--space-2) 0' }}>
      <div style={{ textAlign: 'center', marginBottom: 'var(--space-4)' }}>
        <h3 className="font-heading" style={{ fontSize: 'var(--text-2xl)', marginBottom: 'var(--space-2)', color: 'var(--color-slate-900)' }}>Business Health</h3>
        <div className="font-heading" style={{ fontSize: 'var(--text-6xl)', color: scoreColor, lineHeight: 1 }}>
          {report.healthScore}
        </div>
        <div style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-500)' }}>out of 100</div>
      </div>

      <Card>
        <h4 style={{ color: 'var(--color-slate-900)', marginBottom: 'var(--space-2)', fontSize: 'var(--text-base)' }}>Critical Vulnerabilities</h4>
        <ul style={{ paddingLeft: 'var(--space-4)', color: 'var(--color-gray-500)', fontSize: 'var(--text-sm)' }}>
          {report.vulnerabilities?.map((v, i) => <li key={i} style={{ marginBottom: '4px' }}>{v}</li>)}
          {(!report.vulnerabilities || report.vulnerabilities.length === 0) && <li>None detected.</li>}
        </ul>
      </Card>

      <Card>
        <h4 style={{ color: 'var(--color-slate-900)', marginBottom: 'var(--space-2)', fontSize: 'var(--text-base)' }}>Key Recommendations</h4>
        <ul style={{ paddingLeft: 'var(--space-4)', color: 'var(--color-gray-500)', fontSize: 'var(--text-sm)' }}>
          {report.recommendations?.map((r, i) => <li key={i} style={{ marginBottom: '4px' }}>{r}</li>)}
          {(!report.recommendations || report.recommendations.length === 0) && <li>No specific recommendations.</li>}
        </ul>
      </Card>

      <div style={{ textAlign: 'center', marginTop: 'var(--space-4)' }}>
        <div style={{ fontSize: 'var(--text-xs)', color: 'var(--color-gray-500)', marginBottom: '4px', textTransform: 'uppercase', letterSpacing: '0.05em' }}>Estimated Revenue Gap</div>
        <div style={{ fontSize: 'var(--text-xl)', color: 'var(--color-gold)', fontWeight: 'bold' }}>{report.revenueGapEstimate || 'N/A'}</div>
      </div>
    </div>
  );
};

export default AuditReport;
