package com.luna.warmteaandhonestreviews.service;

import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import com.luna.warmteaandhonestreviews.dto.GetReviewsRespDto;
import com.luna.warmteaandhonestreviews.dto.ReviewDto;
import com.luna.warmteaandhonestreviews.exception.ReviewNotFoundException;
import com.luna.warmteaandhonestreviews.repository.BookReviewRepository;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final BookReviewRepository bookReviewRepository;

    public ReviewService(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    public ReviewDto getReview(@NonNull String adminUserId, @NonNull String reviewId) {
        return bookReviewRepository.findByAdminUserIdAndId(adminUserId, reviewId)
            .map(ReviewDto::of)
            .orElseThrow(
                () -> new ReviewNotFoundException("Review not found with id: " + reviewId));
    }

    public GetReviewsRespDto getReviews(@NonNull String adminUserId,
        @NonNull Integer page,
        @NonNull Integer offset) {
        Page<BookReviewEntity> adminUsers = bookReviewRepository.findAllByAdminUserId(
            adminUserId,
            PageRequest.of(page, offset)
        );

        List<ReviewDto> reviewDtos = adminUsers.getContent().stream()
            .map(ReviewDto::of)
            .toList();

        return new GetReviewsRespDto(reviewDtos,
            adminUsers.getTotalElements(),
            adminUsers.getNumber(),
            adminUsers.getSize());
    }
}
