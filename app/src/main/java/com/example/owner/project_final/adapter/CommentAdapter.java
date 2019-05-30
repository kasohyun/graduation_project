package com.example.owner.project_final.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.owner.project_final.L;
import com.example.owner.project_final.R;
import com.example.owner.project_final.firebase.FirebaseApi;
import com.example.owner.project_final.model.Comment;
import com.example.owner.project_final.model.Reply;
import com.example.owner.project_final.model.RoomWrite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public abstract class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ItemViewHolder> {
    //[오투잡] 댓글을 관리하는 Adapter
    public abstract void selectItem(Comment item, int pos);

    private Context mContext;
    private ArrayList<Comment> mCommentList;

    public CommentAdapter(Context context) {
        this.mContext = context;
        this.mCommentList = new ArrayList<>();
    }


    public synchronized void insertData(Comment list, int pos) {
        this.mCommentList.add(pos, list);
        notifyDataSetChanged();
//        notifyItemInserted(mCommentList.size() - 1);
    }

    public synchronized void insertData(Comment list) {
        this.mCommentList.add(list);
        if (list.getReplyMap() != null && list.getReplyMap().size() > 0) {
            Set<Map.Entry<String, Reply>> set = list.getReplyMap().entrySet();
            Iterator<Map.Entry<String, Reply>> iterator = set.iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Reply> entry = (Map.Entry<String, Reply>) iterator.next();
                Reply reply = (Reply) entry.getValue();
                L.e(":::리플 내용 : " + reply.toString());
                this.mCommentList.add(new Comment(reply.getAddedByUser(), false, reply.getName(), reply.getDate(), reply.getContent(), list.getNodeId(), list.getChildId(), true));
            }
        }
        notifyDataSetChanged();
//        notifyItemInserted(mCommentList.size() - 1);
    }

    public void delete(int index) {
        this.mCommentList.remove(index);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mCommentList != null && mCommentList.size() > 0) {
            mCommentList.clear();
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item_row, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        final int pos = position;
        final Comment comment = mCommentList.get(pos);
        L.e(":::comment reply : " + comment.getReplyMap().size());
        if (comment.getReplyMap().size() > 0) {
            if (!comment.isReply()) {
                holder.reply.setText(String.format(Locale.getDefault(), "답글 %1$01d", comment.getReplyMap().size()));
            } else {
                holder.reply.setVisibility(View.INVISIBLE);
            }
        }

        if (comment.isReply()) {
            holder.replyIcon.setVisibility(View.VISIBLE);
        } else {
            holder.replyIcon.setVisibility(View.GONE);
        }


        if (comment.getAddedByUser().equalsIgnoreCase(FirebaseApi.getCurrentUser().getUid())) {
            holder.name.setTextColor(Color.parseColor("#ffa000"));
            holder.name.setText(!comment.isAnonymous() ? comment.getName() + "(본인)" : "익명(본인)");
            holder.update.setVisibility(View.VISIBLE);
            holder.delete.setText("삭제");
        } else {
            holder.name.setTextColor(Color.parseColor("#111111"));
            holder.name.setText(!comment.isAnonymous() ? comment.getName() : "익명");
            holder.update.setVisibility(View.GONE);
            holder.delete.setText("신고");
        }

        holder.date.setText(comment.getDate());
        holder.content.setText(comment.getContent());

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.replyView.setVisibility(View.VISIBLE);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectItem(comment, pos);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView date;
        private TextView update;
        private TextView delete;
        private TextView content;
        private TextView reply;
        private ImageView replyIcon;
        private LinearLayout replyView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            content = (TextView) itemView.findViewById(R.id.content);
            update = (TextView) itemView.findViewById(R.id.update);
            delete = (TextView) itemView.findViewById(R.id.delete);
            reply = (TextView) itemView.findViewById(R.id.reply);
            replyIcon = (ImageView) itemView.findViewById(R.id.comment_photo);
            replyView = (LinearLayout) itemView.findViewById(R.id.reply_view);
        }
    }
}
