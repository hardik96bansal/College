package com.example.hardik.colleges;



        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.view.View;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLEncoder;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by Ayush on 9/28/2016.
 */


public class InsertIntoReview extends AsyncTask<String,Void,String> {

    Context C;
    private String fresult;
    ProgressDialog pg;

    public InsertIntoReview(View.OnClickListener C) {
        this.C = (Context) C;
    }

    @Override
    protected String doInBackground(String... params) {
        HashMap<String,String> hm = new HashMap<String, String>();
        hm.put("name",params[0]);
        hm.put("contact","9811646352");
        hm.put("email","example@gmail.com");
        hm.put("pgname",params[1]);
        hm.put("pgaddress",params[2]);
        hm.put("fulladdress",params[3]);
        hm.put("service",params[4]);
        hm.put("food",params[5]);
        hm.put("security",params[6]);
        hm.put("cleaneness",params[7]);
        hm.put("transport",params[8]);
        hm.put("neighbourhood",params[9]);
        hm.put("value",params[10]);
        hm.put("avg",params[11]);
        hm.put("review",params[12]);



        try {
            URL url = new URL("http://minorprojectmanager.hol.es/college/yolo/submitrev1.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            //Exception thrown if no data available for reading in the set time.
            conn.setConnectTimeout(15000);
            //Exception thrown if no connection is made by the web server.
            conn.setRequestMethod("POST");
            //sets the request method for the url request.
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream outstream = conn.getOutputStream();
            //Opening a stream with an intention of writing data to server.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outstream, "UTF-8"));
            //BufferedWriter provides a memory buffer and improves the speed.
            //OutputStreamWriter encodes the character written to it to bytes. Encoded data takes less space.
            writer.write(getPostDataString(hm));
            writer.flush();
            writer.close();
            outstream.close();


            InputStream instream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream,"iso-8859-1"));
            StringBuilder result = new StringBuilder();
            String line = null;

            while((line = reader.readLine())!=null){
                result.append(line+"\n");
            }
            fresult = result.toString();
            reader.close();
            JSONObject root = new JSONObject(fresult);
            JSONArray response = root.getJSONArray("result");
            JSONObject finaljsonobject = response.getJSONObject(0);
            fresult = finaljsonobject.getString("output");
            reader.close();
            instream.close();
            conn.disconnect();
        }catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fresult;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg = new ProgressDialog(C);
        pg.setTitle("Signing Up");
        pg.setMessage("Please Wait...");
        pg.setCancelable(false);
        pg.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pg.dismiss();
        //Toast.makeText(C,s,Toast.LENGTH_LONG).show();
        if(s.equals("YES")){
            Intent i_go = new Intent(C,Search.class);
            C.startActivity(i_go);
        }
        else{
            //Toast.makeText(C, "Email or Mobile is already registered.", Toast.LENGTH_SHORT).show();
        }
    }

    public String getPostDataString(HashMap<String,String> hm) throws UnsupportedEncodingException {
        StringBuilder hashString = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String,String> point : hm.entrySet()){
            if(first)
                first = false;
            else
                hashString.append("&");
            hashString.append(URLEncoder.encode(point.getKey(), "UTF-8"));
            hashString.append("=");
            hashString.append(URLEncoder.encode(point.getValue(), "UTF-8"));
        }
        return hashString.toString();
    }
}