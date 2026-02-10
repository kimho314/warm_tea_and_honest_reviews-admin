package com.luna.warmteaandhonestreviews.domain;

import java.time.Instant;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book_reviews")
public class BookReviewEntity extends BaseEntity {

    @Id
    private String id;
    private String adminUserId;
    private String title;
    private String author;
    private Double rating;
    private Integer page;
    private String language;
    private String category;
    private Instant publishedAt;
    private String coverImage;
    private String excerpt;


    public BookReviewEntity() {
    }

    public BookReviewEntity(String adminUserId,
        String title,
        String author,
        Double rating,
        Integer page,
        String language,
        String category,
        Instant publishedAt,
        String coverImage,
        String excerpt) {
        this(null,
            adminUserId,
            title,
            author,
            rating,
            page,
            language,
            category,
            publishedAt,
            coverImage,
            excerpt);
    }

    public BookReviewEntity(String id,
        String adminUserId,
        String title,
        String author,
        Double rating,
        Integer page,
        String language,
        String category,
        Instant publishedAt,
        String coverImage,
        String excerpt) {
        this.id = id;
        this.adminUserId = adminUserId;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.page = page;
        this.language = language;
        this.category = category;
        this.publishedAt = publishedAt;
        this.coverImage = coverImage;
        this.excerpt = excerpt;
    }

    public String getId() {
        return id;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getPage() {
        return page;
    }

    public String getLanguage() {
        return language;
    }

    public String getCategory() {
        return category;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public String getExcerpt() {
        return excerpt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookReviewEntity that = (BookReviewEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BookReviewEntity{" +
            "id='" + id + '\'' +
            ", adminUserId='" + adminUserId + '\'' +
            ", title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", rating=" + rating +
            ", page=" + page +
            ", language='" + language + '\'' +
            ", category='" + category + '\'' +
            ", publishedAt=" + publishedAt +
            ", coverImage='" + coverImage + '\'' +
            ", excerpt='" + excerpt + '\'' +
            "} " + super.toString();
    }
}
