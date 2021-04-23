package com.example.my_huandog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter_TimeLine extends RecyclerView.Adapter<RecyclerViewAdapter_TimeLine.CustomViewHolder> {

    private Context context;
    //adapter에 들어갈 list
    private ArrayList<TimeLine> timeLineArrayList = new ArrayList<>();

    public RecyclerViewAdapter_TimeLine(Context context, ArrayList<TimeLine> timeLineArrayList) {
        this.context = context;
        this.timeLineArrayList = timeLineArrayList;
    }

    @NonNull
    @Override
    //뷰홀더를 생성(레이아웃 생성)
    public RecyclerViewAdapter_TimeLine.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_recycler, parent, false);
        // 뷰 홀더 객체를 생성하면서 뷰 객체를 전달하고 그 뷰홀더 객체를 반환하기
        return new CustomViewHolder(view);
    }

    //뷰 홀더가 재활용될 때 실행되는 메서드
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBine(timeLineArrayList.get(position));
    }

    //아이템 개수를 조회
    @Override
    public int getItemCount() {
        return timeLineArrayList.size();
    }


    //ViewHolder
    //subView를 세팅
    class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView dogName;
        private TextView walkTime;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            dogName = itemView.findViewById(R.id.time_Name);
            walkTime = itemView.findViewById(R.id.time_Time);
        }

        public void onBine(TimeLine timeLine) {

            dogName.setText(timeLine.getDogName());
            walkTime.setText(timeLine.getWalkTime());

        }
    }
}