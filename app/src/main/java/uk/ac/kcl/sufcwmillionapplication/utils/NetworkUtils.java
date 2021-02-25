package uk.ac.kcl.sufcwmillionapplication.utils;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static OkHttpClient client = new OkHttpClient();

    public static String fetchUrl(String url) {
        Request request = new Request.Builder().url(url).get().build();
        String content = "";
        try {
            Response response =  client.newCall(request).execute();
            content = response.body().string();
        }catch (Exception ex){
            Log.e("NetworkUtils","Catch network error",ex);
        }
        return content;
    }
}
