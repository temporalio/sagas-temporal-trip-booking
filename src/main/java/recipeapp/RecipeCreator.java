package recipeapp;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface RecipeCreator {
    @ActivityMethod
    String make(String ingredients);

    @ActivityMethod
    void cancelGeneration();
}