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

/**
 * @author Kodehawa
 */
public class YandereImage implements BoardImage {
    private int actual_preview_width;
    private String author;
    private String file_ext;
    private int file_size;
    private String file_url;
    private int height;
    private int id;
    private int jpeg_file_size;
    private int jpeg_height;
    private String jpeg_url;
    private int jpeg_width;
    private int preview_height;
    private String preview_url;
    private int preview_width;
    private Rating rating;
    private int sample_file_size;
    private int sample_height;
    private String sample_url;
    private int sample_width;
    private int score;
    private String status;
    private String tags;
    private int width;
    private int actual_preview_height;
    private int approver_id;
    private int change;
    private int created_at;
    private int creator_id;
    private List<String> frames;
    private List<String> frames_pending;
    private String frames_pending_string;
    private String frames_string;
    private Boolean has_children;
    private Boolean is_held;
    private Boolean is_note_locked;
    private Boolean is_pending;
    private Boolean is_rating_locked;
    private Boolean is_shown_in_index;
    private int last_commented_at;
    private int last_noted_at;
    private String md5;
    private int parent_id;
    private String source;
    private int updated_at;

    public int getActual_preview_width() {
        return actual_preview_width;
    }

    public String getAuthor() {
        return author;
    }

    public String getFile_ext() {
        return file_ext;
    }

    public int getFile_size() {
        return file_size;
    }

    public String getFile_url() {
        return file_url;
    }

    public int getId() {
        return id;
    }

    public int getJpeg_file_size() {
        return jpeg_file_size;
    }

    public int getJpeg_height() {
        return jpeg_height;
    }

    public String getJpeg_url() {
        return jpeg_url;
    }

    public int getJpeg_width() {
        return jpeg_width;
    }

    public int getPreview_height() {
        return preview_height;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public int getPreview_width() {
        return preview_width;
    }

    public int getSample_file_size() {
        return sample_file_size;
    }

    public int getSample_height() {
        return sample_height;
    }

    public String getSample_url() {
        return sample_url;
    }

    public int getSample_width() {
        return sample_width;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    @Override
    public List<String> getTags() {
        return Collections.unmodifiableList(Arrays.asList(tags.split(" ")));
    }

    @Override
    public String getURL() {
        return getFile_url();
    }
}
