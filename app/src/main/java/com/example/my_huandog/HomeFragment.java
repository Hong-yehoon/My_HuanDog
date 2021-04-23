package com.example.my_huandog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    TabLayout tablayout;
    ViewPager2 viewPager2;
    FragmentStateAdapter pagerAdapter;

    final List<String> tabTitle = Arrays.asList("State","Timeline");


    //Start Activity
    Switch walk, gps;
    Button go, start;

    Intent intent;
    boolean walkFlag =false;
    boolean gpsFlag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_home, container, false);

        tablayout = (TabLayout)viewGroup.findViewById(R.id.home_tabLayout);
        viewPager2 = (ViewPager2)viewGroup.findViewById(R.id.home_viewPager);

        //Adapter
        pagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);

        Button startBtn = (Button)viewGroup.findViewById(R.id.start_fabBtn);
        intent= new Intent(getContext(),StartCount.class);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog startDialog = new Dialog(getContext());
                startDialog.setContentView(R.layout.dialog_start);
                startDialog.setTitle("산책 세팅 다이얼로그");

                walk = (Switch)startDialog.findViewById(R.id.sdWalk);
                gps = (Switch)startDialog.findViewById(R.id.sdGPS);
                go = (Button)startDialog.findViewById(R.id.sdGo);

                walk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if(isChecked){
                            walkFlag=true;
                        }else {
                            walkFlag=false;
                        }
                        intent.putExtra("walk",walkFlag);
                    }
                });

                gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if(isChecked){
                            gpsFlag = true;
                        }else {
                            gpsFlag = false;
                        }
                        intent.putExtra("gps",gpsFlag);
                    }
                });

                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(intent);
                        startDialog.dismiss();
                    }
                });

                startDialog.show();
            }
        });


        return viewGroup;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        TabLayout tabLayout = view.findViewById(R.id.home_tabLayout);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {

                TextView textview = new TextView(getContext());
                textview.setText(tabTitle.get(position));
                textview.setTextSize(17);
                textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tab.setCustomView(textview);

            }
        }).attach();
    }
}