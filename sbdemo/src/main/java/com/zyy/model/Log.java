package com.zyy.model;

public class Log {
    private String id;

    private String time;

    private String httpmethod;

    private String ip;

    private String classmethod;

    private String args;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getHttpmethod() {
        return httpmethod;
    }

    public void setHttpmethod(String httpmethod) {
        this.httpmethod = httpmethod == null ? null : httpmethod.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getClassmethod() {
        return classmethod;
    }

    public void setClassmethod(String classmethod) {
        this.classmethod = classmethod == null ? null : classmethod.trim();
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args == null ? null : args.trim();
    }
}