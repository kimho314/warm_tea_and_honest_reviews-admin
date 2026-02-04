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
      
      <section style="margin-top: 2rem;">
        <h2>Book Reviews</h2>
        <div v-if="loading">Loading reviews...</div>
        <div v-else-if="reviews.length === 0">No reviews found.</div>
        <table v-else style="width: 100%; border-collapse: collapse; margin-top: 1rem;">
          <thead>
            <tr style="border-bottom: 1px solid #ddd; text-align: left;">
              <th style="padding: 0.5rem;">Title</th>
              <th style="padding: 0.5rem;">Author</th>
              <th style="padding: 0.5rem;">Rating</th>
              <th style="padding: 0.5rem;">Date</th>
              <th style="padding: 0.5rem;">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="review in reviews" :key="review.id" style="border-bottom: 1px solid #eee;">
              <td style="padding: 0.5rem;">{{ review.title }}</td>
              <td style="padding: 0.5rem;">{{ review.author }}</td>
              <td style="padding: 0.5rem;">{{ review.rating }} / 5</td>
              <td style="padding: 0.5rem;">{{ review.reviewDate }}</td>
              <td style="padding: 0.5rem;">
                <router-link :to="'/admin/reviews/' + review.id">View</router-link>
              </td>
            </tr>
          </tbody>
        </table>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import api from '../api';

const router = useRouter();
const reviews = ref([]);
const loading = ref(true);

const fetchReviews = async () => {
  try {
    const response = await api.get('/api/reviews');
    reviews.value = response.data;
  } catch (err) {
    console.error('Failed to fetch reviews:', err);
  } finally {
    loading.value = false;
  }
};

onMounted(fetchReviews);

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
