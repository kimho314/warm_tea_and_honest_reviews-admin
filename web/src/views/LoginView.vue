<template>
  <div class="auth-body">
    <main class="auth-container">
      <h1 class="auth-title">Admin Login</h1>

      <form @submit.prevent="handleLogin" class="auth-form">
        <label>
          Username
          <input type="text" v-model="username" required />
        </label>

        <label>
          Password
          <input type="password" v-model="password" required />
        </label>

        <button type="submit" :disabled="isLoading">
          {{ isLoading ? 'Logging in...' : 'Login' }}
        </button>
      </form>

      <p v-if="error" class="auth-error">
        {{ error }}
      </p>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '../api';

const username = ref('');
const password = ref('');
const error = ref('');
const isLoading = ref(false);
const router = useRouter();

const handleLogin = async () => {
  isLoading.value = true;
  error.value = '';
  
  try {
    const params = new URLSearchParams();
    params.append('username', username.value);
    params.append('password', password.value);

    // Spring Security form login endpoint
    await api.post('/admin/login', params, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    });
    
    // 성공 시 대시보드로 이동
    router.push('/admin');
  } catch (err) {
    console.error('Login failed:', err);
    error.value = 'Invalid username or password';
  } finally {
    isLoading.value = false;
  }
};
</script>
