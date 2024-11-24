import java.util.ArrayList;
import java.util.Scanner;

public class SideQuest extends MissionPart {
    private String description;
    private double hourlyWage; // For simple quests
    private boolean isSimpleQuest; // Flag to distinguish between simple and complex quests

    // Constructor for complex quests
    public SideQuest(String name, String description) {
        super(name);
        this.description = description;
        this.isSimpleQuest = false;
    }

    // Constructor for simple quests
    public SideQuest(String name, String description, double hourlyWage) {
        super(name);
        this.description = description;
        this.hourlyWage = hourlyWage;
        this.isSimpleQuest = true;
    }

    @Override
    public void displayPart() {
        System.out.println("\n--- Side Quest: " + this.getName() + " ---");
        System.out.println("\n Description: " + this.description);
        if (isSimpleQuest) {
            System.out.println("You earn $" + this.hourlyWage + " per hour worked.");
        } else {
            System.out.println("\n Your Choices:");
            ArrayList<Choice> choices = this.getChoices();
            for (int i = 0; i < choices.size(); i++) {
                Choice co = choices.get(i);
                System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
            }
        }
    }

    public String getDescription() {return this.description;}

    public void completeSideQuest(Scanner scanner) {
        if (isSimpleQuest) {
            handleSimpleQuest(scanner); // Handle simple quest
        } else {
            handleComplexQuest(scanner); // Handle complex quest
        }
    }

    // Handle simple quests
    private void handleSimpleQuest(Scanner scanner) {
        this.displayPart();
        System.out.print("\n How many hours do you want to work? ");
        double hoursWorked = validateInput(scanner, 1, 24); // Allow up to 24 hours
        double earnings = hoursWorked * this.hourlyWage;
        System.out.println("\n You worked for " + hoursWorked + " hours and earned $" + earnings + ".");
        System.out.println("Side quest completed successfully!");
    }

    // Handle complex quests
    private void handleComplexQuest(Scanner scanner) {
        this.displayPart(); // Display the options to the user

        // Validate user input
        int input = validateInput(scanner, 1, this.getChoices().size());

        // Execute the validated choice
        boolean success = this.executeChoice(input);
        if (success) {
            System.out.println("Side quest completed successfully!");
        } else {
            System.out.println("Side quest failed!");
        }
    }
}
