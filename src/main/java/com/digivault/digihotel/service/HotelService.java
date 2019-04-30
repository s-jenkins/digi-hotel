package com.digivault.digihotel.service;

import com.digitvault.digihotel.repository.HotelRepository;
import com.digivault.digihotel.model.Booking;
import com.digivault.digihotel.model.BookingSlot;
import static com.digivault.digihotel.service.AdditionalInformation.BOOKING_NOT_ACCEPTED;
import static com.digivault.digihotel.service.AdditionalInformation.NO_DATA_FOUND;
import static com.digivault.digihotel.service.AdditionalInformation.SUCCESSFUL_REQUEST;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

/**
 * Service class to perform all operations required in the system.
 */
public class HotelService {

    private final HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    /**
     * Make a booking for a customer using an existing valid booking slot.
     * The booking may fail due to the slot already filled or that the hotel has no rooms.
     * @param customerName
     * @param slot
     * @return a response that confirms or rejects the booking
     */
    public HotelServiceResponse<Booking> makeBooking(String customerName, BookingSlot slot) {

        int ref = repository.getNextBookingRef();
        Booking booking = new Booking(ref, customerName, slot);
        List<Integer> rooms = repository.getAllRooms();
        if (rooms.size() == 0 || !rooms.contains(slot.getRoom())) {
            return rejectBooking(booking);
        }
        Booking confirmationBooking = repository.addBooking(booking);
        if ( ! booking.equals(confirmationBooking)) {
            return rejectBooking(booking);
        }
        return new HotelServiceResponse<>(booking, true, SUCCESSFUL_REQUEST);
    }

    /**
     * Add the booking to the exceptions and send a response rejecting the required booking
     * @param booking
     * @return an unsuccessful response
     */
    private HotelServiceResponse<Booking> rejectBooking(Booking booking) {

        repository.addException(booking);
        return new HotelServiceResponse<>(booking, false, BOOKING_NOT_ACCEPTED);
    }

    /**
     * Return the available booking slots for a given date.
     * Calculate the free slots from the potential rooms available minus the rooms already booked for
     * the given date.
     * @param date - requested date
     * @return a response containing the free slots
     */
    public HotelServiceResponse<Set<BookingSlot>> findAvailableRoomsForDate(LocalDate date) {

        Set<Integer> bookedRooms = repository.findBookingsForDate(date)
                                            .stream()
                                            .map(b -> b.getRequestDetails().getRoom())
                                            .collect(toSet());

        Set<BookingSlot> slots = repository.getAllRooms()
                                            .stream()
                                            .filter(r -> !bookedRooms.contains(r))
                                            .map(r -> new BookingSlot(date, r))
                                            .collect(toSet());

        return toCollectionResponse(slots);
    }

    /**
     * For a given customer name - filter all valid bookings made.
     * @param customerName
     * @return all bookings for the given customer
     */
    public HotelServiceResponse<Set<Booking>> findBookingsForCustomer(String customerName) {

        Set<Booking> bookings = repository.findAllBookings()
                                            .stream()
                                            .filter(b -> b.getCustomerName().equals(customerName))
                                            .collect(toSet());

        return toCollectionResponse(bookings);
    }

    /**
     * Generic response for a requested collection
     * @param collection
     * @param <T>
     * @return collection wrapped by a HotelServiceResponse
     */
    private <T extends Collection> HotelServiceResponse<T> toCollectionResponse(T collection) {

        if (collection.size() == 0) {
            return new HotelServiceResponse<>(collection, false, NO_DATA_FOUND);
        }
        return new HotelServiceResponse<>(collection, true, SUCCESSFUL_REQUEST);
    }
 }
