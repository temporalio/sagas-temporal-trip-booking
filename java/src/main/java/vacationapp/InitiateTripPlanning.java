package vacationapp;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.time.LocalDate;
import java.time.YearMonth;

public class InitiateTripPlanning {

    public static void main(String[] args) throws Exception {
        WorkflowServiceStubs service =
                WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(
                Shared.TRIP_PLANNING_TASK_QUEUE).setWorkflowId(
                "trip-planning-workflow").build();

        WorkflowClient client = WorkflowClient.newInstance(service);

        IWorkflow workflow = client.newWorkflowStub(IWorkflow.class, options);
        BookingInfo info = new BookingInfo(
                new CreditCardInfo(1, YearMonth.of(2023, 3), 123),
                "Emily Fortuna", "123 Temporal Lane");
        LocalDate start = LocalDate.of(2023, 3, 1);
        LocalDate end = LocalDate.of(2023, 3, 15);
        String idempotencyKey = java.util.UUID.randomUUID().toString();
        WorkflowExecution we = WorkflowClient.start(workflow::bookVacation,
                                                    info, start, end,
                                                    idempotencyKey);
        System.out.printf("\nPlanning vacation is processing %s %s\n", start,
                          end);
        System.out.printf("\nWorkflowID: %s RunID: %s", we.getWorkflowId(),
                          we.getRunId());
        System.exit(0);
    }
}
