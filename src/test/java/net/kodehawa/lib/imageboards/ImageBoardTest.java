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
import net.kodehawa.lib.imageboards.entities.*;
import net.kodehawa.lib.imageboards.entities.impl.*;
import net.kodehawa.lib.imageboards.util.ImageBoards;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ImageBoardTest {
    private static final ImageBoardAPI<FurryImage> e621 = ImageBoards.E621;
    private static final ImageBoardAPI<KonachanImage> konachan = ImageBoards.KONACHAN;
    private static final ImageBoardAPI<Rule34Image> rule34 = ImageBoards.RULE34;
    private static final ImageBoardAPI<YandereImage> yandere = ImageBoards.YANDERE;
    private static final ImageBoardAPI<DanbooruImage> danbooru = ImageBoards.DANBOORU;
    private static final ImageBoardAPI<SafebooruImage> safebooru = ImageBoards.SAFEBOORU;

    //Run this first to check if everything returns as expected
    public static void main(String[] args) {
        e621.get(2).async(ImageBoardTest::printImages);
        konachan.get(2).async(ImageBoardTest::printImages);
        rule34.get(2).async(ImageBoardTest::printImages);
        yandere.get(2).async(ImageBoardTest::printImages);
        danbooru.get(2).async(ImageBoardTest::printImages);
        safebooru.get(2).async(ImageBoardTest::printImages);

    }

    private static void printImages(List<? extends BoardImage> images) {
        for (BoardImage image : images) {
            System.out.println(image.getURL()
                    + " " + image.getRating()
                    + " " + image.getTags()
                    + " " + image.getHeight()
                    + " " + image.getWidth());
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
    }

    @Test
    public void blockingTest() {
        printImages(e621.get(2).blocking());
        printImages(konachan.get(2).blocking());
        printImages(rule34.get(2).blocking());
        printImages(yandere.get(2).blocking());
        printImages(danbooru.get(2).blocking());
        printImages(safebooru.get(2).blocking());
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

        assertTrue(e621.search(1, tag).blocking().get(0).getTags().contains(tag) ||
                e621.search(1, tag).blocking().get(0).getTags().contains(knownAliases[1]));

        assertTrue(konachan.search(1, tag).blocking().get(0).getTags().contains(tag));

        assertTrue(rule34.search(1, tag).blocking().get(0).getTags().contains(tag) ||
                rule34.search(1, tag).blocking().get(0).getTags().contains(knownAliases[0]));

        assertTrue(yandere.search(1, tag).blocking().get(0).getTags().contains(tag));

        assertTrue(danbooru.search(1, tag).blocking().get(0).getTags().contains(tag));

        assertTrue(safebooru.search(1, tag).blocking().get(0).getTags().contains(tag));
    }

    @Test
    public void returnsProperClasses() {
        assertTrue(e621.getBoardType().equals(DefaultBoards.E621));
        assertTrue(e621.getImageType().equals(FurryImage.class));

        assertTrue(konachan.getBoardType().equals(DefaultBoards.KONACHAN));
        assertTrue(konachan.getImageType().equals(KonachanImage.class));

        assertTrue(rule34.getBoardType().equals(DefaultBoards.R34));
        assertTrue(rule34.getImageType().equals(Rule34Image.class));

        assertTrue(yandere.getBoardType().equals(DefaultBoards.YANDERE));
        assertTrue(yandere.getImageType().equals(YandereImage.class));

        assertTrue(danbooru.getBoardType().equals(DefaultBoards.DANBOORU));
        assertTrue(danbooru.getImageType().equals(DanbooruImage.class));

        assertTrue(safebooru.getBoardType().equals(DefaultBoards.SAFEBOORU));
        assertTrue(safebooru.getImageType().equals(SafebooruImage.class));
    }
}
