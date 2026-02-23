package com.luna.warmteaandhonestreviews.dto;

import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import java.time.LocalDate;
import java.time.ZoneOffset;

public record ReviewDto(String id,
                        String adminUserId,
                        String title,
                        String author,
                        Double rating,
                        Integer page,
                        String language,
                        String category,
                        LocalDate publishedAt,
                        LocalDate createdAt,
                        String coverImage,
                        String excerpt,
                        String content) {

    public static ReviewDto of(BookReviewEntity bookReviewEntity) {
        return new ReviewDto(bookReviewEntity.getId(),
            bookReviewEntity.getAdminUserId(),
            bookReviewEntity.getTitle(),
            bookReviewEntity.getAuthor(),
            bookReviewEntity.getRating(),
            bookReviewEntity.getPage(),
            bookReviewEntity.getLanguage(),
            bookReviewEntity.getCategory(),
            LocalDate.ofInstant(bookReviewEntity.getPublishedAt(), ZoneOffset.UTC),
            LocalDate.ofInstant(bookReviewEntity.getCreatedAt(), ZoneOffset.UTC),
            bookReviewEntity.getCoverImage(),
            bookReviewEntity.getExcerpt(),
            bookReviewEntity.getContents());
    }

}
