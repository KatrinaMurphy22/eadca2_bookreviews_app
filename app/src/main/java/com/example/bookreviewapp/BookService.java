package com.example.bookreviewapp;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
import retrofit2.http.Path;

public interface BookService {
    @GET("/api/books")
    Call<List<Book>> listBooks();

    @GET("/api/books/{id}/reviews")
    Call<List<Review>> getBookReviews(@Path("id") int bookId);
}

