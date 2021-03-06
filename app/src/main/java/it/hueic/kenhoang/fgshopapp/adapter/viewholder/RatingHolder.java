package it.hueic.kenhoang.fgshopapp.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import it.hueic.kenhoang.fgshopapp.R;

public class RatingHolder extends RecyclerView.ViewHolder {
    public TextView name, time, comment;
    public ImageView avatar;
    public RatingBar ratingBar;

    public RatingHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        time = itemView.findViewById(R.id.time);
        comment = itemView.findViewById(R.id.comment);
        avatar = itemView.findViewById(R.id.avatar);
        ratingBar = itemView.findViewById(R.id.ratingBar);
    }
}
