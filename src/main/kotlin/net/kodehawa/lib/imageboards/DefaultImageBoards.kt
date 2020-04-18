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
package net.kodehawa.lib.imageboards

import net.kodehawa.lib.imageboards.boards.DefaultBoards
import net.kodehawa.lib.imageboards.entities.impl.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Utility class represting already-created objects around the most used imageboards.
 *
 * @author Kodehawa
 */
object DefaultImageBoards {
	private val client = OkHttpClient.Builder()
			.connectTimeout(3, TimeUnit.SECONDS)
			.readTimeout(3, TimeUnit.SECONDS)
			.build()
	@JvmField
	val E621 = ImageBoard(client, DefaultBoards.E621, FurryImage::class.java)
	@JvmField
	val KONACHAN = ImageBoard(client, DefaultBoards.KONACHAN, KonachanImage::class.java)
	@JvmField
	val RULE34 = ImageBoard(client, DefaultBoards.R34, Rule34Image::class.java)
	@JvmField
	val YANDERE = ImageBoard(client, DefaultBoards.YANDERE, YandereImage::class.java)
	@JvmField
	val DANBOORU = ImageBoard(client, DefaultBoards.DANBOORU, DanbooruImage::class.java)
	@JvmField
	val SAFEBOORU = ImageBoard(client, DefaultBoards.SAFEBOORU, SafebooruImage::class.java)
	@JvmField
	val E926 = ImageBoard(client, DefaultBoards.E926, SafeFurryImage::class.java)
	@JvmField
	val GELBOORU = ImageBoard(client, DefaultBoards.GELBOORU, GelbooruImage::class.java)
}