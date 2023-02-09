package recipeapp;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.util.UUID;

public class InitiateRecipeGeneration {

    public static void main(String[] args) throws Exception {

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.RECIPE_GENERATION_TASK_QUEUE)
                .setWorkflowId("recipe-generation-workflow")
                .build();
        WorkflowClient client = WorkflowClient.newInstance(service);
        RecipeGenerationWorkflow workflow = client.newWorkflowStub(RecipeGenerationWorkflow.class, options);
        String transferReferenceId = UUID.randomUUID().toString(); // so it executes only once. I think.
        String fromAccount = "001-001";
        String toAccount = "002-002";
        String ingredients = "Fritos\nChili\nShredded cheddar cheese\nSweet white or red onions, diced small\nSour cream";
        WorkflowExecution we = WorkflowClient.start(workflow::generateRecipe, fromAccount, toAccount, ingredients, transferReferenceId);
        System.out.printf("\nCharge from account %s to account %s is processing\n", fromAccount, toAccount);
        System.out.printf("\nWorkflowID: %s RunID: %s", we.getWorkflowId(), we.getRunId());
        System.exit(0);
    }
}
