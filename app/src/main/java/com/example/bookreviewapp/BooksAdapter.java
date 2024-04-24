package com.example.bookreviewapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Intent;
import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    private List<Book> booksList;

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public TextView textViewAuthor;
        public TextView textViewDescription;

        public BookViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }

    public void filter(String query) {
        List<Book> filteredList = new ArrayList<>();
        for (Book book : booksList) {  // Assuming 'allBooks' stores all your books
            if (book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(book);
            }
        }
        this.booksList.clear();
        this.booksList.addAll(filteredList);
        notifyDataSetChanged();
    }


    public BooksAdapter(List<Book> booksList) {
        this.booksList = booksList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = booksList.get(position);
        holder.textViewTitle.setText(book.getTitle());
        holder.textViewAuthor.setText(book.getAuthor());
        holder.textViewDescription.setText(book.getDescription());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ReviewActivity.class);
            intent.putExtra("BOOK_ID", book.getBookId());
            intent.putExtra("BOOK_TITLE", book.getTitle());
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
}
