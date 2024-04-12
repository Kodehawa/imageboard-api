package net.kodehawa.lib.imageboards.entities.impl.autocomplete.pojo;

import net.kodehawa.lib.imageboards.entities.impl.autocomplete.IAutoComplete;

public class Rule34AutoComplete implements IAutoComplete {
    String label;
    String value;
    String type;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String getTagName() {
        return label;
    }
}
