package net.kodehawa.lib.imageboards;

import net.kodehawa.lib.imageboards.entities.*;
import net.kodehawa.lib.imageboards.util.Imageboards;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ImageboardTest {

    private static final ImageboardAPI<FurryImage> e621 = Imageboards.E621;
    private static final ImageboardAPI<KonachanImage> konachan = Imageboards.KONACHAN;
    private static final ImageboardAPI<Rule34Image> rule34 = Imageboards.RULE34;
    private static final ImageboardAPI<YandereImage> yandere = Imageboards.YANDERE;
    private static final ImageboardAPI<DanbooruImage> danbooru = Imageboards.DANBOORU;

    //Run this first to check if everything returns as expected
    public static void main(String[] args) {
        e621.get(2, (images) -> {
            for(FurryImage image : images) {
                if(image.file_url == null) {
                    System.out.println("Hmm?, e621");
                    return;
                }
                System.out.println(image.file_url + " " + image.getTags() + " " + image.getHeight() + " " + image.getWidth());
            }
        });

        konachan.get(2, (images) -> {
            for(KonachanImage image : images) {
                if(image.getJpeg_url() == null) {
                    System.out.println("Hmm?, konachan");
                    continue;
                }
                System.out.println(image.getJpeg_url() + " " + image.getTags() + " " + image.getHeight() + " " + image.getWidth());
            }
        });

        rule34.get(2, (images) -> {
            for(Rule34Image image : images) {
                if(image.getImageUrl() == null) {
                    System.out.println("Hmm?, R34");
                    continue;
                }
                System.out.println(image.getImageUrl() + " " + image.getTags() + " " + image.getHeight() + " " + image.getWidth());
            }
        });

        yandere.get(2, (images) -> {
            for(YandereImage image : images) {
                if(image.getFile_url() == null) {
                    System.out.println("Hmm?");
                    continue;
                }
                System.out.println(image.getFile_url() + " " + image.getTags() + " " + image.getHeight() + " " + image.getWidth());
            }
        });

        danbooru.get(2, (images) -> {
            for(DanbooruImage image : images) {
                if(image.file_url == null) {
                    System.out.println("Hmm?, danbooru");
                    continue;
                }
                System.out.println(image.file_url + " " + image.tag_string + " " + image.image_height + " " + image.image_width);
            }
        });

        System.out.println(e621.onSearchBlocking(1, "animal_ears").get(0).getTags());
    }

    @Test
    public void returnsNonNullValues() {
        e621.get(1, (images) -> {
            assertNotEquals(images.get(0).getFile_url(), null);
            assertNotEquals(images.get(0).getTags(), null);
        });

        konachan.get(1, (images) -> {
            assertNotEquals(images.get(0).getJpeg_url(), null);
            assertNotEquals(images.get(0).getTags(), null);
        });

        rule34.get(1, (images) -> {
            assertNotEquals(images.get(0).getFile_url(), null);
            assertNotEquals(images.get(0).getTags(), null);
        });

        yandere.get(1, (images) -> {
            assertNotEquals(images.get(0).getFile_url(), null);
            assertNotEquals(images.get(0).getTags(), null);
        });

        danbooru.get(1, (images) -> {
            assertNotEquals(images.get(0).getFile_url(), null);
            assertNotEquals(images.get(0).getTag_string(), null);
        });

    }

    @Test
    public void tagsReturnRelevantResults() {
        String tag = "animal_ears";
        String[] knownAliases = {"animal_ear", "animal_humanoid"};

        assertTrue(e621.onSearchBlocking(1, tag).get(0).getTags().contains(tag) ||
                e621.onSearchBlocking(1, tag).get(0).getTags().contains(knownAliases[1]));

        assertTrue(konachan.onSearchBlocking(1, tag).get(0).getTags().contains(tag));

        assertTrue(rule34.onSearchBlocking(1, tag).get(0).getTags().contains(tag) ||
                rule34.onSearchBlocking(1, tag).get(0).getTags().contains(knownAliases[0]));

        assertTrue(yandere.onSearchBlocking(1, tag).get(0).getTags().contains(tag));

        assertTrue(danbooru.onSearchBlocking(1, tag).get(0).getTag_string().contains(tag));
    }
}
