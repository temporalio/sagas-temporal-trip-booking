package vacationapp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.temporal.client.WorkflowClient;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import io.temporal.client.WorkflowOptions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;

public class TripPlanningActivitiesWorkflowTest {

    private TestWorkflowEnvironment testEnv;
    private Worker worker;
    private WorkflowClient workflowClient;

    @Before
    public void setUp() {
        testEnv = TestWorkflowEnvironment.newInstance();
        worker = testEnv.newWorker(Shared.TRIP_PLANNING_TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(TripPlanningWorkflow.class);
        workflowClient = testEnv.getWorkflowClient();
    }

    @After
    public void tearDown() {
        testEnv.close();
    }

    @Test
    public void testVacation() {
        BookingInfo info = new BookingInfo(
                new CreditCardInfo(1234567, YearMonth.of(2028, 2), 987),
                "you", "42 Temporal Lane");
        String idempotencyKey = "1";
        IActivities activities = mock(TripPlanningActivities.class);
        worker.registerActivitiesImplementations(activities);
        LocalDate start = LocalDate.of(1981, 12, 31);
        LocalDate end = LocalDate.of(1991, 2, 3);

        testEnv.start();
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(
                Shared.TRIP_PLANNING_TASK_QUEUE).build();
        IWorkflow workflow = workflowClient.newWorkflowStub(IWorkflow.class,
                                                            options);
        workflow.bookVacation(info, start, end, idempotencyKey);
        verify(activities).bookHotel(any(BookingInfo.class), eq(start),
                                     eq(end),
                                     eq(idempotencyKey));
        verify(activities).bookFlight(any(BookingInfo.class), eq(start),
                                      eq(end),
                                      eq(idempotencyKey));
        verify(activities).bookExcursion(any(BookingInfo.class), eq(start),
                                         eq(end),
                                         eq(idempotencyKey));
    }
}
