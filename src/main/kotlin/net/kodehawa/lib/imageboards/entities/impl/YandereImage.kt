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
class YandereImage : BoardImage {
	val actualPreviewWidth = 0
	val author: String? = null
	val fileExt: String? = null
	val fileSize = 0
	val fileUrl: String? = null
	override val height = 0
	val id = 0
	val jpegFileSize = 0
	val jpegHeight = 0
	val jpegUrl: String? = null
	val jpegWidth = 0
	val previewHeight = 0
	val previewUrl: String? = null
	val previewWidth = 0
	override val rating: Rating? = null
	val sampleFileSize = 0
	val sampleHeight = 0
	val sampleUrl: String? = null
	val sampleWidth = 0
	override val score = 0
	val status: String? = null
	override val width = 0
	private val actualPreviewHeight = 0
	private val approverId = 0
	private val change = 0
	private val createdAt = 0
	private val creatorId = 0
	private val frames: List<String>? = null
	private val framesPending: List<String>? = null
	private val framesPendingString: String? = null
	private val framesString: String? = null
	private val hasChildren: Boolean? = null
	private val isHeld: Boolean? = null
	private val isNoteLocked: Boolean? = null
	private val isPending: Boolean? = null
	private val isRatingLocked: Boolean? = null
	private val isShownInIndex: Boolean? = null
	private val lastCommentedAt = 0
	private val lastNotedAt = 0
	private val md5: String? = null
	private val parentId = 0
	private val source: String? = null
	private val updatedAt = 0

	override val tags: List<String>
		get() = arrayListOf()

	override val uRL: String?
		get() = fileUrl
}