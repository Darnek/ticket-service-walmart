package com.adrian;

import com.adrian.dao.TicketDao;
import com.adrian.entity.SeatHold;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TicketTest {


        private TicketDao ticketDao;
        private SeatHold seatHold;
        private String EMAIL = "xdarnek@gmail.com";
        private int seatsToBeHolded = 20;

        @Before
        public void init() {

            ticketDao = new TicketDao();

        }

        @Test
        public void test1_NumSeatsAvailable() {

            ticketDao.numSeatsAvailable();

        }

        @Test
        public void test2_FindAndHoldSeats() {

            int numSeats = ticketDao.numSeatsAvailable();
            seatHold = ticketDao.findAndHoldSeats(seatsToBeHolded,EMAIL);
            assertEquals(ticketDao.numSeatsAvailable(), numSeats);
        }

        @Test
        public void test3_ReserveSeats() {
            int numSeats = ticketDao.numSeatsAvailable();
            seatHold = ticketDao.findAndHoldSeats(seatsToBeHolded,EMAIL);
            ticketDao.reserveSeats(seatHold.getId(), EMAIL);
            assertEquals(ticketDao.numSeatsAvailable(), numSeats - seatsToBeHolded);
        }


}
