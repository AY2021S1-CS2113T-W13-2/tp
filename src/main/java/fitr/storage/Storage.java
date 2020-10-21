package fitr.storage;

import fitr.Calorie;
import fitr.Exercise;
import fitr.Food;
import fitr.Goal;
import fitr.list.ExerciseList;
import fitr.list.FoodList;
import fitr.list.GoalList;
import fitr.user.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Handles the loading and saving of data to a file.
 */
public class Storage {
    private static final String DEFAULT_EXERCISE_LIST_FILEPATH = "exercises.txt";
    private static final String DEFAULT_FOOD_LIST_FILEPATH = "food.txt";
    private static final String DEFAULT_USER_CONFIG_FILEPATH = "user.txt";
    private static final String DEFAULT_GOAL_LIST_FILEPATH = "goals.txt";
    private static final String COMMA_SEPARATOR = ",";

    private static final Logger LOGGER = Logger.getLogger(Storage.class.getName());

    private final String exerciseListPath;
    private final String foodListPath;
    private final String userConfigPath;
    private final String goalListPath;
    private static final String tipListPath = "src/main/resources/tips.txt";

    /**
     * Set up the files required in the application, by creating the files if the files do not exist and
     * setting the file paths.
     *
     * @param userConfigPath file path of the user's profile
     * @param foodListPath file path of the food list
     * @param exerciseListPath file path of the exercise list
     * @param goalListPath file path of the goal list
     * @throws IOException if an I/O error has occurred
     */
    public Storage(String userConfigPath, String foodListPath, String exerciseListPath,
                   String goalListPath) throws IOException {
        this.userConfigPath = userConfigPath;
        this.foodListPath = foodListPath;
        this.exerciseListPath = exerciseListPath;
        this.goalListPath = goalListPath;

        File exerciseListFile = new File(exerciseListPath);
        if (!exerciseListFile.exists()) {
            exerciseListFile.createNewFile();
            LOGGER.fine("Exercise list file created: " + exerciseListPath);
        }

        File foodListFile = new File(foodListPath);
        if (!foodListFile.exists()) {
            foodListFile.createNewFile();
            LOGGER.fine("Food list file created: " + foodListPath);
        }

        File userConfigFile = new File(userConfigPath);
        if (!userConfigFile.exists()) {
            userConfigFile.createNewFile();
            LOGGER.fine("User profile file created: " + userConfigPath);
        }

        File goalListFile = new File(goalListPath);
        if (!goalListFile.exists()) {
            goalListFile.createNewFile();
            LOGGER.fine("Goal list file created: " + goalListPath);
        }
    }

    public Storage() throws IOException {
        this(DEFAULT_USER_CONFIG_FILEPATH, DEFAULT_FOOD_LIST_FILEPATH,
                DEFAULT_EXERCISE_LIST_FILEPATH, DEFAULT_GOAL_LIST_FILEPATH);
    }

    /**
     * Reads the user's data from the user config file.
     *
     * @param user the user to load the file into
     * @return True if the file is read successfully, False if not
     * @throws FileNotFoundException if the file is not found
     */
    public boolean readUserConfigFile(User user) throws FileNotFoundException {
        LOGGER.fine("Attempting to read file: " + userConfigPath);
        boolean isReadSuccess = false;
        File file = new File(userConfigPath);
        Scanner readFile = new Scanner(file);
        String line;
        String name;
        String gender;
        int age;
        double height;
        double weight;
        String[] arguments;

        while (readFile.hasNext()) {
            line = readFile.nextLine();
            arguments = line.split(COMMA_SEPARATOR);
            name = arguments[0];
            gender = arguments[1];
            age = Integer.parseInt(arguments[2]);
            height = Double.parseDouble(arguments[3]);
            weight = Double.parseDouble(arguments[4]);
            user.loadUserData(name, age, height, weight, gender);
            isReadSuccess = true;
        }

        LOGGER.fine("User profile file read successfully.");
        return isReadSuccess;
    }

    /**
     * Writes the user's data into the user config file.
     *
     * @param user the user to load the file into
     * @throws IOException if an I/O error has occurred
     */
    public void writeUserConfigFile(User user) throws IOException {
        LOGGER.fine("Attempting to write to file: " + userConfigPath);
        FileWriter file = new FileWriter(userConfigPath);

        file.write(user.getName()
                + COMMA_SEPARATOR + user.getGender()
                + COMMA_SEPARATOR + user.getAge()
                + COMMA_SEPARATOR + user.getHeight()
                + COMMA_SEPARATOR + user.getWeight());

        LOGGER.fine("User profile file written successfully.");
        file.close();
    }

