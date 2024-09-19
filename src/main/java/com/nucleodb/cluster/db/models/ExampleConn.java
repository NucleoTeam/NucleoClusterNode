package com.nucleodb.cluster.db.models;

import com.nucleodb.cluster.db.common.NDBConn;
import com.nucleodb.cluster.db.entries.ExampleDE;
import com.nucleodb.library.database.tables.annotation.Conn;
import com.nucleodb.library.database.tables.connection.Connection;

import java.util.Map;

@Conn("EXAMPLE_CONN")
public class ExampleConn extends NDBConn<ExampleDE, ExampleDE> {
    public ExampleConn() {
    }

    public ExampleConn(ExampleDE from, ExampleDE to) {
        super(from, to);
    }

    public ExampleConn(ExampleDE from, ExampleDE to, Map<String, String> metadata) {
        super(from, to, metadata);
    }
}
