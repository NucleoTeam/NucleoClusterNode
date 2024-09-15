package com.nucleodb.cluster.raft;

import com.nucleodb.cluster.raft.leadership.LeaderCommand;

public class Listener {
    public Object process(LeaderCommand leaderCommand){
        return null;
    }
}
