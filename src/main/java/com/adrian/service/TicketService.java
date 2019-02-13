package com.adrian.service;

import com.adrian.dao.TicketDao;
import com.adrian.entity.Seat;
import com.adrian.entity.SeatHold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TicketService {

    @Autowired
    private TicketDao ticketDao;

    public TicketService(){
    }

    public TicketService(TicketDao ticketDao){
        this.ticketDao=ticketDao;
    }


    public synchronized int numSeatsAvailable(){
        return ticketDao.numSeatsAvailable();
    }

    public synchronized SeatHold findAndHoldSeats(int numSeats, String customerEmail){
        return ticketDao.findAndHoldSeats(numSeats, customerEmail);
    }

    public synchronized String reserveSeats( int seatHoldId,String  customerEmail) {
        return ticketDao.reserveSeats(seatHoldId,customerEmail);
    }


    public synchronized Collection<Seat> getAllSeats(){
        return ticketDao.getAllSeats();
    }


}
