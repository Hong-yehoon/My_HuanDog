package com.example.my_huandog;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.io.InputStream;

public class MyDogsAddActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_STORAGE = 1111;

    ImageView dogImage;
    EditText name, age, weight, gender,sort, social, addr;
    String dogName;
    String dogAge;
    String dogWeight;
    String dogGender;
    String dogSort;
    String dogSocial;
    String dogAddr;
    Button imgAddBtn, resistBtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String userId; //autoEmail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydogs_add_info);

        name = findViewById(R.id.dogName);
        age = findViewById(R.id.dogAge);
        weight = findViewById(R.id.dogWeight);
        gender = findViewById(R.id.dogGender);
        sort = findViewById(R.id.dogSort);
        social = findViewById(R.id.dogSocial);
        addr = findViewById(R.id.dogAddr);


        dogImage = findViewById(R.id.addImage);
        imgAddBtn = findViewById(R.id.imgAddBtn);

        sharedPreferences = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        userId = sharedPreferences.getString("inputEmail",null);

        imgAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                Intent intent = new Intent();
                //?????? ????????? ??????
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 101);
            }
        });

        resistBtn = findViewById(R.id.resistBtn);
        if(userId==null){
            Toast.makeText(getApplicationContext(),"????????? ?????? ????????????", Toast.LENGTH_SHORT).show();
        }else{
            resistBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dogName = name.getText().toString();
                    dogAge = age.getText().toString();
                    dogWeight = weight.getText().toString();
                    dogGender = gender.getText().toString();
                    dogSort = sort.getText().toString();
                    dogSocial = social.getText().toString();
                    dogAddr = addr.getText().toString();

                    writeDog(userId,dogName,dogAge,dogWeight,dogGender,dogSort,dogSocial,dogAddr);

                }

            });
        }
    }

    private void writeDog(String userId,String dogName, String dogAge, String dogWeight, String dogGender, String dogSort, String dogSocial, String dogAddr){

        MyDogs mydogs = new MyDogs(dogName,dogAge,dogWeight,dogGender,dogSort,dogSocial,dogAddr);
        db.collection("MyDog").document(userId).set(mydogs).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"????????????", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyDogsAddActivity.this, MyDogsManager.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(),"????????????", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                Intent intent = new Intent("com.android.camera.action.CROP");
                Bitmap bm = BitmapFactory.decodeStream(is);
                is.close();

                dogImage.setImageBitmap(bm);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(),"????????????", Toast.LENGTH_SHORT).show();
        }

    }
    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // ?????? ?????? ?????? ????????? ???????????? ??? ????????? ?????? ????????? ????????? ?????? ??? (?????? else{..} ?????? ??????)
            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

            // ?????? ???????????? if()?????? ????????? false??? ?????? ??? -> else{..}??? ???????????? ?????????
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("??????")
                        .setMessage("????????? ????????? ?????????????????????. ????????? ???????????? ???????????? ?????? ????????? ?????? ??????????????? ?????????.")
                        .setNeutralButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : ????????? ????????? 0, ????????? ????????? -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(getApplicationContext(), "?????? ????????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // ??????????????? ??? ????????????..

                break;
        }
    }
}
