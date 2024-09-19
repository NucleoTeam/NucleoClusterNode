package com.nucleodb.cluster.raft.common;

import java.util.UUID;

public class UUIDRange {
        private final UUID start;
        private final UUID end;

        public UUIDRange(UUID start, UUID end) {
            this.start = start;
            this.end = end;
        }

        public UUID getStart() {
            return start;
        }

        public UUID getEnd() {
            return end;
        }

        @Override
        public String toString() {
            return "UUIDRange{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }