package net.kodehawa.lib.imageboards.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class DanbooruImage {
    private int uploader_id;
    private int score;
    private String source;
    private String rating;
    private int image_width;
    private int image_height;
    private String tag_string;
    private String file_ext;
    private int file_size;
    private int up_score;
    private int down_score;
    private int tag_count;
    private String uploader_name;
    private String tag_string_artist;
    private String tag_string_character;
    private String file_url;
    private String large_file_url;
    private String preview_file_url;

    /**
     * Danbooru normally returns the URL as <p>"file_url": "/data/__furude_rika_higurashi_no_naku_koro_ni_drawn_by_kamaboko_red__fc6fb27e9c6ea2a77c849e5483eafc40.png"</p>
     * Which isn't reachable. This methods gets around it.
     * @return The *reachable* URL to get this image. PNG format, or the extension defined in file_ext.
     */
    public String getParsedFileUrl() {
        return "https://danbooru.donmai.us" + file_url;
    }

    /**
     * Danbooru normally returns the URL as <p>"large_file_url": "/data/__furude_rika_higurashi_no_naku_koro_ni_drawn_by_kamaboko_red__fc6fb27e9c6ea2a77c849e5483eafc40.jpg"</p>
     * Which isn't reachable. This methods gets around it.
     * @return The *reachable* URL to get this image. JPG format.
     */
    public String getParsedLargeFileUrl() {
        return "https://danbooru.donmai.us" + large_file_url;
    }

    /**
     * Danbooru normally returns the URL as <p>"preview_file_url": "/data/preview/fc6fb27e9c6ea2a77c849e5483eafc40.jpg"</p>
     * Which isn't reachable. This methods gets around it.
     * @return The *reachable* URL to get this image. JPG format.
     */
    public String getParsedPreviewFileUrl() {
        return "https://safebooru.donmai.us/" + preview_file_url;
    }

    public List<String> getTagsArray() {
        return new ArrayList<>(Arrays.asList(tag_string.split(" ")));
    }
}
