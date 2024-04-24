package com.example.bookreviewapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Intent;

public class ReviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Retrieve book name from intent
        String bookName = getIntent().getStringExtra("BOOK_TITLE");

        // Display book name in TextView
        TextView bookNameTextView = findViewById(R.id.book_name_text_view);
        bookNameTextView.setText(bookName);

        int bookId = getIntent().getIntExtra("BOOK_ID", 0);
        TextView reviewsView = findViewById(R.id.reviews_text_view);
        Button backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Ends ReviewActivity and returns to MainActivity if it's in the back stack
            }
        });

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
                    reviewsView.setText("No Reviews Available Yet.");
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                reviewsView.setText("Error loading reviews.");
            }

        });
    }
    public void startAddReviewActivity(View view) {
        Intent intent = new Intent(this, AddReviewActivity.class);
        startActivity(intent);
    }
}
