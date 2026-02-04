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
            Book Title
            <input type="text" v-model="form.title" required />
          </label>

          <label>
            Author
            <input type="text" v-model="form.author" required />
          </label>

          <label>
            Rating (1–5)
            <input type="number" v-model.number="form.rating" min="1" max="5" required />
          </label>

          <label>
            Review Date
            <input type="date" v-model="form.reviewDate" required />
          </label>

          <label>
            Cover Image
            <input type="file" @change="handleFileChange($event, 'coverImage')" accept="image/*" />
          </label>
        </section>

        <section class="form-section">
          <h2>Review Content</h2>

          <label>
            Word File (optional)
            <input type="file" @change="handleFileChange($event, 'docFile')" accept=".doc,.docx" />
          </label>

          <label>
            Review Content (HTML)
            <textarea v-model="form.content" rows="12"></textarea>
          </label>
        </section>

        <div class="form-actions">
          <button type="button" @click="router.back()" style="margin-right: 1rem;">Cancel</button>
          <button type="submit" :disabled="isSubmitting">
            {{ isSubmitting ? 'Publishing...' : 'Publish Review' }}
          </button>
        </div>
      </form>
    </main>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '../api';

const router = useRouter();
const isSubmitting = ref(false);

const form = reactive({
  title: '',
  author: '',
  rating: 5,
  reviewDate: new Date().toISOString().split('T')[0],
  content: '',
  coverImage: null,
  docFile: null
});

const handleFileChange = (event, field) => {
  form[field] = event.target.files[0];
};

const handleSubmit = async () => {
  isSubmitting.value = true;
  
  try {
    const formData = new FormData();
    formData.append('title', form.title);
    formData.append('author', form.author);
    formData.append('rating', form.rating);
    formData.append('reviewDate', form.reviewDate);
    formData.append('content', form.content);
    
    if (form.coverImage) {
      formData.append('coverImage', form.coverImage);
    }
    
    if (form.docFile) {
      // API 명세에는 명시되어 있지 않지만 HTML 폼에 존재하므로 포함
      formData.append('docFile', form.docFile);
    }

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
    router.push('/admin/login');
  } catch (err) {
    router.push('/admin/login');
  }
};
</script>
