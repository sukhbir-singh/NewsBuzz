package com.newsbuzz;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.viewHolder> {
    private Context context;

    public UploadAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Upload_item> list=new ArrayList<>();
    public  void refresh(ArrayList<Upload_item> list){
        this.list=list;
        notifyItemRangeChanged(0,list.size());
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload,parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
if(!list.get(position).title.isEmpty()&&list.get(position).title.length()!=0){
    holder.title.setText(list.get(position).title);
}
        if(!list.get(position).title.isEmpty()&&list.get(position).title.length()!=0){
            holder.description.setText(list.get(position).description);
        }
        if(!list.get(position).image.isEmpty()&&list.get(position).image.length()!=0){
            byte decoded[]= Base64.decode(list.get(position).image,Base64.DEFAULT);
            Glide.with(context).load(decoded).asBitmap().error(R.drawable.business).into(holder.imageView);
        }
        else {
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView title,description;
        ImageView imageView;
        public viewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title_item_upload);
       description= (TextView) itemView.findViewById(R.id.description_item_upload);
            imageView= (ImageView) itemView.findViewById(R.id.image_item_upload);
        }
    }
}
