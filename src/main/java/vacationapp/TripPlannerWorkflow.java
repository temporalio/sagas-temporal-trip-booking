package vacationapp;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.time.LocalDate;

@WorkflowInterface
public interface TripPlannerWorkflow {

    // The Workflow method is called by the initiator either via code or CLI.
    @WorkflowMethod
    void bookVacation(BookingInfo bookingInfo, LocalDate start, LocalDate end, String idempotencyId);
}