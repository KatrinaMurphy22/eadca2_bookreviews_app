package com.example.bookreviewapp;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface BookService {
    @GET("/api/books")
    Call<List<Book>> listBooks();
}

