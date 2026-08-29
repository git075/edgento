import api from './api';

export const agentService = {
  /**
   * Start a new audit conversation
   * @param {Object} data - { visitorName, visitorEmail }
   * @returns {Object} ConversationResponse (contains conversationId and first message)
   */
  startAudit: async (data) => {
    const response = await api.post('/agent/start', data);
    return response.data;
  },

  /**
   * Send a user message to the conversation
   * @param {String} conversationId 
   * @param {Object} data - { content: "user message text" }
   * @returns {Object} ConversationResponse
   */
  processMessage: async (conversationId, data) => {
    const response = await api.post(`/agent/${conversationId}/message`, data);
    return response.data;
  },

  /**
   * Get the final audit report
   * @param {String} conversationId 
   * @returns {Object} AuditReportResponse
   */
  getReport: async (conversationId) => {
    const response = await api.get(`/agent/${conversationId}/report`);
    return response.data;
  }
};

export default agentService;
