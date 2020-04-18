package net.kodehawa.lib.imageboards.entities.impl

import net.kodehawa.lib.imageboards.entities.BoardImage
import net.kodehawa.lib.imageboards.entities.Rating

class GelbooruImage : BoardImage {
	val source: String? = null
	val hash: String? = null
	override val height = 0
	override val width = 0
	val id = 0
	val image: String? = null
	val owner: String? = null
	override val rating: Rating? = null
	override val tags: List<String>
		get() = arrayListOf()

	val fileUrl //Thanks gelbooru for giving a full url I love you
			: String? = null

	override val score = 0


	override val uRL: String?
		get() = fileUrl
}