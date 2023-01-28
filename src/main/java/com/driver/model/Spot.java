package com.driver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private SpotType spotType;
    private int pricePerHour;
    private boolean occupied;
    private ParkingLot parkingLot;
    @OneToMany(mappedBy = "spot",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("spot")
    private List<Reservation> reservationList;

    public int getId() {
        return id;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
