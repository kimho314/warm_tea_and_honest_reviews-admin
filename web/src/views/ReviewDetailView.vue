<template>
  <div>
    <header class="admin-header">
      <h1>Review Detail</h1>
      <button @click="handleLogout" class="logout-btn">Logout</button>
    </header>

    <main class="admin-main">
      <div v-if="loading">Loading...</div>
      <div v-else-if="error" class="auth-error">{{ error }}</div>
      <article v-else class="review-form">
        <section class="form-section">
          <h2>{{ review.title }}</h2>
          <p><strong>Author:</strong> {{ review.author }}</p>
          <p><strong>Rating:</strong> {{ review.rating }} / 5</p>
          <p><strong>Date:</strong> {{ review.publishedAt || review.createdAt }}</p>
          <div v-if="review.coverImage" style="margin-top: 1rem;">
            <img :src="review.coverImage" alt="Cover Image" style="max-width: 200px; border: 1px solid #ddd;" />
          </div>
        </section>

        <section class="form-section">
          <h3>Excerpt</h3>
          <div class="review-content">{{ review.excerpt }}</div>
        </section>

        <section v-if="review.content" class="form-section">
          <h3>Content</h3>
          <div class="review-content ql-editor" v-html="review.content"></div>
        </section>

        <div class="form-actions">
          <button @click="router.push('/admin')">Back to Dashboard</button>
        </div>
      </article>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import api from '../api';

const route = useRoute();
const router = useRouter();
const review = ref(null);
const loading = ref(true);
const error = ref(null);

const fetchReview = async () => {
  try {
    console.log('review id:', route.params.id)
    const response = await api.get(`/admin/reviews/${route.params.id}`);
    console.log('review response data:', response.data);
    review.value = response.data;
  } catch (err) {
    console.error('Failed to fetch review:', err);
    error.value = 'Review not found.';
  } finally {
    loading.value = false;
  }
};

onMounted(fetchReview);

const handleLogout = async () => {
  try {
    await api.post('/admin/logout');
    try { localStorage.removeItem('isAuthenticated'); } catch (_) {}
    router.push('/admin/login');
  } catch (err) {
    router.push('/admin/login');
  }
};
</script>

<style scoped>
.review-content {
  line-height: 1.6;
  border-top: 1px solid #eee;
  padding-top: 1rem;
}
</style>
