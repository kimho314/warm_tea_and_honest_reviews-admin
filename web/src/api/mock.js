import MockAdapter from 'axios-mock-adapter';
import api from './index';

const mock = new MockAdapter(api, { delayResponse: 500 });

// 가짜 리뷰 데이터
let reviews = Array.from({ length: 35 }, (_, i) => {
  const id = String(i + 1);
  return {
    id,
    title: `Mock Book Title ${id}`,
    author: `Author ${id}`,
    rating: (i % 5) + 1,
    reviewDate: new Date(2026, 0, 35 - i).toISOString().split('T')[0],
    coverImageUrl: `https://via.placeholder.com/150?text=Book+${id}`,
    content: `<p>This is a mock review content for book ${id}. It contains some sample text to test the layout and pagination.</p>`
  };
});

// 초기 데이터 중 일부를 구체적인 데이터로 교체 (선택 사항)
reviews[0] = {
  id: '1',
  title: 'Atomic Habits',
  author: 'James Clear',
  rating: 5,
  reviewDate: '2026-01-18',
  coverImageUrl: 'https://images-na.ssl-images-amazon.com/images/I/91bYsX41DVL.jpg',
  content: '<p>This book explains how small changes can lead to remarkable results.</p>'
};
reviews[1] = {
  id: '2',
  title: 'The Midnight Library',
  author: 'Matt Haig',
  rating: 4,
  reviewDate: '2026-01-15',
  coverImageUrl: 'https://images-na.ssl-images-amazon.com/images/I/810vIom6C6L.jpg',
  content: '<p>A beautiful story about the choices that make up a life.</p>'
};

// 1.1 Login
mock.onPost('/admin/login').reply(config => {
  const params = new URLSearchParams(config.data);
  const username = params.get('username');
  const password = params.get('password');

  if (username === 'test' && password === '1234') {
    localStorage.setItem('isAuthenticated', 'true');
    return [200, { message: 'Success' }];
  } else {
    return [401, { message: 'Invalid username or password' }];
  }
});

// 1.2 Logout
mock.onPost('/admin/logout').reply(() => {
  localStorage.removeItem('isAuthenticated');
  return [200];
});

// 3.1 Create Book Review
mock.onPost('/admin/reviews').reply(config => {
  const formData = config.data;
  const newReview = {
    id: String(reviews.length + 1),
    title: formData.get('title'),
    author: formData.get('author'),
    rating: Number(formData.get('rating')),
    reviewDate: formData.get('reviewDate'),
    coverImageUrl: 'https://via.placeholder.com/150', // 가짜 이미지 URL
    content: formData.get('content')
  };
  reviews.unshift(newReview); // 목록 맨 앞에 추가
  return [201, newReview];
});

// 4.1 Get All Book Reviews
mock.onGet(/\/admin\/reviews(\?.*)?/).reply(config => {
  console.log('Mock GET /admin/reviews called', config.url);
  const url = config.url.includes('?') ? config.url : config.url + '?';
  const params = new URLSearchParams(url.split('?')[1]);
  const page = parseInt(params.get('page')) || 0;
  const offset = parseInt(params.get('offset')) || 30;

  const start = page * offset;
  const end = start + offset;
  const paginatedReviews = reviews.slice(start, end);

  const response = {
    reviews: paginatedReviews,
    total: reviews.length,
    page: page,
    offset: offset
  };

  return [200, response];
});

// 4.2 Get Book Review Detail
mock.onGet(/\/admin\/reviews\/[a-zA-Z0-9-]+/).reply(config => {
  const id = config.url.split('/').pop();
  if (config.url.endsWith('/image')) {
    // Return a dummy transparent 1x1 pixel PNG for mock
    const dummyImage = new Uint8Array([
      0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52,
      0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x08, 0x06, 0x00, 0x00, 0x00, 0x1F, 0x15, 0xC4,
      0x89, 0x00, 0x00, 0x00, 0x0A, 0x49, 0x44, 0x41, 0x54, 0x78, 0x9C, 0x63, 0x00, 0x01, 0x00, 0x00,
      0x05, 0x00, 0x01, 0x0D, 0x0A, 0x2D, 0xB4, 0x00, 0x00, 0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, 0xAE,
      0x42, 0x60, 0x82
    ]);
    return [200, new Blob([dummyImage], { type: 'image/png' })];
  }
  console.log('Mock GET /admin/reviews detail called', id);
  const review = reviews.find(r => r.id === id);
  return review ? [200, review] : [404, { message: 'Review not found' }];
});

console.log('Mock API initialized');

export default mock;
