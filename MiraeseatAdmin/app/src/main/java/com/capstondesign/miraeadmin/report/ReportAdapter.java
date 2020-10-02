package com.capstondesign.miraeadmin.report;

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

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> implements OnReportClickListener {
    ArrayList<Report> items = new ArrayList<Report>();

    OnReportClickListener listener;

    int layoutType = 0;

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.single_report, viewGroup, false);

        return new ReportAdapter.ViewHolder(itemView, this, layoutType);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder viewHolder, int position) {
        Report item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Report item) {
        items.add(item);
    }

    public void setItems(ArrayList<Report> items) {
        this.items = items;
    }

    public Report getItem(int position) {
        return items.get(position);
    }

    public void setOnReportClickListener(OnReportClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ReportAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ReportLayout;
        TextView textDate;
        TextView textUser;

        public ViewHolder(View itemView, final OnReportClickListener listener, int layoutType) {
            super(itemView);

            ReportLayout = itemView.findViewById(R.id.ReportLayout);

            textDate = itemView.findViewById(R.id.textReportDate);

            textUser = itemView.findViewById(R.id.textReportUser);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ReportAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(Report item) {

            textDate.setText(item.getReportTime());
            textUser.setText(item.getUserEmail());

            if(item.getIsChecked().equals("true")) {
                textDate.setTextColor(Color.parseColor("#A6A6A6"));
            }
        }
    }
}
