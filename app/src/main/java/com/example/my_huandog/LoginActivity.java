package com.example.my_huandog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn, logoutBtn;
    EditText loginEmail, loginPass;

    String email, pass, enEmail;

    private DatabaseReference database;

    //자동로그인에 저장
    String autoEmail, autoPass;
    SharedPreferences auto;
    SharedPreferences.Editor editor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginEmail = (EditText)findViewById(R.id.loginEmail);
        loginPass = (EditText)findViewById(R.id.loginPass);

        logoutBtn = findViewById(R.id.logoutBtn);

        database = FirebaseDatabase.getInstance().getReference();

        //*****************자동 로그인**************
        auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        autoEmail = auto.getString("inputEmail",null);
        autoPass = auto.getString("inputPass",null);

        //********로그인********

        // 자동로그인의 키값이 null이면 로그인 할수 있게
        if(autoEmail == null && autoPass == null){

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    email = loginEmail.getText().toString();
                    enEmail = email.replace(".",",");
                    pass = loginPass.getText().toString();

                    // 이메일과 비밀번호가 입력되었다면
                    if(enEmail.length() > 0 && pass.length() > 0){

                        readUser(enEmail, pass);
                        Toast.makeText(getApplicationContext(),email+"로그인",Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(LoginActivity.this,MyInfoActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(),"아이디와 비밀번호는 필수사항입니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{      //이미 로그인을한 적이있다면 자동로그인해서 내정보 보여주기
            Toast.makeText(getApplicationContext(),autoEmail+"로 자동로그인",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MyInfoActivity.class);
            startActivity(intent);
            finish();
        }

        //******************** 로그아웃 ***************
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                editor = auto.edit();
                editor.clear();
                editor.commit();

                Toast.makeText(getApplicationContext(),"로그아웃",Toast.LENGTH_SHORT).show();

                onBackPressed();

            }
        });
    }

    //Firebase

    private void readUser (String userEmail, String userPass){

        database.child("Users").child(userEmail).addValueEventListener(new ValueEventListener() {
            User user;

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                user = snapshot.getValue(User.class);

                //아이디가 존재하면
                if(snapshot.getValue(User.class) != null){

                    //비밀번호가 맞으면
                    if(user.getUserPass().equals(userPass)){
                        Toast.makeText(getApplicationContext(), email + "로 로그인 되었습니다", Toast.LENGTH_SHORT).show();

                        //*********자동로그인 키 값 입력*********
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("inputEmail", email);
                        autoLogin.putString("inputPass", pass);
                        autoLogin.apply();

                    }else{
                        Toast.makeText(getApplicationContext(), "비밀번호가 맞지않습니다. ", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "가입된 이메일이 아닙니다. ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }

        });
    }

}
