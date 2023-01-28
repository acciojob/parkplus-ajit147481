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
        try {
            if(!userRepository3.findById(userId).isPresent()||
                    !parkingLotRepository3.findById(parkingLotId).isPresent()){
                throw new Exception("Cannot make reservation");
            }

            User user=userRepository3.findById(userId).get();
            ParkingLot parkingLot=parkingLotRepository3.findById(parkingLotId).get();

            List<Spot> spotList=parkingLot.getSpotList();
            boolean checkSpot=false;
            for(Spot spot:spotList){
                if(!spot.getOccupied()){
                    checkSpot=true;
                    break;
                }
            }

            if(!checkSpot){
                throw new Exception("Cannot make reservation");
            }
            SpotType requestSpotType;
            if(numberOfWheels>4){
                requestSpotType=SpotType.OTHERS;
            } else if (numberOfWheels>2) {
                requestSpotType=SpotType.FOUR_WHEELER;
            } else {
                requestSpotType=SpotType.TWO_WHEELER;
            }

            int minimumprice=Integer.MAX_VALUE;
            checkSpot=false;
            Spot spotChosen=null;

            for(Spot spot:spotList){
                if(requestSpotType.equals(SpotType.OTHERS) && spot.getSpotType().equals(SpotType.OTHERS)){
                    if(spot.getPricePerHour()*timeInHours < minimumprice && !spot.getOccupied()){
                        minimumprice=spot.getPricePerHour()*timeInHours;
                        checkSpot=true;
                        spotChosen=spot;
                    }
                }
                else if (requestSpotType.equals(SpotType.FOUR_WHEELER) && spot.getSpotType().equals(SpotType.OTHERS)||
                    spot.getSpotType().equals(SpotType.FOUR_WHEELER) ) {
                    if(spot.getPricePerHour()*timeInHours<minimumprice && !spot.getOccupied()){
                        minimumprice=spot.getPricePerHour()*timeInHours;
                        checkSpot=true;
                        spotChosen=spot;
                    }
                } else if (requestSpotType.equals(SpotType.TWO_WHEELER) && spot.getSpotType().equals(SpotType.OTHERS) ||
                        spot.getSpotType().equals(SpotType.FOUR_WHEELER) || spot.getSpotType().equals(SpotType.OTHERS)) {
                    if(spot.getPricePerHour()*timeInHours<minimumprice && !spot.getOccupied()){
                        minimumprice=spot.getPricePerHour()*timeInHours;
                        checkSpot=true;
                        spotChosen=spot;
                    }
                }
            }
            if(!checkSpot){
                throw new Exception("Cannot make reservation");
            }
            assert spotChosen!=null;
            spotChosen.setOccupied(true);

            Reservation reservation=new Reservation();
            reservation.setNumberOfHours(timeInHours);
            reservation.setSpot(spotChosen);
            reservation.setUser(user);

            spotChosen.getReservationList().add(reservation);
            user.getReservationList().add(reservation);

            userRepository3.save(user);
            spotRepository3.save(spotChosen);

            return reservation;
        }
        catch (Exception e){
            return null;
        }
    }
}
