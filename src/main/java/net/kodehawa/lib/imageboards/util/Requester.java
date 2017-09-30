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

package net.kodehawa.lib.imageboards.util;

import net.kodehawa.lib.imageboards.entities.exceptions.QueryFailedException;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A requester that can make request actions.
 *
 * @see Action
 * @author Avarel
 */
public class Requester {
    /**
     * Executor service.
     */
    private final ExecutorService executor;

    /**
     * Request client.
     */
    private final OkHttpClient client;

    /**
     * Create a requester that can make request actions.
     *
     * @param executor Executor service.
     * @param client OKHttp client.
     */
    public Requester(ExecutorService executor, OkHttpClient client) {
        this.executor = executor;
        this.client = client;
    }

    /**
     * Creates a {@link Action request action} on a URL.
     *
     * @param url Target url.
     * @param transform Successful response transformer.
     * @param <T> Return type.
     * @return Request action.
     */
    public <T> Requester.Action<T> makeRequest(String url, Function<String, T> transform) {
        return makeRequest(HttpUrl.parse(url), transform);
    }

    /**
     * Creates a {@link Action request action} on a URL.
     *
     * @param url Target url.
     * @param transform Successful response transformer.
     * @param <T> Return type.
     * @return Request action.
     */
    public <T> Requester.Action<T> makeRequest(HttpUrl url, Function<String, T> transform) {
        return new Action<>(url, transform);
    }

    /**
     * Utility action instance that can execute a request and obtain the result
     * by blocking the calling thread or using callbacks.
     *
     * @param <T> Return type of the request.
     */
    public class Action<T> {
        private final HttpUrl url;
        private final Function<String, T> transform;

        private Action(HttpUrl url, Function<String, T> successTransformer) {
            this.url = url;
            this.transform = successTransformer;
        }

        /**
         * Obtain the request result by blocking the calling thread.
         * @return Return type of the request.
         */
        public T blocking() {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response resp = client.newCall(request).execute()) {
                if (resp.code() != 200) throw new QueryFailedException(resp.code(), url.toString());

                ResponseBody body = resp.body();

                if (body == null) {
                    throw new IllegalStateException("Code is 200 but response body is null?");
                }

                return transform.apply(body.string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Obtain the result by submitting the task to an executor and
         * returning a {@link Future promise}.
         * @return Executor promise
         */
        public Future<T> toFuture() {
            return executor.submit(this::blocking);
        }

        /**
         * Obtain the result by submitting the task to an executor
         * and forwarding the result to a success consumer.
         * @param successConsumer Consumer of the response.
         */
        public void async(Consumer<T> successConsumer) {
            async(successConsumer, null);
        }

        /**
         * Obtain the result by submitting the task to an executor
         * and forwarding the result to a success consumer.
         * <br>
         * However, if an exception occurs then the exception will forward to the
         * failure consumer if set.
         *
         * @param successConsumer Consumer of the response.
         * @param failureConsumer Consumer of the exception (if it occurs).
         */
        public void async(Consumer<T> successConsumer, Consumer<Throwable> failureConsumer) {
            if (successConsumer == null) {
                throw new NullPointerException("success consumer == null");
            }

            executor.execute(() -> {
                try {
                    successConsumer.accept(blocking());
                } catch (Throwable e) {
                    if (failureConsumer != null) {
                        failureConsumer.accept(e);
                    }
                }
            });
        }
    }
}