    /**
     * Loads the list of the user's consumed food from a file and returns an ArrayList of Food objects.
     *
     * @return an ArrayList of Food objects
     * @throws FileNotFoundException if the file is not found
     */
    public ArrayList<Food> loadFoodList() throws FileNotFoundException {
        LOGGER.fine("Attempting to read file: " + foodListPath);
        ArrayList<Food> foodList = new ArrayList<>();
        String line;
        String[] arguments;
        File foodListFile = new File(foodListPath);
        Scanner readFile = new Scanner(foodListFile);

        while (readFile.hasNext()) {
            line = readFile.nextLine();
            arguments = line.split(COMMA_SEPARATOR);
            foodList.add(new Food(arguments[0],
                    new Calorie(Integer.parseInt(arguments[1])), Integer.parseInt(arguments[2])));
        }

        LOGGER.fine("Food list file read successfully.");
        return foodList;
    }

    /**
     * Writes the food list data into a file.
     *
     * @param foodList the food list to write to the file
     * @throws IOException if an I/O error has occurred
     */
    public void writeFoodList(FoodList foodList) throws IOException {
        LOGGER.fine("Attempting to write to file: " + foodListPath);
        FileWriter file = new FileWriter(foodListPath);
        Food food;

        for (int i = 0; i < foodList.getSize(); i++) {
            food = foodList.getFood(i);
            file.write(food.getFoodName()
                    + COMMA_SEPARATOR + food.getCalories()
                    + COMMA_SEPARATOR + food.getAmountOfFood() + System.lineSeparator());
        }

        LOGGER.fine("Food list file written successfully.");
        file.close();
    }

    /**
     * Loads the user's exercises from a file and returns an ArrayList of Exercise objects.
     *
     * @return an ArrayList of Exercise objects
     * @throws FileNotFoundException if the file is not found
     */
    public ArrayList<Exercise> loadExerciseList() throws FileNotFoundException {
        LOGGER.fine("Attempting to read file: " + exerciseListPath);
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        String line;
        String[] arguments;
        File exerciseListFile = new File(exerciseListPath);
        Scanner readFile = new Scanner(exerciseListFile);

        while (readFile.hasNext()) {
            line = readFile.nextLine();
            arguments = line.split(COMMA_SEPARATOR);
            exerciseList.add(new Exercise(arguments[0],
                    new Calorie(Integer.parseInt(arguments[1])), Integer.parseInt(arguments[2])));
        }

        LOGGER.fine("Exercise list file read successfully.");
        return exerciseList;
    }

    /**
     * Writes the exercise list data into a file.
     *
     * @param exerciseList the exercise list to write to the file
     * @throws IOException if an I/O error has occurred
     */
    public void writeExerciseList(ExerciseList exerciseList) throws IOException {
        LOGGER.fine("Attempting to write to file: " + exerciseListPath);
        FileWriter file = new FileWriter(exerciseListPath);
        Exercise exercise;

        for (int i = 0; i < exerciseList.getSize(); i++) {
            exercise = exerciseList.getExercise(i);
            file.write(exercise.getNameOfExercise()
                    + COMMA_SEPARATOR + exercise.getCalories()
                    + COMMA_SEPARATOR + exercise.getDuration() + System.lineSeparator());
        }

        LOGGER.fine("Exercise list file written successfully.");
        file.close();
    }

    public ArrayList<Goal> loadGoalList() throws FileNotFoundException {
        LOGGER.fine("Attempting to read file: " + goalListPath);
        ArrayList<Goal> goalList = new ArrayList<>();
        String line;
        String[] arguments;
        File goalListFile = new File(goalListPath);
        Scanner readFile = new Scanner(goalListFile);

        while (readFile.hasNext()) {
            line = readFile.nextLine();
            arguments = line.split(COMMA_SEPARATOR);
            goalList.add(new Goal(arguments[0], arguments[1]));
        }

        LOGGER.fine("Goal list file read successfully.");
        return goalList;
    }

    /**
     * Writes the goal list data into a file.
     *
     * @param goalList the goal list to write to the file
     * @throws IOException if an I/O error has occured
     */
    public void writeGoalList(GoalList goalList) throws IOException {
        LOGGER.fine("Attempting to write to file: " + goalListPath);
        FileWriter fileWriter = new FileWriter(goalListPath);
        Goal goal;

        for (int i = 0; i < goalList.getSize(); i++) {
            goal = goalList.getGoal(i);
            fileWriter.write(goal.getGoalType()
                    + COMMA_SEPARATOR + goal.getDescription() + System.lineSeparator());
        }

        LOGGER.fine("Goal list file written successfully.");
        fileWriter.close();
    }

    /**
     * Loads the tips from a file and returns an ArrayList of String tips.
     * @return an ArrayList of String tips
     * @throws IOException if an I/O error has occurred
     */
    public ArrayList<String> loadTipList() throws IOException {
        LOGGER.fine("Attempting to read file: " + tipListPath);
        ArrayList<String> tipList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(tipListPath));
        String line;
        while ((line = br.readLine()) != null) {
            tipList.add(line);
        }
        LOGGER.fine("Tip list file written successfully.");
        return tipList;
    }
}
