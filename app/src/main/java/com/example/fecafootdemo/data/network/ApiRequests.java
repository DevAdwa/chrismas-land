package com.example.fecafootdemo.data.network;

import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fecafootdemo.AppLoader;
import com.example.fecafootdemo.data.CoreDataManager;
import com.example.fecafootdemo.utils.AppConstants;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/28/22)
 */
public class ApiRequests extends AppConstants implements IApis {
    private final String TAG = ApiRequests.class.getSimpleName();
    private final RequestQueue requestStack = Volley.newRequestQueue(AppLoader.getInstance());

    @Override
    public void authUser(String pseudo, String password, String deviceId, ApiCallback apiCallback) {
        try {
            JSONObject params = new JSONObject();
            params.put(USER_CODE, pseudo);
            params.put(USER_PASS, Base64.encodeToString(password.getBytes(), Base64.DEFAULT));
            params.put(DEVICE_ID, deviceId.toUpperCase());

            Log.i(TAG, params.toString());
            VolleyLog.DEBUG = true;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AUTH_USER, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e(TAG, response.toString());
                    try {
                        JSONObject pesake = response.getJSONObject("pesake");
                        if (pesake.has("code") && !pesake.isNull("code")){
                            apiCallback.ServerErrorCode(pesake.getInt("code"));
                        }else {
                            apiCallback.Success(response.getJSONObject("data"));
                            Log.d("found", response.toString());
                        }
                    } catch (JSONException exception) {
                        apiCallback.Failure(exception);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    apiCallback.VolleyException(error);
                }
            });
            requestStack.add(request);

        }catch (JSONException e){
            apiCallback.Failure(e);
        }
    }

    @Override
    public void getMatchList(String userCode, String deviceId, ApiCallback apiCallback) {
        try {
            JSONObject params = new JSONObject();
            params.put(USER_CODE, userCode);
            params.put(DEVICE_KEY, deviceId);

            Log.i(TAG, params.toString());
            VolleyLog.DEBUG = true;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MATCH_LIST, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e(TAG, response.toString());
                    try {
                        JSONObject pesake = response.getJSONObject("pesake");
                        if (pesake.has("code") && !pesake.isNull("code")){
                            apiCallback.ServerErrorCode(pesake.getInt("code"));
                        }else {
                            JSONArray data = response.getJSONArray("data");
                            apiCallback.Success(data);
                        }
                    } catch (JSONException exception) {
                        apiCallback.Failure(exception);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    apiCallback.VolleyException(error);
                }
            });

            requestStack.add(request);

        }catch (JSONException exception){
            apiCallback.Failure(exception);
        }
    }

    @Override
    public void getControl(String ticketCode, String userCode, ApiCallback apiCallback) {
        try {
            JSONObject params = new JSONObject();
            params.put("ticketCode", ticketCode);
            params.put("qrCode", "");
            params.put("coordinates", CoreDataManager.getInstance().getUser().getUsuser());

            JsonObject coordinates = new JsonObject();
            coordinates.addProperty("lat", 4.039203);
            coordinates.addProperty("lng", 4.039203);
            params.put("coordinates", coordinates);
            params.put(USER_CODE, userCode);
            Log.i(TAG, params.toString());
            VolleyLog.DEBUG = true;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, CONTROL_TICKET, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e(TAG, response.toString());
                    try {
                        JSONObject pesake = response.getJSONObject("pesake");
                        if (pesake.has("code") && !pesake.isNull("code")){
                            apiCallback.ServerErrorCode(pesake.getInt("code"));
                        }else {
                            JSONObject data = response.getJSONObject("data");
                            apiCallback.Success(data);
                        }
                    } catch (JSONException exception) {
                        apiCallback.Failure(exception);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    apiCallback.VolleyException(error);
                }
            });

            requestStack.add(request);

        }catch (JSONException exception){
            apiCallback.Failure(exception);
        }
    }

    @Override
    public void checkCard(String ticketCode, String qrCode, ApiCallback apiCallback) {
        try {
            JSONObject params = new JSONObject();
            params.put("ticketCode", ticketCode);
            params.put("qrCode", qrCode);
            params.put(USER_CODE, CoreDataManager.getInstance().getUser().getUsuser());
            Log.i(TAG, params.toString());
            VolleyLog.DEBUG = true;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, GET_TICKET_INFO, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e(TAG, response.toString());
                    try {
                        JSONObject pesake = response.getJSONObject("pesake");
                        if (pesake.has("code") && !pesake.isNull("code")){
                            apiCallback.ServerErrorCode(pesake.getInt("code"));
                        }else {
                            JSONObject data = response.getJSONArray("data").getJSONObject(0);
                            apiCallback.Success(data);
                        }
                    } catch (JSONException exception) {
                        apiCallback.Failure(exception);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    apiCallback.VolleyException(error);
                }
            });

            requestStack.add(request);

        }catch (JSONException exception){
            apiCallback.Failure(exception);
        }
    }

    @Override
    public void export(JSONObject payload, ApiCallback apiCallback) {
        Log.i(TAG, payload.toString());
        VolleyLog.DEBUG = true;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, EXPORT, payload, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, response.toString());
                try {
                    JSONObject pesake = response.getJSONObject("pesake");
                    if (pesake.has("code") && !pesake.isNull("code")){
                        apiCallback.ServerErrorCode(pesake.getInt("code"));
                    }else {
                        JSONObject data = response.getJSONObject("data");
                        apiCallback.Success(data);
                    }
                } catch (JSONException exception) {
                    apiCallback.Failure(exception);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiCallback.VolleyException(error);
            }
        });

        requestStack.add(request);
    }
}
