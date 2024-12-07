import java.util.*;

class GameGraph {
    private Map<Node, List<Choice>> graph;
    private ArrayList<SideQuest> sideQuests;

    public GameGraph() {
        graph = new HashMap<>();
        this.sideQuests = new ArrayList<SideQuest>();
    }

    public void addSideQuest(SideQuest sideQuest) { this.sideQuests.add(sideQuest);}
    public void addNode(Node node) { graph.putIfAbsent(node, new ArrayList<>());}
    public void addEdge(Node node1, Choice choice) {
        if (graph.containsKey(node1)) {
            graph.get(node1).add(choice);}
    }

    public List<Choice> getChoices(Node node) { return graph.getOrDefault(node, new ArrayList<>());    }
    public ArrayList<SideQuest> getSideQuests() { return this.sideQuests;    }

    public void displayChoices(List<Choice> choices) {
        System.out.println("Your Choices:");

        // Loop through the list of choices and show the options accordingly. The 1st option corresponds with 1
        for (int i = 0; i < choices.size(); i++) {
            Choice co = choices.get(i);
            System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
            }
    }

    public void displaySideQuests(List<SideQuest> sideQuests) {
        System.out.println("\n Your Choices for SideQuests:");
        // Loop through the list of choices and show the options accordingly. The 1st option corresponds with 1
        for (int a = 0; a < sideQuests.size(); a++) {
            SideQuest sq = sideQuests.get(a);
            System.out.println((a + 1) + ". " + sq.getName() + " - " + sq.getDescription());
            }
    }

    private void completeSideQuest(Scanner scanner, int currentMoney, int currentReputation){
        displaySideQuests(sideQuests);
        int userInput = getUserInput(scanner, 1, getSideQuests().size());
        SideQuest selectedSideQuest = getSideQuests().get(userInput - 1); // Indexing starts at 0
        if (selectedSideQuest.getIsSimpleQuest()){
            SideQuest.completeSimpleQuest(scanner, selectedSideQuest.getHourlyWage(),currentMoney);
        } else {
            SideQuest.completeComplexQuest(scanner, selectedSideQuest.getChoices(), currentMoney, currentReputation);
        }
    }

