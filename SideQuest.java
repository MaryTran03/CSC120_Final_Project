import java.util.ArrayList;
import java.util.Scanner;

public class SideQuest extends MissionPart {
    private String name;

    public SideQuest(String name) {
        super(name);
    }

    @Override
    public void displayPart(){
        System.out.println("\n--- Side Quest: " + this.getName() + " ---");
        System.out.println("Your Choices:");

        // Loop through the list of choices and show the options accordingly. The 1st option corresponds with 1

        ArrayList<Choice> Choices = this.getChoices();
        for (int i = 0; i < Choices.size(); i++) {
            Choice co = Choices.get(i);
            System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
        }
    }

    // New Method

    public void completeSideQuest(Scanner scanner) {
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

