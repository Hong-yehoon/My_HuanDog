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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        Button my_Info = (Button) viewGroup.findViewById(R.id.my_Info);
        my_Info.setOnClickListener(new View.OnClickListener() {
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

        Button my_Withdraw = (Button)viewGroup.findViewById(R.id.my_Withdraw);
        my_Withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //쉐어드프리퍼런스에서 아이디 가져오고
                autoLogin = getActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                logoutEmail = autoLogin.getString("inputEmail", "");
                Toast.makeText(getContext(), logoutEmail, Toast.LENGTH_SHORT).show();

                //다이얼로그에 아이디 띄워주구
                Dialog outDialog = new Dialog(getContext());
                outDialog.setContentView(R.layout.dialog_delete);

                TextView deEmail = (TextView) outDialog.findViewById(R.id.DeEmail);
                EditText dePass = (EditText) outDialog.findViewById(R.id.DePass);
                Button deBtn = (Button) outDialog.findViewById(R.id.DeBtn);

                deEmail.setText(logoutEmail);
                firebaseDatabase = FirebaseDatabase.getInstance();


                //버튼 눌렀을 때 비번 맞으면 탈퇴쓰
                deBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (dePass.length() != 0) {
                            database = firebaseDatabase.getReference();
                            database.child("users").child(logoutEmail).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User delete;
                                    delete = snapshot.getValue(User.class);
                                    if (snapshot.getValue(User.class) != null) {
                                        if (delete.getUserPass().equals(dePass.getText().toString())) {

                                            database.child("users").child(logoutEmail).removeValue();

                                            //로그인유지 해제
                                            editor = autoLogin.edit();
                                            editor.clear();
                                            editor.commit();


                                            outDialog.dismiss();
                                        } else {
                                            Toast.makeText(getContext(), "비밀번호가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            Toast.makeText(getContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                outDialog.show();

            }
        });
        return viewGroup;

    }
}

