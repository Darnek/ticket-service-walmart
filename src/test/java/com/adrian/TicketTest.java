package com.adrian;

import com.adrian.dao.TicketDao;
import com.adrian.entity.SeatHold;
import com.adrian.entity.SeatState;
import com.adrian.service.TicketService;
import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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
            SeatHold seatHold1 = ticketService.findAndHoldSeats(seatsToBeHolded,EMAIL);
            assertNotNull(seatHold1);
        }

        @Test
        public void test3_ReserveSeats() {
            SeatHold seatHold2 = ticketService.findAndHoldSeats(seatsToBeHolded,EMAIL);
            String result = ticketService.reserveSeats(seatHold2.getId(), EMAIL).getConfirmationCode();
            assertNotNull(result);
        }

        @Test
        public void test4_HoldedSeats() {
            SeatHold seatHold2 = ticketService.findAndHoldSeats(seatsToBeHolded,EMAIL);
            for (String s : seatHold2.getSeatsHolded()) {
                int id = TicketService.getIdFromSeatId(s);
                ticketService.getSeatById(id).getSeatState();
                assertEquals(ticketService.getSeatById(id).getSeatState(), SeatState.HOLD);
            }
        }

        @Test
        public void test5_ReservedSeats() {
            SeatHold seatHold2 = ticketService.findAndHoldSeats(seatsToBeHolded,EMAIL);
            ticketService.reserveSeats(seatHold2.getId(), EMAIL);
            for (String s : seatHold2.getSeatsHolded()) {
                int id = TicketService.getIdFromSeatId(s);
                ticketService.getSeatById(id).getSeatState();
                assertEquals(ticketService.getSeatById(id).getSeatState(), SeatState.RESERVED);
            }
        }

        @Test
        public void test6_ReserveSeatsByList() {
            SeatHold seatHold = ticketService.findAndHoldSeats(seatsToBeHolded,EMAIL);
            List<String> seatsHolded = ticketService.getSeatsByHoldId(seatHold.getId());
            List<String> seatsToBeReserved = seatsHolded.subList(0,seatsHolded.size()/2); //Will be reserving the first half of the holded seats
            ticketService.reserveSeatsByList(seatHold.getId(), seatsToBeReserved,  EMAIL);
            for (String s : seatsToBeReserved) {
                int id = TicketService.getIdFromSeatId(s);
                ticketService.getSeatById(id).getSeatState();
                assertEquals(ticketService.getSeatById(id).getSeatState(), SeatState.RESERVED);
            }
        }

        @Test
        public void test7_GetAllSeats() {
            assertNotNull(ticketService.getAllSeats());
        }

        @Test
        public void test8_GetSeatById() {
            for (int i = 0;i<ticketService.getAllSeats().length;i++) {
                assertNotNull(ticketService.getSeatById(i));
            }
        }
}
