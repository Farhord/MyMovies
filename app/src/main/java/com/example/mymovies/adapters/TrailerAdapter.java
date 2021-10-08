package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.data.Trailer;
import com.example.mymovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private ArrayList<Trailer> trailerList;

    public TrailerAdapter() {
        trailerList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        holder.textViewName.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    private OnTrailerClickListener onTrailerClickListener;

    public interface OnTrailerClickListener {
        void onTrailerClick(String url);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewNameOfVideo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTrailerClickListener != null) {
                        onTrailerClickListener.onTrailerClick(trailerList.get(getAdapterPosition()).getKey());
                    }
                }
            });
        }
    }

    public void setTrailerList(ArrayList<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }

    public ArrayList<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }
}
