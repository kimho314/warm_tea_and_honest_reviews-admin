package com.luna.warmteaandhonestreviews.repository;

import com.luna.warmteaandhonestreviews.domain.BookReviewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends MongoRepository<BookReviewEntity, String> {
}
