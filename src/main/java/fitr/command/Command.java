package fitr.command;

import fitr.Recommender;
import fitr.storage.Storage;
import fitr.list.ExerciseList;
import fitr.list.FoodList;
import fitr.user.User;

public abstract class Command {
    protected String command;

    public abstract void execute(FoodList foodlist, ExerciseList exerciseList, Storage storage, User user,
                                 Recommender recommender);

    public abstract boolean isExit();
}
