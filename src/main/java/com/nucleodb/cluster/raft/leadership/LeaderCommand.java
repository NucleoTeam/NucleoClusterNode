package com.nucleodb.cluster.raft.leadership;

public class LeaderCommand {
    public LeaderCommand() {

    }

    public LeaderCommand(String command) {
        this.command = command;
    }

    String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
