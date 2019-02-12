package com.adrian.controller;

import com.adrian.entity.*;
import com.adrian.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/seats")
public class TicketController {

    @Autowired
    public TicketService ticketService;


    @PostMapping(value="/hold")
    public SeatHold findAndHoldSeats(@RequestBody HoldRequest hr){
        return ticketService.findAndHoldSeats(hr.getNumSeats(), hr.getCustomerEmail());
    }


    @PostMapping(value="/reserve")
    public String reserveSeats(@RequestBody ReserveRequest rr) {
        return ticketService.reserveSeats(rr.getSeatHoldId(),rr.getCustomerEmail());
    }

    @GetMapping
    public Collection<Seat> getAllSeats(){
        return ticketService.getAllSeats();
    }

    @GetMapping(value = "/number")
    public int numSeatsAvailable(){
        return ticketService.numSeatsAvailable();
    }


}
