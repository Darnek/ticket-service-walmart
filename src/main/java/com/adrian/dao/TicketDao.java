package com.adrian.dao;

import com.adrian.entity.Seat;
import com.adrian.entity.SeatHold;
import com.adrian.entity.SeatState;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TicketDao {

    private static List<SeatHold> holdList = new ArrayList<>();

    private static List<String> confirmationCodeList = new ArrayList<>();

    private static List<Seat> seats;
    private static int NUMBER_OF_SEATS = 100;


    static{
         seats = new ArrayList<>();
         for (int i=0; i<NUMBER_OF_SEATS; i++) { //to generate 100 seats with status open(defined in the constructor)
             seats.add(new Seat(i));
         }

    }


    public Collection<Seat> getAllSeats(){
        return this.seats;
    }

    public Collection<Seat> getAllAvailableSeats(){
        checkForHoldedSeatsNotUsed();
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat s : seats){
            if (s.getSeatState()==SeatState.OPEN){
                availableSeats.add(s);
            }
        }
        return availableSeats;
    }

    public int numSeatsAvailable(){
        checkForHoldedSeatsNotUsed();
        int numSeats=0;
        for (Seat s : seats){
            if (s.getSeatState()== SeatState.OPEN){
                numSeats++;
            }
        }
        return numSeats;
    }

    public void checkForHoldedSeatsNotUsed(){
        for (SeatHold s : holdList){
            if ((new Date().getTime()-s.getHoldTime().getTime())>60000){ //Not used for at least 60 seconds
                for (int e : s.getSeatsHolded()){
                    clearSeatByNumber(e);
                }
            }
        }
    }

    public SeatHold findAndHoldSeats(int numSeats, String customerEmail){

        List<Integer> seatsAvailable = new ArrayList<>();
        List<Integer> seatsHolded = new ArrayList<>();

        for (Seat s : getAllAvailableSeats()){
            seatsAvailable.add(s.getId());
        }
        for (int i =0; i<numSeats; i++){

            holdSeatByNumber(seatsAvailable.get(i));
            seatsHolded.add(seatsAvailable.get(i));
        }
        SeatHold sh = new SeatHold(holdList.size()+1, customerEmail, seatsHolded);
        holdList.add(sh);
        return sh;

    }

    public String reserveSeats( int seatHoldId,String  customerEmail) {
        for (Integer s : holdList.get(seatHoldId-1).getSeatsHolded()) {
            reserveSeatByNumber(s);
        }
        String confirmation = "C"+seatHoldId+customerEmail;
        confirmationCodeList.add(confirmation);
        return confirmation;
    }


    public void holdSeatByNumber(int id){
        this.seats.get(id).holdSeat();
    }

    public void reserveSeatByNumber(int id){
        if (this.seats.get(id).getSeatState()==SeatState.HOLD) {
            this.seats.get(id).reserveSeat();
        }
    }

    public void clearSeatByNumber(int id){
        if (this.seats.get(id).getSeatState()==SeatState.HOLD) {
            this.seats.get(id).clearSeat();
        }
    }

}
