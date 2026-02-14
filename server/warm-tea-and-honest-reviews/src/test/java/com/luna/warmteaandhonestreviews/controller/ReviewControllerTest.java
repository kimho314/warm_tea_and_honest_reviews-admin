package com.luna.warmteaandhonestreviews.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.luna.warmteaandhonestreviews.dto.GetReviewsRespDto;
import com.luna.warmteaandhonestreviews.dto.ReviewDto;
import com.luna.warmteaandhonestreviews.service.ReviewService;
import com.luna.warmteaandhonestreviews.service.StorageService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
            .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())))
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
            .andDo(document("getReview",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }

    @Test
    void getReviewsTest() throws Exception {
        // given
        Integer page = 1;
        Integer offset = 30;
        String adminUserId = "162a59e1-571f-42a3-a41a-edc83b03618a";

        List<ReviewDto> reviewDtos = new ArrayList<>();
        String id = UUID.randomUUID().toString();
        String title = "test title";
        String author = "test author";
        double rating = 4.5;
        int bookPage = 300;
        String language = "English";
        String category = "Fiction";
        LocalDate publishedAt = LocalDate.now();
        LocalDate createdAt = LocalDate.now();
        String coverImage = "test cover image";
        String excerpt = "test excerpt";
        ReviewDto review1 = new ReviewDto(id,
            adminUserId,
            title,
            author,
            rating,
            bookPage,
            language,
            category,
            publishedAt,
            createdAt,
            coverImage,
            excerpt);
        reviewDtos.add(review1);
        Mockito.when(reviewService.getReviews(adminUserId, page, offset))
            .thenReturn(new GetReviewsRespDto(reviewDtos,
                2L,
                page,
                offset));

        // when
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", page.toString());
        params.add("offset", offset.toString());
        ResultActions perform = mockMvc.perform(
            get("/api/reviews").params(params).accept(MediaType.APPLICATION_JSON));

        // then
        perform
            .andExpect(status().isOk())
            .andDo(document("getReviews",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("reviews[]").description("An array of reviews"),
                    fieldWithPath("reviews[].id").description("An review's id"),
                    fieldWithPath("reviews[].adminUserId").description("An review's admin user id"),
                    fieldWithPath("reviews[].title").description("An review's title"),
                    fieldWithPath("reviews[].author").description("An review's author"),
                    fieldWithPath("reviews[].rating").description("An review's rating"),
                    fieldWithPath("reviews[].page").description("An review's book page"),
                    fieldWithPath("reviews[].language").description("An review's language"),
                    fieldWithPath("reviews[].category").description("An review's category"),
                    fieldWithPath("reviews[].publishedAt").description("An review's published at"),
                    fieldWithPath("reviews[].createdAt").description("An review's created at"),
                    fieldWithPath("reviews[].coverImage").description("An review's cover image"),
                    fieldWithPath("reviews[].excerpt").description("An review's excerpt"),
                    fieldWithPath("total").description("Total number of reviews"),
                    fieldWithPath("page").description("Current page number"),
                    fieldWithPath("offset").description("Current offset")
                )));
    }
}
