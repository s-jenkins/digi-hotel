package com.digivault.digihotel.model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * An immutable pojo representing a booking.
 * A booking is unique by reference number and contains a customer, requested date, room number and stay date.
 * Implements Comparable for stay date and room order
 */
public class Booking implements Comparable<Booking> {

    private final int bookingRef;
    private final String customerName;
    private final Timestamp bookingRequested;
    private final BookingSlot requestDetails;

    public Booking(int bookingRef, String customerName, BookingSlot requestDetails) {
        this.bookingRef = bookingRef;
        this.customerName = customerName;
        this.bookingRequested = new Timestamp(System.currentTimeMillis());
        this.requestDetails = requestDetails;
    }

    public int getBookingRef() {
        return bookingRef;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Timestamp getBookingRequested() {
        return bookingRequested;
    }

    public BookingSlot getRequestDetails() {
        return requestDetails;
    }

    @Override
    public int compareTo(Booking o) {
        return requestDetails.compareTo(o.requestDetails);
    }


}
