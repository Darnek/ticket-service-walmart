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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TicketService {

    @Autowired
    private TicketDao ticketDao;

    public int numSeatsAvailable() {
        checkForHoldedSeatsNotUsed();
        int numSeats = 0;
        for (Seat s : ticketDao.getSeats()) {
            if (s.getSeatState() == SeatState.OPEN) {
                numSeats++;
            }
        }
        return numSeats;
    }

    public synchronized Collection<Seat> getAllAvailableSeats() {
        checkForHoldedSeatsNotUsed();
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat s : TicketDao.getSeats()) {
            if (s.getSeatState() == SeatState.OPEN) {
                availableSeats.add(s);
            }
        }
        return availableSeats;
    }

    public synchronized ReserveResponse reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold sh = TicketDao.getHoldList().get(seatHoldId - 1);
        ReserveResponse response = new ReserveResponse();
        if (sh.isActive()) {
            List<String> list = new ArrayList<>();
            for (String s : sh.getSeatsHolded()) {
                int id = getIdFromSeatId(s);
                if (ticketDao.getSeatById(id).getSeatState() == SeatState.HOLD) {
                    list.add(s);
                    ticketDao.reserveSeatByNumber(id);
                }
            }
            sh.setActive(false);
            response.setConfirmationCode(createFolio(seatHoldId + customerEmail));
            response.setSeatsReserved(list);
            TicketDao.getConfirmationCodeList().add(response.getConfirmationCode());
        } else {
            response.setConfirmationCode("Seats not available or already reserved, please try to hold them again");
        }
        return response;
    }


    public synchronized List<String> getSeatsByHoldId(int seatHoldId) {
        SeatHold sh = TicketDao.getHoldList().get(seatHoldId - 1);
        return getTicketDao().getSeatsByHoldId(sh);
    }

    public synchronized Seat[] getAllSeats() {
        checkForHoldedSeatsNotUsed();
        return getTicketDao().getAllSeats();
    }

    public synchronized Seat getSeatById(int id) {
        checkForHoldedSeatsNotUsed();
        return getTicketDao().getSeatById(id);
    }

    public TicketDao getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void checkForHoldedSeatsNotUsed() {
        long currentTime = new Date().getTime();
        for (SeatHold s : TicketDao.getHoldList()) {
            if (s.isActive() && (currentTime - s.getHoldTime().getTime()) > 60000) { //Not used for at least 60 seconds
                s.setActive(false);  //to ignore future checks
                for (String e : s.getSeatsHolded()) {
                    int id = getIdFromSeatId(e);
                    if (ticketDao.getSeatById(id).getSeatState() == SeatState.HOLD) {
                        ticketDao.clearSeatByNumber(id);
                    }
                }
            }
        }
    }

    public synchronized ReserveResponse reserveSeatsByList(int seatHoldId, List<String> list, String customerEmail) {
        SeatHold sh = TicketDao.getHoldList().get(seatHoldId - 1);
        ReserveResponse response = new ReserveResponse();
        if (!sh.isActive()) {
            response.setConfirmationCode("Seats not available or already reserved, please try to hold them again");
        } else {
            list.replaceAll(String::trim); //to remove all spaces entered by the user
            for (String s : sh.getSeatsHolded()) {
                int id = getIdFromSeatId(s);
                if (ticketDao.getSeatById(id).getSeatState() == SeatState.HOLD) {
                    if (list.contains(s)) {
                        ticketDao.reserveSeatByNumber(id); //Only reserve seats that are in the list
                    } else {
                        ticketDao.clearSeatByNumber(id); //clear any other seats
                    }
                }
            }
            sh.setActive(false);
            String confirmation = createFolio(seatHoldId + customerEmail);
            TicketDao.getConfirmationCodeList().add(confirmation);
            response.setConfirmationCode(confirmation);
            response.setSeatsReserved(list);
        }
        return response;
    }

    public synchronized SeatHold findAndHoldSeats(int numSeats, String customerEmail) {

        if (numSeats > numSeatsAvailable()) {
            return null;
        }

        List<Integer> seatsAvailable = new ArrayList<>();
        List<String> seatsHolded = new ArrayList<>();

        for (Seat s : getAllAvailableSeats()) {
            seatsAvailable.add(s.getId());
        }
        for (int i = 0; i < numSeats; i++) {

            ticketDao.holdSeatByNumber(seatsAvailable.get(i));
            seatsHolded.add(getSeatIdFromId(seatsAvailable.get(i)));
        }
        SeatHold sh = new SeatHold(TicketDao.getHoldList().size() + 1, customerEmail, seatsHolded);
        TicketDao.getHoldList().add(sh);
        return sh;

    }

    public static String getSeatIdFromId(int id) {
        return TicketDao.getSeatIdFromId(id);
    }

    public static int getIdFromSeatId(String seatId) {
        return TicketDao.getIdFromSeatId(seatId);
    }

    public static String createFolio(String orig){

        MessageDigest digest = null;
        byte[] encodedhash = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            encodedhash = digest.digest(orig.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "C"+encodedhash.toString().toUpperCase().substring(3,10);
    }

}
