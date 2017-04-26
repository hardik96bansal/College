package com.example.hardik.colleges;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity implements View.OnClickListener{

    Button btn1;
    EditText et1, et2, et3, et4;

    private String name, email, mobile, password;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setTitle("Sign Up");

        btn1 = (Button) findViewById(R.id.su_signup);
        et1 = (EditText) findViewById(R.id.su_usn);
        et2 = (EditText) findViewById(R.id.su_email);
        et3 = (EditText) findViewById(R.id.su_number);
        et4 = (EditText) findViewById(R.id.su_pass);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        name = et1.getText().toString();
        email = et2.getText().toString();
        mobile = et3.getText().toString();
        password = et4.getText().toString();

        if(name.isEmpty())
            Toast.makeText(Signup.this, "Please enter your Name:", Toast.LENGTH_SHORT).show();
        else if(email.isEmpty())
            Toast.makeText(Signup.this, "Please enter you Email", Toast.LENGTH_SHORT).show();
        else if(mobile.isEmpty())
            Toast.makeText(Signup.this, "Please enter your Mobile", Toast.LENGTH_SHORT).show();
        else if(password.isEmpty())
            Toast.makeText(Signup.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
        else
        {
            boolean out = isValidEmail(email);
            if(out==false)
                Toast.makeText(Signup.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            else{
                if(mobile.length()!=10){
                    Toast.makeText(getApplicationContext(),"Invalid Mobile Number",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.length()<8 || password.length()>15){
                        Toast.makeText(getApplicationContext(),"Password should between 8-15 characters",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        InsertIntoTable insert = new InsertIntoTable(this);
                        insert.execute(name, email, mobile, password);

                    }
                }

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}