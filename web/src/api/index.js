import axios from 'axios';

const instance = axios.create({
  baseURL: '/',
  withCredentials: true, // 세션 쿠키를 위해 필요
});

// CSRF 토큰 처리를 위한 인터셉터 (Spring Security CSRF 대응)
// 실제 환경에서는 쿠키에서 XSRF-TOKEN을 읽어 헤더에 설정하도록 구성할 수 있습니다.
instance.interceptors.request.use(config => {
  return config;
}, error => {
  return Promise.reject(error);
});

export default instance;
