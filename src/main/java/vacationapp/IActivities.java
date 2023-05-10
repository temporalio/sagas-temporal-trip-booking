package vacationapp;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.time.LocalDate;

@ActivityInterface
public interface IActivities {

    @ActivityMethod
    int bookHotel(BookingInfo bookingInfo, LocalDate start, LocalDate end,
                  String idempotencyId);

    @ActivityMethod
    int bookFlight(BookingInfo bookingInfo, LocalDate start, LocalDate end,
                   String idempotencyId);

    @ActivityMethod
    int bookExcursion(BookingInfo bookingInfo, LocalDate start, LocalDate end,
                      String idempotencyId);

    @ActivityMethod
    boolean cancelHotel(String idempotencyId);

    @ActivityMethod
    boolean cancelFlight(String idempotencyId);

    @ActivityMethod
    boolean cancelExcursion(String idempotencyId);
}