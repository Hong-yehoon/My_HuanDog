package com.example.my_huandog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyPageFragment extends Fragment {

    private DatabaseReference database;
    FirebaseDatabase firebaseDatabase;

    String logoutEmail;

    SharedPreferences autoLogin;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.main_fragment_mypage, container,false);

        autoLogin = getActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        logoutEmail = autoLogin.getString("inputEmail", "");


        //********** 내 정보 **********

        Button myInfo = (Button) viewGroup.findViewById(R.id.my_Info);
        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (logoutEmail.length() != 0) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);

                } else {

                    Dialog loginDialog = new Dialog(getContext());
                    loginDialog.setContentView(R.layout.dialog_signpage);

                    Button login = (Button) loginDialog.findViewById(R.id.dial_Login);
                    Button join = (Button) loginDialog.findViewById(R.id.dial_Join);

                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent02 = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent02);
                            loginDialog.dismiss();
                        }
                    });

                    join.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent03 = new Intent(getContext(), JoinActivity.class);
                            startActivity(intent03);
                            loginDialog.dismiss();
                        }
                    });

                    loginDialog.show();
                }
            }
        });
        return viewGroup;

    }
}

