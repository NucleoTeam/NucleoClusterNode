package com.nucleodb.cluster.db.models;

import com.nucleodb.cluster.db.entries.ExampleDE;
import com.nucleodb.library.database.tables.annotation.Table;

@Table(tableName = "example", dataEntryClass = ExampleDE.class)
public class Example {
    private static final long serialVersionUID = 1;
    public String name;

    public Example() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
