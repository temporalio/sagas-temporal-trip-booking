package vacationapp;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;

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
        worker.registerWorkflowImplementationTypes(TripPlannerWorkflowImpl.class);
        workflowClient = testEnv.getWorkflowClient();
    }

    @After
    public void tearDown() {
        testEnv.close();
    }

    @Test
    public void testTransfer() {
        /*BookingInfo info = new BookingInfo(new CreditCardInfo(1, YearMonth.of(2023, 3), 123),
                "Emily Fortuna", "123 Temporal Lane");
        LocalDate start = LocalDate.of(2023, 3, 1);
        LocalDate end = LocalDate.of(2023, 3, 15);
        String idempotencyId = "1";
        TripPlanningActivities activities = mock(TripPlannerImpl.class);
        worker.registerActivitiesImplementations(activities);*/
        /*RecipeCreator creator = mock(RecipeCreatorImpl.class);
        when(creator.make(ingredients)).thenReturn("recipe");
        worker.registerActivitiesImplementations(creator);*/

        /*testEnv.start();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.TRIP_PLANNING_TASK_QUEUE)
                .build();
        TripPlannerWorkflow workflow = workflowClient.newWorkflowStub(TripPlannerWorkflow.class, options);
        workflow.bookVacation(info, start, end, idempotencyId);
        verify(workflow).boo(eq(info), eq(start), eq(end), eq(idempotencyId));*/
    }
}
