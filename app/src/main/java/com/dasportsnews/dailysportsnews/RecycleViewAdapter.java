package com.dasportsnews.dailysportsnews;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<NewsInfo> data;
    private NewsInfo current;
    private int currentPos = 0;

    public RecycleViewAdapter(Context context, List<NewsInfo> data) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_card, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        NewsInfo current = data.get(position);
        myHolder.textTitle.setText(current.getTitle());
        myHolder.url = current.getLink();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private class MyHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView textTitle;
        private String url;

        public MyHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.cardTitle);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsActivity.class);
                    intent.putExtra("url", "http://www.dasportsnews.com/dosrochnaya-pobeda-usika/");
                    context.startActivity(intent);
                }
            });
        }


    }
}
