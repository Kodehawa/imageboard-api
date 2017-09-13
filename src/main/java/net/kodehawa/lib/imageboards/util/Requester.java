package net.kodehawa.lib.imageboards.util;

import net.kodehawa.lib.imageboards.entities.QueryFailedException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Requester {
    private final OkHttpClient client = new OkHttpClient();

    public String request(String url) throws QueryFailedException {
        Response resp = null;
        try {
            Request req = new Request.Builder()
                    .url(url)
                    .build();

            resp = client.newCall(req).execute();
            if (resp.code() != 200)
                throw new QueryFailedException(resp.code(), url);
            String response = resp.body().string();
            return response;
        } catch (QueryFailedException e) {
            throw e;
        } catch (Exception e) {
            if (resp != null)
                throw new QueryFailedException(resp.code(), url, e);
        } finally {
            if (resp != null)
                resp.close();
        }
        return null;
    }
}
