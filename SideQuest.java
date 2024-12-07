import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SideQuest {
    private ArrayList<Choice> choices;
    private String name;
    private String description;
    private double hourlyWage; // For simple quests
    private boolean isSimpleQuest; // Flag to distinguish between simple and complex quests

    // Constructor for complex quests
    public SideQuest(String name, String description) {
        this.name = name;
        this.description = description;
        this.choices = new ArrayList<Choice>();
        this.isSimpleQuest = false;
    }

    // Constructor for simple quests
    public SideQuest(String name, String description, double hourlyWage) {
        this.name = name;
        this.description = description;
        this.hourlyWage = hourlyWage;
        this.isSimpleQuest = true;
    }

    public Boolean getIsSimpleQuest() {return this.isSimpleQuest;    }
    public String getName() {return this.name;    }
    public double getHourlyWage() {return this.hourlyWage;    }
    public String getDescription() {return description;}
    public ArrayList<Choice> getChoices() {return choices;}

    public void addChoice(String name, String description,  int moneyReward, int reputationReward, double successProbability) {
        if (!isSimpleQuest) {
            choices.add(new Choice(name, description, moneyReward, reputationReward, successProbability));
        }
    }

    // // Handle simple quests
    // public static void completeSimpleQuest(Scanner scanner, double hourlyWage, int currentMoney) {
    //     System.out.print("\n How many hours do you want to work? ");
    //     double hoursWorked = validateInput(scanner, 1, 24); // Allow up to 24 hours
    //     double earnings = hoursWorked * hourlyWage;
    //     System.out.println("\n You worked for " + hoursWorked + " hours and earned $" + earnings + ".");
    //     System.out.println("Side quest completed successfully!");

    //     currentMoney += earnings;
    //     System.out.println("Current Money:" + currentMoney);                 

    // }

    // // Handle complex quests
    // public static void completeComplexQuest(Scanner scanner, ArrayList<Choice> choices, int currentMoney, int currentReputation) {
    //     System.out.println("\n Your Choices:");
    //     for (int i = 0; i < choices.size(); i++) {
    //         Choice co = choices.get(i);
    //         System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
    //     }

    //     // Validate user input
    //     int input = validateInput(scanner, 1, choices.size());
        
    //     // Execute the validated choice
    //     Choice selectedChoice = choices.get( input - 1);
    //     Random rand = new Random();

    //     if (rand.nextDouble(1) <= selectedChoice.getSuccessProbability()){
    //         currentMoney = currentMoney + selectedChoice.getMoneyReward();
    //         currentReputation = currentReputation+ selectedChoice.getReputationReward();
    //         System.out.println("\n Success!"); 
    //         System.out.println("Current Money: $" + currentMoney);                 
    //         System.out.println("Current Reputation:" + currentReputation); 

    //     } else {
    //         System.out.println("\n Got caught! Again??? Lose $50 and 5 reputation point"); 
    //         currentMoney -= 50;
    //         currentReputation -= 5;
    //         System.out.println("Current Money: $" + currentMoney);                 
    //         System.out.println("Current Reputation:" + currentReputation); 
    //     }
    // }

        // Handle simple quests
    public static int[] completeSimpleQuest(Scanner scanner, double hourlyWage, int currentMoney) {
        System.out.print("\n How many hours do you want to work? ");
        double hoursWorked = validateInput(scanner, 1, 24); // Allow up to 24 hours
        double earnings = hoursWorked * hourlyWage;
        System.out.println("\n You worked for " + hoursWorked + " hours and earned $" + earnings + ".");
        System.out.println("Side quest completed successfully!");

        currentMoney += earnings;
        System.out.println("Current Money:" + currentMoney);

        return new int[]{currentMoney, 0}; // Return updated money and reputation change
    }

    // Handle complex quests
    public static int[] completeComplexQuest(Scanner scanner, ArrayList<Choice> choices, int currentMoney, int currentReputation) {
        System.out.println("\n Your Choices:");
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
            System.out.println("\n Success!");
            System.out.println("Current Money: $" + currentMoney);
            System.out.println("Current Reputation: " + currentReputation);

            return new int[]{currentMoney, currentReputation};
        } else {
            System.out.println("\n Got caught! Again??? Lose $50 and 5 reputation points");
            currentMoney -= 50;
            currentReputation -= 5;
            System.out.println("Current Money: $" + currentMoney);
            System.out.println("Current Reputation: " + currentReputation);

            return new int[]{currentMoney, currentReputation};
        }
    }

    private static int validateInput(Scanner scanner, int min, int max) {
        int input = 0;

        // Validate input
        while (true) {
            System.out.print("\n Enter your choice: ");
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

// public class SideQuest extends MissionPart {
//     private String description;
//     private double hourlyWage; // For simple quests
//     private boolean isSimpleQuest; // Flag to distinguish between simple and complex quests

//     // Constructor for complex quests
//     public SideQuest(String name, String description) {
//         super(name);
//         this.description = description;
//         this.isSimpleQuest = false;
//     }

//     // Constructor for simple quests
//     public SideQuest(String name, String description, double hourlyWage) {
//         super(name);
//         this.description = description;
//         this.hourlyWage = hourlyWage;
//         this.isSimpleQuest = true;
//     }

//     @Override
//     public void displayPart() {
//         System.out.println("\n--- Side Quest: " + this.getName() + " ---");
//         System.out.println("\n Description: " + this.description);
//         if (isSimpleQuest) {
//             System.out.println("You earn $" + this.hourlyWage + " per hour worked.");
//         } else {
//             System.out.println("\n Your Choices:");
//             ArrayList<Choice> choices = this.getChoices();
//             for (int i = 0; i < choices.size(); i++) {
//                 Choice co = choices.get(i);
//                 System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
//             }
//         }
//     }

//     public String getDescription() {return this.description;}

//     public void completeSideQuest(Scanner scanner) {
//         if (isSimpleQuest) {
//             handleSimpleQuest(scanner); // Handle simple quest
//         } else {
//             handleComplexQuest(scanner); // Handle complex quest
//         }
//     }

//     // Handle simple quests
//     private void handleSimpleQuest(Scanner scanner) {
//         this.displayPart();
//         System.out.print("\n How many hours do you want to work? ");
//         double hoursWorked = validateInput(scanner, 1, 24); // Allow up to 24 hours
//         double earnings = hoursWorked * this.hourlyWage;
//         System.out.println("\n You worked for " + hoursWorked + " hours and earned $" + earnings + ".");
//         System.out.println("Side quest completed successfully!");
//     }

//     // Handle complex quests
//     private void handleComplexQuest(Scanner scanner) {
//         this.displayPart(); // Display the options to the user

//         // Validate user input
//         int input = validateInput(scanner, 1, this.getChoices().size());

//         // Execute the validated choice
//         boolean success = this.executeChoice(input);
//         if (success) {
//             System.out.println("Side quest completed successfully!");
//         } else {
//             System.out.println("Side quest failed!");
//         }
//     }
// }
