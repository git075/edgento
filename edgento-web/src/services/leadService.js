import api from './api';

export const leadService = {
  /**
   * Create a new lead
   * @param {Object} leadData - { name, email, companyName, websiteUrl }
   */
  createLead: async (leadData) => {
    const response = await api.post('/leads', leadData);
    return response.data;
  },

  /**
   * Submit a contact form
   * @param {Object} contactData - { name, email, message, service }
   */
  submitContact: async (contactData) => {
    const response = await api.post('/contact', contactData);
    return response.data;
  }
};

export default leadService;
