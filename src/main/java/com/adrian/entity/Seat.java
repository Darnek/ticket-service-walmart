package com.adrian.entity;

public class Seat {

    private int id;
    private SeatState seatState;
    private String seatId;

    public Seat(int id) {
        this.id = id;
        seatState = SeatState.OPEN;
    }

    public Seat(int id, String seatId) {
        this.id = id;
        this.seatId = seatId;
        seatState = SeatState.OPEN;
    }

    public int getId() { return id; }
    public String getSeatId() { return seatId; }


    public SeatState getSeatState() {
        return seatState;
    }

    public void clearSeat() {
        seatState = SeatState.OPEN;
    }

    public void holdSeat() {
        seatState = SeatState.HOLD;
    }

    public void reserveSeat() {
        seatState = SeatState.RESERVED;
    }

}

