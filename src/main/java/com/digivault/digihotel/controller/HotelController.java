package com.digivault.digihotel.controller;

import com.digivault.digihotel.model.Booking;
import com.digivault.digihotel.model.BookingSlot;
import com.digivault.digihotel.service.HotelService;
import com.digivault.digihotel.service.HotelServiceResponse;
import java.time.LocalDate;
import java.util.Set;

/**
 * API entry point for DigiHotel
 */
public class HotelController {

    private final HotelService service;

    public HotelController(HotelService service) {
        this.service = service;
    }

    /**
     * Make a hotel booking for a customer
     * @param customerName - the customer
     * @param slot - the booking slot
     * @return response containing the booking and status
     */
    public HotelServiceResponse<Booking> makeBooking(String customerName, BookingSlot slot) {

        return service.makeBooking(customerName, slot);
    }

    /**
     * Find all booking slots available for a given date
     * @param date - the date
     * @return a response containing a collection of free slots
     */
    public HotelServiceResponse<Set<BookingSlot>> findAvailableRoomsForDate(LocalDate date) {

        return service.findAvailableRoomsForDate(date);
    }

    /**
     * Find all bookings made by a given customer
     * @param customerName - the customer
     * @return a response containing a collection of customer's bookings
     */
    public HotelServiceResponse<Set<Booking>> findBookingsForCustomer(String customerName) {

        return service.findBookingsForCustomer(customerName);
    }
}
