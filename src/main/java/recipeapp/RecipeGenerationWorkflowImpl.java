package recipeapp;

import io.temporal.activity.ActivityOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Workflow;
import io.temporal.common.RetryOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

// Implementation of the workflow using our built-in failure handling.

public class RecipeGenerationWorkflowImpl implements RecipeGenerationWorkflow {
    private static final int priceInCents = 199;
    private final Money account = Workflow.newActivityStub(Money.class);
    private final RecipeCreator recipeCreator = Workflow.newActivityStub(RecipeCreator.class);

    // Workflow Entrypoint.
    @Override
    public void generateRecipe(String fromAccountId, String toAccountId, String referenceId,
                               String ingredients, String email) {
        try {
            chargeAccounts(fromAccountId, toAccountId, referenceId);
            try {
                String result = recipeCreator.make(ingredients);
                sendEmail(email, result);
            } catch (ActivityFailure a) {
                recipeCreator.cancelGeneration();
                // Refund the money.
                chargeAccounts(toAccountId, fromAccountId, referenceId);
                throw a;
            }
        } catch (ActivityFailure a) {
            sendFailureEmail(email);
        }
    }

    private void chargeAccounts(String fromAccountId, String toAccountId, String referenceId) throws ActivityFailure {
        // Move the $$.
        account.withdraw(fromAccountId, referenceId, priceInCents);
        try {
            account.deposit(toAccountId, referenceId, priceInCents);
        } catch (ActivityFailure a) {
            // Refund if we are unable to make the deposit.
            account.deposit(fromAccountId, referenceId, priceInCents);
            throw a;
        }
    }

    private void sendEmail(String emailAddress, String recipe) {
        System.out.printf(
                "\nSending email with recipe to %s.\n", emailAddress);
    }

    private void sendFailureEmail(String emailAddress) {
        System.out.printf("\nSending email to %s to say we could not generate a recipe.\n",
                emailAddress);
    }
}
