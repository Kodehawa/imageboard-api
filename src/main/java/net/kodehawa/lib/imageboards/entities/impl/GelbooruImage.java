package net.kodehawa.lib.imageboards.entities.impl;

import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GelbooruImage implements BoardImage {
    private String source;
    private String hash;
    private int height;
    private int width;
    private int id;
    private String image;
    private String owner;
    private Rating rating;
    private String tags;
    private String file_url; //Thanks gelbooru for giving a full url I love you
    private int score;

    public int getScore() {
        return score;
    }

    public String getSource() {
        return source;
    }

    public String getHash() {
        return hash;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getOwner() {
        return owner;
    }

    public Rating getRating() {
        return rating;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(Arrays.asList(tags.split(" ")));
    }

    public String getFile_url() {
        return file_url;
    }

    public String getURL() {
        return getFile_url();
    }
}
