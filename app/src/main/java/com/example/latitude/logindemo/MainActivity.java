package com.example.latitude.logindemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   private EditText emailText;
    private EditText passText;
    private Button LoginBtn;
    private TextView Info;
    private TextView registration;
    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = (EditText) findViewById(R.id.emailText);
        passText = (EditText) findViewById(R.id.passText);
        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        Info = (TextView) findViewById(R.id.Info);
         registration=(TextView) findViewById(R.id.Registration);
        Info.setText("No. of attempts left:5");

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(emailText.getText().toString(), passText.getText().toString());



            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });



    }

    private void validate(String userName, String userPassword){

        if ((userName != "Aliloves") && (userPassword != "coco")){

            LoginBtn.setEnabled(false);
        }




        if ((userName.equals("Aliloves")) && (userPassword.equals("coco"))){{

                Toast.makeText(this,"Logined",Toast.LENGTH_SHORT).show();
            }


            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);





    }

        else {

            counter--;

            Info.setText("No. of Attempts left:"+ String.valueOf(counter));

                if(counter == 0 ){
                    LoginBtn.setEnabled(false);


                }
            }
        }}





