package com.example.bookreviewapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ca2bookreviewapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the API interface
        BookService bookService = retrofit.create(BookService.class);

        // Fetch the books
        bookService.listBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    List<Book> books = response.body();
                    // TODO: Update the UI or handle the book list
                } else {
                    // Handle the error
                    Log.e("MainActivity", "Error in API response");
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                // Handle the failure
                Log.e("MainActivity", "API call failed", t);
            }
        });
    }
}