    private int getUserInput(Scanner scanner, int min, int max) {
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

    private boolean isSuccessful(double probability) { 
        Random rand = new Random(); 
        return rand.nextDouble(1) < probability; 
    }

    private boolean canAccessNode(Node node, int currentMoney, int currentReputation) { 
        return currentMoney >= node.getMinMoney() && currentReputation >= node.getMinReputation(); 
    }

    public void traverse(Scanner scanner, int currentMoney, int currentReputation, Node startNode, Node endNode){
        Node currentNode = startNode;

        while(canAccessNode(currentNode, currentMoney, currentReputation)){
            System.out.println("\n Current Part: " + currentNode.getName()); 
            List<Choice> choices = getChoices(currentNode); 

            // Break if end game
            if (choices.isEmpty()) { 
                System.out.println("No more missions available. End of the path."); 
                break; 
            }
            
            // Display and get the choices
            displayChoices(choices);
            int userInput = getUserInput(scanner, 1, choices.size());
            Choice selectedChoice = choices.get(userInput - 1); // Minus 1 because indexing start at 1

            // Execute the choice with probability
            if (isSuccessful(selectedChoice.getSuccessProbability())) { 
                currentNode = selectedChoice.getNextNode(); 

                // Update the Money and Reputation Points
                currentMoney += selectedChoice.getMoneyReward();
                currentReputation += selectedChoice.getReputationReward();

                System.out.println("\n Success! Moving to the next part"); 
                System.out.println("Current Money:" + currentMoney);                 
                System.out.println("Current Reputation:" + currentReputation); 
            } 

            else { 
                System.out.println("\n You are caught and beaten! Lose 10 reputation points and pay $100 in bail or damage ." + currentNode.getName() + " mission failed!"); 
                // Update the Money and Reputation Points
                currentMoney -= 100;
                currentReputation -= 10;

                System.out.println("Current Money: " + currentMoney);                 
                System.out.println("Current Reputation: " + currentReputation); 
            }
            
            Node nextNode = selectedChoice.getNextNode();

            if(!canAccessNode(nextNode, currentMoney, currentReputation)){
                // SideQuest
                System.out.println("\n Cannot access " + nextNode.getName() + ". Minimum requirements not met. Money: " + nextNode.getMinMoney() + ", Reputation: " + nextNode.getMinReputation()); // Direct user to side quests 
                System.out.println("You need to complete side quests to increase your money and reputation."); 
                boolean repeat = true; 
                
                while (repeat) { 
                    completeSideQuest(scanner, currentMoney, currentReputation); 
                    System.out.println("\n Do you want to repeat a side quest (1) or try to advance to the remaining mission (2)?");
                    int nextAction = getUserInput(scanner, 1, 2);

                    if (nextAction == 2) { repeat = false; } 
                } 
                    continue; 
            }

                //currentNode = selectedChoice.getNextNode(); 
        

            if (currentNode == endNode){
                System.err.println("CONGRATS! YOU COMPLETED THE GAME");
                break;
            }
        }
    }
    
    // public boolean executeChoice(int choiceIndex){
    //     Random random = new Random();
    //     // Get the selected choice object from the list of choices
    //     Choice selectedChoice = this.Choices.get(choiceIndex - 1); // Minus 1 because the choiceIndex starts with 1

    //     // Incorporate the probability
    //     if (random.nextDouble(1) < selectedChoice.getProbability()) {
    //         System.out.println("Success! You earnt $" + selectedChoice.getMoney() + " and " + selectedChoice.getReputation() + " reputation points");
    //         return true;
    //     } else {
    //         System.out.println("You are caught by the police, lose 10 reputation points and $200 in bail.");
    //         selectedChoice.setMoney(-200);
    //         selectedChoice.setReputation(-10);
    //         return false;
    //     }
    // }


    public static void main(String[] args) {
        GameGraph game = new GameGraph();

        // Create the nodes
        Node mission1_1 = new Node("Choose the car to steal","mission1_1" , 
        "Car Heist Part 1", 200, 0); 
        Node mission1_2 = new Node("Choose the escape route","mission1_2" , 
        "Car Heist Part 2", 200, 0); 
        Node mission2_1 = new Node("Choose the stash","mission2_1" , 
        "The Warehouse Raid Part 1", 200, 10); 
        Node mission2_2 = new Node("Choose the escape route","mission2_2" , 
        "The Warehouse Raid Part 2", 200, 10); 
        Node mission3_1 = new Node("Escape the police","mission3_1" , 
        "The Final Heist Part 1", 200, 30); 
        Node mission3_2 = new Node("Split the loot","mission3_2" , 
        "The final Heist Part 2", 200, 30); 
        Node endNode = new Node("Congrats! You completed all missions!", "end", 
        "The End", 0, 0);

        // Adding mission parts as nodes
        game.addNode(mission1_1);
        game.addNode(mission1_2);
        game.addNode(mission2_1);
        game.addNode(mission2_2);
        game.addNode(mission3_1);
        game.addNode(mission3_2);


        // Add choices
        Choice choice1_1_cheap = new Choice("Steal the cheaper car", 
            "Lower rewards from Vinnie but with 0% probability of being caught by the guard.", 
            100, 10, 1, mission1_2); // 100% chance
        Choice choice1_1_luxury = new Choice("Steal the luxury car", 
            "More rewards from Vinnie but with 30% probability being caught by the guard.", 
            300, 15, 0.6, mission1_2); // 70% chance of success
        Choice choice1_2_return = new Choice("Return the car to Vinnie", 
            "Lower risk of being caught by the police but no fun ... ",
            100, 10, 0.9, mission2_1); // 70% chance
        Choice choice1_2_joyride = new Choice("Joyride and race!", 
            "Higher risk of being caught by the police but more fun!", 
            100, 10, 0.5, mission2_1); // Always happens


        Choice choice2_1_quick = new Choice("Quick Grab", 
            "Take one item and leave quickly to avoid detection. Low risk.", 
            100, 5, 1, mission2_2); // 0% chance of success. The alarm is triggered regardless
        Choice choice2_1_full = new Choice("Full Stash", 
            "Take as many items as possible for a higher reward. Higher risk of triggering the alarm.", 
            300, 10, 0.75, mission2_2); // 0% chance of success. The alarm is triggered regardless
        Choice choice2_2_run = new Choice("Run and drop some loot", 
            "Escape but lose some money.", 
            -100, -5, 1.0, mission3_1); // Always succeeds but penalizes
        Choice choice2_2_fight = new Choice("Fight the guards", 
            "Defeat the guards to escape with all the loot. High risk.", 
            0, 0, 0.5, mission3_1); // 50% chance of success

        Choice choice3_1_evade = new Choice ("Evade the Police", 
            "Use your driving skills to outmaneuver police cars and escape. Risk: Lose part of the loot if caught.", 
            0, 0, 0.7, mission3_2); // 70% chance of success
        Choice choice3_1_fight = new Choice("Fight Back", 
            "Use a weapon to disable pursuing police cars. Outcome: Slower but higher chance of escape.", 
            0, -5, 0.3, mission3_2); // 30% chance of success but reputation -5
        Choice choice3_2_loyal = new Choice("Stay Loyal", 
            "Share the loot evenly with the crew. Gain their trust and ensure future jobs.", 
            100, 10, 1.0, endNode); // 100% chance of success, reputation +10
        Choice choice3_2_betray = new Choice("Betray the Crew", 
            "Escape with the entire loot but anger the crew. Risk: Lose reputation and gain money.", 
            500, -30, 1.0, endNode); // 100% success, reputation -20, money +500


        // Adding choices as edges with probabilities
        game.addEdge(mission1_1, choice1_1_cheap);
        game.addEdge(mission1_1, choice1_1_luxury);
        game.addEdge(mission1_2, choice1_2_joyride);
        game.addEdge(mission1_2, choice1_2_return);

        game.addEdge(mission2_1, choice2_1_full);
        game.addEdge(mission2_1, choice2_1_quick);
        game.addEdge(mission2_2, choice2_2_run);
        game.addEdge(mission2_2, choice2_2_fight);

        game.addEdge(mission3_1, choice3_1_evade);
        game.addEdge(mission3_1, choice3_1_fight);
        game.addEdge(mission3_2, choice3_2_loyal);
        game.addEdge(mission3_2, choice3_2_betray);

        // Add the SideQuest
        SideQuest badDebt = new SideQuest("Bad Debt Collection", "Vinnie isn’t pleased with your recent actions and wants you to prove yourself useful. He assigns you to collect protection fees from two local businesses in Rivertown. Approach them to demand payment, but handle it carefully as being too aggressive may backfire.");
        badDebt.addChoice("Polite", "Approach the business owner with a firm but nonthreatening attitude. This will lower your chance of increasing your reputation and reduce the chance of them resisting or calling the police."
                        , 100, 5, 0.9);
        badDebt.addChoice("Intimidating", "Use intimidation to collect the payment faster and increase your reputation more. However, this increases the risk of the owner calling the police, which could cost you more money in bail if caught."
                        , 100, 5, 0.7);
        
        // Add Side Quest 2: Burger King
        SideQuest burgerKing = new SideQuest("Burger King", "Work at a local shop to save up some money. However, this doesn't increase your reputation points with Vinnie", 14.75);
        game.addSideQuest(burgerKing);
        game.addSideQuest(badDebt);

    // Traverse the graph
        Scanner scanner = new Scanner(System.in);        
        game.traverse(scanner, 200, 0, mission1_1, endNode); // Start with $200 and 20 reputation
        scanner.close();
    }
}
