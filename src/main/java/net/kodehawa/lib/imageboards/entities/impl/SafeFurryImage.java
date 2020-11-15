package net.kodehawa.lib.imageboards.entities.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SafeFurryImage implements BoardImage {
    private String description;
    private Rating rating;
    private File file;
    private Flags flags;

    @JsonProperty("score")
    private Score score;
    @JsonProperty("tags")
    private Tags tags;

    public List<String> getTags() {
        return tags.getAll();
    }

    public Tags getAllTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public File getFile() {
        return file;
    }

    public int getScore() {
        return score.total;
    }

    public String getURL() {
        return file.url;
    }

    public Rating getRating() {
        return rating;
    }

    public int getWidth() {
        return file.width;
    }

    public int getHeight() {
        return file.height;
    }

    public List<String> getArtist() {
        return tags.artist;
    }

    public Flags getFlags() {
        return flags;
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
        private int width;
        private int height;
        private int size;
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

    static class Flags {
        private boolean pending;
        private boolean flagged;
        private boolean note_locked;
        private boolean status_locked;
        private boolean rating_locked;
        private boolean deleted;

        public boolean isPending() {
            return pending;
        }

        public boolean isFlagged() {
            return flagged;
        }

        public boolean isNote_locked() {
            return note_locked;
        }

        public boolean isStatus_locked() {
            return status_locked;
        }

        public boolean isRating_locked() {
            return rating_locked;
        }

        public boolean isDeleted() {
            return deleted;
        }
    }

    // Doesn't implement it, probably doesn't matter since it's a safe board.
    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public boolean isPending() {
        return getFlags().isPending() || getFlags().isDeleted() || getFlags().isFlagged();
    }
}
