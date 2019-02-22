package com.adrian.dao;

import com.adrian.entity.Seat;
import com.adrian.entity.SeatHold;
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
    private static int NUMBER_OF_COLUMNS = 10;


    static{
         setSeats(new ArrayList<>());
         for (int i=0; i<NUMBER_OF_SEATS; i++) { //to generate 100 seats with status open(defined in the constructor)
             getSeats().add(new Seat(i, getSeatIdFromId(i)));
         }
    }

    public static String getSeatIdFromId(int id){
        return (char)(65+id/NUMBER_OF_COLUMNS) +""+ (id%NUMBER_OF_COLUMNS +1);
    }

    public static int getIdFromSeatId(String seatId){
        return (seatId.charAt(0)-65)*NUMBER_OF_COLUMNS + Integer.parseInt(seatId.substring(1))-1;
    }

    public static List<SeatHold> getHoldList() {
        return holdList;
    }

    public static void setHoldList(List<SeatHold> holdList) {
        TicketDao.holdList = holdList;
    }

    public static List<String> getConfirmationCodeList() {
        return confirmationCodeList;
    }

    public static void setConfirmationCodeList(List<String> confirmationCodeList) {
        TicketDao.confirmationCodeList = confirmationCodeList;
    }

    public static List<Seat> getSeats() {
        return seats;
    }

    public static void setSeats(List<Seat> seats) {
        TicketDao.seats = seats;
    }

    public Collection<Seat> getAllSeats(){
        return this.getSeats();
    }

    public Seat getSeatById(int id){
        return this.getSeats().get(id);
    }

    public List<String> getSeatsByHoldId( SeatHold seatHold) {
        return seatHold.getSeatsHolded();
    }

    public void holdSeatByNumber(int id){
        this.getSeats().get(id).holdSeat();
    }

    public void reserveSeatByNumber(int id){
            this.getSeats().get(id).reserveSeat();
    }

    public void clearSeatByNumber(int id){
            this.getSeats().get(id).clearSeat();
    }

}
