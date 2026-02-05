import MockAdapter from 'axios-mock-adapter';
import api from './index';

const mock = new MockAdapter(api, { delayResponse: 500 });

// 가짜 리뷰 데이터
let reviews = [
  {
    id: '1',
    title: 'Atomic Habits',
    author: 'James Clear',
    rating: 5,
    reviewDate: '2026-01-18',
    coverImageUrl: 'https://images-na.ssl-images-amazon.com/images/I/91bYsX41DVL.jpg',
    content: '<p>This book explains how small changes can lead to remarkable results.</p>'
  },
  {
    id: '2',
    title: 'The Midnight Library',
    author: 'Matt Haig',
    rating: 4,
    reviewDate: '2026-01-15',
    coverImageUrl: 'https://images-na.ssl-images-amazon.com/images/I/810vIom6C6L.jpg',
    content: '<p>A beautiful story about the choices that make up a life.</p>'
  }
];

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
mock.onGet('/api/reviews').reply(200, reviews);

// 4.2 Get Book Review Detail
mock.onGet(/\/api\/reviews\/\d+/).reply(config => {
  const id = config.url.split('/').pop();
  const review = reviews.find(r => r.id === id);
  return review ? [200, review] : [404, { message: 'Review not found' }];
});

console.log('Mock API initialized');

export default mock;
