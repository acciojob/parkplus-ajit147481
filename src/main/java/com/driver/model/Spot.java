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

}
