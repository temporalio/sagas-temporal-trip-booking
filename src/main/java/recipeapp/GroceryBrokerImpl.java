package recipeapp;

import io.temporal.activity.ActivityMethod;

public class GroceryBrokerImpl {
    @Override
    public boolean orderGroceries(String ingredients,
                                  GeographicLocation location, String fromAccountId,
                                  String idempotencyKey) {
        System.out.printf("Calculating nearest grocery store given coordinates %d, %d",
                location.getLatitude(), location.getLongitude());
        System.out.printf("Ordering %s at local grocery on behalf of user, charging account %s, " +
                        "order number %s",
                ingredients, fromAccountId, idempotencyKey);
        // Simulating the API calls were successful in reserving groceries and there were
        // sufficient funds.
        return true;
    }

    @Override
    public boolean cancelOrder(String fromAccountId, String idempotencyKey) {
        System.out.printf("Canceling order %s and refunding money to account %s", idempotencyKey,
                fromAccountId);
        return true;
    }
}
