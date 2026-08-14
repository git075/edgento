/**
 * Agent Service
 * Handles API calls for agent configuration and status.
 */
import api from './api';

export const fetchAgentStatus = async (agentId) => {
  const response = await api.get(`/agents/${agentId}/status`);
  return response.data;
};
