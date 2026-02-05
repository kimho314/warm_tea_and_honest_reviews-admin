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
        <div v-else>
          <table style="width: 100%; border-collapse: collapse; margin-top: 1rem;">
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

          <!-- Pagination -->
          <div class="pagination" style="margin-top: 2rem; display: flex; justify-content: center; align-items: center; gap: 1rem;">
            <button 
              @click="changePage(currentPage - 1)" 
              :disabled="currentPage === 1"
              style="padding: 0.5rem 1rem;"
            >
              Previous
            </button>
            <span>Page {{ currentPage }} of {{ totalPages }} (Total: {{ totalCount }})</span>
            <button 
              @click="changePage(currentPage + 1)" 
              :disabled="currentPage >= totalPages"
              style="padding: 0.5rem 1rem;"
            >
              Next
            </button>
          </div>
        </div>
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
const currentPage = ref(1);
const totalPages = ref(1);
const totalCount = ref(0);
const offset = 30;

const fetchReviews = async (page = 1) => {
  loading.value = true;
  try {
    const response = await api.get(`/api/reviews?page=${page}&offset=${offset}`);
    reviews.value = response.data;
    
    if (response.data.length > 0) {
      totalCount.value = response.data[0].total || 0;
      currentPage.value = response.data[0].page || page;
      totalPages.value = Math.ceil(totalCount.value / offset) || 1;
    } else {
      totalCount.value = 0;
      currentPage.value = page;
      totalPages.value = 1;
    }
  } catch (err) {
    console.error('Failed to fetch reviews:', err);
  } finally {
    loading.value = false;
  }
};

const changePage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    fetchReviews(page);
  }
};

onMounted(() => fetchReviews(1));

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
