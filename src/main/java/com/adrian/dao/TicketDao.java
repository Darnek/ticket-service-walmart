package com.adrian.dao;

import com.adrian.entity.ReserveResponse;
import com.adrian.entity.Seat;
import com.adrian.entity.SeatHold;
import com.adrian.entity.SeatState;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TicketDao {

    private static List<SeatHold> holdList = new ArrayList<>();
    private static List<String> confirmationCodeList = new ArrayList<>();
    private static volatile List<Seat> seats;
    private static int NUMBER_OF_SEATS = 100;


    static{
         seats = new ArrayList<>();
         for (int i=0; i<NUMBER_OF_SEATS; i++) { //to generate 100 seats with status open(defined in the constructor)
             seats.add(new Seat(i));
         }

    }


    public Collection<Seat> getAllSeats(){
        checkForHoldedSeatsNotUsed();
        return this.seats;
    }

    public Seat getSeatById(int id){
        checkForHoldedSeatsNotUsed();
        return this.seats.get(id);
    }

    public synchronized Collection<Seat> getAllAvailableSeats(){
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
            if (s.isActive() && (new Date().getTime()-s.getHoldTime().getTime())>60000){ //Not used for at least 60 seconds
                s.setActive(false);  //to ignore future checks
                for (int e : s.getSeatsHolded()){
                    if (this.seats.get(e).getSeatState()==SeatState.HOLD) {
                        clearSeatByNumber(e);
                    }
                }
            }
        }
    }

    public SeatHold findAndHoldSeats(int numSeats, String customerEmail){

        if (numSeats> numSeatsAvailable()){
            return null;
        }

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

    public List<Integer> getSeatsByHoldId( int seatHoldId) {
        SeatHold sh = holdList.get(seatHoldId-1);
        return sh.getSeatsHolded();
    }

    public ReserveResponse reserveSeats(int seatHoldId, String  customerEmail) {
        SeatHold sh = holdList.get(seatHoldId-1);
        ReserveResponse response = new ReserveResponse();
        if (sh.isActive()){
            List<Integer> list = new ArrayList<>();
            for (Integer s : sh.getSeatsHolded()) {
                if (this.seats.get(s).getSeatState()==SeatState.HOLD) {
                    list.add(s);
                    reserveSeatByNumber(s);
                }
            }
            sh.setActive(false);
            response.setConfirmationCode("C"+seatHoldId+customerEmail);
            response.setSeatsReserved(list);
            confirmationCodeList.add(response.getConfirmationCode());
        }
        else {
            response.setConfirmationCode("Seats not available or already reserved, please try to hold them again");
        }
        return response;
    }

    public ReserveResponse reserveSeatsByList( int seatHoldId, List<Integer> list, String  customerEmail) {
        SeatHold sh = holdList.get(seatHoldId-1);
        ReserveResponse response = new ReserveResponse();
        if (!sh.isActive()) {
            response.setConfirmationCode("Seats not available or already reserved, please try to hold them again");
        }else {
            for (Integer s : sh.getSeatsHolded()) {
                if (this.seats.get(s).getSeatState() == SeatState.HOLD) {
                    if (list.contains(s)) {
                        reserveSeatByNumber(s); //Only reserve seats that are in the list
                    } else {
                        clearSeatByNumber(s); //c;ear any other seats
                    }
                }
            }
            sh.setActive(false);
            String confirmation = "C" + seatHoldId + customerEmail;
            confirmationCodeList.add(confirmation);
            response.setConfirmationCode(confirmation);
            response.setSeatsReserved(list);
        }
            return response;
    }

    public void holdSeatByNumber(int id){
        this.seats.get(id).holdSeat();
    }

    public void reserveSeatByNumber(int id){
            this.seats.get(id).reserveSeat();
    }

    public void clearSeatByNumber(int id){
            this.seats.get(id).clearSeat();
    }

}
