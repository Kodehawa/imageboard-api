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

import net.kodehawa.lib.imageboards.ImageBoard
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * A requester that can make request actions.
 *
 * @author Avarel
 * @see RequestAction
 * Create a requester that can make request actions.
 *
 * @param client OKHttp client.
 */
class RequestFactory(private val client: OkHttpClient) {

	/**
	 * Creates a [request action][RequestAction] on a URL.
	 *
	 * @param url       Target url.
	 * @param transform Successful response transformer.
	 * @param <T>       Return type.
	 * @return Request action.
	</T> */
	fun <T> makeRequest(url: String, transform: (Response) -> T): RequestAction<T> =
			makeRequest(HttpUrl.parse(url)!!, transform)

	/**
	 * Creates a [request action][RequestAction] on a URL.
	 *
	 * @param url       Target url.
	 * @param transform Successful response transformer.
	 * @param <T>       Return type.
	 * @return Request action.
	</T> */
	fun <T> makeRequest(url: HttpUrl, transform: (Response) -> T): RequestAction<T> =
			RequestAction(client.newCall(Request.Builder().url(url).header(
					"User-Agent",
					ImageBoard.userAgent
			).build()), transform)
}