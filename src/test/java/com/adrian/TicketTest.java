package com.adrian;

import com.adrian.dao.TicketDao;
import com.adrian.entity.SeatHold;
import com.adrian.entity.SeatState;
import com.adrian.service.TicketService;
import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(ConcurrentTestRunner.class)  //To check for thread safe of the methods
public class TicketTest {


        private TicketDao ticketDao;
        private TicketService ticketService;
        private String EMAIL = "xdarnek@gmail.com";
        private int seatsToBeHolded = 5;

        @Before
        public void init() {
            ticketDao = new TicketDao();
            ticketService = new TicketService();
            ticketService.setTicketDao(ticketDao);
        }

        @Test
        public void test1_NumSeatsAvailable() {
            ticketService.numSeatsAvailable();
        }

        @Test
        public void test2_FindAndHoldSeats() {
            int numSeats = ticketService.numSeatsAvailable();
            SeatHold seatHold1 = ticketService.findAndHoldSeats(seatsToBeHolded,EMAIL);
            assertNotNull(seatHold1);
        }

        @Test
        public void test3_ReserveSeats() {
            int numSeats = ticketService.numSeatsAvailable();
            SeatHold seatHold2 = ticketService.findAndHoldSeats(seatsToBeHolded,EMAIL);
            String result = ticketService.reserveSeats(seatHold2.getId(), EMAIL);
            assertNotNull(result);
        }

        @Test
        public void test4_HoldedSeats() {
            SeatHold seatHold2 = ticketService.findAndHoldSeats(seatsToBeHolded,EMAIL);
            for (int s : seatHold2.getSeatsHolded()) {
                ticketService.getSeatById(s).getSeatState();
                assertEquals(ticketService.getSeatById(s).getSeatState(), SeatState.HOLD);
            }
        }

        @Test
        public void test5_ReservedSeats() {
            SeatHold seatHold2 = ticketService.findAndHoldSeats(seatsToBeHolded,EMAIL);
            ticketService.reserveSeats(seatHold2.getId(), EMAIL);
            for (int s : seatHold2.getSeatsHolded()) {
                ticketService.getSeatById(s).getSeatState();
                assertEquals(ticketService.getSeatById(s).getSeatState(), SeatState.RESERVED);
            }
        }

        @Test
        public void test6_GetAllSeats() {
            assertNotNull(ticketService.getAllSeats());
        }

        @Test
        public void test7_GetSeatById() {
            for (int i = 0;i<ticketService.getAllSeats().size();i++) {
                assertNotNull(ticketService.getSeatById(i));
            }
        }
}
