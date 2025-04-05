// API configuration
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export const config = {
  apiUrl: API_URL,
  endpoints: {
    generate: `${API_URL}/api/v1/generate`,
    constraints: `${API_URL}/api/v1/constraints`,
    feedback: `${API_URL}/api/v1/feedback`
  }
}; 