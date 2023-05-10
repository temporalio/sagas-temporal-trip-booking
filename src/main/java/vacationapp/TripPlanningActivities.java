package vacationapp;

import java.time.LocalDate;

public class TripPlanningActivities implements IActivities {

    @Override
    public int bookHotel(BookingInfo bookingInfo, LocalDate start,
                         LocalDate end, String idempotencyId) {
        System.out.printf("\nSimulating hotel booking. IdempotencyId: %s\n",
                          idempotencyId);
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("Hotel booking error");
        return 100; // the "confirmation id"
    }

    @Override
    public int bookFlight(BookingInfo bookingInfo, LocalDate start,
                          LocalDate end, String idempotencyId) {
        System.out.printf("\nSimulating flight booking. IdempotencyId: %s\n",
                          idempotencyId);
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("Flight booking error");
        return 100; // the "confirmation id"
    }

    @Override
    public int bookExcursion(BookingInfo bookingInfo, LocalDate start,
                             LocalDate end, String idempotencyId) {
        System.out.printf("\nSimulating excursion booking. IdempotencyId: %s\n",
                          idempotencyId);
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("Excursion booking error");
        return 100; // the "confirmation id"
    }

    @Override
    public boolean cancelHotel(String idempotencyId) {
        System.out.printf("\nCancelling hotel using idempotencyId: %s\n",
                          idempotencyId);
        return true;
    }

    @Override
    public boolean cancelFlight(String idempotencyId) {
        System.out.printf("\nCancelling flight using idempotencyId: %s\n",
                          idempotencyId);
        return true;
    }

    @Override
    public boolean cancelExcursion(String idempotencyId) {
        System.out.printf("\nCancelling excursion using idempotencyId: %s\n",
                          idempotencyId);
        return true;
    }
}