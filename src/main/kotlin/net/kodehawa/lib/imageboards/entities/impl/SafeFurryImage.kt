package net.kodehawa.lib.imageboards.entities.impl

import net.kodehawa.lib.imageboards.entities.BoardImage
import net.kodehawa.lib.imageboards.entities.Rating

class SafeFurryImage : BoardImage {
    val id = 0
    val description: String? = null
    val creatorId = 0
    val author: String? = null
    val change = 0
    val source: String? = null
    override val score = 0
    val favCount = 0
    val md5: String? = null
    val fileSize = 0
    val fileUrl: String? = null
    val fileExt: String? = null
    val previewUrl: String? = null
    override val rating: Rating? = null
    val status: String? = null
    override val width = 0
    override val height = 0
    val artist: Array<String> = arrayOf()

    override val tags: List<String>
        get() = arrayListOf()

    override val uRL: String?
        get() = fileUrl

}