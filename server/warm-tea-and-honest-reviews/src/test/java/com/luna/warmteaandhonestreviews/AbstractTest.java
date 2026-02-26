package com.luna.warmteaandhonestreviews;

import com.luna.warmteaandhonestreviews.auth.RoleEnum;
import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import com.luna.warmteaandhonestreviews.domain.UserEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class AbstractTest {

    public static UserEntity adminUser1 = new UserEntity(UUID.randomUUID().toString(),
        "test1",
        "1234",
        RoleEnum.ADMIN.getRole());
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
