package com.example.my_huandog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter_MyDogs extends RecyclerView.Adapter<RecyclerViewAdapter_MyDogs.ItemViewHolder> {

    private ArrayList<MyDogs> myDogsArrayList;
    private int position;
    public RecyclerViewAdapter_MyDogs(ArrayList<MyDogs> list){
        this.myDogsArrayList = list;
    }

    @NonNull

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mydogs, parent,false);

        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_MyDogs.ItemViewHolder holder, int position) {
        holder.onBind(myDogsArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //뷰홀더 상속 및 구현
    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView dogName;

        public ItemViewHolder( View itemView) {
            super(itemView);
            this.dogName = itemView.findViewById(R.id.myDogs_Name);
        }
        public void onBind(MyDogs mydogs){
            dogName.setText(mydogs.getDogName());
        }
    }
}
