package com.luna.warmteaandhonestreviews.dto;

import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import java.time.LocalDate;
import java.time.ZoneOffset;

public record ReviewDto(String id,
                        String title,
                        String author,
                        Double rating,
                        LocalDate reviewDate,
                        String coverImageUrl,
                        String content) {

    public static ReviewDto of(BookReviewEntity bookReviewEntity) {
        return new ReviewDto(bookReviewEntity.getId(),
            bookReviewEntity.getTitle(),
            bookReviewEntity.getAuthor(),
            bookReviewEntity.getRating(),
            LocalDate.ofInstant(bookReviewEntity.getReviewDate(), ZoneOffset.UTC),
            bookReviewEntity.getCoverImageUrl(),
            bookReviewEntity.getContentHtml());
    }

}
