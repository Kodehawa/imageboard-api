package net.kodehawa.lib.imageboards.entities;

public class Rule34Image {

    private String directory;
    private String image;
    private int height;
    private String tags;
    private int width;

    //Backwards-compatible.
    public String getFile_url() {
        return "https://img.rule34.xxx/images/" + directory + "/" + image;
    }

    public String getImageUrl(){
        return getFile_url();
    }

    public int getHeight() {
        return height;
    }

    public String getTags() {
        return tags;
    }

    public int getWidth() {
        return width;
    }
}
