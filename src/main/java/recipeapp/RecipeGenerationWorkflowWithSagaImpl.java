package recipeapp;

import io.temporal.activity.ActivityOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Workflow;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

// Implementation of the workflow using the built-in Saga class.
public class RecipeGenerationWorkflowWithSagaImpl implements RecipeGenerationWorkflow {
    private static final String WITHDRAW = "Withdraw";
    private static final double price = 1.99;
    private final Money account = Workflow.newActivityStub(Money.class);
    private final RecipeCreator recipeCreator = Workflow.newActivityStub(RecipeCreator.class);
    private final Email emailer = Workflow.newActivityStub(Email.class);


    // Workflow Entrypoint.
    @Override
    public void generateRecipe(String fromAccountId, String toAccountId, String referenceId,
                               String ingredients, String email) {
        Saga saga = new Saga(new Saga.Options.Builder().build());
        try {
            chargeAccounts(fromAccountId, toAccountId, referenceId, saga);
            String result = recipeCreator.make(ingredients);
            emailer.send(email, result);
        } catch (ActivityFailure e) {
            emailer.sendFailureEmail(email);
            saga.compensate();
            throw e;
        }
    }

    private void chargeAccounts(String fromAccountId, String toAccountId, String referenceId,
                                Saga saga) {
        // Move the $$.
        account.withdraw(fromAccountId, referenceId, price);
        saga.addCompensation(account::deposit, fromAccountId, referenceId, price);

        account.deposit(toAccountId, referenceId, price);
        saga.addCompensation(account::withdraw, toAccountId, referenceId, price);
    }
}
