package com.example.bookreviewapp;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BookService {
    @GET("/api/books")
    Call<List<Book>> listBooks();

    @GET("/api/books/{id}/reviews")
    Call<List<Review>> getBookReviews(@Path("id") int bookId);

    @POST("/api/reviews")
    Call<Void> submitReview(@Body Review review);

    @GET("/api/books/search/{search}")
    Call<List<Book>> listSearchedBooks(@Path("search") String searchQuery);

}

