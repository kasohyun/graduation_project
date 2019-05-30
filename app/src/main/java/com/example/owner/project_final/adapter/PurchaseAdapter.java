package com.example.owner.project_final.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.owner.project_final.R;
import com.example.owner.project_final.model.PurchaseWrite;

import java.util.ArrayList;

public abstract class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ItemViewHolder> {
    public abstract void selectItem(PurchaseWrite item);

    private Context mContext;
    private ArrayList<PurchaseWrite> mPurchaseWriteList;

    public PurchaseAdapter(Context mContext) {
        this.mContext = mContext;
        this.mPurchaseWriteList = new ArrayList<>();
    }

    public synchronized void insertData(PurchaseWrite list) {
        this.mPurchaseWriteList.add(list);
        notifyItemInserted(mPurchaseWriteList.size() - 1);
    }

    public void clear() {
        if (mPurchaseWriteList != null && mPurchaseWriteList.size() > 0) {
            mPurchaseWriteList.clear();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchase_item_row, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final PurchaseWrite item = mPurchaseWriteList.get(position);
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getPostingDate());
        holder.name.setText(item.getWriter());
        holder.address.setText(item.getAddress() + " " + item.getDetailAddress());
        holder.tradeDate.setText(item.geteditTradeDate());
        holder.tradeTime.setText(item.geteditTradeTime());
        //holder.cost.setText(item.geteditCost());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPurchaseWriteList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private TextView name;
        private TextView tradeDate;
        private TextView tradeTime;
        //private  TextView cost;
        private TextView address;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.name);
            tradeDate = (TextView) itemView.findViewById(R.id.tradeDate);
            tradeTime = (TextView) itemView.findViewById(R.id.tradeTime);
            //cost = (TextView) itemView.findViewById(R.id.cost) ;
            address = (TextView) itemView.findViewById(R.id.address);
        }
    }
}
