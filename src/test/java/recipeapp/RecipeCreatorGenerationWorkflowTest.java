package recipeapp;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

public class RecipeCreatorGenerationWorkflowTest {

    private TestWorkflowEnvironment testEnv;
    private Worker worker;
    private WorkflowClient workflowClient;

    @Before
    public void setUp() {
        testEnv = TestWorkflowEnvironment.newInstance();
        worker = testEnv.newWorker(Shared.RECIPE_GENERATION_TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(RecipeGenerationWorkflowImpl.class);
        workflowClient = testEnv.getWorkflowClient();
    }

    @After
    public void tearDown() {
        testEnv.close();
    }

    @Test
    public void testTransfer() {
        String ingredients = "bread";
        String acct1 = "account1";
        String acct2 = "account2";
        String reference = "idempotencyKey1";
        String email = "foo@bar.com";
        Money activities = mock(MoneyImpl.class);
        worker.registerActivitiesImplementations(activities);
        RecipeCreator creator = mock(RecipeCreatorImpl.class);
        when(creator.make(ingredients)).thenReturn("recipe");
        worker.registerActivitiesImplementations(creator);

        testEnv.start();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.RECIPE_GENERATION_TASK_QUEUE)
                .build();
        RecipeGenerationWorkflow workflow = workflowClient.newWorkflowStub(RecipeGenerationWorkflow.class, options);
        workflow.generateRecipe(acct1, acct2, reference, ingredients, email);
        verify(activities).withdraw(eq(acct1), eq(reference), eq(1.99));
        verify(activities).deposit(eq(acct2), eq(reference), eq(1.99));
        verify(creator).make(eq(ingredients));
    }
}
