package com.example.hardik.colleges;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Filter extends ActionBarActivity {
    Spinner sp;
    Button bt1,bt_boys,bt_girls,bt_coed,bt_ac,bt_nac,search;
    int boys=1,girls=0,coed=0,ac=1,nac=0;
    String loc="",url,fJSON;
    ArrayAdapter<String> nd,dl,gn;
    LinearLayout l1,l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        TextView t1=(TextView)findViewById(R.id.filter_name);
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/ABOVE  - PERSONAL USE ONLY.ttf");
        t1.setTypeface(tf);

        String[] noida={"Delhi","Noida","Gurgaon","Punjab"};
        String[] delhi={"Gujrat","Kolkata","Kanpur"};
        final String[] ggn={"Mumbai","Chennai","Hyderabad","Banglore"};

        bt1=(Button)findViewById(R.id.filter_b1);
        bt_boys=(Button)findViewById(R.id.filter_male);
        bt_girls=(Button)findViewById(R.id.filter_female);
        bt_coed=(Button)findViewById(R.id.filter_coed);
        bt_ac=(Button)findViewById(R.id.filter_ac);
        bt_nac=(Button)findViewById(R.id.filter_nonac);
        search=(Button)findViewById(R.id.filter_search);

        //LayoutInflater inf=Filter.this.getLayoutInflater();
        //View v1=inf.inflate(R.layout.activity_filter,null,true);
        l1=(LinearLayout)findViewById(R.id.filter_l1);
        l2=(LinearLayout)findViewById(R.id.filter_l2);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(Filter.this,ChooseCity.class);
                startActivityForResult(i1,10);
            }
        });

        bt_boys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boys=1;
                girls=0;
                coed=0;
                l1.setBackgroundResource(R.drawable.mab);
                l1.invalidate();
            }
        });

        bt_girls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boys=0;
                girls=1;
                coed=0;
                l1.setBackgroundResource(R.drawable.mag);
                l1.invalidate();
            }
        });

        bt_coed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boys=0;
                girls=0;
                coed=1;
                l1.setBackgroundResource(R.drawable.mac);
                l1.invalidate();
            }
        });

        bt_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac=1;
                nac=0;
                l2.setBackgroundResource(R.drawable.nac);
            }
        });

        bt_nac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac=0;
                nac=1;
                l2.setBackgroundResource(R.drawable.nnac);
            }
        });

        sp=(Spinner)findViewById(R.id.spinner1);
        nd=new ArrayAdapter<String>(this,R.layout.spinnerlayout,noida);
        dl=new ArrayAdapter<String>(this,R.layout.spinnerlayout,delhi);
        gn=new ArrayAdapter<String>(this,R.layout.spinnerlayout,ggn);

        sp.setAdapter(nd);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loc= (String) parent.getItemAtPosition(position);
                ////Toast.makeText(Filter.this,loc,Toast.LENGTH_SHORT).show();
                loc=parent.getItemAtPosition(position).toString();
                String arr[] = loc.split(" ", 2);
                loc=arr[0];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boys==1){
                    if(ac==1){
                        url="http://www.minorprojectmanager.hol.es/college/yolo/refine.php?location="+loc+"&type=eng&rtype=ac";
                    }
                    if(nac==1){
                        url="http://www.minorprojectmanager.hol.es/college/yolo/refine.php?location="+loc+"&type=eng&rtype=nac";
                    }
                }

                if(girls==1){
                    if(ac==1){
                        url="http://www.minorprojectmanager.hol.es/college/yolo/refine.php?location="+loc+"&type=mgmnt&rtype=ac";
                    }
                    if(nac==1){
                        url="http://www.minorprojectmanager.hol.es/college/yolo/refine.php?location="+loc+"&type=mgmnt&rtype=nac";
                    }
                }

                if(coed==1){
                    if(ac==1){
                        url="http://www.minorprojectmanager.hol.es/college/yolo/refine.php?location="+loc+"&type=dr&rtype=ac";
                    }
                    if(nac==1){
                        url="http://www.minorprojectmanager.hol.es/college/yolo/refine.php?location="+loc+"&type=dr&rtype=nac";
                    }
                }
                //Toast.makeText(Filter.this,url,Toast.LENGTH_SHORT).show();
                getJSON(url);



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
                loading = ProgressDialog.show(Filter.this, "Please Wait...", null, true, true);
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
                //Toast.makeText(Filter.this,"Hello is "+fJSON,Toast.LENGTH_SHORT).show();
                //parseJSON(fJSON);
                loading.dismiss();
                Intent i=new Intent(Filter.this,PGList.class);
                i.putExtra("pgdetails",fJSON);
                i.putExtra("location",loc);
                startActivity(i);
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
            //x=o.getString("password");
            ////Toast.makeText(Signin.this,"Hello"+x,Toast.LENGTH_SHORT).show();

            ////Toast.makeText(Signin.this,o.getString("username"),Toast.LENGTH_SHORT).show();
            ////Toast.makeText(Signin.this,o.getString("password"),Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==10&&resultCode==1&&data!=null){
            sp.setAdapter(nd);
            bt1.setText("NORTH");
        }
        if(requestCode==10&&resultCode==2&&data!=null){
            sp.setAdapter(dl);
            bt1.setText("WEST-EAST");
        }
        if(requestCode==10&&resultCode==3&&data!=null){
            sp.setAdapter(gn);
            bt1.setText("SOUTH");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
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
