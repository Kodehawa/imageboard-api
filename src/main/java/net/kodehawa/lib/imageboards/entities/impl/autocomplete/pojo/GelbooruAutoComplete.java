package net.kodehawa.lib.imageboards.entities.impl.autocomplete.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.kodehawa.lib.imageboards.entities.impl.autocomplete.IAutoComplete;

public class GelbooruAutoComplete implements IAutoComplete {
    String type;
    String label;
    String value;
    String category;
    int post_count;

    @JsonProperty("post_count")
    public void setPostCount(int post_count) {
        this.post_count = post_count;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("post_count")
    public int getPostCount() {
        return post_count;
    }

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getTagName() {
        return value;
    }
}
