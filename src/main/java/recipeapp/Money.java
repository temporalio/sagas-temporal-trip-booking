package recipeapp;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Money {

    @ActivityMethod
    void deposit(String accountId, String referenceId, double amount);

    @ActivityMethod
    void withdraw(String accountId, String referenceId, double amount);
}