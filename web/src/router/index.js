import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import DashboardView from '../views/DashboardView.vue'
import AddReviewView from '../views/AddReviewView.vue'
import ReviewDetailView from '../views/ReviewDetailView.vue'

const routes = [
  {
    path: '/admin/login',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/',
    redirect: '/admin/login'
  },
  {
    path: '/admin',
    name: 'Dashboard',
    component: DashboardView
  },
  {
    path: '/admin/reviews/new',
    name: 'AddReview',
    component: AddReviewView
  },
  {
    path: '/admin/reviews/:id',
    name: 'ReviewDetail',
    component: ReviewDetailView
  }

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Navigation Guard: 로그인 여부 확인
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('isAuthenticated') === 'true';
  console.info('isAuthenticated:', isAuthenticated);
  if (to.path.startsWith('/admin') && to.path !== '/admin/login' && !isAuthenticated) {
    // 로그인이 필요한 페이지에 접근 시 로그인 페이지로 리다이렉트
    next('/admin/login');
  } else if (to.path === '/admin/login' && isAuthenticated) {
    // 이미 로그인된 상태에서 로그인 페이지 접근 시 대시보드로 이동
    next('/admin');
  } else {
    next();
  }
});

export default router
