package com.adrian.service;

import com.adrian.dao.TicketDao;
import com.adrian.entity.ReserveResponse;
import com.adrian.entity.Seat;
import com.adrian.entity.SeatHold;
import com.adrian.entity.SeatState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TicketService {

    @Autowired
    private TicketDao ticketDao;

    public int numSeatsAvailable(){
        checkForHoldedSeatsNotUsed();
        int numSeats=0;
        for (Seat s : ticketDao.getSeats()){
            if (s.getSeatState()== SeatState.OPEN){
                numSeats++;
            }
        }
        return numSeats;
    }

    public synchronized Collection<Seat> getAllAvailableSeats(){
        checkForHoldedSeatsNotUsed();
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat s : TicketDao.getSeats()){
            if (s.getSeatState()==SeatState.OPEN){
                availableSeats.add(s);
            }
        }
        return availableSeats;
    }

    public synchronized ReserveResponse reserveSeats(int seatHoldId, String  customerEmail) {
        SeatHold sh = TicketDao.getHoldList().get(seatHoldId-1);
        ReserveResponse response = new ReserveResponse();
        if (sh.isActive()){
            List<Integer> list = new ArrayList<>();
            for (Integer s : sh.getSeatsHolded()) {
                if (TicketDao.getSeats().get(s).getSeatState()==SeatState.HOLD) {
                    list.add(s);
                    ticketDao.reserveSeatByNumber(s);
                }
            }
            sh.setActive(false);
            response.setConfirmationCode("C"+seatHoldId+customerEmail);
            response.setSeatsReserved(list);
            TicketDao.getConfirmationCodeList().add(response.getConfirmationCode());
        }
        else {
            response.setConfirmationCode("Seats not available or already reserved, please try to hold them again");
        }
        return response;
    }


    public synchronized List<Integer> getSeatsByHoldId(int seatHoldId){
        SeatHold sh = TicketDao.getHoldList().get(seatHoldId-1);
        return getTicketDao().getSeatsByHoldId(sh);
    }

    public synchronized Collection<Seat> getAllSeats(){
        checkForHoldedSeatsNotUsed();
        return getTicketDao().getAllSeats();
    }

    public synchronized Seat getSeatById(int id){
        checkForHoldedSeatsNotUsed();
        return getTicketDao().getSeatById(id);
    }

    public TicketDao getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void checkForHoldedSeatsNotUsed(){
        for (SeatHold s : TicketDao.getHoldList()){
            if (s.isActive() && (new Date().getTime()-s.getHoldTime().getTime())>60000){ //Not used for at least 60 seconds
                s.setActive(false);  //to ignore future checks
                for (int e : s.getSeatsHolded()){
                    if (TicketDao.getSeats().get(e).getSeatState()== SeatState.HOLD) {
                        ticketDao.clearSeatByNumber(e);
                    }
                }
            }
        }
    }

    public synchronized ReserveResponse reserveSeatsByList( int seatHoldId, List<Integer> list, String  customerEmail) {
        SeatHold sh = ticketDao.getHoldList().get(seatHoldId-1);
        ReserveResponse response = new ReserveResponse();
        if (!sh.isActive()) {
            response.setConfirmationCode("Seats not available or already reserved, please try to hold them again");
        }else {
            for (Integer s : sh.getSeatsHolded()) {
                if (TicketDao.getSeats().get(s).getSeatState() == SeatState.HOLD) {
                    if (list.contains(s)) {
                        ticketDao.reserveSeatByNumber(s); //Only reserve seats that are in the list
                    } else {
                        ticketDao.clearSeatByNumber(s); //clear any other seats
                    }
                }
            }
            sh.setActive(false);
            String confirmation = "C" + seatHoldId + customerEmail;
            TicketDao.getConfirmationCodeList().add(confirmation);
            response.setConfirmationCode(confirmation);
            response.setSeatsReserved(list);
        }
        return response;
    }

    public synchronized SeatHold findAndHoldSeats(int numSeats, String customerEmail){

        if (numSeats> numSeatsAvailable()){
            return null;
        }

        List<Integer> seatsAvailable = new ArrayList<>();
        List<Integer> seatsHolded = new ArrayList<>();

        for (Seat s : getAllAvailableSeats()){
            seatsAvailable.add(s.getId());
        }
        for (int i =0; i<numSeats; i++){

            ticketDao.holdSeatByNumber(seatsAvailable.get(i));
            seatsHolded.add(seatsAvailable.get(i));
        }
        SeatHold sh = new SeatHold(TicketDao.getHoldList().size()+1, customerEmail, seatsHolded);
        TicketDao.getHoldList().add(sh);
        return sh;

    }



}
