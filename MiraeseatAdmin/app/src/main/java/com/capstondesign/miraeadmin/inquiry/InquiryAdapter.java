package com.capstondesign.miraeadmin.inquiry;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstondesign.miraeadmin.R;

import java.util.ArrayList;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryAdapter.ViewHolder> implements OnInquiryClickListener {
    ArrayList<Inquiry> items = new ArrayList<Inquiry>();

    OnInquiryClickListener listener;

    int layoutType = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.single_inquiry, viewGroup, false);

        return new ViewHolder(itemView, this, layoutType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Inquiry item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Inquiry item) {
        items.add(item);
    }

    public void setItems(ArrayList<Inquiry> items) {
        this.items = items;
    }

    public Inquiry getItem(int position) {
        return items.get(position);
    }

    public void setOnInquiryClickListener(OnInquiryClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout InquiryLayout;
        TextView textTitle;
        TextView textDate;

        public ViewHolder(View itemView, final OnInquiryClickListener listener, int layoutType) {
            super(itemView);

            InquiryLayout = itemView.findViewById(R.id.InquiryLayout);

            textTitle = itemView.findViewById(R.id.textTitle);

            textDate = itemView.findViewById(R.id.textDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(Inquiry item) {

            textTitle.setText(item.getInquiryTitle());
            textDate.setText(item.getInquiryTime());

            if(item.getIsChecked() == "true") {
                textTitle.setTextColor(Color.parseColor("#A6A6A6"));
            }
        }
    }
}
