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
package net.kodehawa.lib.imageboards.boards

/**
 * Information about the board.
 *
 * @author Avarel
 * @author Kodehawa
 */
interface Board {
	/**
	 * @return Scheme of the board. ie. http/https
	 */
	val scheme: String

	/**
	 * @return Base url of the board.
	 */
	val host: String

	/**
	 * @return Path segment.
	 */
	val path: String

	/**
	 * @return The string that separates the base url from the queries.
	 */
	val query: String?

	/**
	 * @return Page marker.
	 */
	val pageMarker: String
}