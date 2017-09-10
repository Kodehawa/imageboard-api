package net.kodehawa.lib.imageboards.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KonachanImage implements BoardImage {
    private String author;
    private int created_at;
    private long file_size;
    private String file_url;
    private int height;
    private int id;
    private long jpeg_file_size;
    private int jpeg_height;
    private String jpeg_url;
    private int jpeg_width;
    private int preview_height;
    private String preview_url;
    private int preview_width;
    private String rating;
    private long sample_file_size;
    private int sample_height;
    private String sample_url;
    private int sample_width;
    private int score;
    private String source;
    private String status;
    private String tags;
    private int width;

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public String getParsedUrl() {
        return "https:" + jpeg_url;
    }

    public String getJpeg_url() {
        return jpeg_url;
    }

    public String getStatus() {
        return status;
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
    public String getRating() {
        return rating;
    }


    @Override
    public List<String> getTags() {
        return tags == null ? new ArrayList<>(Arrays.asList("empty", "")) : new ArrayList<>(Arrays.asList(tags.split(" ")));
    }

    @Override
    public String getImageUrl() {
        return getParsedUrl();
    }
}
