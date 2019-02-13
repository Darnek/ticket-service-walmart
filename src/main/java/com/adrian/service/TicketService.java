package com.adrian.service;

import com.adrian.dao.TicketDao;
import com.adrian.entity.Seat;
import com.adrian.entity.SeatHold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TicketService {

    @Autowired
    private TicketDao ticketDao;


    public synchronized int numSeatsAvailable(){
        return getTicketDao().numSeatsAvailable();
    }

    public synchronized SeatHold findAndHoldSeats(int numSeats, String customerEmail){
        return getTicketDao().findAndHoldSeats(numSeats, customerEmail);
    }

    public synchronized String reserveSeats( int seatHoldId,String  customerEmail) {
        return getTicketDao().reserveSeats(seatHoldId,customerEmail);
    }


    public synchronized Collection<Seat> getAllSeats(){
        return getTicketDao().getAllSeats();
    }

    public synchronized Seat getSeatById(int id){
        return getTicketDao().getSeatById(id);
    }

    public TicketDao getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
}
