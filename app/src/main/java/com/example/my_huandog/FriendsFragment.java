package com.example.my_huandog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FriendsFragment extends Fragment {

    Button myDogsBtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.main_fragment_friends,container,false);
        myDogsBtn = viewGroup.findViewById(R.id.my_Dogs);
        myDogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MyDogsManager.class);
                startActivity(intent);
            }
        });
        return viewGroup;
    }

}
