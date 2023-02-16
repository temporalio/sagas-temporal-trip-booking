package recipeapp;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface GroceryBroker {
    @ActivityMethod
    void orderGroceries(String ingredients,
                        GeographicLocation location, String fromAccountId,
                        String idempotencyKey);

    @ActivityMethod
    void cancelOrder(String fromAccountId, String idempotencyKey);
}