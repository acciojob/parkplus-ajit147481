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
    public Payment(){
     paymentCompleted=false;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setPaymentCompleted(PaymentMode paymentMode,Reservation reservation) {
        this.paymentCompleted = false;
        this.paymentMode=paymentMode;
        this.reservation=reservation;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public int getId() {
        return id;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }
}
