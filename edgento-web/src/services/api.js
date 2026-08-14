/**
 * API Service
 * Centralized Axios instance with interceptors.
 */
import axios from 'axios';

// 📚 CONCEPT: Create a base axios instance to handle common headers and interceptors.
const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
});

export default api;
