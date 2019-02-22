package com.adrian.entity;

import java.util.List;

public class ReserveResponse {
    private String confirmationCode;
    private List<String> seatsReserved;

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public List<String> getSeatsReserved() {
        return seatsReserved;
    }

    public void setSeatsReserved(List<String> seatsReserved) {
        this.seatsReserved = seatsReserved;
    }
}
