import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/admin.css'

// Mock API 설정
if (import.meta.env.DEV) {
  import('./api/mock');
}

const app = createApp(App)
app.use(router)
app.mount('#app')
