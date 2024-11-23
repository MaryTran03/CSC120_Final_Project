import java.util.ArrayList;
import java.util.Random;

public class MissionPart {
    private String name;
    private ArrayList<Choice> choices;

    public MissionPart(String name){
        this.name = name;
        this.choices = new ArrayList<>();
    }

    // Getters
    public String getName() { return this.name; }

    public void addChoice(String name, String Description, int money, int reputation, double probability){
        this.choices.add(new Choice(name, Description, money, reputation, probability));
    }

    public void displayPart() {
        System.out.println("\n--- Mission Part: " + this.name + " ---");
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
            System.out.println("Success! You earnt $" + selectedChoice.getMoney() + " and " + selectedChoice.getReputation());
            return true;
        } else {
            System.out.println("You are caught by the police, lose 10 reputation points and $200 in bail.");
            System.out.println("Mission Part: " + this.name + " failed!");
            selectedChoice.setMoney(-200);
            selectedChoice.setReputation(-10);
            return false;
        }
    }
}



