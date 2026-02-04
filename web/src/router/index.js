import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import DashboardView from '../views/DashboardView.vue'
import AddReviewView from '../views/AddReviewView.vue'

const routes = [
  {
    path: '/admin/login',
    name: 'Login',
    component: LoginView
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
    path: '/',
    redirect: '/admin'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
