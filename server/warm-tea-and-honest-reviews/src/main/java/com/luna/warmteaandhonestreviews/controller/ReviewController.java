package com.luna.warmteaandhonestreviews.controller;

import com.luna.warmteaandhonestreviews.dto.GetReviewsRespDto;
import com.luna.warmteaandhonestreviews.dto.ReviewDto;
import com.luna.warmteaandhonestreviews.service.ReviewService;
import com.luna.warmteaandhonestreviews.service.StorageService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final StorageService storageService;

    public ReviewController(ReviewService reviewService, StorageService storageService) {
        this.reviewService = reviewService;
        this.storageService = storageService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDto> getReview(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok(
            reviewService.getReview("162a59e1-571f-42a3-a41a-edc83b03618a", id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReviewsRespDto> getReviews(
        @NonNull @RequestParam(defaultValue = "0") Integer page,
        @NonNull @RequestParam(defaultValue = "30") Integer offset) {
        return ResponseEntity.ok(
            reviewService.getReviews("162a59e1-571f-42a3-a41a-edc83b03618a", page, offset));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReview(@RequestParam("file") MultipartFile file) {
        storageService.store(file);
        return ResponseEntity.ok().build();
    }

}
