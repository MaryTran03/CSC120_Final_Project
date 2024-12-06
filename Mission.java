import java.util.ArrayList;
import java.util.Scanner;

public class Mission {
    private ArrayList<MissionPart> parts;
    private int min_reputation;
    private int min_money;
    private String name;
    private String description;
    private int current_part;

    // Constructor
    public Mission(String name, int min_money, int min_reputation, String description){
        this.name = name;
        this.description = description;
        this.min_money = min_money;
        this.min_reputation = min_reputation;
        this.parts = new ArrayList<MissionPart>();
        this.current_part = 0; // Initialize to the first part of the lists of missions
    }

    // Add Mission Part
    public void addMissionPart (MissionPart missionPart){
        parts.add(missionPart);
    }

    // Getters
    public String getName(){return this.name;}
    public int getMinReputaton(){return this.min_reputation;}
    public int getMinMoney(){return this.min_money;}

    public void displayDetails(){
        System.out.println("\n--- Mission " + this.name + " ---");
        System.out.println("\n" + this.description);
    }

    public void completeMission(int currentMoney, int currentReputation, Scanner scanner) {
        this.displayDetails(); // Display mission details
    
        // Check if the user meets the requirements
        if (currentMoney >= this.min_money && currentReputation >= this.min_reputation) {
            // Loop through each mission part
            while (this.current_part < this.parts.size()) {
                MissionPart currentMissionPart = this.parts.get(this.current_part); // Get the current mission part
                currentMissionPart.displayPart(); // Display the choices to the user
    
                // Validate user input
                int input = currentMissionPart.validateInput(scanner, 1, currentMissionPart.getChoices().size());
    
                // Execute the choice and check if the mission part was successful
                boolean success = currentMissionPart.executeChoice(input);
                if (!success) {
                    System.out.println("Mission failed at part: " + currentMissionPart.getName());
                    break; // Exit the loop if the user is caught
                }
    
                // Move to the next mission part
                this.current_part++;
            }
    
            // Check if the mission was completed
            if (this.current_part == this.parts.size()) {
                System.out.println("Congratulations! You completed the mission: " + this.name);
            }
        } else {
            System.out.println("You are not eligible for this mission. Requirements: $" + this.min_money 
                               + " and " + this.min_reputation + " reputation points.");
        }
    }
    
}
