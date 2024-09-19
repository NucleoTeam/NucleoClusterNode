package com.nucleodb.cluster.raft.common;

import com.nucleodb.library.NucleoDB;

import java.util.UUID;

public class AssignedResource {
    String name;
    Type type;
    UUIDRange range;
    public enum Type {
        TABLE("TABLE"),
        CONNECTION("CONNECTION");
        String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public AssignedResource() {
    }

    public AssignedResource(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public <T> T getResource(NucleoDB nucleoDB){
        return  switch(type){
            case TABLE -> (T)nucleoDB.getTable(name);
            case CONNECTION -> (T)nucleoDB.getConnections().get(name);
            default -> null;
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public UUIDRange getRange() {
        return range;
    }

    public void setRange(UUIDRange range) {
        this.range = range;
    }
    public boolean isUUIDInRange(UUID check) {
        // Compare UUIDs lexicographically
        return check.compareTo(range.getStart()) >= 0 && check.compareTo(range.getEnd()) <= 0;
    }
}
