package com.example.hardik.colleges;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Search extends ActionBarActivity {

    static String usn;
    String fJSON;
    Button bt1,bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        bt1=(Button)findViewById(R.id.search_bk);
        bt2=(Button)findViewById(R.id.search_lg);



        Intent intent=getIntent();
        usn=intent.getStringExtra("batman");


        ////Toast.makeText(this,"good is "+usn,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"Welcome "+usn,Toast.LENGTH_SHORT).show();

        ImageButton b1=(ImageButton)findViewById(R.id.searchbutton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Search.this,Filter.class);
                startActivity(i);
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String url="http://minorprojectmanager.hol.es/college/yolo/bookref.php?username="+usn;
                //getJSON(url);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Search.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        });
    }



    private void getJSON(final String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ////Toast.makeText(Signin.this,url,Toast.LENGTH_LONG).show();
                loading = ProgressDialog.show(Search.this, "Please Wait...", null, true, true);
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
                ////Toast.makeText(Signin.this,s,Toast.LENGTH_LONG).show();
                super.onPostExecute(s);

                fJSON=s;
                //Toast.makeText(Search.this,"Hello is "+fJSON,Toast.LENGTH_SHORT).show();
                //parseJSON(fJSON);
                loading.dismiss();
                Intent i=new Intent(Search.this,PGList.class);
                i.putExtra("pgdetails",fJSON);
                //i.putExtra("location",loc);
                startActivity(i);
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
