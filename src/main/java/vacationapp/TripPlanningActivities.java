package vacationapp;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.time.LocalDate;

@ActivityInterface
public interface TripPlanningActivities {

    @ActivityMethod
    int bookHotel(BookingInfo bookingInfo, LocalDate start, LocalDate end, String idempotencyId);

    @ActivityMethod
    int bookFlight(BookingInfo bookingInfo, LocalDate start, LocalDate end, String idempotencyId);

    @ActivityMethod
    int bookExcursion(BookingInfo bookingInfo, LocalDate start, LocalDate end, String idempotencyId);

    @ActivityMethod
    boolean cancelHotel(int reservationNumber, String idempotencyId);

    @ActivityMethod
    boolean cancelFlight(int reservationNumber, String idempotencyId);

    @ActivityMethod
    boolean cancelExcursion(int reservationNumber, String idempotencyId);
}