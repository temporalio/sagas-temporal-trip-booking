package recipeapp;

public class MoneyImpl implements Money {
    @Override
    public void withdraw(String accountId, String referenceId, double amount) {
        System.out.printf(
                "\nSimulating withdrawing $%.2f from account %s. ReferenceId: %s\n",
                amount, accountId, referenceId
        );
    }

    @Override
    public void deposit(String accountId, String referenceId, double amount) {
        System.out.printf(
                "\nSimulating depositing $%.2f into account %s. ReferenceId: %s\n",
                amount, accountId, referenceId
        );
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("simulated");
    }
}