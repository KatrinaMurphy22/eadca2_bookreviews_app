package com.example.bookreviewapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.util.Log;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddReviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        Button backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Ends ReviewActivity and returns to MainActivity if it's in the back stack
            }
        });

        final EditText editTextReviewId = findViewById(R.id.editTextReviewId);
        final EditText editTextBookId = findViewById(R.id.editTextBookId);
        final EditText editTextReviewer = findViewById(R.id.editTextReviewer);
        final EditText editTextRating = findViewById(R.id.editTextRating);
        final EditText editTextComment = findViewById(R.id.editTextComment);
        Button buttonSubmitReview = findViewById(R.id.buttonSubmitReview);

        buttonSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int reviewId = Integer.parseInt(editTextReviewId.getText().toString());
                int bookId = Integer.parseInt(editTextBookId.getText().toString());
                String reviewer = editTextReviewer.getText().toString();
                int rating = Integer.parseInt(editTextRating.getText().toString());
                String comment = editTextComment.getText().toString();
                submitReview(reviewId, bookId, reviewer, rating, comment);
            }
        });
    }

    public void submitReview(int reviewId, int bookId, String reviewer, int rating, String comment) {
        Review newReview = new Review(reviewId, bookId, reviewer, rating, comment);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ca2bookreviewapi.azurewebsites.net/") // Ensure this URL is correct
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BookService bookService = retrofit.create(BookService.class);
        Call<Void> call = bookService.submitReview(newReview); // Assume submitReview is defined in your BookService interface
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful submission here, e.g., show a confirmation message
                    Log.d("Review Submission", "Review submitted successfully.");
                } else {
                    // Handle the case where the server responded with an error status
                    Log.e("Review Submission", "Failed to submit review: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the case where the network request failed
                Log.e("Review Submission", "Error submitting review: " + t.getMessage());
            }
        });
    }


}
