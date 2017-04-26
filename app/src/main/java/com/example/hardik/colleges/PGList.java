package com.example.hardik.colleges;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class PGList extends ActionBarActivity {

    private ListView listView;
    String names[], address[],url,loc;//,arr[];
    String val,type,rtype,order,nJSON;
    Spinner sp1,sp2,sp3;
    Button update;
    ArrayAdapter<String > aa1,aa2,aa3,aa4;


    private Integer imageid[] = {
            R.drawable.pgtry,
            R.drawable.try1p,
            R.drawable.noida,
            R.drawable.delhi,
            R.drawable.gurgaon,
            R.drawable.noida
    };

    private Integer price[];
    int a;

    String jsonVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pglist);
        type="boys";
        rtype="ac";


/*
        try {
            JSONObject ob=new JSONObject(jsonVal);
            JSONArray ar=ob.getJSONArray("result");
            a=ar.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/





        //////Toast.makeText(PGList.this,"Oh",Toast.LENGTH_SHORT).show();
        Intent in=getIntent();
        jsonVal=in.getStringExtra("pgdetails");
        loc=in.getStringExtra("location");
        ////Toast.makeText(PGList.this,jsonVal,Toast.LENGTH_SHORT).show();

        ////Toast.makeText(PGList.this,loc,Toast.LENGTH_SHORT).show();


        String[] filter1={"Boys","Girls","Co-ed"};
        String[] filter2={"AC","Non-AC"};
        String[] sort={"Price Low","Price High"};


        update=(Button)findViewById(R.id.upd);
        sp1=(Spinner)findViewById(R.id.list_sp1);
        sp2=(Spinner)findViewById(R.id.list_sp2);
        sp3=(Spinner)findViewById(R.id.list_sp3);

        aa1=new ArrayAdapter<String>(this,R.layout.spinner1,filter1);
        aa2=new ArrayAdapter<String>(this,R.layout.spinner1,filter2);
        aa3=new ArrayAdapter<String>(this,R.layout.spinner1,sort);

        sp1.setAdapter(aa1);
        sp2.setAdapter(aa2);
        sp3.setAdapter(aa3);



        try {
            JSONObject ob=new JSONObject(jsonVal);
            JSONArray ar=ob.getJSONArray("result");

            names=new String[ar.length()];
            address=new String[ar.length()];
            price=new Integer[ar.length()];
            //////Toast.makeText(PGList.this,""+ar.length(),Toast.LENGTH_SHORT).show();
            //JSONObject o=ar.getJSONObject(0);
            //////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();
            //o=ar.getJSONObject(1);
            //////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();


            for(int i=0;i<ar.length();i++){
                JSONObject o=ar.getJSONObject(i);
                ////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();

                names[i]=o.getString("pgname");
                address[i]=o.getString("address");
                price[i]=o.getInt("price");
                //address[i]=o.getString("address");
                //price[i]=o.getInt("price");
                if(names[i].equals("Pradhan P.G.")){
                    imageid[i]=R.drawable.f11;
                }

                if(names[i].equals("Shivshakti P.G.")){
                    imageid[i]=R.drawable.f21;
                }

                if(names[i].equals("Madhu P.G.")){
                    imageid[i]=R.drawable.f31;
                }

                if(names[i].equals("Raj P.G.")){
                    imageid[i]=R.drawable.f41;
                }

                if(names[i].equals("Workingmen P.G.")){
                    imageid[i]=R.drawable.f51;
                }

                if(names[i].equals("Lotus P.G.")){
                    imageid[i]=R.drawable.f61;
                }

                if(names[i].equals("Malhotra's P.G.")){
                    imageid[i]=R.drawable.f71;
                }

                if(names[i].equals("Bansal Kutir P.G.")){
                    imageid[i]=R.drawable.f81;
                }

                if(names[i].equals("Ahuja P.G.")){
                    imageid[i]=R.drawable.f91;
                }

                if(names[i].equals("Fresher House P.G.")){
                    imageid[i]=R.drawable.f101;
                }

                if(names[i].equals("Shakti P.G.")){
                    imageid[i]=R.drawable.f111;
                }

                if(names[i].equals("Jain P.G.")){
                    imageid[i]=R.drawable.f121;
                }

                if(names[i].equals("Nahar Niketan P.G.")){
                    imageid[i]=R.drawable.f131;
                }

                if(names[i].equals("Laxmi Girls P.G.")){
                    imageid[i]=R.drawable.f141;
                }

                if(names[i].equals("Vajiram P.G.")){
                    imageid[i]=R.drawable.f151;
                }

                if(names[i].equals("Agarwal Atithi P.G.")){
                    imageid[i]=R.drawable.f161;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //showParseActivity(jsonVal);



        listView=(ListView)findViewById(R.id.pglist);
        CustomList customList=new CustomList(this,names,address,imageid,price);

        listView.setAdapter(customList);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                val=parent.getItemAtPosition(position).toString();
                String arr[] = val.split(" ", 2);
                val=arr[0];

                //////Toast.makeText(PGList.this,"yo yo yo is "+val,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(PGList.this,PgDetails.class);
                intent.putExtra("red",val);
                ////Toast.makeText(PGList.this,"yo yo yo is "+val,Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });






        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //loc= (String) parent.getItemAtPosition(position);
                //////Toast.makeText(PGList.this,""+position,Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    type = "boys";
                }
                if (position == 1) {
                    type = "girls";
                }

                if (position == 2) {
                    type = "coed";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //loc= (String) parent.getItemAtPosition(position);
                //////Toast.makeText(PGList.this,""+position,Toast.LENGTH_SHORT).show();
                if(position==0){
                    rtype="ac";
                }
                if(position==1){
                    rtype="nac";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String o=sp3.getSelectedItem().toString();
                if(o.equals("Price Low")){
                    url="http://www.minorprojectmanager.hol.es/college/yolo/refine1.php?location="+loc+"&type="+type+"&rtype="+rtype+"&order=price&sort=asc";

                }
                if(o.equals("Price High")){
                    url="http://www.minorprojectmanager.hol.es/college/yolo/refine1.php?location="+loc+"&type="+type+"&rtype="+rtype+"&order=price&sort=desc";

                }
                //////Toast.makeText(PGList.this,url,Toast.LENGTH_SHORT).show();
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
                //////Toast.makeText(Signin.this,url,Toast.LENGTH_LONG).show();
                loading = ProgressDialog.show(PGList.this, "Please Wait...", null, true, true);
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
                //////Toast.makeText(Signin.this,s,Toast.LENGTH_LONG).show();
                super.onPostExecute(s);

                nJSON=s;
                ////Toast.makeText(PGList.this,"hoopla is "+nJSON,Toast.LENGTH_SHORT).show();
                //parseJSON(fJSON);

                try {
                    JSONObject ob=new JSONObject(nJSON);
                    JSONArray ar=ob.getJSONArray("result");

                    names=new String[ar.length()];
                    address=new String[ar.length()];
                    price=new Integer[ar.length()];
                    //////Toast.makeText(PGList.this,""+ar.length(),Toast.LENGTH_SHORT).show();
                    //JSONObject o=ar.getJSONObject(0);
                    //////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();
                    //o=ar.getJSONObject(1);
                    //////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();


                    for(int i=0;i<ar.length();i++){
                        JSONObject o=ar.getJSONObject(i);
                        ////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();

                        names[i]=o.getString("pgname");
                        address[i]=o.getString("address");
                        price[i]=o.getInt("price");

                        if(names[i].equals("Pradhan P.G.")){
                            imageid[i]=R.drawable.f11;
                        }

                        if(names[i].equals("Shivshakti P.G.")){
                            imageid[i]=R.drawable.f21;
                        }

                        if(names[i].equals("Madhu P.G.")){
                            imageid[i]=R.drawable.f31;
                        }

                        if(names[i].equals("Raj P.G.")){
                            imageid[i]=R.drawable.f41;
                        }

                        if(names[i].equals("Workingmen P.G.")){
                            imageid[i]=R.drawable.f51;
                        }

                        if(names[i].equals("Lotus P.G.")){
                            imageid[i]=R.drawable.f61;
                        }

                        if(names[i].equals("Malhotra's P.G.")){
                            imageid[i]=R.drawable.f71;
                        }

                        if(names[i].equals("Bansal Kutir P.G.")){
                            imageid[i]=R.drawable.f81;
                        }

                        if(names[i].equals("Ahuja P.G.")){
                            imageid[i]=R.drawable.f91;
                        }

                        if(names[i].equals("Fresher House P.G.")){
                            imageid[i]=R.drawable.f101;
                        }

                        if(names[i].equals("Shakti P.G.")){
                            imageid[i]=R.drawable.f111;
                        }

                        if(names[i].equals("Jain P.G.")){
                            imageid[i]=R.drawable.f121;
                        }

                        if(names[i].equals("Nahar Niketan P.G.")){
                            imageid[i]=R.drawable.f131;
                        }

                        if(names[i].equals("Laxmi Girls P.G.")){
                            imageid[i]=R.drawable.f141;
                        }

                        if(names[i].equals("Vajiram P.G.")){
                            imageid[i]=R.drawable.f151;
                        }

                        if(names[i].equals("Agarwal Atithi P.G.")){
                            imageid[i]=R.drawable.f161;
                        }







                        //address[i]=o.getString("address");
                        //price[i]=o.getInt("price");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listView=(ListView)findViewById(R.id.pglist);
                CustomList customList=new CustomList(PGList.this,names,address,imageid,price);

                listView.setAdapter(customList);




                loading.dismiss();
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);



    }





    public void showParseActivity(String a){
        String nm;

        ////Toast.makeText(PGList.this,"yes",Toast.LENGTH_SHORT).show();

        ////Toast.makeText(PGList.this,"bye "+a,Toast.LENGTH_SHORT).show();
        try {
            JSONObject ob=new JSONObject(a);
            JSONArray ar=ob.getJSONArray("result");


            //////Toast.makeText(PGList.this,""+ar.length(),Toast.LENGTH_SHORT).show();
            //JSONObject o=ar.getJSONObject(0);
            //////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();
            //o=ar.getJSONObject(1);
            //////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();


            for(int i=0;i<ar.length();i++){
                JSONObject o=ar.getJSONObject(i);
                ////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();

                names[i]=o.getString("pgname");
                address[i]=o.getString("address");
                price[i]=o.getInt("price");
                //address[i]=o.getString("address");
                //price[i]=o.getInt("price");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pglist, menu);
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
