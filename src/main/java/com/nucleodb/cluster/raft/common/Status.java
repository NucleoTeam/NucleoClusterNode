package com.nucleodb.cluster.raft.common;

import com.google.common.collect.Sets;
import com.nucleodb.library.NucleoDB;
import com.nucleodb.library.database.tables.connection.ConnectionHandler;
import com.nucleodb.library.database.tables.table.DataTable;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class Status {
    public Status() {
    }

    public Status(NucleoDB nucleoDB, int port) {
        this.port = port;
        this.nucleoDB = nucleoDB;
        tables = new LinkedHashSet<>();
        for (DataTable t : this.nucleoDB.getTables().values()) {
            tables.add(t.getName());
        }
        connections = new LinkedHashSet<>();
        for (ConnectionHandler c : this.nucleoDB.getConnections().values()) {
            connections.add(c.getName());
        }
    }

    public Status(Status original) {
        this.items = original.items;
        this.port = original.port;
        this.nucleoDB = original.nucleoDB;
        tables = new LinkedHashSet<>();
        for (DataTable t : this.nucleoDB.getTables().values()) {
            tables.add(t.getName());
        }
        connections = new LinkedHashSet<>();
        for (ConnectionHandler c : this.nucleoDB.getConnections().values()) {
            connections.add(c.getName());
        }
        this.os = original.os;
        this.leaderId = original.leaderId;
        this.id = original.id;  // Assuming you want to copy the original UUID, otherwise generate a new one
    }

    private transient NucleoDB nucleoDB;
    private Set<String> tables;
    private Set<String> connections;
    private int items;
    private int port;
    private String os = System.getProperty("os.name");
    private static String host;

    static {
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    String leaderId = "";
    String id = UUID.randomUUID().toString();



    public int getItems() {
        return nucleoDB.getConnections().size() + nucleoDB.getTables().size();
    }

    public void setItems(int items) {
        this.items = items;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public Set<String> getTables() {
        return tables;
    }

    public Set<String> getConnections() {
        return connections;
    }

    public String getHost() {
        return host;
    }

    public String getOs() {
        return os;
    }

}
