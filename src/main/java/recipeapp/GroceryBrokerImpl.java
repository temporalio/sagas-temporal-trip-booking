package recipeapp;

public class GroceryBrokerImpl implements GroceryBroker {
    @Override
    public void orderGroceries(String ingredients,
                               GeographicLocation location, String fromAccountId,
                               String idempotencyKey) {
        System.out.printf("\nCalculating nearest grocery store given coordinates %f, %f\n",
                location.getLatitude(), location.getLongitude());
        System.out.printf("\nOrdering %s at local grocery on behalf of user, charging account %s," +
                        " order number %s\n",
                ingredients, fromAccountId, idempotencyKey);
    }

    @Override
    public void cancelOrder(String fromAccountId, String idempotencyKey) {
        System.out.printf("\nCanceling order %s and refunding money to account %s\n",
                idempotencyKey, fromAccountId);
    }
}
