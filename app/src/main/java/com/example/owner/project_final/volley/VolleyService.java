package com.example.owner.project_final.volley;

import android.content.Context;
import android.location.Location;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.example.owner.project_final.L;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class VolleyService {
    private VolleyResult resultCallback = null;
    private Context mContext = null;


    private Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            resultCallback.notifySuccess(null, response);
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            resultCallback.notifyError(error);
        }
    };

    public VolleyService(VolleyResult resultCallback, Context context) {
        this.resultCallback = resultCallback;
        mContext = context;
    }

    public void getGeocoder(Location location) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        String output = "json";
        String parameter = "latlng=" + location.getLatitude() + "," + location.getLongitude() + "&language=ko" + "&key=" + "AIzaSyAggFaSQsTk8_Rsk_znu9QYMD7agMtoZVA";
        final String url = "https://maps.googleapis.com/maps/api/geocode/"
                + output + "?" + parameter;

        L.e("::::geocoder : " + url);

        Request<JSONObject> req = new Request<JSONObject>(Request.Method.GET, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultCallback.notifyError(error);
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String json = new String(
                            response.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(json), HttpHeaderParser.parseCacheHeaders(response));

                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JsonSyntaxException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONObject response) {
                resultCallback.notifySuccess("", response);
            }
        };
        requestQueue.add(req);
    }

}
