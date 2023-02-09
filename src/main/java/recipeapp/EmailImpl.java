package recipeapp;

public class EmailImpl implements Email {
    @Override
    public void send(String emailAddress, String recipe) {
        System.out.printf(
                "\nSending email with recipe to %s.\n", emailAddress);
    }

    @Override
    public void sendFailureEmail(String emailAddress) {
        System.out.printf("\nSending email to %s to say we could not generate a recipe.\n",
                emailAddress);
    }
}
