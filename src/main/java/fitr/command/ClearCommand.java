package fitr.command;

import fitr.list.ExerciseList;
import fitr.list.FoodList;
import fitr.storage.Storage;
import fitr.ui.Ui;
import fitr.user.User;

import java.util.logging.Logger;

/**
 * Clears the food or exercise lists.
 */
public class ClearCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(ClearCommand.class.getName());
    private final String[] arguments;

    public ClearCommand(String userInput) {
        assert userInput != null;
        arguments = userInput.split(" ");
    }

    @Override
    public void execute(FoodList foodlist, ExerciseList exerciseList, Storage storage, User user) {
        if (arguments.length == 1) {
            LOGGER.fine("Clearing food and exercise lists.");
            foodlist.clearList();
            exerciseList.clearList();
            Ui.printCustomMessage("Food and exercise lists are both cleared!");
        } else if (arguments.length == 2) {
            switch (arguments[1]) {
            case "food":
                LOGGER.fine("Clearing food list.");
                foodlist.clearList();
                Ui.printCustomMessage("Food list is cleared!");
                break;
            case "exercise":
                LOGGER.fine("Clearing exercise lists.");
                exerciseList.clearList();
                Ui.printCustomMessage("Exercise list is cleared!");
                break;
            default:
                Ui.printCustomMessage("Invalid clear command entered!");
                break;
            }
        } else {
            Ui.printCustomMessage("Invalid clear command entered!");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
