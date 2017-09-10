package net.kodehawa.lib.imageboards.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FurryImage implements BoardImage {
    private String description;
    private String file_url;
    private int height;
    private String tags;
    private int width;

    //Image description
    public String getDescription() {
        return description;
    }

    public String getFile_url() {
        return file_url;
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
        return "e";
    }

    @Override
    public List<String> getTags() {
        return new ArrayList<>(Arrays.asList(tags.split(" ")));
    }

    @Override
    public String getImageUrl() {
        return getFile_url();
    }
}
