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

import net.kodehawa.lib.imageboards.entities.exceptions.QueryFailedException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Utility action instance that can execute a request and obtain the result
 * by blocking the calling thread or using callbacks.
 *
 * @param <T> Return type of the request.
 * @author Avarel
 */
public class RequestAction<T> {
    private final Call call;
    private final Function<Response, T> transform;

    RequestAction(Call call, Function<Response, T> successTransformer) {
        this.call = call;
        this.transform = successTransformer;
    }

    /**
     * Obtain the request result by blocking the calling thread.
     *
     * @return Return type of the request.
     */
    public T blocking() {
        try (Response response = call.execute()) {
            if (response.code() != 200) {
                throw new QueryFailedException(response.code(), call.request().url().toString());
            }

            return transform.apply(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtain the result by submitting the task to an executor and
     * returning a {@link Future promise}.
     *
     * @return Executor promise.
     */
    public Future<T> submit() {
        CompletableFuture<T> future = new CompletableFuture<>();
        async(future::complete, future::completeExceptionally);
        return future;
    }

    /**
     * Obtain the result by submitting the task to an executor
     * and forwarding the result to a success consumer.
     *
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

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                failureConsumer.accept(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.code() != 200) {
                        throw new QueryFailedException(response.code(), RequestAction.this.call.request().url().toString());
                    }

                    successConsumer.accept(transform.apply(response));
                } catch (Throwable e) {
                    if (failureConsumer != null) {
                        failureConsumer.accept(e);
                    }
                } finally {
                    response.close();
                }
            }
        });
    }
}
