package com.example.my_huandog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class JoinActivity extends AppCompatActivity {

    Button joinBtn;
    EditText joinEmail,joinPass,joinPassChk,joinName,joinAddr;

    String email, enEmail, pass, passChk, name, addr;

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);


        joinBtn = (Button)findViewById(R.id.joinBtn);

        joinEmail = (EditText)findViewById(R.id.joinEmail);
        joinPass = (EditText)findViewById(R.id.joinPass);
        joinPassChk = (EditText)findViewById(R.id.joinPassChk);
        joinName = (EditText)findViewById(R.id.joinName);
        joinAddr = (EditText)findViewById(R.id.joinAddr);

        //**********회원가입**********
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = joinEmail.getText().toString().trim();
                enEmail = email.replace(".",",");

                pass = joinPass.getText().toString();
                passChk = joinPassChk.getText().toString();
                name = joinName.getText().toString();
                addr = joinAddr.getText().toString();

                // 필수항목이 입력되면
                if(enEmail.length() > 0 && pass.length() >0
                        && passChk.length() > 0 && name.length() >0 ){

                    //비밀번호와 비밀번호 확인이 맞으면
                    if(pass.equals(passChk)){
                        //데이터 입력(회원가입 완료)
                        writeUser(enEmail,enEmail,pass,name,addr);


                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(),"비밀번호가 서로 다릅니다",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"* 는 필수 항목입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //데이터베이스 컬렉션 만들기
    //userId = email
    private void writeUser (String userId, String userEmail, String userPass, String userName, String userAddr){

        user = new User(userEmail,userPass, userName, userAddr);

        database.child("Users").child(userId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"회원가입되었습니다.",Toast.LENGTH_LONG).show();
                Log.w("firebase","123");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"회원가입 실패 ",Toast.LENGTH_LONG).show();
                Log.w("firebase","456");
            }
        });
    }
}
