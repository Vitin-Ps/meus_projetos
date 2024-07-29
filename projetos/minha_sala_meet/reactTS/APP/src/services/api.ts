import axios from 'axios';
import { getToken } from './TokenService';

export function getBaseUrl() {
  return process.env.REACT_APP_API;
}

const api = axios.create({
  baseURL: getBaseUrl(),
});

api.interceptors.request.use(
  async (config) => {
    try {
      const token = getToken();
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    } catch (error) {
      console.error('Error retrieving token:', error);
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

export default api;
