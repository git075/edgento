import axios from 'axios';

/**
 * Centralized Axios instance pointing to the Spring Boot backend.
 */
const api = axios.create({
  // Read from Vite environment variables for production, fallback to localhost for dev
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  }
});

export default api;
