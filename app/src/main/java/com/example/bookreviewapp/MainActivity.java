package com.example.bookreviewapp;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.util.Log;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BooksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ca2bookreviewapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the API interface
        BookService bookService = retrofit.create(BookService.class);

        // Display progress bar while fetching data
        recyclerView.setVisibility(View.GONE);

        // Fetch the books
        bookService.listBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body();
                    if (!books.isEmpty()) {
                        // Initialize adapter and set it to the RecyclerView
                        adapter = new BooksAdapter(books);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        // Handle empty data scenario
                        Log.e("MainActivity", "No books found");
                    }
                } else {
                    Log.e("MainActivity", "Error in API response");
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("MainActivity", "API call failed", t);
                // Optionally, you could display an error message to the user or retry button
            }
        });
    }
}
