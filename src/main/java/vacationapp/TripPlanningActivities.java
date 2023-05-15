package vacationapp;

import java.time.LocalDate;

public class TripPlanningActivities implements IActivities {

    @Override
    public int bookHotel(BookingInfo bookingInfo, LocalDate start,
                         LocalDate end, String idempotencyKey) {
        System.out.printf("\nSimulating hotel booking. idempotencyKey: %s\n",
                          idempotencyKey);
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("Hotel booking error");
        return 100; // the "confirmation id"
    }

    @Override
    public int bookFlight(BookingInfo bookingInfo, LocalDate start,
                          LocalDate end, String idempotencyKey) {
        System.out.printf("\nSimulating flight booking. idempotencyKey: %s\n",
                          idempotencyKey);
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("Flight booking error");
        return 100; // the "confirmation id"
    }

    @Override
    public int bookExcursion(BookingInfo bookingInfo, LocalDate start,
                             LocalDate end, String idempotencyKey) {
        System.out.printf(
                "\nSimulating excursion booking. idempotencyKey: %s\n",
                idempotencyKey);
        // Uncomment the following line to simulate an Activity error.
        throw new RuntimeException("Excursion booking error");
        //return 100; // the "confirmation id"
    }

    @Override
    public boolean cancelHotel(String idempotencyKey) {
        System.out.printf("\nCancelling hotel using idempotencyKey: %s\n",
                          idempotencyKey);
        return true;
    }

    @Override
    public boolean cancelFlight(String idempotencyKey) {
        System.out.printf("\nCancelling flight using idempotencyKey: %s\n",
                          idempotencyKey);
        return true;
    }

    @Override
    public boolean cancelExcursion(String idempotencyKey) {
        System.out.printf("\nCancelling excursion using idempotencyKey: %s\n",
                          idempotencyKey);
        return true;
    }
}