/*
 * Copyright 2017 Kodehawa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.kodehawa.lib.imageboards.requests;

import net.kodehawa.lib.imageboards.ImageBoard;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.function.Function;

/**
 * A requester that can make request actions.
 *
 * @author Avarel
 * @see RequestAction
 */
public class RequestFactory {
    /**
     * Request client.
     */
    private final OkHttpClient client;

    /**
     * Create a requester that can make request actions.
     *
     * @param client OKHttp client.
     */
    public RequestFactory(OkHttpClient client) {
        this.client = client;
    }

    /**
     * Creates a {@link RequestAction request action} on a URL.
     *
     * @param url       Target url.
     * @param transform Successful response transformer.
     * @param <T>       Return type.
     * @return Request action.
     */
    public <T> RequestAction<T> makeRequest(String url, Function<Response, T> transform) {
        return makeRequest(HttpUrl.parse(url), transform);
    }

    /**
     * Creates a {@link RequestAction request action} on a URL.
     *
     * @param url       Target url.
     * @param transform Successful response transformer.
     * @param <T>       Return type.
     * @return Request action.
     */
    public <T> RequestAction<T> makeRequest(HttpUrl url, Function<Response, T> transform) {
        return new RequestAction<>(client.newCall(new Request.Builder().url(url).header("User-Agent", ImageBoard.userAgent).build()), transform);
    }
}
