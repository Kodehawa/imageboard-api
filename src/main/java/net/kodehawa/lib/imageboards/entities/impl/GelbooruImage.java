package net.kodehawa.lib.imageboards.entities.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("has_children")
    private boolean has_children;
    @JsonProperty("change")
    private long change; // timestamp in seconds

    public String getSource() {
        return source;
    }

    public String getHash() {
        return hash;
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

    public String getFile_url() {
        return file_url;
    }

    public boolean isHas_children() {
        return has_children;
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
    public int getScore() {
        return score;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public String getURL() {
        return getFile_url();
    }

    @Override
    public boolean hasChildren() {
        return isHas_children();
    }

    // Doesn't implement this
    @Override
    public boolean isPending() {
        return false;
    }

    @Override
    public long getCreationMillis() {
        return change * 1000;
    }
}
