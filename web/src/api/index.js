import axios from 'axios';

const instance = axios.create({
  baseURL: '/',
  withCredentials: true, // 세션 쿠키를 위해 필요
});

// 요청 인터셉터 (필요 시 CSRF 토큰 추가 위치)
instance.interceptors.request.use(config => {
  return config;
}, error => {
  return Promise.reject(error);
});

// 응답 인터셉터: 인증 만료(401) 시 로그인 페이지로 이동
instance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error?.response?.status === 401) {
      try {
        localStorage.removeItem('isAuthenticated');
      } catch (_) {}
      // 세션 만료 시 로그인 페이지로 이동
      if (typeof window !== 'undefined') {
        window.location.href = '/admin/login';
      }
    }
    return Promise.reject(error);
  }
);

export default instance;
