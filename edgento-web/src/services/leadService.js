/**
 * Lead Service
 * Handles API calls related to leads.
 */
import api from './api';

export const submitLead = async (leadData) => {
  const response = await api.post('/leads', leadData);
  return response.data;
};
