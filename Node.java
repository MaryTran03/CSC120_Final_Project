// import java.util.HashMap;
import java.util.Hashtable;
// import java.util.Map;
// import java.util.Random;
// import java.util.Scanner;

class Node {
    private Hashtable<String, String> node;
    private String name;
    private int minMoney;
    private int minReputation; 

    // Method to get the description by key 
    public String getDescription(String key) { return this.node.get(key); }

    // Constructor
    public Node(String description, String key, String name, int minMoney, int minReputation) {
        this.node = new Hashtable<>();
        this.node.put(key, description);
        this.name = name;
        this.minMoney = minMoney;
        this.minReputation = minReputation;
    }

    // Getters
    public String getName() { return this.name; }
    public int getMinMoney() { return this.minMoney; }
    public int getMinReputation() { return this.minReputation; }

    @Override
    public String toString(){
        return this.name + " : " + this.getDescription(node.keys().nextElement());
    }
}    

//     // Add a choice to this Node
//     public void addChoice(String choiceDescription, Choice choice) {
//         choices.put(choiceDescription, choice);
//     }

//     // Display available choices
//     public void displayChoices() {
//         System.out.println("\n" + description);
//         System.out.println("Available Choices:");
//         for (String choiceDescription : choices.keySet()) {
//             System.out.println("- " + choiceDescription);
//         }
//     }

//     // Receive and validate user input
//     public String getUserInput(Scanner scanner) {
//         String userInput;
//         while (true) {
//             System.out.print("Enter your choice: ");
//             userInput = scanner.nextLine();

//             if (choices.containsKey(userInput)) {
//                 break; // Valid input
//             } else {
//                 System.out.println("Invalid choice. Please try again.");
//             }
//         }
//         return userInput;
//     }

//     // Execute a choice and determine the outcome
//     public Node executeChoice(String choiceDescription) {
//         Random random = new Random();
//         Choice choice = choices.get(choiceDescription);

//         // Check probability
//         if (random.nextDouble() <= choice.getSuccessProbability()) {
//             System.out.println("Success! You earned $" + choice.getMoneyReward() + " and " + choice.getReputationReward() + " reputation.");
//             return choice.getNextNode(); // Progress to the next Node
//         } else {
//             System.out.println("Failure! You earned no rewards.");
//             return null; // End the mission
//         }
//     }
// }


// public class Node {
//     private String type; 
//     private String name;
//     private String description;
//     private int moneyReward;
//     private int reputationReward;
//     private int minMoney; // Minimum money required to unlock
//     private int minReputation; // Minimum reputation required to unlock
//     private Map<Node, Double> edges; // Adjacent nodes with probabilities

//     public Node(String name, String description, int moneyReward, int reputationReward, int minMoney, int minReputation) {
//         this.name = name;
//         this.description = description;
//         this.moneyReward = moneyReward;
//         this.reputationReward = reputationReward;
//         this.minMoney = minMoney;
//         this.minReputation = minReputation;
//         this.edges = new HashMap<>();
//     }

//     // Display the node
//     public String toString(){
//         return this.name + this.description + "\n --- Rewards: $" + this.moneyReward + " " + this.reputationReward + " reputation points.";

//     }

//     // Add an edge to the node
//     public void addEdge(Node node, double probability) {
//         edges.put(node, probability);
//     }

//         // Add an edge conditionally based on player's progress
//     public void addEdgeIfEligible(Node nextNode, double probability, int currentMoney, int currentReputation) {
//         if (currentMoney >= nextNode.minMoney && currentReputation >= nextNode.minReputation) {
//             edges.put(nextNode, probability);
//             System.out.println("Unlocked: " + nextNode.name);
//         } else {
//             System.out.println("You do not meet the requirements to unlock: " + nextNode.name + ". Try doing Side Quests to unlock the next Mission");
//         }
//     }

//     // Getters
//     public String getName() { return name; }
//     public String getDescription() { return description; }
//     public int getMoneyReward() { return moneyReward; }
//     public int getReputationReward() { return reputationReward; }
//     public Map<Node, Double> getEdges() { return edges; }
// }

