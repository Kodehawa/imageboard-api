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

package net.kodehawa.lib.imageboards;

import net.kodehawa.lib.imageboards.boards.DefaultBoards;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;
import net.kodehawa.lib.imageboards.entities.impl.*;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ImageBoardTest {

    static {
        ImageBoard.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:52.0) Gecko/20100101 Firefox/52.0");
    }

    private static final ImageBoard<FurryImage> e621 = DefaultImageBoards.E621;
    private static final ImageBoard<KonachanImage> konachan = DefaultImageBoards.KONACHAN;
    private static final ImageBoard<Rule34Image> rule34 = DefaultImageBoards.RULE34;
    private static final ImageBoard<YandereImage> yandere = DefaultImageBoards.YANDERE;
    private static final ImageBoard<DanbooruImage> danbooru = DefaultImageBoards.DANBOORU;
    private static final ImageBoard<SafebooruImage> safebooru = DefaultImageBoards.SAFEBOORU;
    private static final ImageBoard<SafeFurryImage> e926 = DefaultImageBoards.E926;
    private static final ImageBoard<GelbooruImage> gelbooru = DefaultImageBoards.GELBOORU;

    //Run this first to check if everything returns as expected
    public static void main(String[] args) {
        e621.get(2).async(ImageBoardTest::printImages);
        konachan.get(2).async(ImageBoardTest::printImages);
        rule34.get(2).async(ImageBoardTest::printImages);
        yandere.get(2).async(ImageBoardTest::printImages);
        danbooru.get(2).async(ImageBoardTest::printImages);
        safebooru.get(2).async(ImageBoardTest::printImages);
        gelbooru.get(2).async(ImageBoardTest::printImages);
        e926.get(2).async(ImageBoardTest::printImages);
    }

    private static void printImages(List<? extends BoardImage> images) {
        for (BoardImage image : images) {
            System.out.println(
                    "ImageBoard: " + image.getClass()
                            + " " + image.getURL()
                            + " " + image.getScore()
                            + " " + image.getRating()
                            + " " + image.getTags()
                            + " " + image.getHeight()
                            + " " + image.getWidth()
                            + " " + image.hasChildren()
                            + " " + image.isPending()
                            + " " + new Date(image.getCreationMillis()).toString()
            );
        }
    }

    @Test
    public void returnsNonNullValues() {
        e621.get(1).async(ImageBoardTest::assertImages);
        konachan.get(1).async(ImageBoardTest::assertImages);
        rule34.get(1).async(ImageBoardTest::assertImages);
        yandere.get(1).async(ImageBoardTest::assertImages);
        danbooru.get(1).async(ImageBoardTest::assertImages);
        safebooru.get(1).async(ImageBoardTest::assertImages);
        gelbooru.get(1).async(ImageBoardTest::assertImages);
        e926.get(1).async(ImageBoardTest::assertImages);
    }

    @Test
    public void blockingTest() {
        printImages(e621.get(2).blocking());
        printImages(konachan.get(2).blocking());
        printImages(rule34.get(2).blocking());
        printImages(yandere.get(2).blocking());
        printImages(danbooru.get(2).blocking());
        printImages(safebooru.get(2).blocking());
        printImages(gelbooru.get(2).blocking());
        printImages(e926.get(2).blocking());
    }

    @Test(expected = NullPointerException.class)
    public void throwErrorNullConsumer() {
        e621.get(1).async(null);
    }

    public static void assertImages(List<? extends BoardImage> images) {
        assertNotEquals(images.get(0).getURL(), null);
        assertNotEquals(images.get(0).getTags(), null);
    }

    @Test
    public void tagsReturnRelevantResults() {
        String tag = "animal_ears";
        String[] knownAliases = {"animal_ear", "animal_humanoid"};

        // This actually used to alias animal_ears to animal_humanoid, but it doesn't anymore (As of 08/12/2021)
        // Nothing I can do on my side!
        assertTrue(e621.search(1, knownAliases[1]).blocking().get(0).getTags().contains(knownAliases[1]));

        assertTrue(konachan.search(1, tag).blocking().get(0).getTags().contains(tag));

        assertTrue(rule34.search(1, tag).blocking().get(0).getTags().contains(tag) ||
                rule34.search(1, tag).blocking().get(0).getTags().contains(knownAliases[0]));

        assertTrue(yandere.search(1, tag).blocking().get(0).getTags().contains(tag));

        assertTrue(danbooru.search(1, tag).blocking().get(0).getTags().contains(tag));

        assertTrue(safebooru.search(1, tag).blocking().get(0).getTags().contains(tag));

        assertTrue(yandere.search("animal_ears yuri", Rating.EXPLICIT).blocking().get(0).getTags().contains(tag));

        assertTrue(gelbooru.search(1, tag).blocking().get(0).getTags().contains(tag));
        // Same for e961, no more tag aliases seemingly
        assertTrue(e926.search(1, knownAliases[1]).blocking().get(0).getTags().contains(knownAliases[1]));

        assertSame(yandere.search(tag, Rating.EXPLICIT).blocking().get(0).getRating(), Rating.EXPLICIT);
        assertSame(danbooru.search(tag, Rating.EXPLICIT).blocking().get(0).getRating(), Rating.EXPLICIT);
        assertSame(danbooru.search(tag, Rating.QUESTIONABLE).blocking().get(0).getRating(), Rating.QUESTIONABLE);
        assertSame(gelbooru.search(tag, Rating.SAFE).blocking().get(0).getRating(), Rating.SAFE);
        assertSame(gelbooru.search(tag, Rating.EXPLICIT).blocking().get(0).getRating(), Rating.EXPLICIT);
        assertSame(gelbooru.search(tag, Rating.QUESTIONABLE).blocking().get(0).getRating(), Rating.QUESTIONABLE);
        assertSame(konachan.search(tag, Rating.EXPLICIT).blocking().get(0).getRating(), Rating.EXPLICIT);
        assertSame(konachan.search(tag, Rating.SAFE).blocking().get(0).getRating(), Rating.SAFE);
        assertSame(konachan.search("", Rating.SAFE).blocking().get(0).getRating(), Rating.SAFE);
        assertSame(konachan.get(7, Rating.SAFE).blocking().get(0).getRating(), Rating.SAFE);
        assertSame(danbooru.get(7, Rating.SAFE).blocking().get(0).getRating(), Rating.SAFE);
        assertSame(e926.get(7, Rating.SAFE).blocking().get(0).getRating(), Rating.SAFE);
    }

    @Test
    public void returnsProperClasses() {
        assertEquals(e621.getBoardType(), DefaultBoards.E621);
        assertEquals(e621.getImageType(), FurryImage.class);

        assertEquals(konachan.getBoardType(), DefaultBoards.KONACHAN);
        assertEquals(konachan.getImageType(), KonachanImage.class);

        assertEquals(rule34.getBoardType(), DefaultBoards.R34);
        assertEquals(rule34.getImageType(), Rule34Image.class);

        assertEquals(yandere.getBoardType(), DefaultBoards.YANDERE);
        assertEquals(yandere.getImageType(), YandereImage.class);

        assertEquals(danbooru.getBoardType(), DefaultBoards.DANBOORU);
        assertEquals(danbooru.getImageType(), DanbooruImage.class);

        assertEquals(safebooru.getBoardType(), DefaultBoards.SAFEBOORU);
        assertEquals(safebooru.getImageType(), SafebooruImage.class);

        assertEquals(gelbooru.getBoardType(), DefaultBoards.GELBOORU);
        assertEquals(gelbooru.getImageType(), GelbooruImage.class);

        assertEquals(e926.getBoardType(), DefaultBoards.E926);
        assertEquals(e926.getImageType(), SafeFurryImage.class);

    }

    @Test
    public void recentDate() {
        long oneDayAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1);
        assertTrue(e621.get(1).blocking().get(0).getCreationMillis() > oneDayAgo);
        assertTrue(konachan.get(1).blocking().get(0).getCreationMillis() > oneDayAgo);
        assertTrue(rule34.get(1).blocking().get(0).getCreationMillis() > oneDayAgo);
        assertTrue(yandere.get(1).blocking().get(0).getCreationMillis() > oneDayAgo);
        assertTrue(danbooru.get(1).blocking().get(0).getCreationMillis() > oneDayAgo);
        assertTrue(safebooru.get(1).blocking().get(0).getCreationMillis() > oneDayAgo);
        assertTrue(gelbooru.get(1).blocking().get(0).getCreationMillis() > oneDayAgo);
        assertTrue(e926.get(1).blocking().get(0).getCreationMillis() > oneDayAgo);
    }
}
