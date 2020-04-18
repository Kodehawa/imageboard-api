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
import net.kodehawa.lib.imageboards.entities.BoardImage
import net.kodehawa.lib.imageboards.entities.Rating.*
import net.kodehawa.lib.imageboards.entities.exceptions.QueryFailedException
import net.kodehawa.lib.imageboards.entities.exceptions.QueryParseException
import net.kodehawa.lib.imageboards.entities.impl.*
import org.junit.Assert
import org.junit.Test
import java.util.function.Consumer

class ImageBoardTest {
    @Test
    fun returnsNonNullValues() {
        e621[1].async(Consumer { images: List<FurryImage> -> assertImages(images) })
        konachan[1].async(Consumer { images: List<KonachanImage> -> assertImages(images) })
        rule34[1].async(Consumer { images: List<Rule34Image> -> assertImages(images) })
        yandere[1].async(Consumer { images: List<YandereImage> -> assertImages(images) })
        danbooru[1].async(Consumer { images: List<DanbooruImage> -> assertImages(images) })
        safebooru[1].async(Consumer { images: List<SafebooruImage> -> assertImages(images) })
        gelbooru[1].async(Consumer { images: List<GelbooruImage> -> assertImages(images) })
        e926[1].async(Consumer { images: List<SafeFurryImage> -> assertImages(images) })
    }

    @Test
    fun blockingTest() {
        printImages(e621[2].blocking())
        printImages(konachan[2].blocking())
        printImages(rule34[2].blocking())
        printImages(yandere[2].blocking())
        printImages(danbooru[2].blocking())
        printImages(safebooru[2].blocking())
        printImages(gelbooru[2].blocking())
        printImages(e926[2].blocking())
    }

    @Test(expected = NullPointerException::class)
    fun throwErrorNullConsumer() {
        e621[1].async(null)
    }

    @Test
    @Throws(QueryParseException::class, QueryFailedException::class)
    fun tagsReturnRelevantResults() {
        ImageBoard.throwExceptionOnEOF = false
        val tag = "animal_ears"
        val knownAliases = arrayOf("animal_ear", "animal_humanoid")
        Assert.assertTrue(e621.search(limit = 0, query = tag).blocking()[0].tags.contains(tag) ||
                e621.search(limit = 1, query = tag).blocking()[0].tags.contains(knownAliases[1]))
        Assert.assertTrue(konachan.search(limit = 1, query = tag).blocking()[0].tags.contains(tag))
        Assert.assertTrue(rule34.search(limit = 1, query = tag).blocking()[0].tags.contains(tag) ||
                rule34.search(limit = 1, query = tag).blocking()[0].tags.contains(knownAliases[0]))
        Assert.assertTrue(yandere.search(limit = 1, query = tag).blocking()[0].tags.contains(tag))
        Assert.assertTrue(danbooru.search(limit = 1, query = tag).blocking()[0].tags.contains(tag))
        Assert.assertTrue(safebooru.search(limit = 1, query = tag).blocking()[0].tags.contains(tag))
        //  assertTrue(yandere.search("animal_ears yuri", Rating.EXPLICIT).blocking()[0].tags.contains(tag));
        Assert.assertTrue(gelbooru.search(limit = 1, query = tag).blocking()[0].tags.contains(tag))
        Assert.assertTrue(e926.search(limit = 1, query = tag).blocking()[0].tags.contains(tag) ||
                e926.search(limit = 1, query = tag).blocking()[0].tags.contains(knownAliases[1]))

        Assert.assertSame(yandere.search(query = tag, rating = EXPLICIT).blocking()[0].rating, EXPLICIT);
        Assert.assertSame(danbooru.search(query = tag, rating = EXPLICIT).blocking()[0].rating, EXPLICIT);
        Assert.assertSame(danbooru.search(query = tag, rating = QUESTIONABLE).blocking()[0].rating, QUESTIONABLE);
        Assert.assertSame(gelbooru.search(query = tag, rating = SAFE).blocking()[0].rating, SAFE);
        Assert.assertSame(gelbooru.search(query = tag, rating = EXPLICIT).blocking()[0].rating, EXPLICIT);
        Assert.assertSame(gelbooru.search(query = tag, rating = QUESTIONABLE).blocking()[0].rating, QUESTIONABLE);
        Assert.assertSame(konachan.search(query = tag, rating = EXPLICIT).blocking()[0].rating, EXPLICIT);
        Assert.assertSame(konachan.search(query = tag, rating = SAFE).blocking()[0].rating, SAFE)
        Assert.assertSame(konachan.search("", rating = SAFE).blocking()[0].rating, SAFE);
        Assert.assertSame(konachan.get(limit = 7, rating = SAFE).blocking()[0].rating, SAFE);
        Assert.assertSame(danbooru.get(limit = 7, rating = SAFE).blocking()[0].rating, SAFE);
        Assert.assertSame(e926.get(limit = 7, rating = SAFE).blocking()[0].rating, SAFE);
    }

