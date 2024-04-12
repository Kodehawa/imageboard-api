package net.kodehawa.lib.imageboards.entities.impl.autocomplete.pojo;

import net.kodehawa.lib.imageboards.entities.impl.autocomplete.IAutoComplete;

public class SafebooruAutoComplete implements IAutoComplete {
    String label;
    String value;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String getTagName() {
        return value;
    }
}
