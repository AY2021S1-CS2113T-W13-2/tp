package fitr;

import fitr.command.Command;
import fitr.list.ExerciseList;
import fitr.list.FoodList;
import fitr.list.GoalList;
import fitr.storage.Storage;
import fitr.ui.Ui;
import fitr.user.User;
import fitr.parser.Parser;

import java.io.IOException;

public class Fitr {
    private Storage storage;
    private FoodList foodList;
    private ExerciseList exerciseList;
    private User user;
    private GoalList goalList;

    public Fitr(String filePathOfUserConfig, String filePathOfFoodList,
                String filePathOfExerciseList, String filePathOfGoalList) {
        try {
            user = new User();
            Ui.printGreetingMessage();
            storage = new Storage(filePathOfUserConfig, filePathOfFoodList,
                    filePathOfExerciseList, filePathOfGoalList);
            if (!storage.readUserConfigFile(user)) {
                user.setup();
                storage.writeUserConfigFile(user);
            }
            Ui.printSuggestQuestion();
            foodList = new FoodList(storage.loadFoodList());
            exerciseList = new ExerciseList(storage.loadExerciseList());
            goalList = new GoalList(storage.loadGoalList());
        } catch (IOException e) {
            System.out.println("Theres no file");
        }
    }

    public void run() {
        boolean isExit = false;
        while (!isExit) {
            String userInput = Ui.read();
            Command c = Parser.parse(userInput);
            c.execute(foodList, exerciseList, storage, user, goalList);
            isExit = c.isExit();
        }
        Ui.printExitMessage();
    }

    public static void main(String[] args) {
        new Fitr("userConfig.txt", "foodList.txt", "exerciseList.txt", "goalList.txt").run();
    }
}
