package com.example.hardik.colleges;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Signin extends Activity {

    EditText usn,pass;
    Button signin;
    String fJSON,username,password,x;


    private static final String JSON_URL = "http://www.minorprojectmanager.hol.es/college/yolo/checklogin.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //Toast.makeText(Signin.this,"Hello",Toast.LENGTH_SHORT).show();

        usn=(EditText)findViewById(R.id.si_usn);
        pass=(EditText)findViewById(R.id.si_pass);
        signin=(Button)findViewById(R.id.si_signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=usn.getText().toString();
                password=pass.getText().toString();

                if(username.equals("")){
                    Toast.makeText(Signin.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    Toast.makeText(Signin.this,"Enter Password",Toast.LENGTH_SHORT).show();
                }


                else {
                    check(username, password);
                    //Toast.makeText(Signin.this,"bye  is"+fJSON,Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void check(String username,String password){
        getJSON("http://www.minorprojectmanager.hol.es/college/yolo/checklogin.php?username="+username,password);
        //Toast.makeText(Signin.this,username,Toast.LENGTH_SHORT).show();
        //Toast.makeText(Signin.this,password,Toast.LENGTH_SHORT).show();
        //Toast.makeText(Signin.this,"fsjon is"+fJSON,Toast.LENGTH_LONG).show();

        //parseJSON(fJSON);
    }

    private void getJSON(final String url, final String pass) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Toast.makeText(Signin.this,url,Toast.LENGTH_LONG).show();
                loading = ProgressDialog.show(Signin.this, "Please Wait...", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String s) {
                //Toast.makeText(Signin.this,s,Toast.LENGTH_LONG).show();
                super.onPostExecute(s);

                fJSON=s;
                //Toast.makeText(Signin.this,"Hello is "+fJSON,Toast.LENGTH_SHORT).show();
                parseJSON(fJSON,pass);
                loading.dismiss();
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);



    }

    public void parseJSON(String a,String p){

        try {
            JSONObject ob=new JSONObject(a);
            JSONArray ar=ob.getJSONArray("result");


            JSONObject o=ar.getJSONObject(0);
            x=o.getString("password");
            //Toast.makeText(Signin.this,"Hello"+x,Toast.LENGTH_SHORT).show();

            //Toast.makeText(Signin.this,o.getString("username"),Toast.LENGTH_SHORT).show();
            //Toast.makeText(Signin.this,o.getString("password"),Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            //Toast.makeText(Signin.this,"Final"+p,Toast.LENGTH_LONG).show();
            if(fJSON.equals("{\"result\":[]}")){
                Toast.makeText(Signin.this,"Username not found",Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(Signin.this,"Username not found",Toast.LENGTH_SHORT).show();

            //Toast.makeText(Signin.this,"Bye"+x,Toast.LENGTH_SHORT).show();
            else if (!p.equals(x)){
                Toast.makeText(Signin.this,"Enter Correct Password",Toast.LENGTH_SHORT).show();
            }
            else if (p.equals(x)){
                Intent signinToSearch=new Intent(Signin.this,Search.class);
                signinToSearch.putExtra("batman",username);
                startActivity(signinToSearch);
                Toast.makeText(Signin.this,"Welcome "+username,Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }


}
