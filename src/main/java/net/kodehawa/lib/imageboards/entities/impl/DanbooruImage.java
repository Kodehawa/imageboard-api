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

package net.kodehawa.lib.imageboards.entities.impl;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kodehawa
 */
public class DanbooruImage implements BoardImage {
    private int uploader_id;
    private int score;
    private String source;
    private Rating rating;
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

    private Pattern urlPattern = Pattern.compile("https(:)?//[\\w\\d.]*donmai.us");

    /**
     * Danbooru normally returns the URL as <p>"file_url": "/data/__furude_rika_higurashi_no_naku_koro_ni_drawn_by_kamaboko_red__fc6fb27e9c6ea2a77c849e5483eafc40.png"</p>
     * Which isn't reachable. This methods gets around it.
     * @return The *reachable* URL to get this image. PNG format, or the extension defined in file_ext.
     */
    public String getParsedFileUrl() {
        return getFixedURL(file_url);
    }

    /**
     * Danbooru normally returns the URL as <p>"large_file_url": "/data/__furude_rika_higurashi_no_naku_koro_ni_drawn_by_kamaboko_red__fc6fb27e9c6ea2a77c849e5483eafc40.jpg"</p>
     * Which isn't reachable. This methods gets around it.
     * @return The *reachable* URL to get this image. JPG format.
     */
    public String getParsedLargeFileUrl() {
        return getFixedURL(large_file_url);
    }

    /**
     * Danbooru normally returns the URL as <p>"preview_file_url": "/data/preview/fc6fb27e9c6ea2a77c849e5483eafc40.jpg"</p>
     * Which isn't reachable. This methods gets around it.
     * @return The *reachable* URL to get this image. JPG format.
     */
    public String getParsedPreviewFileUrl() {
        return getFixedURL(this.preview_file_url);
    }

    private String getFixedURL(String url) {
        Matcher matcher = urlPattern.matcher(url);
        if(matcher.find()) {
            if(matcher.group(1).isEmpty()) {
                // Broken URL (https//)
                return url.replace("https//", "https://");
            } else {
                return url; // Valid URL
            }
        } else {
            // URL without domain (/data/XXX)
            return "https://danbooru.donmai.us" + url;
        }
    }

    @Override
    public int getWidth() {
        return image_width;
    }

    @Override
    public int getHeight() {
        return image_height;
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public List<String> getTags() {
        return Collections.unmodifiableList(Arrays.asList(tag_string.split(" ")));
    }

    @Override
    public String getURL() {
        return getParsedFileUrl();
    }

    public int getUploader_id() {
        return uploader_id;
    }

    public String getSource() {
        return source;
    }

    public int getImage_width() {
        return image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public String getTag_string() {
        return tag_string;
    }

    public String getFile_ext() {
        return file_ext;
    }

    public int getFile_size() {
        return file_size;
    }

    public int getUp_score() {
        return up_score;
    }

    public int getDown_score() {
        return down_score;
    }

    public int getTag_count() {
        return tag_count;
    }

    public String getUploader_name() {
        return uploader_name;
    }

    public String getTag_string_artist() {
        return tag_string_artist;
    }

    public String getTag_string_character() {
        return tag_string_character;
    }

    public String getFile_url() {
        return file_url;
    }

    public String getLarge_file_url() {
        return large_file_url;
    }

    public String getPreview_file_url() {
        return preview_file_url;
    }
}
