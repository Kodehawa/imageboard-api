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
package net.kodehawa.lib.imageboards.entities.impl

import net.kodehawa.lib.imageboards.entities.BoardImage
import net.kodehawa.lib.imageboards.entities.Rating

/**
 * @author Kodehawa
 */
class KonachanImage : BoardImage {
	val author: String? = null
	val createdAt = 0
	val fileSize: Long = 0
	val fileUrl: String? = null
	override val height = 0
	val id = 0
	val jpegFileSize: Long = 0
	val jpegHeight = 0

	val parsedUrl: String? = null

	val jpegWidth = 0
	val previewHeight = 0
	val previewUrl: String? = null
	val previewWidth = 0
	override val rating: Rating? = null
	val sampleFileSize: Long = 0
	val sampleHeight = 0
	val sampleUrl: String? = null
	val sampleWidth = 0
	override val score = 0
	val source: String? = null
	val status: String? = null

	override val width = 0

	override val tags: List<String>
		get() = arrayListOf()

	override val uRL: String?
		get() = parsedUrl
}