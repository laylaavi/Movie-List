package com.example.mobi6006_la26_group_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobi6006_la26_group_project.Object.Movie;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolderHome> {
    ArrayList<Movie> movieArrayList;
    Context ctx;

    public AdapterHome(ArrayList<Movie> movieArrayList, Context ctx) {
        this.movieArrayList = movieArrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.rv_home, parent, false);
        return new ViewHolderHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHome holder, int position) {
        Movie movie = movieArrayList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvGenre.setText(movie.getGenre());
        holder.tvRating.setText("Rating: " + movie.getRating());
        String imgUrl = movie.getPoster();
        Glide.with(ctx)
                .load(imgUrl)
                .thumbnail(0.5f)
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class ViewHolderHome extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvGenre, tvRating;
        ImageView ivPoster;

        public ViewHolderHome(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_Title);
            tvGenre = itemView.findViewById(R.id.tv_Genre);
            tvRating = itemView.findViewById(R.id.tv_Rating);
            ivPoster = itemView.findViewById(R.id.iv_Poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
            intent.putExtra("position", position);
            // v.getContext().startActivity(intent) error when running => android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag.
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);
        }
    }
}
