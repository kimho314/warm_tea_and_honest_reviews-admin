package com.luna.warmteaandhonestreviews.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.luna.warmteaandhonestreviews.AbstracTest;
import com.luna.warmteaandhonestreviews.config.MongoDBConfig;
import com.luna.warmteaandhonestreviews.domain.AdminUserEntity;
import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Import(MongoDBConfig.class)
@DataMongoTest
public class BookReviewRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(BookReviewRepositoryTest.class);

    @Autowired
    BookReviewRepository bookReviewRepository;

    @AfterEach
    void tearDown() {
//        bookReviewRepository.deleteAll();
    }

    @Test
    void saveTest() {
        // given
        String adminUserId = UUID.randomUUID().toString();
        BookReviewEntity bookReview = new BookReviewEntity(adminUserId,
            "title",
            "author",
            4.3,
            LocalDateTime.now().toInstant(ZoneOffset.UTC),
            "image",
            "html");

        // when
        BookReviewEntity saved = bookReviewRepository.save(bookReview);

        //then
        log.info("saved={}", saved);
        assertThat(saved).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("title");
        assertThat(saved.getAuthor()).isEqualTo("author");
        assertThat(saved.getRating()).isEqualTo(4.3);
        assertThat(saved.getAdminUserId()).isEqualTo(adminUserId);
        assertThat(saved.getCoverImageUrl()).isEqualTo("image");
        assertThat(saved.getContentHtml()).isEqualTo("html");
    }

    @Test
    void findByAdminUserIdTest() {
        // given
        AdminUserEntity adminUser1 = AbstracTest.adminUser1;

        List<BookReviewEntity> list = new ArrayList<>();
        list.add(AbstracTest.bookReview1);
        list.add(AbstracTest.bookReview2);

        // when
        bookReviewRepository.saveAll(list);
        Page<BookReviewEntity> reviews = bookReviewRepository.findAllByAdminUserId(
            adminUser1.getId(),
            PageRequest.of(0, 30));

        // then
        assertThat(reviews.getTotalElements()).isEqualTo(2);
        assertThat(reviews.getContent()).hasSameElementsAs(list);
    }
}
