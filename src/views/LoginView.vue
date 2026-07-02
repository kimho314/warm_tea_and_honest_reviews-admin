<template>
  <div class="auth-body" :class="{ 'light-on': isLampOn }">
    <div class="lamp-container">
      <div class="lamp">
        <div class="lamp-shade"></div>
        <div class="lamp-stand"></div>
        <div class="pull-chain" @click="toggleLamp" :class="{ 'pulled': isPulled }">
          <div class="chain-line"></div>
          <div class="chain-knob"></div>
        </div>
        <div class="lamp-light" v-if="isLampOn"></div>
      </div>
    </div>

    <main class="auth-container" :style="{ visibility: isLampOn ? 'visible' : 'hidden', pointerEvents: isLampOn ? 'all' : 'none' }">
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
const isLampOn = ref(false);
const isPulled = ref(false);
const router = useRouter();

const toggleLamp = () => {
  if (isPulled.value) return; // Prevent double clicks
  isPulled.value = true;
  
  // Try to play a click sound if available (optional)
  try {
    const audio = new Audio('https://raw.githubusercontent.com/StarKnightt/Light-Bulb/main/click.mp3');
    audio.volume = 0.2;
    audio.play().catch((reason) => {
      console.warn(reason);
    }); // Ignore errors if browser blocks autoplay
  } catch (e) {}

  setTimeout(() => {
    isPulled.value = false;
    isLampOn.value = !isLampOn.value;
  }, 150);
};

const handleLogin = async () => {
  isLoading.value = true;
  error.value = ''; // 다음 시도 시에도 이전 에러 메시지를 유지함
  
  //Test account bypass
  if (username.value === 'test' && password.value === '1234') {
    try {
      localStorage.setItem('isAuthenticated', 'true');
      const token = btoa(`${username.value}:${password.value}`);
      localStorage.setItem('basicToken', token);
    } catch (_) {}
    router.push('/admin');
    isLoading.value = false;
    return;
  }
  
  try {
    // Basic Auth를 위한 로그인 API 호출 (POST /login with JSON)
    await api.post('/admin/login', {
      username: username.value,
      password: password.value
    });

    // 성공 시 Basic Auth 토큰 생성 및 저장
    try { 
      const token = btoa(`${username.value}:${password.value}`);
      localStorage.setItem('basicToken', token);
      localStorage.setItem('isAuthenticated', 'true'); 
    } catch (_) {}
    
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
