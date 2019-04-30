package com.digitvault.digihotel.repository;

import com.digivault.digihotel.model.Booking;
import com.digivault.digihotel.model.BookingSlot;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import static java.util.Arrays.stream;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;
import java.util.stream.IntStream;
import static java.util.stream.IntStream.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class InMemoryRepositoryTest {

    private static final Integer[] rooms =
            {
                    101, 102, 103, 104, 105,
                    201, 202, 203, 204, 205,
                    301, 302, 303, 304, 305,
                    401, 402, 403, 404, 405,
            };

    private static final Random rnd = new Random();
    private static InMemoryRepository repo = new InMemoryRepository(stream(rooms).collect(toSet()));

    @Test
    public void testGetNextBookingRef() throws Exception {

        // Attempting many randomly delayed async calls should result in a perfect sequence.

        int n = 99;
        Vector<Integer> refs = new Vector<>();
        List<Thread> threads = new ArrayList<>();

        Runnable r = () -> {int i = repo.getNextBookingRef(); pause(100); refs.add(i);};
        for (Thread t : startThreads(n, r)) {
            t.join();
        }
        Collections.sort(refs);
        range(1, n).forEach(i -> assertEquals(refs.get(i), (Integer)( i + 1 )));
    }

    @Test
    public void findBookingsForDate() {

        int i = 0;
        Booking[] bookings =
                {
                  new Booking(++i, "Customer" + i, slot(1, 100 + i) ),
                  new Booking(++i, "Customer" + i, slot(1, 100 + i) ),
                  new Booking(++i, "Customer" + i, slot(1, 100 + i) ),
                  new Booking(++i, "Customer" + i, slot(1, 100 + i) ),
                  new Booking(++i, "Customer" + i, slot(1, 100 + i) ),

                  new Booking(++i, "Customer" + i, slot(2, 200 + i) ),
                  new Booking(++i, "Customer" + i, slot(2, 200 + i) ),
                  new Booking(++i, "Customer" + i, slot(2, 200 + i) ),
                  new Booking(++i, "Customer" + i, slot(2, 200 + i) ),

                  new Booking(++i, "Customer" + i, slot(3, 300 + i) ),
                  new Booking(++i, "Customer" + i, slot(3, 300 + i) ),
                  new Booking(++i, "Customer" + i, slot(3, 300 + i) ),
                };

        assertEquals(0, repo.findBookingsForDate(slot(1, 0).getDateOfStay()).size());
        assertEquals(0, repo.findBookingsForDate(slot(2, 0).getDateOfStay()).size());
        assertEquals(0, repo.findBookingsForDate(slot(3, 0).getDateOfStay()).size());

        stream(bookings).forEach(repo::addBooking);

        assertEquals(5, repo.findBookingsForDate(slot(1, 0).getDateOfStay()).size());
        assertEquals(4, repo.findBookingsForDate(slot(2, 0).getDateOfStay()).size());
        assertEquals(3, repo.findBookingsForDate(slot(3, 0).getDateOfStay()).size());

    }

    @Test
    public void findAllBookings() {
    }

    @Test
    public void addBooking() {
    }

    @Test
    public void getAllRooms() {
    }

    @Test
    public void addException() {
    }

    @Test
    public void getAllExceptions() {
    }

    private static void pause(int limit) {

        int mls = rnd.nextInt(limit);
        try {
            Thread.sleep(mls);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Thread> startThreads(int n, Runnable r) {

        List<Thread> threads = new ArrayList<>();
        IntStream.range(0, n).mapToObj(i -> new Thread(r)).forEach(threads::add);
        threads.forEach(Thread::start);
        return threads;
    }

    private static BookingSlot slot(long daysAhead, int room) {

        return new BookingSlot(LocalDate.now().plusDays(daysAhead), room);
    }




}