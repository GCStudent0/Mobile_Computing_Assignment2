package com.example.mobile_computing_assignment2.ReceyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mobile_computing_assignment2.R;
import com.example.mobile_computing_assignment2.model.MovieItemModel;

import java.util.ArrayList;
import java.util.List;


//adapter to display movies in recycler view
public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

    //list for movie items
    public List<MovieItemModel> movieItemModelArrayList = new ArrayList<>();
    //context for the adapter
    Context context;

    //create click listener from interface
    public ItemClickListener clickListener;

    //constructor for adapter
    public MyAdapter(Context context, List<MovieItemModel> items) {
        this.context = context;
        this.movieItemModelArrayList = items;
    }

    //create a click listener from the activity
    public void setClickListener(ItemClickListener myListener) {
        this.clickListener = myListener;
    }

    //create view holder for each item in the recycler view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView, this.clickListener);

        //pass the item view AND the click listener.
        // each item will have its view AND a click listener pointing back to the adapter.
        return new ViewHolder(itemView, clickListener);
    }

    //set up bind for view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItemModel movie = movieItemModelArrayList.get(position);
        holder.itemTitle.setText(movie.getTitle());
        holder.itemYear.setText(movie.getYear());

        // load image with Glide lib
        Glide.with(context)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_launcher_background)
                //recycler view not loading due to image cache?
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.itemImage);
    }

    //get item count of list
    @Override
    public int getItemCount() {
        return movieItemModelArrayList.size();
    }

    // clear and than update the data and refresh the adapter
    public void updateData(List<MovieItemModel> newMovies) {
        if (newMovies != null) {
            Log.d("Test", "Updating data with " + newMovies.size() + " movies.");
            movieItemModelArrayList.clear();
            movieItemModelArrayList.addAll(newMovies);
            notifyDataSetChanged();
        }
    }
}