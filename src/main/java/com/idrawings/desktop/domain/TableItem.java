package com.idrawings.desktop.domain;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by White Stream on 30.05.2017.
 */
public class TableItem {
    private SimpleStringProperty key;
    private SimpleStringProperty value;

    public TableItem(SimpleStringProperty key, SimpleStringProperty value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key.get();
    }

    public SimpleStringProperty keyProperty() {
        return key;
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}
