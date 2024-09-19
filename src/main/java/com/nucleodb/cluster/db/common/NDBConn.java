package com.nucleodb.cluster.db.common;

import com.nucleodb.cluster.db.entries.ExampleDE;
import com.nucleodb.library.database.tables.connection.Connection;
import com.nucleodb.library.database.tables.table.DataEntry;

import java.util.Map;

public class NDBConn<T extends DataEntry, X extends DataEntry> extends Connection<T, X> {
    public NDBConn() {
    }

    public NDBConn(T from, X to) {
        super(from, to);
    }

    public NDBConn(T from, X to, Map<String, String> metadata) {
        super(from, to, metadata);
    }

    @Override
    public T fromEntry() {
        return null;
    }

    @Override
    public X toEntry() {
        return null;
    }
}
