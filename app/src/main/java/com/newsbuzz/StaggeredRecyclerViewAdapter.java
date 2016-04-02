package com.newsbuzz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sukhbir on 2/4/16.
 */

public class StaggeredRecyclerViewAdapter  extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.StaggeredViewHolder> {

    private ArrayList<ItemStaggered> itemList;
    private Context context;

    public StaggeredRecyclerViewAdapter(Context context, ArrayList<ItemStaggered> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public StaggeredViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.staggered_card,parent,false);
        return new StaggeredViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaggeredViewHolder holder, int position) {
        holder.title.setText(itemList.get(position).getTitle());
        holder.background.setImageResource(itemList.get(position).getImage_url());
    }


    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public static class StaggeredViewHolder extends RecyclerView.ViewHolder {

        public ImageView background;
        public TextView title;

        public StaggeredViewHolder(View itemView) {
            super(itemView);
            background=(ImageView)itemView.findViewById(R.id.background_staggeredcard);
            title=(TextView)itemView.findViewById(R.id.title_staggeredcard);
        }

    }
}