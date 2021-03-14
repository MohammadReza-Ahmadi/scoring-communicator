package com.vosouq.scoringcommunicator.models;

public enum RequestStatus {
    WAITING(0),
    ACCEPTED(1),
    REJECTED(-1);

    private int code;

    RequestStatus(int code) {
        this.code = code;
    }

    public static RequestStatus resolve(int code) {
        for (RequestStatus v : RequestStatus.values()) {
            if (v.code == code)
                return v;
        }
        throw new IllegalArgumentException("RequestStatus by code" + code + " is not found!");
    }
}
