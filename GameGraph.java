import java.util.*;

class GameGraph {
    private ArrayList<Player> players;
    private Map<Node, List<Choice>> graph;
    private ArrayList<SideQuest> sideQuests;
    private ArrayList<Node> orderNodes;

    public GameGraph() {
        graph = new HashMap<>();
        this.sideQuests = new ArrayList<SideQuest>();
        this.orderNodes = new ArrayList<Node>();
    }

    public void addSideQuest(SideQuest sideQuest) { this.sideQuests.add(sideQuest);}
    public void addNode(Node node) { 
        graph.putIfAbsent(node, new ArrayList<>());
        orderNodes.add(node);}

    public void addEdge(Node node1, Choice choice) {
        if (graph.containsKey(node1)) {
            graph.get(node1).add(choice);}
    }

    public List<Choice> getChoices(Node node) { return graph.getOrDefault(node, new ArrayList<>());    }
    public ArrayList<SideQuest> getSideQuests() { return this.sideQuests;    }
    public ArrayList<Node> getOrderNodes() { return this.orderNodes;    }
    public ArrayList<Player> getPlayers() { return this.players;    }


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

    private void completeSideQuest(Scanner scanner, Player player) {
        displaySideQuests(sideQuests);
        int userInput = getUserInput(scanner, 1, getSideQuests().size());
        SideQuest selectedSideQuest = getSideQuests().get(userInput - 1); // Indexing starts at 0
    
        System.out.println("\n***************************");
        System.out.println("You chose to do Side Quest " + selectedSideQuest.getName());

        if (selectedSideQuest.getIsSimpleQuest()) {
            SideQuest.completeSimpleQuest(scanner, selectedSideQuest.getHourlyWage(), player);
        } else {
            SideQuest.completeComplexQuest(scanner, selectedSideQuest.getChoices(), player);
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
        return rand.nextDouble(1) <= probability; 
    }

    private boolean canAccessNode(Node node, Player player) { 
        return player.getCurrentMoney() >= node.getMinMoney() && player.getCurrentReputation() >= node.getMinReputation(); 
    }

    /*
     * Return the players currentMoney, currentReputation, currentNodeIndex
     */
    public void traverse(Scanner scanner, Player player){
        
        //double currentMoney = player.getCurrentMoney();
        //int currentReputation = player.getCurrentReputation();

        ArrayList<Node> orderNodes = this.getOrderNodes();
        Node currentNode = orderNodes.get(0); // Set the default currentNode to the first of the startNode aka the first index

        while(true){
            System.out.println("\n***************************");
            System.out.println("Current Mission: " + currentNode.getName()); 
            List<Choice> choices = getChoices(currentNode); 

            // Break if end game
            if (choices.isEmpty()) { 
                System.out.println("CONGRATS! YOU COMPLETED THE GAME"); 
                break; 
            }
            
            // Display and get the choices
            displayChoices(choices);
            int userInput = getUserInput(scanner, 1, choices.size());
            Choice selectedChoice = choices.get(userInput - 1); // Minus 1 because indexing start at 1

            // Execute the choice with probability

            // If successful
            if (isSuccessful(selectedChoice.getSuccessProbability())) { 
                Node nextNode = selectedChoice.getNextNode(); 

                // Update the Money and Reputation Points
                player.setCurrentMoney(player.getCurrentMoney() + selectedChoice.getMoneyReward());
                player.setCurrentReputation(player.getCurrentReputation() + selectedChoice.getReputationReward());

                System.out.println("\n Success! Moving to the next part"); 
                System.out.println("Current Money: $" + player.getCurrentMoney());                 
                System.out.println("Current Reputation: " + player.getCurrentReputation()); 

                if (canAccessNode(nextNode, player)){
                    currentNode = nextNode; 
                    continue;
                } else {
                    while(true){
                        System.out.println("\n You have not earned enough money or reputation to advance to " + nextNode.getName()); 
                        System.out.println("Minimum requirements $" + nextNode.getMinMoney() + ", Reputation: " + nextNode.getMinReputation());
                        System.out.println("You need to complete side quests to unlock the next misson");     
                        
                        // Keep asking users to Side Quest unless they want to resume their misisons (and if they are eligible to)
                        completeSideQuest(scanner, player); 

                        // After complete SideQuest check their current status. If they are eligible ask if they want to continue to do SideQuests
                        if (canAccessNode(nextNode, player)){
                            System.out.println("\n Do you want to repeat a side quest or advance to the next mission ?");
                            System.out.println("1: Keep doing Side Quests to earn more money and reputation");
                            System.out.println("2: Proceed at the next mission " + nextNode.getName());

                            int input = getUserInput(scanner, 1, 2);
                            if (input == 2) { // Choosing 2 means to proceed with the next mission. 
                                currentNode = nextNode;
                                break;
                        }
                    }
                }   continue;
            }
        } 

            // If not successful
            else { 
                System.out.println("\n You are caught and beaten! Lose 10 reputation points and pay $100 in bail or damage ." + currentNode.getName() + " mission failed!"); 
                // Update the Money and Reputation Points
                player.setCurrentMoney(player.getCurrentMoney() - 100);
                player.setCurrentReputation(player.getCurrentReputation() - 10);

                System.out.println("Current Money: " + player.getCurrentMoney());                 
                System.out.println("Current Reputation: " + player.getCurrentReputation()); 

                //Update the node;
                currentNode = updateNode(currentNode);
                System.out.println("\n You have to restart at " + currentNode.getName());

                while(true){
                    if (canAccessNode(currentNode, player)){
                        System.out.println("\n Luckily, you earned enough money and reputation to resume at " + currentNode.getName());
                        System.out.println("There is no need to do Side Quest. Mission resumed ... ");
                        break;
                    } else {
                        // Create a loop to ask users to work on SideQuest to earn enough money        
                        while(true){
                            System.out.println("\n You have not earned enough money and reputation to resume at " + currentNode.getName()); 
                            System.out.println("Minimum requirements $" + currentNode.getMinMoney() + ", Reputation: " + currentNode.getMinReputation());
                            System.out.println("You need to complete side quests to increase your money and reputation.");  
                            System.out.println("\n***************************");   
                            
                            // Keep asking users to Side Quest unless they want to resume their misisons (and if they are eligible to)
                            completeSideQuest(scanner, player); 
                            
                            // After complete SideQuest check their current status. If they are eligible ask if they want to continue to do SideQuests
                            if (canAccessNode(currentNode, player)){
                                System.out.println("\n Do you want to repeat a side quest or try to advance to resume the mission ?");
                                System.out.println("1: Keep doing Side Quests to earn more money and reputation");
                                System.out.println("2: Resume the mission " + currentNode.getName());
                                int input = getUserInput(scanner, 1, 2);
                                if (input == 2) { // Choosing 2 means to resume the mission. 
                                    break;
                            }
                        }
                    } 
                    continue;
                }
            }
        
            // Conditions to end the game
            if (currentNode == orderNodes.get(orderNodes.size() - 1)){
                System.err.println("CONGRATS! YOU COMPLETED THE GAME");
                break;
            }
        }
    }
}
        private Node updateNode(Node currentNode){
            int currentIndex = this.getOrderNodes().indexOf(currentNode);
            // If the currentCode is not divided by 2, reset the currentNode to currentNode - 1
            if (this.getOrderNodes().indexOf(currentNode) % 2 == 1){
                currentIndex -= 1;
                currentNode = this.getOrderNodes(). get(currentIndex);
            }
            return currentNode;
        }
}
