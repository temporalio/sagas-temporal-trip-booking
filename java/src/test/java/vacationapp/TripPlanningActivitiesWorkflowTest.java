package vacationapp;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.failure.ActivityFailure;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import io.temporal.client.WorkflowOptions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
        LocalDate start = LocalDate.of(1981, 12, 31);
        LocalDate end = LocalDate.of(1991, 2, 3);
        BookingInfo info = new BookingInfo(
                new CreditCardInfo(1234567, YearMonth.of(2028, 2), 987),
                "you", "123", "42 Temporal Lane", start, end);
        BookingInfo info2 = new BookingInfo(
                new CreditCardInfo(1234567, YearMonth.of(2028, 2), 987),
                "you", "123", "42 Temporal Lane", start, end);
        IActivities activities = mock(TripPlanningActivities.class);
        worker.registerActivitiesImplementations(activities);

        testEnv.start();
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(
                Shared.TRIP_PLANNING_TASK_QUEUE).build();
        IWorkflow workflow = workflowClient.newWorkflowStub(IWorkflow.class,
                                                            options);
        workflow.bookVacation(info);
        verify(activities).bookHotel(eq(info));
        verify(activities).bookFlight(eq(info));
        verify(activities).bookExcursion(eq(info));
    }
    @Test
    public void testSagaFailure() {
        LocalDate start = LocalDate.of(1981, 12, 31);
        LocalDate end = LocalDate.of(1991, 2, 3);
        BookingInfo info = new BookingInfo(
                new CreditCardInfo(1234567, YearMonth.of(2028, 2), 987),
                "you", "123", "42 Temporal Lane", start, end);
        IActivities activities = mock(TripPlanningActivities.class);
        worker.registerActivitiesImplementations(activities);
        Mockito.when(activities.bookExcursion(info)).thenThrow(
                new RuntimeException("failed to book excursion"));

        testEnv.start();
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(
                Shared.TRIP_PLANNING_TASK_QUEUE).build();
        IWorkflow workflow = workflowClient.newWorkflowStub(IWorkflow.class,
                                                            options);
        assertThrows(WorkflowException.class, () -> workflow.bookVacation(info));

        verify(activities).bookHotel(eq(info));
        verify(activities).bookFlight(eq(info));
        assertThrows(RuntimeException.class, () -> activities.bookExcursion(info));
        verify(activities).cancelExcursion(info.getClientId());
        verify(activities).cancelFlight(info.getClientId());
        verify(activities).cancelHotel(info.getClientId());
    }
}
