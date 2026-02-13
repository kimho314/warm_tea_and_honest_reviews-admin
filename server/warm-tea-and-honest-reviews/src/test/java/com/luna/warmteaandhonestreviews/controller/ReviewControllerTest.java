package com.luna.warmteaandhonestreviews.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.luna.warmteaandhonestreviews.dto.ReviewDto;
import com.luna.warmteaandhonestreviews.service.ReviewService;
import com.luna.warmteaandhonestreviews.service.StorageService;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    private MockMvc mockMvc;

    @MockitoBean
    ReviewService reviewService;
    @MockitoBean
    StorageService storageService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    @Test
    void getReviewTest() throws Exception {
        // given
        String id = UUID.randomUUID().toString();
        String adminUserID = "162a59e1-571f-42a3-a41a-edc83b03618a";
        String title = "test title";
        String author = "test author";
        double rating = 4.5;
        int page = 300;
        String language = "English";
        String category = "Fiction";
        LocalDate publishedAt = LocalDate.now();
        LocalDate createdAt = LocalDate.now();
        String coverImage = "test cover image";
        String excerpt = "test excerpt";

        Mockito.when(reviewService.getReview(adminUserID, id))
            .thenReturn(new ReviewDto(
                id,
                adminUserID,
                title,
                author,
                rating,
                page,
                language,
                category,
                publishedAt,
                createdAt,
                coverImage,
                excerpt
            ));

        // when
        ResultActions perform = mockMvc.perform(
            get("/api/reviews/{id}", id).accept(MediaType.APPLICATION_JSON));

        // then
        perform
            .andExpect(status().isOk())
            .andDo(document("index"));
    }
}
