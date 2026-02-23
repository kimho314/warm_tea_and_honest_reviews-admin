package com.luna.warmteaandhonestreviews;

import com.luna.warmteaandhonestreviews.domain.AdminUserEntity;
import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class AbstractTest {

    public static AdminUserEntity adminUser1 = new AdminUserEntity(UUID.randomUUID().toString(),
        "test1",
        "1234");
    public static BookReviewEntity bookReview1 = new BookReviewEntity(adminUser1.getId(),
        "title1",
        "author1",
        4.3,
        300,
        "English",
        "Fiction",
        LocalDateTime.now().toInstant(ZoneOffset.UTC),
        "/image1",
        "contents1",
        "excerpt1");
    public static BookReviewEntity bookReview2 = new BookReviewEntity(adminUser1.getId(),
        "title2",
        "author2",
        4.0,
        200,
        "English",
        "Romance",
        LocalDateTime.now().toInstant(ZoneOffset.UTC),
        "/image2",
        "contents2",
        "excerpt2");
}
