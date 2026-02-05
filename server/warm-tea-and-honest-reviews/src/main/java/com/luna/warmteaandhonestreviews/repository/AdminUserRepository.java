package com.luna.warmteaandhonestreviews.repository;

import com.luna.warmteaandhonestreviews.domain.AdminUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends MongoRepository<AdminUserEntity, String> {
}
