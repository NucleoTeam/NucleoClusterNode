package com.nucleodb.cluster.raft.leadership;

public class LeaderRequest {
    public LeaderRequest() {
    }

    public LeaderRequest(String node, boolean renew) {
        this.node = node;
        this.renew = renew;
    }

    String node = "";
    boolean renew = false;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public boolean isRenew() {
        return renew;
    }

    public void setRenew(boolean renew) {
        this.renew = renew;
    }
}
