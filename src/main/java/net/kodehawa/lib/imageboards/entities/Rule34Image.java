package net.kodehawa.lib.imageboards.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rule34Image implements BoardImage {

    private String directory;
    private String image;
    private int height;
    private String tags;
    private int width;

    //Backwards-compatible.
    public String getFile_url() {
        return "https://img.rule34.xxx/images/" + directory + "/" + image;
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
    public String getImageUrl(){
        return getFile_url();
    }
}
