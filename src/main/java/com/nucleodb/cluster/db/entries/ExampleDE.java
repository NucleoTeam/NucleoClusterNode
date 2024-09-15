package com.nucleodb.cluster.db.entries;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nucleodb.cluster.db.models.Example;
import com.nucleodb.library.database.modifications.Create;
import com.nucleodb.library.database.tables.table.DataEntry;
import org.springframework.data.annotation.Id;


public class ExampleDE extends DataEntry<Example> {

    public ExampleDE(Example obj) {
        super(obj);
    }

    public ExampleDE(Create create) throws ClassNotFoundException, JsonProcessingException {
        super(create);
    }

    public ExampleDE() {
    }

    public ExampleDE(String key) {
        super(key);
    }
}
