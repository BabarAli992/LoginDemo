package com.example.latitude.logindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName , userEmail , userPass;
    private Button register;
    private TextView LoginReTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        SetupUIViews();

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (validate()){
                    //upload to database....
                    startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

                }

            }
        });
        LoginReTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });


    }

    private void SetupUIViews(){

        userName = (EditText) findViewById(R.id.name);
        userEmail = (EditText) findViewById(R.id.Email);
        userPass = (EditText) findViewById(R.id.pass);
        register = (Button) findViewById(R.id.registerBtn);
        LoginReTurn = (TextView) findViewById(R.id.loginReturn);

    }
    private Boolean validate(){

        String Name =userName.getText().toString();
        String email=userEmail.getText().toString();
        String password =userPass.getText().toString();

        Boolean result=false;
        if (Name.isEmpty() && email.isEmpty()&& password.isEmpty()){

            Toast.makeText(this, "Fill in Details", Toast.LENGTH_SHORT).show();


        }else{
            result=true;

        }
        return result;
    }
}




