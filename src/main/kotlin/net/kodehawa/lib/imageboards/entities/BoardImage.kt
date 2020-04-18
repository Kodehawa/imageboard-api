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
package net.kodehawa.lib.imageboards.entities

/**
 * Common board image interface.
 * @author NatchRaben
 */
interface BoardImage {
	/**
	 * @return Width of the image.
	 */
	val width: Int

	/**
	 * @return Height of the image.
	 */
	val height: Int

	/**
	 * @return Score of the image.
	 */
	val score: Int

	/**
	 * @return Rating of the image.
	 */
	val rating: Rating?

	/**
	 * @return Tags of the image.
	 */
	val tags: List<String>

	/**
	 * @return Image url.
	 */
	val uRL: String?
}