package com.digivault.digihotel.model;

import java.time.LocalDate;

/**
 * An immutable pojo representing an available booking slot.
 * A slot consists of a date and room number.
 * Implements Comparable for date and room order
 */
public class BookingSlot implements Comparable<BookingSlot> {

    private final LocalDate dateOfStay;
    private final int room;

    public BookingSlot(LocalDate dateOfStay, int room) {
        this.dateOfStay = dateOfStay;
        this.room = room;
    }

    public LocalDate getDateOfStay() {
        return dateOfStay;
    }

    public int getRoom() {
        return room;
    }

    @Override
    public int compareTo(BookingSlot o) {

        if (dateOfStay.equals(o.dateOfStay)) {
            return room - o.room;
        }
        return dateOfStay.compareTo(o.dateOfStay);
    }
}
