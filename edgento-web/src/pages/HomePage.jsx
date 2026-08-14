/**
 * HomePage
 * The main landing page.
 */
import React from 'react';
import Hero from '../components/sections/Hero';
import ServicesOverview from '../components/sections/ServicesOverview';
import ProductShowcase from '../components/sections/ProductShowcase';
import Testimonials from '../components/sections/Testimonials';
import CTASection from '../components/sections/CTASection';

const HomePage = () => (
  <div>
    <Hero />
    <ServicesOverview />
    <ProductShowcase />
    <Testimonials />
    <CTASection />
  </div>
);

export default HomePage;
