package com.zy.statuslayoutdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 作者：赵岩 on 2019/1/23 16:36
 */
public class RlvAdapter extends RecyclerView.Adapter<RlvAdapter.Holder> {
    private Context context;
    private ArrayList<String> data;

    public RlvAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.mItemTv.setText(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView mItemTv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.mItemTv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
