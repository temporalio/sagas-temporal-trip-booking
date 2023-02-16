package recipeapp;

import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Workflow;

// Implementation of the workflow using our built-in failure handling.

public class RecipeGenerationWorkflowImpl implements RecipeGenerationWorkflow {
    private static final int priceInCents = 199;
    private final Money account = Workflow.newActivityStub(Money.class);
    private final RecipeCreator recipeCreator = Workflow.newActivityStub(RecipeCreator.class);
    private final GroceryBroker broker = Workflow.newActivityStub(GroceryBrokerImpl.class);

    // Workflow Entrypoint.
    @Override
    public void generateRecipe(String fromAccountId, String toAccountId, String idempotencyKey,
                               String ingredients, GeographicLocation location, String email) {
        try {
            chargeAccounts(fromAccountId, toAccountId, idempotencyKey);
            // "Fail forward" compensation already built in with Temporal via retries if we have
            // problems connecting to the AI recipe service, so no explicit compensation necessary.
            String result = recipeCreator.make(ingredients);
            try {
                broker.orderGroceries(ingredients, location, fromAccountId, idempotencyKey);
            } catch (ActivityFailure a) {
                // Refund the money.
                chargeAccounts(toAccountId, fromAccountId, idempotencyKey);
                broker.cancelOrder(fromAccountId, idempotencyKey);
                sendFailureEmail(email);
            }
            shareResult(email, result);
        } catch (ActivityFailure a) {
            // Refund the money.
            chargeAccounts(toAccountId, fromAccountId, idempotencyKey);
            sendFailureEmail(email);
            throw a;
        }
    }

    private void chargeAccounts(String fromAccountId, String toAccountId, String idempotencyKey) {
        // Move the $$.
        account.withdraw(fromAccountId, idempotencyKey, priceInCents);
        try {
            account.deposit(toAccountId, idempotencyKey, priceInCents);
        } catch (ActivityFailure a) {
            // Refund if we are unable to make the deposit.
            account.deposit(fromAccountId, idempotencyKey, priceInCents);
            throw a;
        }
    }

    private void shareResult(String emailAddress, String recipe) {
        System.out.printf(
                "\nSending email with recipe to %s. Recipe contents: %s\n", emailAddress, recipe);
    }

    private void sendFailureEmail(String emailAddress) {
        System.out.printf("\nSending email to %s to say we could not generate a recipe and order " +
                        "ingredients.\n",
                emailAddress);
    }
}
