<template>
  <div>
    <header class="admin-header">
      <h1>Admin</h1>
      <button @click="handleLogout" class="logout-btn">Logout</button>
    </header>

    <main class="admin-main">
      <nav class="admin-nav">
        <router-link to="/admin/reviews/new">Add Book Review</router-link>
      </nav>
      
      <!-- PRD에는 없지만 실제 사용성을 위해 간단한 환영 메시지 추가 -->
      <section style="margin-top: 2rem;">
        <p>Welcome to the Book Review Admin System.</p>
      </section>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import api from '../api';

const router = useRouter();

const handleLogout = async () => {
  try {
    await api.post('/admin/logout');
    router.push('/admin/login');
  } catch (err) {
    console.error('Logout failed:', err);
    // 세션이 이미 만료된 경우에도 로그인 페이지로 이동
    router.push('/admin/login');
  }
};
</script>
