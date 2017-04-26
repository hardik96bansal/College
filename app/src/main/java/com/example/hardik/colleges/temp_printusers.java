package com.example.hardik.colleges;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class temp_printusers extends ActionBarActivity {

    private TextView textViewJSON;
    private Button buttonGet;
    private Button buttonParse;
    String a;

    private static final String JSON_URL = "http://www.minorprojectmanager.hol.es/college/yolo/temp_print.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_printusers);


        textViewJSON = (TextView) findViewById(R.id.textViewJSON);
        textViewJSON.setMovementMethod(new ScrollingMovementMethod());
        buttonGet = (Button) findViewById(R.id.buttonGet);
        buttonParse = (Button) findViewById(R.id.buttonParse);
        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJSON("http://www.minorprojectmanager.hol.es/college/yolo/temp_print1.php?username=hardik");
            }
        });
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showParseActivity(textViewJSON.getText().toString());
            }
        });
    }



    private void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(temp_printusers.this, "Please Wait...", null, true, true);
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
                Toast.makeText(temp_printusers.this,s,Toast.LENGTH_SHORT).show();
                super.onPostExecute(s);
                loading.dismiss();
                a=s;
                textViewJSON.setText(s);
                Toast.makeText(temp_printusers.this,a,Toast.LENGTH_SHORT).show();
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }


    public void showParseActivity(String a){
        try {
            JSONObject ob=new JSONObject(a);
            JSONArray ar=ob.getJSONArray("result");

            for(int i=0;i<=5;i++){
                JSONObject o=ar.getJSONObject(i);
                textViewJSON.append(o.getString("username")+"\t"+o.getString("password")+"\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_temp_printusers, menu);
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
