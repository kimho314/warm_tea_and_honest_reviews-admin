<template>
  <div>
    <header class="admin-header">
      <h1>Edit Book Review</h1>
      <button @click="handleLogout" class="logout-btn">Logout</button>
    </header>

    <main class="admin-main">
      <div v-if="loading">Loading review data...</div>
      <form v-else @submit.prevent="handleSubmit" class="review-form">
        <section class="form-section">
          <h2>Metadata</h2>

          <label>
            Book Title <span style="color: red;">*</span>
            <input type="text" v-model="form.title" required />
          </label>

          <label>
            Author <span style="color: red;">*</span>
            <input type="text" v-model="form.author" required />
          </label>

          <label>
            Rating (1–5) <span style="color: red;">*</span>
            <input 
              type="number" 
              v-model.number="form.rating" 
              min="1" 
              max="5" 
              step="0.5" 
              required 
              @blur="touched.rating = true"
            />
            <span v-if="touched.rating && !isRatingValid" class="error-msg">Rating must be between 1 and 5.</span>
          </label>

          <label>
            Page Count <span style="color: red;">*</span>
            <input 
              type="number" 
              v-model.number="form.page" 
              min="1" 
              required 
              @blur="touched.page = true"
            />
            <span v-if="touched.page && !isPageValid" class="error-msg">Page Count must be greater than 0.</span>
          </label>

          <label>
            Language <span style="color: red;">*</span>
            <input type="text" v-model="form.language" required />
          </label>

          <label>
            Categories (comma separated) <span style="color: red;">*</span>
            <input type="text" v-model="categoryInput" placeholder="e.g. Fiction, Mystery" required />
          </label>

          <label>
            Published At <span style="color: red;">*</span>
            <input type="date" v-model="form.publishedAt" required />
          </label>

          <label>
            Cover Image (Leave empty to keep current)
            <input type="file" @change="handleFileChange($event, 'cover')" accept="image/*" />
          </label>
          <div v-if="currentCoverUrl && !form.cover" style="margin-top: 0.5rem;">
            <p>Current Cover:</p>
            <img :src="currentCoverUrl" alt="Current Cover" style="max-width: 150px; border: 1px solid #ddd;" />
          </div>
        </section>

        <section class="form-section">
          <h2>Excerpt</h2>
          <label>
            <textarea v-model="form.excerpt" rows="8"></textarea>
          </label>
        </section>

        <section class="form-section">
          <h2>Content <span style="color: red;">*</span></h2>
          <label>
            <QuillEditor v-model:content="form.content" contentType="html" theme="snow" style="height: 300px;" />
          </label>
        </section>

        <div class="form-actions">
          <button type="button" @click="router.back()" style="margin-right: 1rem;">Cancel</button>
          <button type="submit" :disabled="isSubmitting || !isFormValid">
            {{ isSubmitting ? 'Updating...' : 'Update Review' }}
          </button>
        </div>
      </form>
    </main>
  </div>
</template>

<script setup>
import { reactive, ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { QuillEditor } from '@vueup/vue-quill';
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import api from '../api';

const router = useRouter();
const route = useRoute();
const isSubmitting = ref(false);
const loading = ref(true);
const currentCoverUrl = ref('');

const touched = reactive({
  rating: false,
  page: false
});

const isRatingValid = computed(() => form.rating >= 1 && form.rating <= 5);
const isPageValid = computed(() => form.page > 0);

const categoryInput = ref('');

const form = reactive({
  title: '',
  author: '',
  rating: '',
  page: '',
  language: '',
  categories: [],
  publishedAt: '',
  excerpt: '',
  content: '',
  cover: null
});

const isFormValid = computed(() => {
  const categories = categoryInput.value.split(',').map(c => c.trim()).filter(c => c !== '');
  return (
    form.title.trim() !== '' &&
    form.author.trim() !== '' &&
    isRatingValid.value &&
    isPageValid.value &&
    form.language.trim() !== '' &&
    categories.length > 0 &&
    form.publishedAt !== '' &&
    form.content.trim() !== '' &&
    form.content !== ''
  );
});

const fetchReviewData = async () => {
  try {
    const id = route.params.id;
    const response = await api.get(`/admin/reviews/${id}`);
    const data = response.data;
    
    form.title = data.title || '';
    form.author = data.author || '';
    form.rating = data.rating || '';
    form.page = data.page || '';
    form.language = data.language || '';
    form.excerpt = data.excerpt || '';
    form.content = data.content || '';
    
    if (data.publishedAt) {
      form.publishedAt = data.publishedAt;
    } else if (data.createdAt) {
      form.publishedAt = data.createdAt.split('T')[0];
    }
    
    if (data.categories) {
      categoryInput.value = Array.isArray(data.categories) ? data.categories.join(', ') : data.categories;
    }

    if (data.imageUrl) {
      currentCoverUrl.value = data.imageUrl;
    }
  } catch (err) {
    console.error('Failed to fetch review data:', err);
    alert('Failed to load review data.');
    router.push('/admin');
  } finally {
    loading.value = false;
  }
};

onMounted(fetchReviewData);

const handleFileChange = (event, field) => {
  form[field] = event.target.files[0];
};

const handleSubmit = async () => {
  isSubmitting.value = true;
  
  try {
    const id = route.params.id;
    const formData = new FormData();
    
    formData.append('title', new Blob([form.title], { type: 'application/json' }));
    formData.append('author', new Blob([form.author], { type: 'application/json' }));
    formData.append('rating', new Blob([form.rating], { type: 'application/json' }));
    formData.append('page', new Blob([form.page], { type: 'application/json' }));
    formData.append('language', new Blob([form.language], { type: 'application/json' }));
    
    const categories = categoryInput.value.split(',').map(c => c.trim()).filter(c => c !== '');
    formData.append('category', new Blob([JSON.stringify(categories)], { type: 'application/json' }));

    formData.append('publishedAt', new Blob([form.publishedAt], { type: 'application/json' }));
    formData.append('excerpt', new Blob([form.excerpt], { type: 'application/json' }));
    formData.append('content', new Blob([form.content], { type: 'application/json' }));
    
    if (form.cover) {
      formData.append('cover', form.cover);
    }

    await api.put(`/admin/reviews/${id}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    alert('Review updated successfully!');
    router.push(`/admin/reviews/${id}`);
  } catch (err) {
    console.error('Failed to update review:', err);
    alert('Failed to update review. Please check your inputs.');
  } finally {
    isSubmitting.value = false;
  }
};

const handleLogout = async () => {
  try {
    await api.post('/admin/logout');
    localStorage.removeItem('isAuthenticated');
    localStorage.removeItem('basicToken');
    router.push('/login');
  } catch (err) {
    localStorage.removeItem('isAuthenticated');
    localStorage.removeItem('basicToken');
    router.push('/login');
  }
};
</script>

<style scoped>
.error-msg {
  color: red;
  font-size: 0.8rem;
  display: block;
  margin-top: 0.25rem;
}
</style>
