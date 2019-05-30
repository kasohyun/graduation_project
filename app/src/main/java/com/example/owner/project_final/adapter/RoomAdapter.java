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
import com.example.owner.project_final.model.RoomWrite;

import java.util.ArrayList;

public abstract class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ItemViewHolder> {
    public abstract void selectItem(RoomWrite item);

    private Context mContext;
    private ArrayList<RoomWrite> mRoomWriteList;

    public RoomAdapter(Context mContext) {
        this.mContext = mContext;
        this.mRoomWriteList = new ArrayList<>();
    }

    public synchronized void insertData(RoomWrite list) {
        this.mRoomWriteList.add(list);
        notifyItemInserted(mRoomWriteList.size() - 1);
    }

    public void clear() {
        if (mRoomWriteList != null && mRoomWriteList.size() > 0) {
            mRoomWriteList.clear();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_item_row, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final RoomWrite item = mRoomWriteList.get(position);
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getPostingDate());
        holder.name.setText(item.getWriter());
        holder.address.setText(item.getAddress() + " " + item.getDetailAddress());
        holder.priod.setText(item.getRentalStartTime() + " ~ " + item.getRentalEndTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRoomWriteList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private TextView name;
        private TextView priod;
        private TextView address;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.name);
            priod = (TextView) itemView.findViewById(R.id.priod);
            address = (TextView) itemView.findViewById(R.id.address);
        }
    }
}
