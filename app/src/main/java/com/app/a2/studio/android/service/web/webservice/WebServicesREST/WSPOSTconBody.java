package com.app.a2.studio.android.service.web.webservice.WebServicesREST;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.a2.studio.android.service.web.webservice.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WSPOSTconBody extends AsyncTask<String, Void, Void>{

    Context applicationContext;

    MainActivity mainActivity;

    String USERNAME;
    String PASSWORD;

    String url;

    String respuesta;

    public WSPOSTconBody(Context context){
        mainActivity = new MainActivity();

        USERNAME = "a";
        PASSWORD = "x";

        url = "http://192.168.1.39:8081/persona/hola";

        applicationContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("firstname", params[0]);
            jsonBody.put("lastname", params[1]);
            final String requestBody = jsonBody.toString();

            /* ******************* */
            StringRequest objectRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            respuesta = response.toString();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {



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

    @Override
    protected void onPostExecute(Void aVoid) {
        mainActivity.POSTconBodyThreadFinished(respuesta);
    }
}
