package com.app.a2.studio.android.service.web.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.a2.studio.android.service.web.webservice.WebServicesREST.Interfaces.OnResponsePOSTconBody;
import com.app.a2.studio.android.service.web.webservice.WebServicesREST.Services.WSPOSTconBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    Button button;
    TextView textView, textView2;

    WSPOSTconBody wsposTconBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.text);
        textView2 = (TextView) findViewById(R.id.textView);



        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            textView2.setText(String.valueOf(isConnected));
        }catch (NullPointerException e){
            textView2.setText("NullpointerException");
        }

        wsposTconBody = new WSPOSTconBody(MainActivity.this, new OnResponsePOSTconBody() {
            @Override
            public void onSuccess(String response) {
                if (response != null){
                    textView.setText(response);
                }
            }

            @Override
            public void onFailure(Exception exception) {
                if (exception != null){
                    textView.setText(exception.toString());
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.button){
                    //GETbasicoConAuth geTbasicoConAuth = new GETbasicoConAuth();
                    //geTbasicoConAuth.execute();

                    //GETConParamsConAuth getConParamsConAuth = new GETConParamsConAuth();
                    //getConParamsConAuth.execute();

                    //PostConBody postConBody = new PostConBody();
                    //postConBody.execute("algo", "nada");

                    wsposTconBody.executeWebService("Eder", "Villar");
                }
            }
        });

    }

    private class GETbasicoConAuth extends AsyncTask<Void, Void, Void>{

        String USERNAME = "a";
        String PASSWORD = "x";

        String url = "http://192.168.1.39:8081/persona/hola";

        @Override
        protected Void doInBackground(Void... voids) {

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

            /* ******************* */
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            textView.setText("Response: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            textView.setText("Error: " + error.toString());
                        }
                    }

            ){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();

                    String credentials = USERNAME+":"+PASSWORD;
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            /* ******************* */

            requestQueue.add(objectRequest);

            return null;
        }
    }

    private class GETConParamsConAuth extends AsyncTask<Void, Void, Void>{

        String USERNAME = "a";
        String PASSWORD = "x";

        String url = "http://192.168.1.39:8081/persona/saludo?nombre=jorge&apellido=rda";

        @Override
        protected Void doInBackground(Void... voids) {

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

            /* ******************* */
            StringRequest objectRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            textView.setText("Response: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            textView.setText("Error: " + error.toString());
                        }
                    }

            ){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();

                    String credentials = USERNAME+":"+PASSWORD;
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            /* ******************* */

            requestQueue.add(objectRequest);

            return null;
        }
    }

    private class PostConBody extends AsyncTask<String, Void, Void>{

        String USERNAME = "a";
        String PASSWORD = "x";

        String url = "http://192.168.1.39:8081/persona/hola";

        @Override
        protected Void doInBackground(String... params) {

            try {

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("firstname", params[0]);
                jsonBody.put("lastname", params[1]);
                final String requestBody = jsonBody.toString();

                /* ******************* */
                StringRequest objectRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                textView.setText("Response: " + response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                textView.setText("Error: " + error.toString());
                            }
                        }

                ) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() {
                        try {
                            return (requestBody.getBytes("utf-8"));
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();

                        String credentials = USERNAME + ":" + PASSWORD;
                        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        headers.put("Authorization", auth);
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }
                };
                /* ******************* */

                requestQueue.add(objectRequest);

            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

    }

    public void POSTconBodyThreadFinished(String s){
        textView.setText(s);
    }

}
