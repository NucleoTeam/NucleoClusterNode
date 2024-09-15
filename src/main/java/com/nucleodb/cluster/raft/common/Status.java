package com.nucleodb.cluster.raft.common;

import com.google.common.collect.Sets;
import com.nucleodb.library.NucleoDB;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public class Status {
    public Status() {
    }

    public Status(int port) {
        this.port = port;
    }

    public Status(Status original) {
        this.items = original.items;
        this.port = original.port;
        this.tables = original.tables != null ? Sets.newCopyOnWriteArraySet(original.tables) : null;
        this.connections = original.connections != null ? Sets.newCopyOnWriteArraySet(original.connections) : null;
        this.os = original.os;
        this.leaderId = original.leaderId;
        this.id = original.id;  // Assuming you want to copy the original UUID, otherwise generate a new one
    }

    int items;
    int port;
    Set<String> tables = Sets.newHashSet();
    Set<String> connections = Sets.newHashSet();
    String os = System.getProperty("os.name");
    static String host;

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
        return items;
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

    public void setTables(Set<String> tables) {
        this.tables = tables;
    }

    public Set<String> getConnections() {
        return connections;
    }

    public void setConnections(Set<String> connections) {
        this.connections = connections;
    }

    public String getHost() {
        return host;
    }

    public String getOs() {
        return os;
    }

}
