package com.example.mobile_computing_assignment2.ReceyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_computing_assignment2.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    //item layout
    ImageView itemImage;
    TextView itemTitle;
    TextView itemYear;

    //click listener
    ItemClickListener itemClickListener;

    //constructor for view holder
    public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);

        itemImage = itemView.findViewById(R.id.itemImage);
        itemTitle = itemView.findViewById(R.id.itemTitle);
        itemYear = itemView.findViewById(R.id.itemYear);

        //store a reference to the click listener
        this.itemClickListener = itemClickListener;


        //detect click on each item and pass down to adapter to than be passed back to activity
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Test", "Item Clicked");
                // pass click listener back to the adapter
                itemClickListener.onClick(view, getAdapterPosition());
            }
        });

    }

}
