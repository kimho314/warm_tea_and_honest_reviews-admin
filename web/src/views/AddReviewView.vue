<template>
  <div>
    <header class="admin-header">
      <h1>Add Book Review</h1>
      <button @click="handleLogout" class="logout-btn">Logout</button>
    </header>

    <main class="admin-main">
      <form @submit.prevent="handleSubmit" class="review-form">
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
            <input type="number" v-model.number="form.rating" min="1" max="5" step="0.1" required />
          </label>

          <label>
            Page Count <span style="color: red;">*</span>
            <input type="number" v-model.number="form.page" min="1" required />
          </label>

          <label>
            Language <span style="color: red;">*</span>
            <input type="text" v-model="form.language" required />
          </label>

          <label>
            Category <span style="color: red;">*</span>
            <input type="text" v-model="form.category" required />
          </label>

          <label>
            Published At <span style="color: red;">*</span>
            <input type="date" v-model="form.publishedAt" required />
          </label>

          <label>
            Cover Image <span style="color: red;">*</span>
            <input type="file" @change="handleFileChange($event, 'cover')" accept="image/*" required />
          </label>
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
            {{ isSubmitting ? 'Publishing...' : 'Publish Review' }}
          </button>
        </div>
      </form>
    </main>
  </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { QuillEditor } from '@vueup/vue-quill';
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import api from '../api';

const router = useRouter();
const isSubmitting = ref(false);

const today = new Date().toISOString().split('T')[0];

const form = reactive({
  title: '',
  author: '',
  rating: 5,
  page: 0,
  language: '',
  category: '',
  publishedAt: today,
  excerpt: '',
  content: '',
  cover: null
});

const isFormValid = computed(() => {
  return (
    form.title.trim() !== '' &&
    form.author.trim() !== '' &&
    form.rating >= 1 && form.rating <= 5 &&
    form.page > 0 &&
    form.language.trim() !== '' &&
    form.category.trim() !== '' &&
    form.publishedAt !== '' &&
    form.content.trim() !== '' &&
    form.content !== '' && // Quill empty content check
    form.cover !== null
  );
});

const handleFileChange = (event, field) => {
  form[field] = event.target.files[0];
};

const handleSubmit = async () => {
  isSubmitting.value = true;
  
  try {
    const formData = new FormData();
    // FormData에 각 필드를 추가할 때, API 명세에 맞춰 Content-Type을 application/json으로 설정하기 위해 Blob을 사용합니다.
    // Blob 생성자의 첫 번째 인자는 배열 형태여야 합니다.
    formData.append('title', new Blob([form.title], { type: 'application/json' }));
    formData.append('author', new Blob([form.author], { type: 'application/json' }));
    formData.append('rating', new Blob([form.rating], { type: 'application/json' }));
    
    formData.append('page', new Blob([form.page], { type: 'application/json' }));
    formData.append('language', new Blob([form.language], { type: 'application/json' }));
    formData.append('category', new Blob([form.category], { type: 'application/json' }));

    formData.append('publishedAt', new Blob([form.publishedAt], { type: 'application/json' }));
    formData.append('excerpt', new Blob([form.excerpt], { type: 'application/json' }));
    formData.append('content', new Blob([form.content], { type: 'application/json' }));
    
    formData.append('cover', form.cover);

    await api.post('/admin/reviews', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    alert('Review published successfully!');
    router.push('/admin');
  } catch (err) {
    console.error('Failed to publish review:', err);
    alert('Failed to publish review. Please check your inputs.');
  } finally {
    isSubmitting.value = false;
  }
};

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
