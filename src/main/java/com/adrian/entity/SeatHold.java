package com.adrian.entity;

import java.util.Date;
import java.util.List;

public class SeatHold {

    private int id;
    private int customerId;
    private Date holdTime;
    private String customerEmail;
    private List<String> seatsHolded;
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public SeatHold(){}

    public SeatHold(int id, String email, List<String> seatsHolded){
        this.id=id;
        this.customerEmail = email;
        this.seatsHolded = seatsHolded;
        this.holdTime = new Date();
    }


    public List<String> getSeatsHolded() {
        return seatsHolded;
    }


    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Date holdTime) {
        this.holdTime = holdTime;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setId(int id) {
        this.id = id;
    }
}
