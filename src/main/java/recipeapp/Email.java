package recipeapp;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Email {
    @ActivityMethod
    void send(String emailAddress, String recipe);
    
    @ActivityMethod
    void sendFailureEmail(String emailAddress);
}