package com.luna.warmteaandhonestreviews.repository;


import com.luna.warmteaandhonestreviews.config.MongoDBConfig;
import com.luna.warmteaandhonestreviews.domain.AdminUserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(MongoDBConfig.class)
@DataMongoTest
public class AdminUserRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(AdminUserRepositoryTest.class);
    @Autowired
    AdminUserRepository adminUserRepository;

    @AfterEach
    void tearDown() {
        adminUserRepository.deleteAll();
    }

    @Test
    void saveTest() {
        // given
        AdminUserEntity userEntity = new AdminUserEntity("hoseopi", "1234");

        // when
        AdminUserEntity saved = adminUserRepository.save(userEntity);

        // then
        assertThat(saved).isNotNull();
        log.info("saved={}", saved);
        assertThat(saved.getUsername()).isEqualTo("hoseopi");
        assertThat(saved.getPassword()).isEqualTo("1234");
    }
}
