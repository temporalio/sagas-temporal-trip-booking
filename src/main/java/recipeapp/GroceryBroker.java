package recipeapp;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface GroceryBroker {
    @ActivityMethod
    boolean orderGroceries(String ingredients,
                           GeographicLocation location, String fromAccountId,
                           String idempotencyKey);

    @ActivityMethod
    boolean cancelOrder(String fromAccountId, String idempotencyKey);
}