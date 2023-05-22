package vacationapp;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.time.LocalDate;

@ActivityInterface
public interface IActivities {

    @ActivityMethod
    int bookHotel(BookingInfo bookingInfo);

    @ActivityMethod
    int bookFlight(BookingInfo bookingInfo);

    @ActivityMethod
    int bookExcursion(BookingInfo bookingInfo);

    @ActivityMethod
    boolean cancelHotel(String idempotencyKey);

    @ActivityMethod
    boolean cancelFlight(String idempotencyKey);

    @ActivityMethod
    boolean cancelExcursion(String idempotencyKey);
}