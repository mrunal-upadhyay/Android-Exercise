package com.servicenow.exercise_java;

public class NetworkState {

    public enum Status{
        LOADING,
        LOADED,
        FAILED
    }

    private final Status status;
    private final String msg;

    public static final NetworkState LOADING = new NetworkState(Status.LOADING,"Running");
    public static final NetworkState LOADED = new NetworkState(Status.LOADED,"Success");
    public static final NetworkState FAILED = new NetworkState(Status.FAILED,"Failed");

    public NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
