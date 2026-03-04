import axios from 'axios';

const instance = axios.create({
  baseURL: '/',
  withCredentials: true, // 세션 쿠키를 위해 필요
});

// 요청 인터셉터: 로컬 스토리지에 토큰이 있으면 Authorization 헤더 추가
instance.interceptors.request.use(config => {
  const token = localStorage.getItem('basicToken');
  if (token) {
    config.headers.Authorization = `Basic ${token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

// 응답 인터셉터: 인증 만료(401) 시 로그인 페이지로 이동
instance.interceptors.response.use(
  (response) => response,
  (error) => {
    const isLoginRequest = error.config && (error.config.url === '/admin/login' || error.config.url === '/login');
    
    if (error?.response?.status === 401 && !isLoginRequest) {
      try {
        localStorage.removeItem('isAuthenticated');
        localStorage.removeItem('basicToken');
      } catch (_) {}
      // 세션 만료 시 로그인 페이지로 이동
      if (typeof window !== 'undefined') {
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export default instance;
