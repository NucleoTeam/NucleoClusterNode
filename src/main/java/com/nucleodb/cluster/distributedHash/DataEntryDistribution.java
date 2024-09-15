package com.nucleodb.cluster.distributedHash;

import com.nucleodb.library.database.modifications.Create;
import com.nucleodb.library.database.modifications.Delete;
import com.nucleodb.library.database.modifications.Update;
import com.nucleodb.library.database.tables.table.DataEntry;
import com.nucleodb.library.database.tables.table.NodeFilter;

public class DataEntryDistribution extends NodeFilter {
    public DataEntryDistribution() {
        super();
    }

    @Override
    public <T extends DataEntry> boolean accept(String key) {
        return true;
    }

    @Override
    public <T extends DataEntry> boolean update(Update u, T existing) {
        return true;
    }

    @Override
    public <T extends DataEntry> boolean delete(Delete d, T existing) {
        return true;
    }

    @Override
    public boolean create(Create c) {
        return true;
    }
}
