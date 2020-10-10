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

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Kodehawa
 */
public class Rule34Image implements BoardImage {
    private String directory;
    private String image;
    private int height;
    private String tags;
    private int width;

    //Backwards-compatible.
    public String getFile_url() {
        return "https://img.rule34.xxx/images/" + getDirectory() + "/" + getImage();
    }

    public String getDirectory() {
        return directory;
    }

    public String getImage() {
        return image;
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
    public int getScore() {
        return 0;
    }

    @Override
    public Rating getRating() {
        return Rating.EXPLICIT;
    }

    @Override
    public List<String> getTags() {
        return Collections.unmodifiableList(Arrays.asList(tags.split(" ")));
    }

    @Override
    @JsonIgnore
    public String getURL(){
        return getFile_url();
    }

    // Doesn't implement it, lol.
    @Override
    @JsonIgnore
    public boolean hasChildren() {
        return false;
    }
}
