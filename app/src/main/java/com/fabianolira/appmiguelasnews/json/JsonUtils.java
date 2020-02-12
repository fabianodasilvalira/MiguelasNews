package com.fabianolira.appmiguelasnews.json;

import android.content.Context;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

public class JsonUtils {
    Context context;
    private static HttpClient httpClient = HttpClientBuilder.create().build();


    public JsonUtils(Context context){
        this.context = context;
    }

    public static String retornaJsonDeGet(String url){
        HttpGet client = new HttpGet(url);
        client.addHeader ("Content-type", "application/json");

        HttpResponse response = null;
        String json = null;
        try {
            response = httpClient.execute(client);
            json = EntityUtils.toString(response.getEntity());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
