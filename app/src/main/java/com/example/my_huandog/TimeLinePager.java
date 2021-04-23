package com.example.my_huandog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeLinePager extends Fragment {

    ArrayList<TimeLine> timeLineArrayList = new ArrayList<>();

    ViewGroup viewGroup;

    private RecyclerViewAdapter_TimeLine adapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewGroup = (ViewGroup)inflater.inflate(R.layout.home_timeline,container,false);

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter_TimeLine(getActivity(),timeLineArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return viewGroup;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareData();

    }

    private void prepareData(){
        timeLineArrayList.add(new TimeLine("재돌","09 분"));
        timeLineArrayList.add(new TimeLine("핑구","13 분"));
        timeLineArrayList.add(new TimeLine("철훈","22 분"));
        timeLineArrayList.add(new TimeLine("우디","37 분"));
        timeLineArrayList.add(new TimeLine("밤이","45 분"));

    }
}
