package com.digitvault.digihotel.repository;

import com.digivault.digihotel.model.Booking;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.stream.Collectors.toList;

public class InMemoryRepository implements HotelRepository {

    private final List<Integer> rooms;

    private List<Booking> bookingExceptions = new ArrayList<>();
    private static final AtomicInteger nextBookingRef = new AtomicInteger(0);

    /**
     * Constructor accepts the potentially bookable room numbers in the hotel.
     * These are sorted and stored in an unmodifiable list structure.
     * A set is used as the parameter so as to prevent duplicates.
     * @param rooms
     */
    public InMemoryRepository(Set<Integer> rooms) {

        this.rooms = unmodifiableList(rooms.stream().sorted().collect(toList()));
    }

    @Override
    public int getNextBookingRef() {
        return nextBookingRef.incrementAndGet();
    }

    /**
     * Return all the bookings made for a specified date.
     * Use the skip key methods to iterate through all the entries for the required date.
     * @param date - the required date
     * @return
     */
    @Override
    public Set<Booking> findBookingsForDate(LocalDate date) {

        //TODO - required

        return null;
    }

    /**
     * Return the master calendar entries.
     * Return a copy so that the master cannot be modified illegally.
     * The entries are immutable so they are safe from modification.
     * @return a copy of the calendar entries
     */
    @Override
    public Set<Booking> findAllBookings() {
        //TODO - required
        return null;
    }

    /**
     * Add the booking to the master calendar. Return the booking if successful, otherwise return the
     * booking that occupies the slot.
     * @param booking to add
     * @return the booking occupying the slot
     */
    @Override
    public Booking addBooking(Booking booking) {

        //TODO - required
        return null;
    }

    /**
     * Return all rooms that exist in the hotel as room numbers. This is an immutable list of integers.
     * @return the room numbers in a sorted list
     */
    @Override
    public List<Integer> getAllRooms() {
        return rooms;
    }

    /**
     * Add a booking to the failed bookings store.
     * @param booking
     */
    @Override
    public void addException(Booking booking) {

        bookingExceptions.add(booking);
    }

    /**
     * Returns a copy of all failed bookings
     * @return list of failed bookings
     */
    List<Booking> getAllExceptions() {

        return unmodifiableList(bookingExceptions);
    }


}
