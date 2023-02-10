package recipeapp;

public class RecipeCreatorImpl implements RecipeCreator {
    @Override
    public String make(String ingredients) {
        // TODO(efortuna): actually call to OpenAI
        String recipe = "Bake at 350F";
        System.out.printf(
                "\nGenerated the following recipe: %s.\n", recipe);
        return recipe;
    }

    @Override
    public void cancelGeneration() {
        System.out.printf("Canceling recipe generation");
    }
}