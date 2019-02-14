package com.adrian.service;

import com.adrian.dao.TicketDao;
import com.adrian.entity.ReserveResponse;
import com.adrian.entity.Seat;
import com.adrian.entity.SeatHold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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

    public synchronized ReserveResponse reserveSeats(int seatHoldId, String  customerEmail) {
        return getTicketDao().reserveSeats(seatHoldId,customerEmail);
    }

    public synchronized ReserveResponse reserveSeatsByList(int seatHoldId, List<Integer> list, String  customerEmail) {
        return getTicketDao().reserveSeatsByList(seatHoldId,list,customerEmail);
    }

    public synchronized List<Integer> getSeatsByHoldId(int seatHoldId){
        return getTicketDao().getSeatsByHoldId(seatHoldId);
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
