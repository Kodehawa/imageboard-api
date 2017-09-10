package net.kodehawa.lib.imageboards.entities;

import java.util.List;

public interface BoardImage {

    int getWidth();
    int getHeight();
    String getRating();
    List<String> getTags();
    String getImageUrl();

}
