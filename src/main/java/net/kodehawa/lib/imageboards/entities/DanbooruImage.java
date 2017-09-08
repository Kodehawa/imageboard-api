package net.kodehawa.lib.imageboards.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class DanbooruImage {
    public int uploader_id;
    public int score;
    public String source;
    public String rating;
    public int image_width;
    public int image_height;
    public String tag_string;
    public String file_ext;
    public int file_size;
    public int up_score;
    public int down_score;
    public int tag_count;
    public String uploader_name;
    public String tag_string_artist;
    public String tag_string_character;
    public String file_url;
    public String large_file_url;
    public String preview_file_url;

    /**
     * Danbooru normally returns the URL as <p>"file_url": "/data/__furude_rika_higurashi_no_naku_koro_ni_drawn_by_kamaboko_red__fc6fb27e9c6ea2a77c849e5483eafc40.png"</p>
     * Which isn't reachable. This methods gets around it.
     * @return The *reachable* URL to get this image. PNG format, or the extension defined in file_ext.
     */
    public String getParsedFileUrl() {
        return "https://safebooru.donmai.us/" + file_url;
    }

    /**
     * Danbooru normally returns the URL as <p>"large_file_url": "/data/__furude_rika_higurashi_no_naku_koro_ni_drawn_by_kamaboko_red__fc6fb27e9c6ea2a77c849e5483eafc40.jpg"</p>
     * Which isn't reachable. This methods gets around it.
     * @return The *reachable* URL to get this image. JPG format.
     */
    public String getParsedLargeFileUrl() {
        return "https://safebooru.donmai.us/" + large_file_url;
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
