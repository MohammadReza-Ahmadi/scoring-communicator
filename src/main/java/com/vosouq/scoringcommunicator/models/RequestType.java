package com.vosouq.scoringcommunicator.models;

public enum RequestType {
    SENT, RECEIVED;

    public boolean isSent() {
        return this.equals(SENT);
    }
}
