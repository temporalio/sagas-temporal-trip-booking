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
    // RetryOptions specify how to automatically handle retries when Activities fail.
    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();
    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            // Optionally provide customized RetryOptions.
            // Temporal retries failures by default, this is simply an example.
            .setRetryOptions(retryoptions)
            .build();
    // ActivityStubs enable calls to methods as if the Activity object is local, but actually perform an RPC.
    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<String, ActivityOptions>() {{
        put(WITHDRAW, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    }};
    private final Money account = Workflow.newActivityStub(Money.class, defaultActivityOptions, perActivityMethodOptions);
    private final RecipeCreator recipeCreator = Workflow.newActivityStub(RecipeCreator.class, defaultActivityOptions, perActivityMethodOptions);
    private final Email emailer = Workflow.newActivityStub(Email.class, defaultActivityOptions, perActivityMethodOptions);


    // Workflow Entrypoint.
    @Override
    public void generateRecipe(String fromAccountId, String toAccountId, String referenceId,
                               String ingredients, String email) {
        Saga saga = new Saga(new Saga.Options.Builder().build());
        try {
            chargeAccounts(fromAccountId, toAccountId, referenceId, saga);
            String result = recipeCreator.make(ingredients);
            saga.addCompensation(recipeCreator::cancelGeneration);
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
