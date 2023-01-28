package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation=reservationRepository2.findById(reservationId).get();
        Spot spot=reservation.getSpot();
        int price_per_hour= spot.getPricePerHour();
        User user=reservation.getUser();

        int total=spot.getPricePerHour()*reservation.getNumberOfHours();

        Payment payment=new Payment();

        if(amountSent<total){
            throw new Exception("Insufficient Amount");
        }
        if(!mode.equals("CASH") || !mode.equals("CARD") || !mode.equals("UPI")){
            throw new Exception("Payment mode not detected");
        }

        payment.setPaymentCompleted(true);
        payment.setPaymentMode(PaymentMode.valueOf(mode));
        spot.setOccupied(false);
        payment.setReservation(reservation);
        paymentRepository2.save(payment);

        reservation.setSpot(spot);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);

        return payment;
    }
}
