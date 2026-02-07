package com.luna.warmteaandhonestreviews.service;

import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import com.luna.warmteaandhonestreviews.dto.GetReviewsRespDto;
import com.luna.warmteaandhonestreviews.dto.ReviewDto;
import com.luna.warmteaandhonestreviews.repository.BookReviewRepository;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final BookReviewRepository bookReviewRepository;

    public ReviewService(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    @Transactional(readOnly = true)
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
