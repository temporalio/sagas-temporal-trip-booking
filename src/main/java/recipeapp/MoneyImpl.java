package recipeapp;

public class MoneyImpl implements Money {
    @Override
    public void withdraw(String accountId, String referenceId, double amount) {

        System.out.printf(
                "\nSimulating withdrawing $%f from account %s. ReferenceId: %s\n",
                amount, accountId, referenceId
        );
    }

    @Override
    public void deposit(String accountId, String referenceId, double amount) {

        System.out.printf(
                "\nSimulating depositing $%f into account %s. ReferenceId: %s\n",
                amount, accountId, referenceId
        );
        // Uncomment the following line to simulate an Activity error.
        // throw new RuntimeException("simulated");
    }
}