package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.Integer;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        Reservation reservation=new Reservation();
        reservation.setNumberOfHours(timeInHours);

        User user=userRepository3.findById(userId).get();
        List<Reservation> reservationList=user.getReservationList();

        ParkingLot parkingLot=parkingLotRepository3.findById(parkingLotId).get();
        List<Spot> spotList=parkingLot.getSpotList();

        if(user==null || parkingLot==null){
            throw new Exception("Cannot make reservation");
        }
        Spot newSpot=null;

        SpotType spotType=null;
        if(numberOfWheels==2){
            spotType=SpotType.TWO_WHEELER;
        } else if (numberOfWheels==4) {
            spotType=SpotType.FOUR_WHEELER;
        }else{
            spotType=SpotType.OTHERS;
        }

        int price=Integer.MAX_VALUE;

        for(Spot spot:spotList){
            if(newSpot==null ||
                    ( !spot.isOccupied() && spot.getSpotType().equals(spotType)
                            && spot.getPricePerHour()<price )){
                newSpot=spot;
            }
        }

        if(newSpot==null){
            return null;
        }

        reservation.setSpot(newSpot);
        reservation.setNumberOfHours(numberOfWheels);
        reservationList.add(reservation);
        reservationRepository3.save(reservation);

        newSpot.setOccupied(true);
        spotList.add(newSpot);
        spotRepository3.save(newSpot);

        parkingLot.setSpotList(spotList);
        parkingLotRepository3.save(parkingLot);

        user.setReservationList(reservationList);
        userRepository3.save(user);

        return reservation;
    }
}
