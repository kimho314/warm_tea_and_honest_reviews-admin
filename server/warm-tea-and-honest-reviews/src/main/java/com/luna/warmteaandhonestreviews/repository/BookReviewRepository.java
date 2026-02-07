package com.luna.warmteaandhonestreviews.repository;

import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends MongoRepository<BookReviewEntity, String> {

    Page<BookReviewEntity> findAllByAdminUserId(@NonNull String adminUserId,
        @NonNull Pageable pageable);

    Optional<BookReviewEntity> findByAdminUserIdAndId(@NonNull String adminUserId,
        @NonNull String id);
}
