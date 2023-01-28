package com.driver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    @OneToMany(mappedBy = "spot",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("spot")
    private List<Spot> spotList;
    public ParkingLot(){

    }

    public ParkingLot(String name,String address,List<Spot> spotList){
        this.name=name;
        this.address=address;
        this.spotList=spotList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public String getAddress() {
        return address;
    }
}