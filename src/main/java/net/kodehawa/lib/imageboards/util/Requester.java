package net.kodehawa.lib.imageboards.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Requester {
    private final OkHttpClient client = new OkHttpClient();

    public String request(String url){
        try {
            Request r = new Request.Builder()
                    .url(url)
                    .build();

            Response r1 = client.newCall(r).execute();
            String response = r1.body().string();

            r1.close();

            return response;
        } catch (Exception e) {
            return null;
        }

    }
}
