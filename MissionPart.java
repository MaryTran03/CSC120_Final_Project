import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MissionPart {
    private String name;
    private ArrayList<Choice> Choices;

    public MissionPart(String name){
        this.name = name;
        this.Choices = new ArrayList<>();
    }

    // Getters
    public String getName() { return this.name; }
    public ArrayList<Choice> getChoices() { return this.Choices; }


    public void addChoice(String name, String Description, int money, int reputation, double probability){
        this.Choices.add(new Choice(name, Description, money, reputation, probability));
    }

    public void displayPart() {
        System.out.println("\n--- Mission Part: " + this.name + " ---");
        System.out.println("Your Choices:");

        // Loop through the list of choices and show the options accordingly. The 1st option corresponds with 1
        for (int i = 0; i < Choices.size(); i++) {
            Choice co = Choices.get(i);
            System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
        }
    }

    // Return true if game continues, false if caught

    public boolean executeChoice(int choiceIndex){
        Random random = new Random();
        // Get the selected choice object from the list of choices
        Choice selectedChoice = this.Choices.get(choiceIndex - 1); // Minus 1 because the choiceIndex starts with 1

        // Incorporate the probability
        if (random.nextDouble(1) < selectedChoice.getProbability()) {
            System.out.println("Success! You earnt $" + selectedChoice.getMoney() + " and " + selectedChoice.getReputation() + " reputation points");
            return true;
        } else {
            System.out.println("You are caught by the police, lose 10 reputation points and $200 in bail.");
            selectedChoice.setMoney(-200);
            selectedChoice.setReputation(-10);
            return false;
        }
    }

    public int validateInput(Scanner scanner, int min, int max) {
        int input = 0;

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
                System.out.println("Invalid choice. Please select a number between " + min + " and " + max + ".");
            }
        }

        return input; // Return validated input
    }

}



