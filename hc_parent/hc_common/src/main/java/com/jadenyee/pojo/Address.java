package com.jadenyee.pojo;

import java.io.Serializable;

public class Address implements Serializable {
    private Integer id;
    private String name;
    private String locationName;
    private Double longitude;
    private Double latitude;
    private Integer valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Address() {
    }

    public Address(Integer id, String name, String locationName, Double longitude, Double latitude, Integer valid) {
        this.id = id;
        this.name = name;
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", locationName='" + locationName + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", valid=" + valid +
                '}';
    }
}
