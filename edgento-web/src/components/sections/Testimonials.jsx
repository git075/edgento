import React from 'react';

const Testimonials = () => {
  const testimonials = [
    {
      quote: "Edgento transformed our business. We cut operational costs by 40% in just three months, all while elevating our client experience.",
      author: "Sarah Jenkins",
      title: "CEO, Nexus Agency"
    },
    {
      quote: "The cleanest code we've ever received from an external team. Absolutely world-class engineering that scales effortlessly.",
      author: "David Chen",
      title: "CTO, FinTech Solutions"
    }
  ];

  return (
    <section className="theme-white" style={{ borderTop: '1px solid var(--color-gray-200)' }}>
      <div className="container">
        <div style={{ textAlign: 'center', marginBottom: 'var(--space-16)' }}>
          <h2 style={{ fontSize: 'var(--text-4xl)' }}>Trusted by Industry Leaders</h2>
          <div style={{ width: '60px', height: '3px', backgroundColor: 'var(--color-gold)', margin: '0 auto' }}></div>
        </div>

        <div className="grid-2">
          {testimonials.map((test, index) => (
            <div key={index} style={{
              backgroundColor: 'var(--color-gray-50)',
              padding: 'var(--space-12)',
              borderRadius: 'var(--radius-2xl)',
              boxShadow: 'var(--shadow-sm)'
            }}>
              <div style={{ color: 'var(--color-gold)', fontSize: '3rem', lineHeight: 1, marginBottom: 'var(--space-4)', fontFamily: 'var(--font-heading)' }}>"</div>
              <p className="font-heading" style={{ 
                fontSize: 'var(--text-xl)', 
                marginBottom: 'var(--space-8)', 
                color: 'var(--color-slate-900)',
                fontStyle: 'italic',
                lineHeight: '1.5'
              }}>
                {test.quote}
              </p>
              <div>
                <div style={{ fontWeight: '600', color: 'var(--color-slate-900)', fontSize: 'var(--text-base)' }}>{test.author}</div>
                <div style={{ fontSize: 'var(--text-sm)', color: 'var(--color-gray-500)' }}>{test.title}</div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default Testimonials;
