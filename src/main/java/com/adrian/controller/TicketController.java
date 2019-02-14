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


    @PostMapping(value="/hold") //when holding seats new SeatHols is created, to have information about the seats
    public SeatHold findAndHoldSeats(@RequestBody HoldRequest hr){
        return ticketService.findAndHoldSeats(hr.getNumSeats(), hr.getCustomerEmail());
    }


    @PutMapping(value="/reserve")
    public String reserveSeats(@RequestBody ReserveRequest rr) {
        return ticketService.reserveSeats(rr.getSeatHoldId(),rr.getCustomerEmail());
    }

    @PutMapping(value="/reservelist") //When reserving no new information is created
    public String reserveSeatsByList(@RequestBody ReserveRequest rr) {
        return ticketService.reserveSeatsByList(rr.getSeatHoldId(),rr.getSeatsList(), rr.getCustomerEmail());
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
