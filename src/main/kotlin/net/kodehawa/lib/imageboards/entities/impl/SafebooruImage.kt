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
import java.util.*

/**
 * @author Kodehawa
 */
class SafebooruImage : BoardImage {
	val directory: String? = null
	val image: String? = null
	override val height = 0
	override val width = 0
	override val tags: List<String>
		get() = arrayListOf()
	val fileUrl: String
		get() = "https://safebooru.org/images/$directory/$image"

	override val score: Int
		get() = 0

	override val rating: Rating?
		get() = Rating.SAFE

	override val uRL: String?
		get() = fileUrl
}