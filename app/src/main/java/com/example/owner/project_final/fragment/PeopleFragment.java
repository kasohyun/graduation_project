package com.example.owner.project_final.fragment;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.owner.project_final.R;
import com.example.owner.project_final.chat.MessageActivity;
import com.example.owner.project_final.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment {
    //@BindView(R.id.frienditem_imageview)
    //ImageView ivRoomPhoto1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people,container,false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.peoplefragment_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new PeopleFragmentRecyclerViewAdapter());

        return view;

    }

    class PeopleFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<UserModel> userModels;
        public PeopleFragmentRecyclerViewAdapter(){
            userModels = new ArrayList<>();
            //final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userModels.clear(); //친구추가 시 다시 불러옴


                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        //UserModel userModel = snapshot.getValue(UserModel.class);
/*
                            if(userModel.uid.equals(myUid)){
                                continue;
                            }
*/
                        //userModels.add(userModel);
                        userModels.add(snapshot.getValue(UserModel.class));
                    }

                    notifyDataSetChanged();//새로고침(친구가 뜸)
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);


            return new CustomViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {//이미지넣어주는 부분
/*
            Glide.with
                    (holder.itemView.getContext())
                    .load(userModels.get(position).profileImageUrl)
                    //.apply(new RequestOptions().circleCrop())
                    .into(((CustomViewHolder)holder).imageView);
                    */
            //UserName 불러오는게 getName이 맞는지?
            ((CustomViewHolder)holder).textView.setText(userModels.get(position).getName());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),MessageActivity.class);
                    //uid받아와서 메시지창 띄우기
                    intent.putExtra("destinationUid",userModels.get(position).uid);
                    //화면전환 시 애니메이션 효과
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(),R.anim.fromright,R.anim.toleft);
                    startActivity(intent,activityOptions.toBundle());
                }
            });
        }


        @Override
        public int getItemCount() {
            return userModels.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            //public ImageView imageView;
            public TextView textView;

            public CustomViewHolder(View view) {
                super(view);
                //imageView = (ImageView) view.findViewById(R.id.frienditem_imageview);
                textView = (TextView) view.findViewById(R.id.frienditem_textview);
            }
        }
    }
/*
    private void setVaildFirebaseStorage(String key, String storageKey) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        if (storageKey.equalsIgnoreCase("")) {
            return;
        }

            StorageReference storageRef = storage.getReferenceFromUrl(PublicVariable.FIREBASE_STORAGE).child(PublicVariable.FIREBASE_STORAGE_ROOMS).child(key).child("userImages"  + ".jpg").child(storageKey);

            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onSuccess(Uri uri) {
                    ImageLoaderHelper.setProfileImage(getContext(), uri,  ivRoomPhoto1 , "");
                }
            });

    }
    */
}
