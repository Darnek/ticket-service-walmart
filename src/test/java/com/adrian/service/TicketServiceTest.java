package com.adrian.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.util.Arrays;


import com.adrian.dao.TicketDao;
import com.adrian.entity.Seat;
import com.adrian.entity.SeatHold;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {


    private TicketService ticketService;

    @Mock
    private TicketDao ticketDao; //Mocking the Dao

    private String EMAIL = "xdarnek@gmail.com";
    private int seatsToBeHolded = 10;

    @Before
    public void init() {

        ticketService = new TicketService();
        ticketService.setTicketDao(ticketDao);
        when(ticketService.numSeatsAvailable()).thenReturn(100);

    }

    @Test
    public void test1_NumSeatsAvailable() {
        assertEquals(ticketService.numSeatsAvailable(),100);
    }

    @Test
    public void test2_FindAndHoldSeats() {
        when(ticketService.findAndHoldSeats(Mockito.anyInt(),Mockito.anyString())).thenReturn(new SeatHold());
        assertNotNull(ticketService.findAndHoldSeats(seatsToBeHolded, EMAIL));
    }

    @Test
    public void test3_GetAllSeats() {
        when(ticketService.getAllSeats()).thenReturn( Arrays.asList(new Seat(1)));
        assertNotNull(ticketService.getAllSeats());
    }

}
