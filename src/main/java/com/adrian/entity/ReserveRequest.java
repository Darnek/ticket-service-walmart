package com.adrian.entity;

import java.util.List;

public class ReserveRequest {
    private int seatHoldId;
    private String  customerEmail;
    private List<Integer> seatsList;

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

    public List<Integer> getSeatsList() {
        return seatsList;
    }

    public void setSeatsList(List<Integer> seatsList) {
        this.seatsList = seatsList;
    }
}
