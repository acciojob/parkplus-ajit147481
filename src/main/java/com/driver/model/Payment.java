package com.driver.model;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean paymentCompleted;
    private PaymentMode paymentMode;
    @OneToOne
    @JoinColumn
    private Reservation reservation;
}
