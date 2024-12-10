import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * SideQuest class
 */
public class SideQuest {
    private ArrayList<Choice> choices;
    private String name;
    private String description;
    private double hourlyWage; // For simple quests
    private boolean isSimpleQuest; // Flag to distinguish between simple and complex quests

    /**
     * Constructor for complex quests
     * @param name
     * @param description
     */
    public SideQuest(String name, String description) {
        this.name = name;
        this.description = description;
        this.choices = new ArrayList<Choice>();
        this.isSimpleQuest = false;
    }

   /**
    * Constructor for simple quests
    * @param name
    * @param description
    * @param hourlyWage
    */
    public SideQuest(String name, String description, double hourlyWage) {
        this.name = name;
        this.description = description;
        this.hourlyWage = hourlyWage;
        this.isSimpleQuest = true;
    }
    /**
     * Checks for simple request
     * @return true/false
     */
    public Boolean getIsSimpleQuest() {return this.isSimpleQuest;    }
    /**
     * Name accessor
     * @return name
     */
    public String getName() {return this.name;    }
    /**
     * Hourly wage accessor for burger king side quest
     * @return hourlyWage 
     */
    public double getHourlyWage() {return this.hourlyWage;    }
    /**
     * Sidequest description
     * @return description of side quest
     */
    public String getDescription() {return description;}
    /**
     * Accessor for choices
     * @return choices
     */
    public ArrayList<Choice> getChoices() {return choices;}
    /**
     * Method used to add choice
     * @param name
     * @param description
     * @param moneyReward
     * @param reputationReward
     * @param successProbability
     */
    public void addChoice(String name, String description,  int moneyReward, int reputationReward, double successProbability) {
        if (!isSimpleQuest) {
            choices.add(new Choice(name, description, moneyReward, reputationReward, successProbability));
        }
    }

    /**
     * Handle simple quests
     * @param scanner
     * @param hourlyWage
     * @param player
     */
    public static void completeSimpleQuest(Scanner scanner, double hourlyWage, Player player) {
        // Get the player double money
        double currentMoney = player.getCurrentMoney();

        System.out.print("\nHow many hours do you want to work? ");
        double hoursWorked = validateInput(scanner, 1, 24); // Allow up to 24 hours
        double earnings = hoursWorked * hourlyWage;

        System.out.println("\nYou worked for " + hoursWorked + " hours and earned $" + earnings + ".");
        System.out.println("Side quest completed successfully!");

        double updatedMoney = currentMoney + earnings;

        // Updating the Player Current Money
        player.setCurrentMoney(updatedMoney);

        System.out.println("Current Money: $" + player.getCurrentMoney());
        System.out.println("\n***************************");

    }
    /**
     * Handle complex quests
     * @param scanner
     * @param choices
     * @param player
     */
    public static void completeComplexQuest(Scanner scanner, ArrayList<Choice> choices, Player player) {
        // Get the Players money and reputation
        int currentReputation = player.getCurrentReputation();
        double currentMoney = player.getCurrentMoney();

        System.out.println("\nYour Choices:");
        for (int i = 0; i < choices.size(); i++) {
            Choice co = choices.get(i);
            System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
        }

        // Validate user input
        int input = validateInput(scanner, 1, choices.size());

        // Execute the validated choice
        Choice selectedChoice = choices.get(input - 1);
        Random rand = new Random();

        if (rand.nextDouble() <= selectedChoice.getSuccessProbability()) {
            currentMoney += selectedChoice.getMoneyReward();
            currentReputation += selectedChoice.getReputationReward();
            System.out.println("\nSuccess!");
        } else {
            System.out.println("\nGot caught! Again??? Lose $50 and 5 reputation points");
            currentMoney -= 50;
            currentReputation -= 5;
        }

        player.setCurrentMoney(currentMoney);
        player.setCurrentReputation(currentReputation);

        System.out.println("Current Money: $" + currentMoney);
        System.out.println("Current Reputation: " + currentReputation);
        System.out.println("\n***************************");

    }
    /**
     * Method to validate input
     * @param scanner
     * @param min
     * @param max
     * @return integer input
     */
    public static int validateInput(Scanner scanner, int min, int max) {
        int input = 0;

        // Validate input
        while (true) {
            System.out.print("\nEnter your choice: ");
            if (!scanner.hasNextInt()) { // Check if input is a number
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                scanner.next(); // Clear invalid input
                continue; // Restart loop
                }
            input = scanner.nextInt();
            // Check if input is within range
            if (input >= min && input <= max) {
                break; // Valid input, exit loop
            } else {
                System.out.println("Invalid choice. Please select a number between " + min + " and " + max + ".");}}
        return input; // Return validated input
        
    }
}