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

import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kodehawa
 */
public class FurryImage implements BoardImage {
    private String description;
    private String file_url;
    private int height;
    private int width;
    private String tags;

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
    public Rating getRating() {
        return Rating.EXPLICIT;
    }

    @Override
    public List<String> getTags() {
        return new ArrayList<>(Arrays.asList(tags.split(" ")));
    }

    @Override
    public String getURL() {
        return getFile_url();
    }
}
