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
          <p><strong>Date:</strong> {{ review.reviewDate }}</p>
          <div v-if="review.coverImageUrl" style="margin-top: 1rem;">
            <img :src="review.coverImageUrl" alt="Cover Image" style="max-width: 200px; border: 1px solid #ddd;" />
          </div>
        </section>

        <section class="form-section">
          <h3>Content</h3>
          <div v-html="review.content" class="review-content"></div>
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
import api from '../api';

const route = useRoute();
const router = useRouter();
const review = ref(null);
const loading = ref(true);
const error = ref(null);

const fetchReview = async () => {
  try {
    const response = await api.get(`/api/reviews/${route.params.id}`);
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
