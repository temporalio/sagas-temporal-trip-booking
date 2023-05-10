package vacationapp;

import java.time.LocalDate;

public class TripPlannerImpl implements TripPlanningActivities {

    @Override
    public int bookHotel(BookingInfo bookingInfo, LocalDate start, LocalDate end, String idempotencyId) {
        System.out.printf(
                "\nSimulating hotel booking. IdempotencyId: %s\n", idempotencyId
        );
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("Hotel booking error");
        return 100; // the "confirmation id"
    }

    @Override
    public int bookFlight(BookingInfo bookingInfo, LocalDate start, LocalDate end, String idempotencyId) {
        System.out.printf(
                "\nSimulating flight booking. IdempotencyId: %s\n", idempotencyId
        );
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("Flight booking error");
        return 100; // the "confirmation id"
    }

    @Override
    public int bookExcursion(BookingInfo bookingInfo, LocalDate start, LocalDate end, String idempotencyId) {
        System.out.printf(
                "\nSimulating excursion booking. IdempotencyId: %s\n", idempotencyId
        );
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("Excursion booking error");
        return 100; // the "confirmation id"
    }

    @Override
    public boolean cancelHotel(int reservationNumber, String idempotencyId) {
        System.out.printf(
                "\nCancelling hotel %d. IdempotencyId: %s\n", reservationNumber, idempotencyId
        );
        return true;
    }

    @Override
    public boolean cancelFlight(int reservationNumber, String idempotencyId) {
        System.out.printf(
                "\nCancelling flight %d. IdempotencyId: %s\n", reservationNumber, idempotencyId
        );
        return true;
    }

    @Override
    public boolean cancelExcursion(int reservationNumber, String idempotencyId) {
        System.out.printf(
                "\nCancelling excursion %d. IdempotencyId: %s\n", reservationNumber, idempotencyId
        );
        return true;
    }
}