    @Test
    fun returnsProperClasses() {
        Assert.assertEquals(e621.boardType, DefaultBoards.E621)
        Assert.assertEquals(e621.imageType, FurryImage::class.java)
        Assert.assertEquals(konachan.boardType, DefaultBoards.KONACHAN)
        Assert.assertEquals(konachan.imageType, KonachanImage::class.java)
        Assert.assertEquals(rule34.boardType, DefaultBoards.R34)
        Assert.assertEquals(rule34.imageType, Rule34Image::class.java)
        Assert.assertEquals(yandere.boardType, DefaultBoards.YANDERE)
        Assert.assertEquals(yandere.imageType, YandereImage::class.java)
        Assert.assertEquals(danbooru.boardType, DefaultBoards.DANBOORU)
        Assert.assertEquals(danbooru.imageType, DanbooruImage::class.java)
        Assert.assertEquals(safebooru.boardType, DefaultBoards.SAFEBOORU)
        Assert.assertEquals(safebooru.imageType, SafebooruImage::class.java)
        Assert.assertEquals(gelbooru.boardType, DefaultBoards.GELBOORU)
        Assert.assertEquals(gelbooru.imageType, GelbooruImage::class.java)
        Assert.assertEquals(e926.boardType, DefaultBoards.E926)
        Assert.assertEquals(e926.imageType, SafeFurryImage::class.java)
    }

    companion object {
        private val e621 = DefaultImageBoards.E621
        private val konachan = DefaultImageBoards.KONACHAN
        private val rule34 = DefaultImageBoards.RULE34
        private val yandere = DefaultImageBoards.YANDERE
        private val danbooru = DefaultImageBoards.DANBOORU
        private val safebooru = DefaultImageBoards.SAFEBOORU
        private val e926 = DefaultImageBoards.E926
        private val gelbooru = DefaultImageBoards.GELBOORU

        //Run this first to check if everything returns as expected
        @JvmStatic
        fun main(args: Array<String>) {
            e621[2].async(Consumer { images: List<FurryImage> -> printImages(images) })
            konachan[2].async(Consumer { images: List<KonachanImage> -> printImages(images) })
            rule34[2].async(Consumer { images: List<Rule34Image> -> printImages(images) })
            yandere[2].async(Consumer { images: List<YandereImage> -> printImages(images) })
            danbooru[2].async(Consumer { images: List<DanbooruImage> -> printImages(images) })
            safebooru[2].async(Consumer { images: List<SafebooruImage> -> printImages(images) })
            gelbooru[2].async(Consumer { images: List<GelbooruImage> -> printImages(images) })
            e926[2].async(Consumer { images: List<SafeFurryImage> -> printImages(images) })
        }

        private fun printImages(images: List<BoardImage>) {
            for (image in images) {
                System.out.printf("%s %d %s %s %d %d%n",
                        image.uRL,
                        image.score,
                        image.rating,
                        image.tags,
                        image.height,
                        image.width)
            }
        }

        fun assertImages(images: List<BoardImage>) {
            Assert.assertNotEquals(images[0].uRL, null)
            Assert.assertNotEquals(images[0].tags, null)
        }
    }
}