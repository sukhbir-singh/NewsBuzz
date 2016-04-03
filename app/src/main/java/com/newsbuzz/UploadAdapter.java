package com.newsbuzz;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.viewHolder> {
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView title,description;
        public viewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title_item_upload);
       description= (TextView) itemView.findViewById(R.id.description_item_upload);
        }
    }
}