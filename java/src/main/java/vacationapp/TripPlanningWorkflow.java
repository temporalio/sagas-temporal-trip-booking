package vacationapp;

import io.temporal.activity.ActivityOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import io.temporal.common.RetryOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TripPlanningWorkflow implements IWorkflow {
    private static final String BOOK_VACATION = "bookVacation";
    // RetryOptions specify how to automatically handle retries when Activities fail.
    private final RetryOptions retryoptions = RetryOptions.newBuilder().setInitialInterval(
            Duration.ofSeconds(1)).setMaximumInterval(
            Duration.ofSeconds(100)).setBackoffCoefficient(
            2).setMaximumAttempts(2).build();
    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            // Optionally provide customized RetryOptions.
            // Temporal retries failures by default, this is simply an example.
            .setRetryOptions(retryoptions).build();
    // ActivityStubs enable calls to methods as if the Activity object is local, but actually perform an RPC.
    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<String, ActivityOptions>() {{
        put(BOOK_VACATION, ActivityOptions.newBuilder().setHeartbeatTimeout(
                Duration.ofSeconds(5)).build());
    }};
    private final IActivities activities = Workflow.newActivityStub(
            IActivities.class, defaultActivityOptions,
            perActivityMethodOptions);

    // The transfer method is the entry point to the Workflow.
    // Activity method executions can be orchestrated here or from within other Activity methods.
    @Override
    public void bookVacation(BookingInfo bookingInfo, LocalDate start,
                             LocalDate end, String idempotencyKey) {
        Saga saga = new Saga(new Saga.Options.Builder().build());
        try {
            saga.addCompensation(activities::cancelHotel, idempotencyKey);
            activities.bookHotel(bookingInfo, start, end, idempotencyKey);

            saga.addCompensation(activities::cancelFlight, idempotencyKey);
            activities.bookFlight(bookingInfo, start, end, idempotencyKey);

            saga.addCompensation(activities::cancelExcursion, idempotencyKey);
            activities.bookExcursion(bookingInfo, start, end, idempotencyKey);
        } catch (ActivityFailure e) {
            saga.compensate();
            throw e;
        }
    }
}
