package net.kodehawa.lib.imageboards.entities.impl.autocomplete.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.kodehawa.lib.imageboards.entities.impl.autocomplete.IAutoComplete;

public class FurryAutoComplete implements IAutoComplete {
    int id;
    String name;
    int post_count;
    int category;
    String antecedent_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("post_count")
    public int getPostCount() {
        return post_count;
    }

    @JsonProperty("post_count")
    public void setPostCount(int post_count) {
        this.post_count = post_count;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @JsonProperty("antecedent_name")
    public String getAntecedentName() {
        return antecedent_name;
    }

    @JsonProperty("antecedent_name")
    public void setAntecedentName(String antecedent_name) {
        this.antecedent_name = antecedent_name;
    }

    @Override
    public String getTagName() {
        return name;
    }
}
