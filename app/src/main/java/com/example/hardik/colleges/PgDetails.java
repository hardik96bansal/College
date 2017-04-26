package com.example.hardik.colleges;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PgDetails extends ActionBarActivity {

    TextView pgname,pgloc,pgadd,serv,rat,extra1,price;
    int nor;
    String pname="",ploc,sv,url,fJSON,nm[],review[],mJSON,phn,prc,fpnm,time,date;
    static String padd;
    float rt=0,rating[];
    ImageView im1,im2,im3,im4,im;

    String[] xyz={"Hello","Bye","yo"};
    String[] mno={"one","two","three"};
    String lat="28.5215930000";
    String lon="77.3656700000";
    Button book,stay,call,map,rev,visit;
    int mYear, mMonth, mDay, mHour, mMinute;

    ListView l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_details);


        pgname=(TextView)findViewById(R.id.details_pgname);
        pgloc=(TextView)findViewById(R.id.details_pglocation);
        pgadd=(TextView)findViewById(R.id.details_pgadd2);
        serv=(TextView)findViewById(R.id.details_pgser2);
        rat=(TextView)findViewById(R.id.details_pgrating);
        extra1=(TextView)findViewById(R.id.details_extra1);
        price=(TextView)findViewById(R.id.details_price2);

        im=(ImageView)findViewById(R.id.im);
        im1=(ImageView)findViewById(R.id.im1);
        im2=(ImageView)findViewById(R.id.im2);
        im3=(ImageView)findViewById(R.id.im3);
        im4=(ImageView)findViewById(R.id.im4);


        rev=(Button)findViewById(R.id.details_review_button);
        map=(Button)findViewById(R.id.details_map);
        call=(Button)findViewById(R.id.details_call_button);
        visit=(Button)findViewById(R.id.visit);
        book=(Button)findViewById(R.id.bookmark);


        Intent rec=getIntent();
        pname=rec.getStringExtra("red");
        fpnm=pname;

        //Toast.makeText(this,"name is "+pname,Toast.LENGTH_SHORT).show();


        //url="http://www.minorprojectmanager.hol.es/college/yolo/detail.php?location="+pname+";";
        url="http://www.minorprojectmanager.hol.es/college/yolo/detail.php?location="+pname;
        //Toast.makeText(PgDetails.this, url, Toast.LENGTH_SHORT).show();
        getJSON(url);

        rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PgDetails.this,AddReview.class);
                intent.putExtra("alpha",pname);
                intent.putExtra("beta",ploc);
                intent.putExtra("tom",fpnm);
                startActivity(intent);
                finish();
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Uri location = Uri.parse("geo:28.5215930000,77.3656700000"); // z param is zoom level
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                Intent mapIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:28.5215930000,77.3656700000?q=<14>,<long>x    "));
                startActivity(mapIntent1);
                */
                getJSONmap("http://www.minorprojectmanager.hol.es/college/yolo/map.php?pgname=" + pname);


            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:"+phn);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                TimePickerDialog tpd = new TimePickerDialog(PgDetails.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time=(hourOfDay + ":" + minute);
                                ////Toast.makeText(PgDetails.this,date +time,Toast.LENGTH_SHORT).show();
                                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                                smsIntent.setType("vnd.android-dir/mms-sms");
                                smsIntent.putExtra("address", phn);
                                smsIntent.putExtra("sms_body","Hello Sir, I am "+Search.usn+" and came to know about your P.G. through NOMADO app. I would like to visit the P.G. on "+date+" at "+time+". Please reply if the time is suitable for you.");
                                startActivity(smsIntent);
                            }
                        }, mHour, mMinute, false);
                tpd.show();


                DatePickerDialog dpd = new DatePickerDialog(PgDetails.this,
                        new DatePickerDialog.OnDateSetListener() {



                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date=(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);



                            }
                        }, mYear, mMonth, mDay);
                dpd.show();





            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertToDatabase1(Search.usn,pname,padd,prc);

            }
        });

    }

    private void insertToDatabase1(final String username, final String pgname, final String address, final String price){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ////Toast.makeText(Signin.this,url,Toast.LENGTH_LONG).show();

                loading = ProgressDialog.show(PgDetails.this, "Please Wait...", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                String paramUsername = params[0];
                String paramAddress = params[1];

                ////Toast.makeText(PgDetails.this,"mid",Toast.LENGTH_SHORT).show();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("pgname", pgname));
                nameValuePairs.add(new BasicNameValuePair("address", address));
                nameValuePairs.add(new BasicNameValuePair("price", price));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://minorprojectmanager.hol.es/college/yolo/bookmark.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {
                    //Toast.makeText(PgDetails.this,"catch",Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                loading.dismiss();

                Toast.makeText(PgDetails.this,"Bookmarked",Toast.LENGTH_SHORT).show();


            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute( username,  pgname,  address,  price);
    }

    private void getJSON(final String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ////Toast.makeText(Signin.this,url,Toast.LENGTH_LONG).show();
                loading = ProgressDialog.show(PgDetails.this, "Please Wait...", null, true, true);
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

                rt=0;
                parseJSON(fJSON);
                loading.dismiss();
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);




    }



    private void getJSONmap(final String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ////Toast.makeText(Signin.this,url,Toast.LENGTH_LONG).show();
                loading = ProgressDialog.show(PgDetails.this, "Please Wait...", null, true, true);
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

                mJSON=s;
                parseJSONmap(mJSON);
               //Toast.makeText(PgDetails.this,mJSON,Toast.LENGTH_SHORT).show();
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);




    }


    public void parseJSONmap(String jsonval){

        //Toast.makeText(PgDetails.this, "yo is " + jsonval, Toast.LENGTH_SHORT).show();

        try {
            JSONObject ob=new JSONObject(jsonval);
            JSONArray ar=ob.getJSONArray("result");

            JSONObject h=ar.getJSONObject(0);
            lat= h.getString("lat");
            lon=h.getString("lon");


            String head=pname;
            String geoUriString="geo:"+lat+","+lon+"?q=("+head+")@"+lat+","+lon;
            Uri geoUri = Uri.parse(geoUriString);
            Intent mapCall  = new Intent(Intent.ACTION_VIEW, geoUri);
            startActivity(mapCall);
            ////Toast.makeText(PgDetails.this,"lat="+lat,Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }






    public void parseJSON(String jsonval){

        //Toast.makeText(PgDetails.this, "yo is " + jsonval, Toast.LENGTH_SHORT).show();

        try {
            JSONObject ob=new JSONObject(jsonval);
            JSONArray ar=ob.getJSONArray("result");

            nm=new String[ar.length()];
            review=new String[ar.length()];
            rating=new float[ar.length()];
            ////Toast.makeText(PGList.this,""+ar.length(),Toast.LENGTH_SHORT).show();
            //JSONObject o=ar.getJSONObject(0);
            ////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();
            //o=ar.getJSONObject(1);
            ////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();

            JSONObject h=ar.getJSONObject(0);
            pname= h.getString("pgname");
            ploc=h.getString("pgaddress");
            padd=h.getString("fulladdress");
            phn=h.getString("contact");
            prc=h.getString("price");
            nor=ar.length();


            if(pname.equals("Pradhan P.G.")){
                im.setImageResource(R.drawable.f11);
                im1.setImageResource(R.drawable.f11);
                im2.setImageResource(R.drawable.f12);
                im3.setImageResource(R.drawable.f13);
                im4.setImageResource(R.drawable.f14);
            }

            if(pname.equals("Shivshakti P.G.")){
                im.setImageResource(R.drawable.f21);
                im1.setImageResource(R.drawable.f21);
                im2.setImageResource(R.drawable.f22);
                im3.setImageResource(R.drawable.f23);
                im4.setImageResource(R.drawable.f24);
            }

            if(pname.equals("Madhu P.G.")){
                im.setImageResource(R.drawable.f31);
                im1.setImageResource(R.drawable.f31);
                im2.setImageResource(R.drawable.f32);
                im3.setImageResource(R.drawable.f33);
                im4.setImageResource(R.drawable.f34);
            }

            if(pname.equals("Raj P.G.")){
                im.setImageResource(R.drawable.f41);
                im1.setImageResource(R.drawable.f41);
                im2.setImageResource(R.drawable.f42);
                im3.setImageResource(R.drawable.f43);
                im4.setImageResource(R.drawable.f44);
            }

            if(pname.equals("Workingmen P.G.")){
                im.setImageResource(R.drawable.f51);
                im1.setImageResource(R.drawable.f51);
                im2.setImageResource(R.drawable.f52);
                im3.setImageResource(R.drawable.f53);
                im4.setImageResource(R.drawable.f54);
            }

            if(pname.equals("Lotus P.G.")){
                im.setImageResource(R.drawable.f61);
                im1.setImageResource(R.drawable.f61);
                im2.setImageResource(R.drawable.f62);
                im3.setImageResource(R.drawable.f63);
                im4.setImageResource(R.drawable.f64);
            }

            if(pname.equals("Malhotra's P.G.")){
                im.setImageResource(R.drawable.f71);
                im1.setImageResource(R.drawable.f71);
                im2.setImageResource(R.drawable.f72);
                im3.setImageResource(R.drawable.f73);
                im4.setImageResource(R.drawable.f74);
            }

            if(pname.equals("Bansal Kutir P.G.")){
                im.setImageResource(R.drawable.f81);
                im1.setImageResource(R.drawable.f81);
                im2.setImageResource(R.drawable.f82);
                im3.setImageResource(R.drawable.f83);
                im4.setImageResource(R.drawable.f84);
            }

            if(pname.equals("Ahuja P.G.")){
                im.setImageResource(R.drawable.f91);
                im1.setImageResource(R.drawable.f91);
                im2.setImageResource(R.drawable.f92);
                im3.setImageResource(R.drawable.f93);
                im4.setImageResource(R.drawable.f94);
            }

            if(pname.equals("Fresher House P.G.")){
                im.setImageResource(R.drawable.f101);
                im1.setImageResource(R.drawable.f101);
                im2.setImageResource(R.drawable.f102);
                im3.setImageResource(R.drawable.f103);
                im4.setImageResource(R.drawable.f104);
            }

            if(pname.equals("Shakti P.G.")){
                im.setImageResource(R.drawable.f111);
                im1.setImageResource(R.drawable.f111);
                im2.setImageResource(R.drawable.f112);
                im3.setImageResource(R.drawable.f113);
                im4.setImageResource(R.drawable.f114);
            }

            if(pname.equals("Jain P.G.")){
                im.setImageResource(R.drawable.f121);
                im1.setImageResource(R.drawable.f121);
                im2.setImageResource(R.drawable.f122);
                im3.setImageResource(R.drawable.f123);
                im4.setImageResource(R.drawable.f124);
            }

            if(pname.equals("Nahar Niketan P.G.")){
                im.setImageResource(R.drawable.f131);
                im1.setImageResource(R.drawable.f131);
                im2.setImageResource(R.drawable.f132);
                im3.setImageResource(R.drawable.f133);
                im4.setImageResource(R.drawable.f134);
            }

            if(pname.equals("Laxmi Girls P.G.")){
                im.setImageResource(R.drawable.f141);
                im1.setImageResource(R.drawable.f141);
                im2.setImageResource(R.drawable.f142);
                im3.setImageResource(R.drawable.f143);
                im4.setImageResource(R.drawable.f144);
            }

            if(pname.equals("Vajiram P.G.")){
                im.setImageResource(R.drawable.f151);
                im1.setImageResource(R.drawable.f151);
                im2.setImageResource(R.drawable.f152);
                im3.setImageResource(R.drawable.f153);
                im4.setImageResource(R.drawable.f154);
            }

            if(pname.equals("Agarwal Atithi P.G.")){
                im.setImageResource(R.drawable.f161);
                im1.setImageResource(R.drawable.f161);
                im2.setImageResource(R.drawable.f162);
                im3.setImageResource(R.drawable.f163);
                im4.setImageResource(R.drawable.f164);
            }


            //Toast.makeText(PgDetails.this,"Phone is "+phn,Toast.LENGTH_SHORT).show();


            for(int i=0;i<ar.length();i++){
                JSONObject o=ar.getJSONObject(i);
                ////Toast.makeText(PGList.this,o.getString("pgname"),Toast.LENGTH_SHORT).show();

                nm[i]=o.getString("name");
                review[i]=o.getString("review");
                rating[i]=Float.parseFloat(o.getString("avg"));

                rt=rating[i]+rt;

                ////Toast.makeText(PgDetails.this,""+rating[i],Toast.LENGTH_SHORT).show();
                //address[i]=o.getString("address");
                //price[i]=o.getInt("price");
            }
            rt=rt/ar.length();
            ////Toast.makeText(PgDetails.this,String.format("%.1f", rt),Toast.LENGTH_SHORT).show();

            if(rt>3.5){
                sv="- Laundry\n- Maid\n- High Speed Internet\n- 24hr Power Backup\n- Guarded Facility\n- CCTV Cameras\n- Superior Food Quality\n- Parking Facility";
            }

            if(rt<3.5&&rt>2.5){
                sv="- Laundry\n- Maid\n- High Speed Internet\n- 24hr Power Backup\n- CCTV Cameras\n- Superior Food Quality";

            }

            if(rt<2.5){
                sv="- Laundry\n- Maid\n- High Speed Internet";
            }

            pgname.setText(pname);
            pgloc.setText(ploc);
            pgadd.setText(padd);
            rat.setText(String.format("%.1f",rt));
            extra1.setText(nor+" Reviews  |  6 Bookmarks  |  14 Stayed Here");
            serv.setText(sv);
            price.setText("Rs "+prc+"/-");


            l1=(ListView)findViewById(R.id.details_list1);


            ReviewList rl=new ReviewList(this,nm,review);
            l1.setAdapter(rl);
           // int m=setListViewHeightBasedOnChildren(l1);
            //setListViewHeightBasedOnChildren(l1);
            ////Toast.makeText(PgDetails.this,"m is "+m,Toast.LENGTH_SHORT).show();

            /*
            ViewGroup.LayoutParams params = l1.getLayoutParams();
            params.height = m + (l1.getDividerHeight() * (l1.getCount() - 1));

            //Toast.makeText(PgDetails.this,"p is "+params.height,Toast.LENGTH_SHORT).show();
            l1.setLayoutParams(params);
            l1.setMinimumHeight(m);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pg_details, menu);
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
