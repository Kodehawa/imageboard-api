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
package net.kodehawa.lib.imageboards.requests

import net.kodehawa.lib.imageboards.entities.exceptions.QueryFailedException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.annotations.NotNull
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

/**
 * Utility action instance that can execute a request and obtain the result
 * by blocking the calling thread or using callbacks.
 *
 * @param <T> Return type of the request.
 * @author Avarel
</T> */
class RequestAction<T> internal constructor(private val call: Call, private val transform: (Response) -> T) {

	/**
	 * Obtain the request result by blocking the calling thread.
	 *
	 * @return Return type of the request.
	 */
	fun blocking(): T {
		try {
			call.execute().use { response ->
				if (response.code() != 200) {
					throw QueryFailedException(response.code(), call.request().url().toString())
				}
				return transform(response)
			}
		} catch (e: IOException) {
			throw RuntimeException(e)
		}
	}

	/**
	 * Obtain the result by submitting the task to an executor and
	 * returning a [promise][CompletionStage].
	 *
	 * @return Executor promise.
	 */
	fun submit(): CompletionStage<T> {
		val future = CompletableFuture<T>()
		async(
				{ t: T -> future.complete(t) },
				{ throwable: Throwable? -> future.completeExceptionally(throwable) }
		)
		return future
	}

	/**
	 * Obtain the result by submitting the task to an executor
	 * and forwarding the result to a success consumer.
	 * <br></br>
	 * However, if an exception occurs then the exception will forward to the
	 * failure consumer if set.
	 *
	 * @param failureConsumer Consumer of the exception (if it occurs).
	 * @param successConsumer Consumer of the response.
	 */
	@JvmOverloads
	fun async(
			@NotNull
			successConsumer: (T) -> Unit, failureConsumer: ((Throwable?) -> Unit)? = null) {
		call.enqueue(object : Callback {
			override fun onFailure(call: Call, e: IOException) {
				call.cancel()
				failureConsumer!!(e)
			}

			@Throws(IOException::class)
			override fun onResponse(call: Call, response: Response) {
				try {
					if (response.code() != 200) {
						throw QueryFailedException(
								response.code(),
								this@RequestAction.call.request().url().toString()
						)
					}
					successConsumer(transform(response))
				} catch (e: Throwable) {
					failureConsumer?.let { it(e) }
				} finally {
					response.close()
				}
			}
		})
	}

}