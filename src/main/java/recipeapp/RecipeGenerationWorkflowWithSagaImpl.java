package recipeapp;

import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.Saga;

// Implementation of the workflow using the built-in Saga class.
public class RecipeGenerationWorkflowWithSagaImpl implements RecipeGenerationWorkflow {
    private static final double price = 1.99;
    private final Money account = Workflow.newActivityStub(Money.class);
    private final RecipeCreator recipeCreator = Workflow.newActivityStub(RecipeCreator.class);
    private final GroceryBroker broker = Workflow.newActivityStub(GroceryBroker.class);

    // Workflow Entrypoint.
    @Override
    public void generateRecipe(String fromAccountId, String toAccountId, String idempotencyKey,
                               String ingredients, GeographicLocation location, String email) {
        Saga saga = new Saga(new Saga.Options.Builder().build());
        try {
            chargeAccounts(fromAccountId, toAccountId, idempotencyKey, saga);
            // "Fail forward" compensation already built in with Temporal via retries if we have
            // problems connecting to the AI recipe service, so no explicit compensation necessary.
            String result = recipeCreator.make(ingredients);
            broker.orderGroceries(ingredients, location, fromAccountId, idempotencyKey);
            saga.addCompensation(broker::cancelOrder, fromAccountId, idempotencyKey);
            shareResult(email, result);
        } catch (ActivityFailure e) {
            saga.compensate();
            throw e;
        }
    }

    private void chargeAccounts(String fromAccountId, String toAccountId, String idempotencyKey,
                                Saga saga) {
        // Move the $$.
        account.withdraw(fromAccountId, idempotencyKey, price);
        saga.addCompensation(account::deposit, fromAccountId, idempotencyKey, price);

        account.deposit(toAccountId, idempotencyKey, price);
        saga.addCompensation(account::withdraw, toAccountId, idempotencyKey, price);
    }

    private void shareResult(String emailAddress, String recipe) {
        System.out.printf(
                "\nSending email with recipe to %s. Recipe contents: %s\n", emailAddress, recipe);
    }
}
