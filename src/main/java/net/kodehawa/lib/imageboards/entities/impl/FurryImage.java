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
import com.fasterxml.jackson.annotation.JsonProperty;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kodehawa
 */
public class FurryImage implements BoardImage {
    private String description;
    private Rating rating;
    private File file;
    @JsonProperty("has_children")
    private boolean has_children;

    @JsonProperty("score")
    private Score score;
    @JsonProperty("tags")
    private Tags tags;

    public boolean isHas_children() {
        return has_children;
    }

    //Image description
    public String getDescription() {
        return description;
    }

    public File getFile() {
        return file;
    }

    public Tags getAllTags() {
        return tags;
    }

    static class Score {
        private int up;
        private int down;
        private int total;

        public int getUp() {
            return up;
        }

        public int getDown() {
            return down;
        }

        public int getTotal() {
            return total;
        }
    }

    static class Tags {
        private List<String> general;
        private List<String> species;
        private List<String> character;
        private List<String> lore;
        private List<String> meta;
        private List<String> artist;
        private List<String> copyright;

        public List<String> getGeneral() {
            return general;
        }

        public List<String> getSpecies() {
            return species;
        }

        public List<String> getCharacter() {
            return character;
        }

        public List<String> getLore() {
            return lore;
        }

        public List<String> getMeta() {
            return meta;
        }

        public List<String> getArtist() {
            return artist;
        }

        public List<String> getCopyright() {
            return copyright;
        }

        public List<String> getAll() {
            return Stream.of(general, species, character, lore, meta, artist, copyright)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
    }

    static class File {
        @JsonProperty("width")
        private int width;
        @JsonProperty("height")
        private int height;
        @JsonProperty("size")
        private int size;
        @JsonProperty("url")
        private String url;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getSize() {
            return size;
        }

        public String getUrl() {
            return url;
        }
    }

    @Override
    public int getWidth() {
        return getFile().getWidth();
    }

    @Override
    public int getHeight() {
        return getFile().getHeight();
    }

    @Override
    public int getScore() {
        return score.getTotal();
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    @Override
    @JsonIgnore
    public List<String> getTags() {
        return tags.getAll();
    }

    public List<String> getArtist() {
        return getAllTags().getArtist();
    }

    @Override
    public String getURL() {
        return getFile().getUrl();
    }

    @Override
    public boolean hasChildren() {
        return isHas_children();
    }
}
