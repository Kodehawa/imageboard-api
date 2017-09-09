/*
 * Copyright (C) 2016-2017 David Alejandro Rubio Escares / Kodehawa
 *
 * Mantaro is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Mantaro is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mantaro.  If not, see http://www.gnu.org/licenses/
 */

package net.kodehawa.lib.imageboards.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YandereImage {

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
    private String rating;
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

    public int getHeight() {
        return height;
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

    public String getRating() {
        return rating;
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

    public int getScore() {
        return score;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getTags() {
        return new ArrayList<>(Arrays.asList(tags.split(" ")));
    }

    public int getWidth() {
        return width;
    }
}
