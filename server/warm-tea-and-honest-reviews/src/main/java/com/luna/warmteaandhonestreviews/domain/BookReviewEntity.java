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
    private Instant reviewDate;
    private String coverImageUrl;
    private String contentHtml;


    public BookReviewEntity() {
    }

    public BookReviewEntity(String adminUserId,
        String title,
        String author,
        Double rating,
        Instant reviewDate,
        String coverImageUrl,
        String contentHtml) {
        this(null, adminUserId, title, author, rating, reviewDate, coverImageUrl, contentHtml);
    }

    public BookReviewEntity(String id,
        String adminUserId,
        String title,
        String author,
        Double rating,
        Instant reviewDate,
        String coverImageUrl,
        String contentHtml) {
        this.id = id;
        this.adminUserId = adminUserId;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.coverImageUrl = coverImageUrl;
        this.contentHtml = contentHtml;
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

    public Instant getReviewDate() {
        return reviewDate;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getContentHtml() {
        return contentHtml;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setReviewDate(Instant reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
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
            ", reviewData=" + reviewDate +
            ", coverImageUrl='" + coverImageUrl + '\'' +
            ", contentHtml='" + contentHtml + '\'' +
            "} " + super.toString();
    }
}
