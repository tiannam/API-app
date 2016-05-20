package com.example.tiannanmcclanahan.tiannaflikrapi;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by tiannan.mcclanahan on 5/20/16.
 */
public class PhotoCalls {

    private static PhotoCalls instance;
    private static ApiResponseHandler responseHandler;

    //Singleton to get instance of PhotoCalls
    public static PhotoCalls getInstance(ApiResponseHandler handler) {
        responseHandler = handler;
        if(instance == null) {
            instance = new PhotoCalls();
        }
        return instance;
    }

    //get photos
    public void doRequest() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=10f37feff9a19338e1dcbe6499cf45bc&format=json&text=disneyland", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                String disneyland = null;

                try {
                    JSONArray results = response.getJSONArray("results");
                    JSONObject place = (JSONObject) results.get(0);
                    disneyland = place.getString("Disneyland");
                }catch (JSONException e){
                    e.printStackTrace();
                }

                responseHandler.handleResponse(disneyland);
            }
        });
    }


    public interface ApiResponseHandler{
        void handleResponse(String response);
    }
}
