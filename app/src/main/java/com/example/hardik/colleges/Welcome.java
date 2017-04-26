package com.example.hardik.colleges;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Hardik on 9/21/2016.
 */
public class Welcome extends Activity{



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView t1=(TextView)findViewById(R.id.welcome_name);
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/ABOVE  - PERSONAL USE ONLY.ttf");
        t1.setTypeface(tf);

        View.OnClickListener goto_signup=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent su=new Intent(Welcome.this,Signup.class);
                startActivity(su);
                //finish();
            }
        };

        View.OnClickListener goto_signin=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent su=new Intent(Welcome.this,Signin.class);
                startActivity(su);
                //finish();
            }
        };

        Button si=(Button)findViewById(R.id.sgin);
        Button su=(Button)findViewById(R.id.sgup);
        si.setOnClickListener(goto_signin);
        su.setOnClickListener(goto_signup);

    }



}
