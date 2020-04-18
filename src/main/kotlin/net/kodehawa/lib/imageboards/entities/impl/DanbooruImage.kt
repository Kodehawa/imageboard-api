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
import java.util.regex.Pattern

/**
 * @author Kodehawa
 */
class DanbooruImage : BoardImage {
	val uploaderId = 0
	override val score = 0
	val source: String? = null
	override val rating: Rating? = null
	override val width = 0
	override val height = 0
	val tagString: String? = null
	val fileExt: String? = null
	val fileSize = 0
	val upScore = 0
	val downScore = 0
	val tagCount = 0
	val uploaderName: String? = null
	val tagStringArtist: String? = null
	val tagStringCharacter: String? = null
	val fileUrl: String? = null
	val largeFileUrl: String? = null
	val previewFileUrl: String? = null
	private val urlPattern = Pattern.compile("https(:)?//[\\w\\d.]*donmai.us")

	/**
	 * Danbooru normally returns the URL as
	 *
	 *"file_url": "/data/__furude_rika_higurashi_no_naku_koro_ni_drawn_by_kamaboko_red__fc6fb27e9c6ea2a77c849e5483eafc40.png"
	 * Which isn't reachable. This methods gets around it.
	 * @return The *reachable* URL to get this image. PNG format, or the extension defined in file_ext.
	 */
	val parsedFileUrl: String?
		get() = getFixedURL(fileUrl)

	/**
	 * Danbooru normally returns the URL as
	 *
	 *"large_file_url": "/data/__furude_rika_higurashi_no_naku_koro_ni_drawn_by_kamaboko_red__fc6fb27e9c6ea2a77c849e5483eafc40.jpg"
	 * Which isn't reachable. This methods gets around it.
	 * @return The *reachable* URL to get this image. JPG format.
	 */
	val parsedLargeFileUrl: String?
		get() = getFixedURL(largeFileUrl)

	/**
	 * Danbooru normally returns the URL as
	 *
	 *"preview_file_url": "/data/preview/fc6fb27e9c6ea2a77c849e5483eafc40.jpg"
	 * Which isn't reachable. This methods gets around it.
	 * @return The *reachable* URL to get this image. JPG format.
	 */
	val parsedPreviewFileUrl: String?
		get() = getFixedURL(previewFileUrl)

	private fun getFixedURL(url: String?): String? {
		if (url == null) {
			return null
		}
		val matcher = urlPattern.matcher(url)
		return if (matcher.find()) {
			if (matcher.group(1).isEmpty()) {
				// Broken URL (https//)
				url.replace("https//", "https://")
			} else {
				url // Valid URL
			}
		} else {
			// URL without domain (/data/XXX)
			"https://danbooru.donmai.us$url"
		}
	}

	override val tags: List<String>
		get() = Collections.unmodifiableList(listOf(*tagString!!.split(" ").toTypedArray()))

	override val uRL: String?
		get() = parsedFileUrl

}