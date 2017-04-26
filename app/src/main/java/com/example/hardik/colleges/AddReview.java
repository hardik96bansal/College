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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AddReview extends ActionBarActivity {
    RatingBar ser,food,sec,clean,trans,ngbd,value;
    EditText et;
    TextView pgname,pglocation;
    Float fser,ffood,fsec,fclean,ftrans,fngbd,fvalue,favg;
    String sser,sfood,ssec,sclean,strans,sngbd,svalue,savg;
    String pgnm,pglc,rev,uname,fulladd,site,fullsite,fp;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        ser=(RatingBar)findViewById(R.id.ratingBarService);
        food=(RatingBar)findViewById(R.id.ratingBarFood);
        sec=(RatingBar)findViewById(R.id.ratingBarSecurity);
        clean=(RatingBar)findViewById(R.id.ratingBarClean);
        trans=(RatingBar)findViewById(R.id.ratingBarTrans);
        ngbd=(RatingBar)findViewById(R.id.ratingBarNeigh);
        value=(RatingBar)findViewById(R.id.ratingBarValue);

        pgname=(TextView)findViewById(R.id.review_pgname);
        pglocation=(TextView)findViewById(R.id.review_pgloc);
        et=(EditText)findViewById(R.id.review_et);


        Intent intent=getIntent();
        pgnm=intent.getStringExtra("alpha");
        pglc=intent.getStringExtra("beta");
        fp=intent.getStringExtra("tom");
        uname=Search.usn;
        fulladd=PgDetails.padd;

        pgname.setText(pgnm);
        pglocation.setText(pglc);


        ////Toast.makeText(this,"tree is "+fulladd,Toast.LENGTH_SHORT).show();

        submit=(Button)findViewById(R.id.review_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fser=ser.getRating();
                ffood=food.getRating();
                fsec=sec.getRating();
                fclean=clean.getRating();
                ftrans=trans.getRating();
                fngbd=ngbd.getRating();
                fvalue=value.getRating();
                favg=(fser+ffood+fsec+fclean+ftrans+fngbd+fvalue)/7;
                rev=et.getText().toString();


                sser=String.valueOf(fser);
                sfood=String.valueOf(ffood);
                ssec=String.valueOf(fsec);
                sclean=String.valueOf(fclean);
                strans=String.valueOf(ftrans);
                sngbd=String.valueOf(fngbd);
                svalue=String.valueOf(fvalue);
                savg=String.valueOf(favg);


                if(fser==0||ffood==0||fsec==0||fclean==0||ftrans==0||fngbd==0||fvalue==0||favg==0||rev.equals("")){
                    Toast.makeText(AddReview.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                }

                else{
                    //Toast.makeText(AddReview.this,uname,Toast.LENGTH_SHORT).show();
                    fullsite="http://www.minorprojectmanager.hol.es/college/yolo/submitrev.php?name="+uname+"&pgname="+pgnm+"&pgaddress="+pglc+
                            "&faddress="+fulladd+"&service="+fser+"&food="+ffood+"&security="+fsec+"&clean="+fclean+
                            "&transport="+ftrans+"&neighbourhood="+fngbd+"&value="+fvalue+"&avg="+favg+"&review="+rev;

                    site="http://www.minorprojectmanager.hol.es/college/yolo/submitrev.php";
                    //getJSON(url);

                    ////Toast.makeText(AddReview.this,"hola hola hola",Toast.LENGTH_SHORT).show();


                    /*
                    HttpClient httpClient = new DefaultHttpClient();
                    // Creating HTTP Post
                    HttpPost httpPost = new HttpPost(
                            "http://www.minorprojectmanager.hol.es/college/yolo/submitrev.php");

                    // Building post parameters
                    // key and value pair
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                    nameValuePair.add(new BasicNameValuePair("name", name));
                    nameValuePair.add(new BasicNameValuePair("pgname",pgnm));
                    nameValuePair.add(new BasicNameValuePair("pgaddress",pglc));
                    nameValuePair.add(new BasicNameValuePair("faddress",fulladd));
                    nameValuePair.add(new BasicNameValuePair("service",""+fsec));
                    nameValuePair.add(new BasicNameValuePair("food",""+ffood));
                    nameValuePair.add(new BasicNameValuePair("clean",""+fclean));
                    nameValuePair.add(new BasicNameValuePair("transport",""+ftrans));
                    nameValuePair.add(new BasicNameValuePair("neighbourhood",""+fngbd));
                    nameValuePair.add(new BasicNameValuePair("value",""+fvalue));
                    nameValuePair.add(new BasicNameValuePair("avg",""+favg));
                    nameValuePair.add(new BasicNameValuePair("review",""+rev));

                    // Url Encoding the POST parameters
                    try {
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                    } catch (UnsupportedEncodingException e) {
                        // writing error to Log
                        e.printStackTrace();
                    }

                    // Making HTTP Request
                    try {
                        HttpResponse response = httpClient.execute(httpPost);

                        // writing response to log
                        Log.d("Http Response:", response.toString());
                    } catch (ClientProtocolException e) {
                        // writing exception to log
                        e.printStackTrace();
                    } catch (IOException e) {
                        // writing exception to log
                        e.printStackTrace();

                    }*/

                    //insertToDatabase("u","a");


                    //SendDataToServer(uname,pgnm,pglc,fulladd,""+fser    ,""+ffood,""+fsec,""+fclean,""+ftrans,""+fngbd,""+fvalue,""+favg,rev);

                    insertToDatabase1(uname,"h","h",pgnm,pglc,fulladd,sser,sfood,ssec,sclean,strans,sngbd,svalue,savg,rev);
                    //insertToDatabase1("t","t","t","t","t","t","t","t","t","t","t","t","t","t","t");

                    //Toast.makeText(AddReview.this,uname+pgnm+pglc+fulladd+sser+sfood+ssec+sclean+strans+sngbd+svalue+savg+rev,Toast.LENGTH_SHORT).show();






                }


            }
        });

    }






    private void insertToDatabase1(final String name, final String contact, final String email, final String pgname, final String pgaddress, final String fulladdress,
                                   final String service, final String food, final String security, final String clean, final String trans,
                                   final String ngbd, final String value, final String avg, final String rev){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ////Toast.makeText(Signin.this,url,Toast.LENGTH_LONG).show();

                loading = ProgressDialog.show(AddReview.this, "Please Wait...", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                String paramUsername = params[0];
                String paramAddress = params[1];

                ////Toast.makeText(AddReview.this,"mid",Toast.LENGTH_SHORT).show();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("contact", contact));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("pgname", pgname));
                nameValuePairs.add(new BasicNameValuePair("pgaddress", pgaddress));
                nameValuePairs.add(new BasicNameValuePair("faddress", fulladdress));
                nameValuePairs.add(new BasicNameValuePair("service", service));
                nameValuePairs.add(new BasicNameValuePair("food", food));
                nameValuePairs.add(new BasicNameValuePair("security", security));
                nameValuePairs.add(new BasicNameValuePair("clean", clean));
                nameValuePairs.add(new BasicNameValuePair("transport", trans));
                nameValuePairs.add(new BasicNameValuePair("neighbourhood", ngbd));
                nameValuePairs.add(new BasicNameValuePair("value", value));
                nameValuePairs.add(new BasicNameValuePair("avg", avg));
                nameValuePairs.add(new BasicNameValuePair("review", rev));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://minorprojectmanager.hol.es/college/yolo/submitrev.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {
                    //Toast.makeText(AddReview.this,"catch",Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                loading.dismiss();

                Toast.makeText(AddReview.this,"Submitted "+fp,Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(AddReview.this,PgDetails.class);
                intent1.putExtra("red",fp);
                startActivity(intent1);
                finish();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute( name,  contact,  email,  pgname,  pgaddress,  fulladdress,
                service,  food,  security,  clean,  trans,
                ngbd,  value,  avg,  rev);
    }



    private void insertToDatabase(final String name, final String add){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ////Toast.makeText(Signin.this,url,Toast.LENGTH_LONG).show();

                loading = ProgressDialog.show(AddReview.this, "Please Wait...", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                String paramUsername = params[0];
                String paramAddress = params[1];

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", uname));
                nameValuePairs.add(new BasicNameValuePair("pgname",pgnm));
                nameValuePairs.add(new BasicNameValuePair("pgaddress",pglc));
                nameValuePairs.add(new BasicNameValuePair("faddress",fulladd));
                nameValuePairs.add(new BasicNameValuePair("service","fsd"));
                nameValuePairs.add(new BasicNameValuePair("food","fwf"));
                nameValuePairs.add(new BasicNameValuePair("clean","efwe"));
                nameValuePairs.add(new BasicNameValuePair("transport","fwedf"));
                nameValuePairs.add(new BasicNameValuePair("neighbourhood","efew"));
                nameValuePairs.add(new BasicNameValuePair("value","wefew"));
                nameValuePairs.add(new BasicNameValuePair("avg","erfew"));
                nameValuePairs.add(new BasicNameValuePair("review","erew"));




                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://minorprojectmanager.hol.es/college/yolo/submitrev.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                ////Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                //TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                         }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(uname,pgnm,pglc,fulladd,"fsd","fwf","efwe","fd","dfsd","dsfd","efd","efdd");
    }




    private void getJSON(final String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ////Toast.makeText(Signin.this,url,Toast.LENGTH_LONG).show();
                loading = ProgressDialog.show(AddReview.this, "Please Wait...", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    /*StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();*/

                    return "done";

                }catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String s) {
                ////Toast.makeText(Signin.this,s,Toast.LENGTH_LONG).show();
                super.onPostExecute(s);

                loading.dismiss();
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);




    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_review, menu);
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
