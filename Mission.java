import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Choice{
    private String name;
    private int reputation; // The reputation reward. 
    private int money; // The money reward. Can be negative
    private double probability; // Probability of success between 0.0 and 1.0
    private String description;

    public Choice(String name, String description, int money, int reputation, double probability){
        this.name = name;
        this.description = description;
        this.money = money;
        this.reputation = reputation;
        this.probability = probability;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return this.description; }
    public int getReputation() { return this.reputation; }
    public int getMoney() { return this.money; }
    public double getProbability() { return this.probability; }

    // Setters
    public void setReputation(int newReputation) {this.reputation = newReputation; }
    public void setMoney(int newMoney) {this.money = newMoney; }

}

class MissionPart{
    private String name;
    private String instruction;
    private ArrayList<Choice> choices;

    public MissionPart(String name){
        this.choices = new ArrayList<>();
    }

    // Getters
    public String getName() { return this.name; }
    public String getInstruction() { return this.instruction; }


    public void addChoice(String name, String Description, int money, int reputation, double probability){
        this.choices.add(new Choice(name, Description, money, reputation, probability));
    }

    public void displayPart() {
        System.out.println("\n--- Mission Part ---");
        System.out.println(this.getInstruction());
        System.out.println("Your Choices:");

        // Loop through the list of choices and show the options accordingly. The 1st option corresponds with 1
        for (int i = 0; i < choices.size(); i++) {
            Choice co = choices.get(i);
            System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
        }
    }

    public boolean executeChoice(int choiceIndex){
        Random random = new Random();
        // Get the selected choice object from the list of choices
        Choice selectedChoice = this.choices.get(choiceIndex - 1); // Minus 1 because the choiceIndex starts with 1

        // Incorporate the probability
        if (random.nextDouble(1) < selectedChoice.getProbability()) {
            System.out.println("You avoided being caught by the police. Mission Part accomplished");
            return true;
        } else {
            System.out.println("You are caught by the police, lose 10 reputation points and $200 in bail.");
            System.out.println("Mission Part" + this.name + " failed!");
            selectedChoice.setMoney(-200);
            selectedChoice.setReputation(-10);
            return false;
        }

    }
}

public class Mission{
    private String name;
    private ArrayList<MissionPart> parts;
    private int min_reputation;
    private int min_money;
    private int current_part;

    // Constructor
    public Mission(String name, int min_money, int min_reputation){
        this.name = name;
        this.min_money = min_money;
        this.min_reputation = min_reputation;
        this.parts = new ArrayList<MissionPart>();
        this.current_part = 0; // Initialize to the first part of the lists of missions
    }

    // Unlock the mission
    public boolean unlockMission (int current_reputation, int current_money) {
        if (current_reputation < this.min_reputation){
            System.out.println("Vinnie doesn't trust you with this mission yet. Consider side quests to increase your reputation and unlock this mission");
            return false;
        } else if (current_money < this.min_reputation){
            System.out.println("You need a minimum of $200 to unlock this mission. Consider doing side quests to unlock this mission.");
            return false;
        } else {
            return true;
        }
    }

    // Add Mission Part
    public void addMissionPart (MissionPart missionPart){
        parts.add(missionPart);
    }

    // Getters
    public String getName(){return this.name;}
    public int getMinReputaton(){return this.min_reputation;}
    public int getMinMoney(){return this.min_money;}

    // Compile
    public void completeMission(int current_money, int current_reputation){

        // If the user meet the requirement to unlock
        Scanner scanner = new Scanner(System.in);
        if (unlockMission(current_money, current_money)){
            // Loop through each mission part

            while(this.current_part <= this.parts.size()){
                // Create the current mission part
                MissionPart currentMissionPart = this.parts.get(this.current_part);
                // Display the choices for the users
                currentMissionPart.displayPart();
                // Get the user choices
                int input = scanner.nextInt();

                // Execute the choice and end the mission part if the user got caught
                if (currentMissionPart.executeChoice(input) == false) {
                    break;
                }

                this.current_part = this.current_part + 1;

            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Mission mission1 = new Mission("The Car Heist",200,0);

        MissionPart part1 = new MissionPart("Steal a luxury car for Vinnie. Choose your approach");
        part1.addChoice("Steal the cheaper car", 
            "Lower rewards from Vinnie but with 0% probability of being caught by the guard.", 100, 10, 1.0); // 100% chance
        part1.addChoice("Steal the luxury car", 
            "More rewards from Vinnie but with 30% probability being caught by the guard.", 300, 15, 0.7); // 70% chance of success
        mission1.addMissionPart(part1);

        MissionPart part2 = new MissionPart("Great! Now your dream car is in your hands. You still have one hour before the deadline. Would you");
        part2.addChoice("Return the car to Vinnie", 
            "Lower risk of being caught by the police but no fun ... ",100, 10, 0.9); // 70% chance
        part2.addChoice("Joyride and race!", 
            "Higher risk but more fun!", 100, 10, 0.5); // Always happens
        mission1.addMissionPart(part2);

        mission1.completeMission(500, 0);
    }
}



    /*
    ## Mission 1: The Car Heist
Objective: Prove yourself to Vinnie by stealing a high-end car from a wealthy neighborhood.
Description: Vinnie needs a luxury car for a deal and sends you to steal one from a nightclub parking lot. However, the vehicle is parked near security guards, requiring you to approach quietly and hotwire it.

### Key Choices:
- Return The Car: Return the car indirectly. Reputation + 10 + Money +$100
- Joyride Option: 
(Random) Risk of being chased by the police:
    Caught + paid bail: (random probability): 
    Money: -$100
    Reputation: -10
Avoid the police + return the car
    Reputation + 10
    Money +$100
(Random) Risk of being seen by a rival gang: 
    Outrun them + return the car
        Reputation + 10
        Money +$100
    Caught + damaged:
        Money: -$200
        Reputation: -10

    */ 


