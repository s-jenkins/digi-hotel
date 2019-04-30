package com.digitvault.digihotel.repository;

import com.digivault.digihotel.model.Booking;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface HotelRepository {

    /**
     * Return a new unused booking reference number
     * @return a booking ref integer
     */
    int getNextBookingRef();

    /**
     * Return all the bookings made for a specified date.
     * @param date - the required date
     * @return
     */
    Set<Booking> findBookingsForDate(LocalDate date);

    /**
     * Return all the bookings made.
     * @return
     */
    Set<Booking> findAllBookings();

    /**
     * Attempt to add the booking and return the actual booking made.
     * @param booking to add
     * @return booking or null if fails
     */
    Booking addBooking(Booking booking);

    /**
     * Return all rooms that exist in the hotel as room numbers.
     * @return a list of room numbers
     */
    List<Integer> getAllRooms();
    void addException(Booking booking);
}
