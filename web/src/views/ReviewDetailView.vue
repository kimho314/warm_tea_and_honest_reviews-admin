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
          <div v-if="coverImageUrl" style="margin-top: 1rem;">
            <img :src="coverImageUrl" alt="Cover Image" style="max-width: 200px; border: 1px solid #ddd;" />
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
import { ref, onMounted, onUnmounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import api from '../api';

const route = useRoute();
const router = useRouter();
const review = ref(null);
const loading = ref(true);
const error = ref(null);
const coverImageUrl = ref('');

const fetchReview = async () => {
  try {
    const id = route.params.id;
    console.log('review id:', id)
    const response = await api.get(`/admin/reviews/${id}`);
    console.log('review response data:', response.data);
    review.value = response.data;

    // Fetch image
    try {
      const imageResponse = await api.get(`/admin/reviews/${id}/image`, {
        responseType: 'blob'
      });
      if (coverImageUrl.value) {
        URL.revokeObjectURL(coverImageUrl.value);
      }
      coverImageUrl.value = URL.createObjectURL(imageResponse.data);
    } catch (imageErr) {
      console.error('Failed to fetch review image:', imageErr);
      // Fallback to existing coverImage or placeholder if needed
    }

  } catch (err) {
    console.error('Failed to fetch review:', err);
    error.value = 'Review not found.';
  } finally {
    loading.value = false;
  }
};

onMounted(fetchReview);

onUnmounted(() => {
  if (coverImageUrl.value) {
    URL.revokeObjectURL(coverImageUrl.value);
  }
});

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
