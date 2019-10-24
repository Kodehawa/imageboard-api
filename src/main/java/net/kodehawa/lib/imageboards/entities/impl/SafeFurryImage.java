package net.kodehawa.lib.imageboards.entities.impl;

import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SafeFurryImage implements BoardImage {
    private int id;
    private String tags;
    private String description;
    private int creator_id;
    private String author;
    private int change;
    private String source;
    private int score;
    private int fav_count;
    private String md5;
    private int file_size;
    private String file_url;
    private String file_ext;
    private String preview_url;
    private Rating rating;
    private String status;
    private int width;
    private int height;
    private String[] artist;

    public int getId() {
        return id;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(Arrays.asList(tags.split(" ")));
    }

    public String getDescription() {
        return description;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public String getAuthor() {
        return author;
    }

    public int getChange() {
        return change;
    }

    public String getSource() {
        return source;
    }

    public int getScore() {
        return score;
    }

    public int getFav_count() {
        return fav_count;
    }

    public String getMd5() {
        return md5;
    }

    public int getFile_size() {
        return file_size;
    }

    public String getFile_url() {
        return file_url;
    }

    public String getURL() {
        return getFile_url();
    }

    public String getFile_ext() {
        return file_ext;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public Rating getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String[] getArtist() {
        return artist;
    }
}
