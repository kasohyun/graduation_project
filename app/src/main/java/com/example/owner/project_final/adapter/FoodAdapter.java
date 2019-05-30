package com.example.owner.project_final.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.owner.project_final.L;
import com.example.owner.project_final.R;
import com.example.owner.project_final.model.FoodWrite;

import java.util.ArrayList;

public abstract class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ItemViewHolder> {
    public abstract void selectItem(FoodWrite item);

    private Context mContext;
    private ArrayList<FoodWrite> mFoodWriteList;

    public FoodAdapter(Context mContext) {
        this.mContext = mContext;
        this.mFoodWriteList = new ArrayList<>();
    }

    public synchronized void insertData(FoodWrite list) {
        this.mFoodWriteList.add(list);
        notifyItemInserted(mFoodWriteList.size() - 1);
    }

    public void clear() {
        if (mFoodWriteList != null && mFoodWriteList.size() > 0) {
            mFoodWriteList.clear();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_item_row, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final FoodWrite item = mFoodWriteList.get(position);
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getPostingDate());
        holder.name.setText(item.getWriter());
        holder.address.setText(item.getAddress() + " " + item.getDetailAddress());
        holder.tradeDate.setText(item.geteditTradeDate());
        holder.tradeTime.setText(item.geteditTradeTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFoodWriteList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private TextView name;
        private TextView tradeDate;
        private TextView tradeTime;
        private TextView address;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.name);
            tradeDate = (TextView) itemView.findViewById(R.id.tradeDate);
            tradeTime = (TextView) itemView.findViewById(R.id.tradeTime);
            address = (TextView) itemView.findViewById(R.id.address);
        }
    }
}
