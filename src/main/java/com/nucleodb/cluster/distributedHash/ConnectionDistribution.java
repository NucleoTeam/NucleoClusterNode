package com.nucleodb.cluster.distributedHash;

import com.nucleodb.library.database.modifications.ConnectionCreate;
import com.nucleodb.library.database.modifications.ConnectionDelete;
import com.nucleodb.library.database.modifications.ConnectionUpdate;
import com.nucleodb.library.database.tables.connection.Connection;
import com.nucleodb.library.database.tables.connection.NodeFilter;

public class ConnectionDistribution  extends NodeFilter {
    public ConnectionDistribution() {
        super();
    }

    @Override
    public boolean create(ConnectionCreate c) {
        return true;
    }

    @Override
    public <C extends Connection> boolean delete(ConnectionDelete d, C existing) {
        return true;
    }

    @Override
    public <C extends Connection> boolean update(ConnectionUpdate u, C existing) {
        return true;
    }

    @Override
    public <C extends Connection> boolean accept(String key) {
        return true;
    }
}
