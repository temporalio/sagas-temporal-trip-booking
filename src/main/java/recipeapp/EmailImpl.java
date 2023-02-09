package recipeapp;

public class EmailImpl implements Email {
    @Override
    public void send(String emailAddress, String recipe) {
        System.out.printf(
                "\nSimulating sending email to %s.\n", emailAddress);
    }
}
