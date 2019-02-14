package com.adrian.entity;

import java.util.List;

public class ReserveResponse {
    private String confirmationCode;
    private List<Integer> seatsReserved;

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public List<Integer> getSeatsReserved() {
        return seatsReserved;
    }

    public void setSeatsReserved(List<Integer> seatsReserved) {
        this.seatsReserved = seatsReserved;
    }
}
