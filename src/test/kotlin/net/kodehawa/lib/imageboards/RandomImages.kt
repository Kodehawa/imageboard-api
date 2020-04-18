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

import net.kodehawa.lib.imageboards.entities.BoardImage
import net.kodehawa.lib.imageboards.entities.impl.KonachanImage
import java.util.function.Consumer

object RandomImages {
    @JvmStatic
    fun main(args: Array<String>) {
        // Asynchronous GET
        // 60 random images
        DefaultImageBoards.KONACHAN.get().async(Consumer { images: List<KonachanImage> -> for (image in images) println(image.uRL) })

        // Blocking GET
        // 1 random image
        val image: BoardImage = DefaultImageBoards.KONACHAN[1].blocking()[0]
        println(image.uRL)
        println(image.rating)
        println(image.tags)
        println(image.height)
        println(image.width)
    }
}