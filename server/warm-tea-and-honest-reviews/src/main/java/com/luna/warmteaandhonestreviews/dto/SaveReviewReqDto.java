package com.luna.warmteaandhonestreviews.dto;

import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import java.time.LocalDate;
import java.time.ZoneOffset;

public record SaveReviewReqDto(String adminUserId,
                               String title,
                               String author,
                               Double rating,
                               Integer page,
                               String language,
                               String category,
                               LocalDate publishedAt,
                               String excerpt,
                               String coverImage,
                               String contents) {

    public BookReviewEntity toEntity() {
        return new BookReviewEntity(adminUserId,
            title,
            author,
            rating,
            page,
            language,
            category,
            publishedAt.atStartOfDay(ZoneOffset.UTC).toInstant(),
            coverImage,
            contents,
            excerpt);
    }
}
