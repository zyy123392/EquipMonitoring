package com.zyy.model;

public class EquipInfo {
    private String id;

    private String ip;

    private String installdate;

    private Double lng;

    private Double lat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getInstalldate() {
        return installdate;
    }

    public void setInstalldate(String installdate) {
        this.installdate = installdate == null ? null : installdate.trim();
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}