package recipeapp;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface RecipeGenerationWorkflow {

    // The Workflow method is called by the initiator either via code or CLI.
    @WorkflowMethod
    void generateRecipe(String fromAccountId, String toAccountId, String referenceId,
                        String ingredients, GeographicLocation location, String email);
}