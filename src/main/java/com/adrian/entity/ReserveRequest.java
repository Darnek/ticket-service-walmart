package com.adrian.entity;

import java.util.List;

public class ReserveRequest {
    private int seatHoldId;
    private String  customerEmail;
    private List<String> seatsList;

    public int getSeatHoldId() {
        return seatHoldId;
    }

    public void setSeatHoldId(int seatHoldId) {
        this.seatHoldId = seatHoldId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<String> getSeatsList() {
        return seatsList;
    }

    public void setSeatsList(List<String> seatsList) {
        this.seatsList = seatsList;
    }
}
