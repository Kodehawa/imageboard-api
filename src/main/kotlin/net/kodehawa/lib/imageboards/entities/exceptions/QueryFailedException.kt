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
package net.kodehawa.lib.imageboards.entities.exceptions

/**
 * Exception thrown when the image board returns a response
 * code other than 200.
 *
 * @author Kodehawa
 */
class QueryFailedException : RuntimeException {
	val code: Int
	val uRL: String

	constructor(code: Int, url: String) : super("Failed to query $url") {
		this.code = code
		uRL = url
	}

	constructor(code: Int, url: String, e: Throwable?) : super("Failed to query $url", e) {
		this.code = code
		uRL = url
	}

}