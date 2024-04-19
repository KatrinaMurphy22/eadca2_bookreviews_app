package com.example.bookreviewapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        int bookId = getIntent().getIntExtra("BOOK_ID", 0);

        // Assuming you have a TextView to display reviews
        TextView reviewsView = findViewById(R.id.reviews_text_view);

        // Initialize Retrofit and fetch reviews
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ca2bookreviewapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BookService bookService = retrofit.create(BookService.class);
        bookService.getBookReviews(bookId).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StringBuilder reviewsText = new StringBuilder();
                    for (Review review : response.body()) {
                        reviewsText.append("Rating: ").append(review.getRating())
                                .append("\nComment: ").append(review.getComment())
                                .append("\nReviewer: ").append(review.getReviewer())
                                .append("\n\n");
                    }
                    reviewsView.setText(reviewsText.toString());
                } else {
                    reviewsView.setText("Failed to retrieve reviews.");
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                reviewsView.setText("Error loading reviews.");
            }
        });
    }
}

