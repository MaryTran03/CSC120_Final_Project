import java.util.*;
import java.io.*;
/**
 * Game Graph class by Trang & Ada
 */
class GameGraph {
    private ArrayList<Player> players;
    private Map<Node, List<Choice>> graph;
    private ArrayList<SideQuest> sideQuests;
    private ArrayList<Node> orderNodes;
    private Scanner scanner;

    /*
     * Initialize the game
     */
    public GameGraph() {
        this.scanner = new Scanner(System.in);
        graph = new HashMap<>();
        this.sideQuests = new ArrayList<SideQuest>();
        this.orderNodes = new ArrayList<Node>();
        this.players = new ArrayList<Player>();
    }

    /*
     * Add SideQuest to the list of Side Quests
     */
    public void addSideQuest(SideQuest sideQuest) { this.sideQuests.add(sideQuest);}

    
    /*
     * Add Node to the map
     */
    public void addNode(Node node) { 
        graph.putIfAbsent(node, new ArrayList<>());
        orderNodes.add(node);}

    
    /*
     * Add edges (List<Choice>)
     */    
    public void addEdge(Node node1, Choice choice) {
        if (graph.containsKey(node1)) {
            graph.get(node1).add(choice);}
    }
    /**
     * Accessor for choices
     * @param node
     * @return Choices in List
     */
    public List<Choice> getChoices(Node node) { return graph.getOrDefault(node, new ArrayList<>());    }
   /**
    * Scanner accessor
    * @return scanner
    */
    public Scanner getScanner() { return this.scanner;    }
    /**
     * Arraylist of side quests
     * @return side quest arraylist
     */
    public ArrayList<SideQuest> getSideQuests() { return this.sideQuests;    }
    /**
     * Accessor for nodes
     * @return arraylist of nodes
     */
    public ArrayList<Node> getOrderNodes() { return this.orderNodes;    }
    /**
     * Accessor for players
     * @return arraylist of players
     */
    public ArrayList<Player> getPlayers() { return this.players;    }

    /**
     * Game Description
     */
    public void describeGame() {
        System.out.println("\n***************************");
        System.out.println("\nYou've just moved to a new neighborhood and, in a twist of fate, found yourself entangled with a notorious criminal gang.");
        System.out.println("\nUnder the command of the ruthless yet cunning Vinnie, you're tasked with completing three high-stakes missions.");
        System.out.println("But beware, each mission demands a specific amount of money and reputation points to unlock. Don't worry, you can always tackle side quests to boost your stats.");
        System.out.println("\nRemember, you can pause the game at any moment to save your progress and resume later");
        
        System.out.println("\nWin the game: Finish all 3 missions.\n");
    
        System.out.println("Mission 1: The Car Heist");
        System.out.println("Objective: Prove yourself to Vinnie by stealing a high-end car.");
        System.out.println("Requirements: $200, 0 reputation points\n");
    
        System.out.println("Mission 2: The Warehouse Raid");
        System.out.println("Objective: Infiltrate the Iron Hounds’ warehouse and steal valuable goods.");
        System.out.println("Requirements: $200, 10 reputation points\n");
    
        System.out.println("Mission 3: The Final Heist");
        System.out.println("Objective: Pull off a bank heist with Vinnie’s crew as the getaway driver.");
        System.out.println("Requirements: $200, 30 reputation points\n");
    
        System.out.println("Good luck, and may you succeed in completing all the missions! Press any key to continue");

        scanner.nextLine();
    }
    

    /**
     * End game method
     */
    public void endGame(){
        System.out.println("\nThank you for playing the game!");
        scanner.close();
    }
    /**
     * Method displays choices
     * @param choices
     */
    public void displayChoices(List<Choice> choices) {
        System.out.println("\nYour Choices:");

        // Loop through the list of choices and show the options accordingly. The 1st option corresponds with 1
        for (int i = 0; i < choices.size(); i++) {
            Choice co = choices.get(i);
            System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
            }
    }
    /**
     * Method allows player to show side quests
     * @param sideQuests
     */
    public void displaySideQuests(List<SideQuest> sideQuests) {
        System.out.println("\nYour Choices for SideQuests:");
        // Loop through the list of choices and show the options accordingly. The 1st option corresponds with 1
        for (int a = 0; a < sideQuests.size(); a++) {
            SideQuest sq = sideQuests.get(a);
            System.out.println((a + 1) + ". " + sq.getName() + " - " + sq.getDescription());
            }
    }
    /**
     * Method to allow player to complete side quests
     * @param scanner
     * @param player
     */
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
    /**
     * Method to allow numerical input of choices
     * @param scanner
     * @param min
     * @param max
     * @return int input
     */
    private int getUserInput(Scanner scanner, int min, int max) {
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
    /**
     * Checks if missions/side quests are successful based on probability of success
     * @param probability
     * @return success of mission
     */
    private boolean isSuccessful(double probability) { 
        Random rand = new Random(); 
        return rand.nextDouble(1) <= probability; 
    }

    /*
     * Return the player
     */
    /**
     * Method checks accessibility of Node
     * @param node
     * @param player
     * @return if Node is accessible
     */
    private boolean canAccessNode(Node node, Player player) { 
        return player.getCurrentMoney() >= node.getMinMoney() && player.getCurrentReputation() >= node.getMinReputation(); 
    }

    /*
     * Return the players currentMoney, currentReputation, currentNodeIndex
     */
    /**
     * Traverse allows player to play the game
     * @param player
     */
    public void traverse(Player player){
        
        ArrayList<Node> orderNodes = this.getOrderNodes();
        Node currentNode = player.getCurrentNode(); // default to StartNode to user
        boolean userPause = false;
        boolean isFirstIteration = true;

        while(!userPause){
            System.out.println("\n***************************");
            System.out.println("Current Mission: " + currentNode.getName()); 
            System.out.println(currentNode); 

            List<Choice> choices = getChoices(currentNode); 


            if (isFirstIteration) {
                isFirstIteration = false; // Skip the pause check in the first iteration
            } else {

                // Check if the player wants to pause the game
                System.out.println("\nEnter `PAUSE` to end the game early and save your progress. If not press any key to proceed.");
                scanner.nextLine();
                String input1 = scanner.nextLine();

                if (input1.equalsIgnoreCase("pause")) {
                    player.setCurrentNode(currentNode);
                    saveAndStoreProgress(player);

                    break; // Exit the loop
                }
            }
        
            // Break if end game
            if (choices.isEmpty()) { 
                System.out.println("\n***************************");   
                System.out.println("CONGRATS! YOU COMPLETED THE GAME"); 
                player.setCurrentNode(currentNode);
                saveAndStoreProgress(player);
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

                System.out.println("\nSuccess! Moving to the next part"); 
                System.out.println("Current Money: $" + player.getCurrentMoney());                 
                System.out.println("Current Reputation: " + player.getCurrentReputation()); 

                if (canAccessNode(nextNode, player)){
                    currentNode = nextNode; 
                    continue;
                } else {
                    while(!userPause){
                        System.out.println("\nYou have not earned enough money or reputation to advance to " + nextNode.getName()); 
                        System.out.println("Minimum requirements $" + nextNode.getMinMoney() + ", Reputation: " + nextNode.getMinReputation());
                        System.out.println("\nYou need to complete side quests to unlock the next misson");     

                        
                        // Check if the player wants to pause the game
                        System.out.println("Or enter `PAUSE` to end the game early and save your progress. If not, press any key to proceed.");
                        scanner.nextLine();
                        String input2= scanner.nextLine();
                        if (input2.equalsIgnoreCase("pause")){
                            player.setCurrentNode(currentNode);
                            userPause = true;
                            saveAndStoreProgress(player);

                            break;
                        }
                        
                        // Keep asking users to Side Quest unless they want to resume their misisons (and if they are eligible to)
                        System.out.println("\n***************************");
                        completeSideQuest(scanner, player); 

                        // After complete SideQuest check their current status. If they are eligible ask if they want to continue to do SideQuests
                        if (canAccessNode(nextNode, player)){
                            System.out.println("\nDo you want to repeat a side quest or advance to the next mission ?");
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
                System.out.println("\nYou are caught and beaten! Lose 10 reputation points and pay $100 in bail or damage ." + currentNode.getName() + " mission failed!"); 
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
                        System.out.println("\nLuckily, you earned enough money and reputation to resume at " + currentNode.getName());
                        System.out.println("There is no need to do Side Quest. Mission resumed ... ");
                        break;
                    } else {
                        // Create a loop to ask users to work on SideQuest to earn enough money        
                        while(true){
                            System.out.println("\nYou have not earned enough money and reputation to resume at " + currentNode.getName()); 
                            System.out.println("Minimum requirements $" + currentNode.getMinMoney() + ", Reputation: " + currentNode.getMinReputation());
                            System.out.println("\nYou need to complete side quests to increase your money and reputation.");  

                            // Check if the player wants to pause the game
                            System.out.println("Or enter `PAUSE` to end the game early and save your progress. If not, press any key to proceed.");
                            scanner.nextLine();
                            String input2= scanner.nextLine();
                            if (input2.equalsIgnoreCase("pause")){
                                player.setCurrentNode(currentNode);
                                userPause = true;
                                saveAndStoreProgress(player);
                                break;
                            }

                            System.out.println("\n***************************");   
                            
                            // Keep asking users to Side Quest unless they want to resume their misisons (and if they are eligible to)
                            completeSideQuest(scanner, player); 
                            
                            // After complete SideQuest check their current status. If they are eligible ask if they want to continue to do SideQuests
                            if (canAccessNode(currentNode, player)){
                                System.out.println("\nDo you want to repeat a side quest or try to advance to resume the mission ?");
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
    /**
     * Updates Node
     * @param currentNode
     * @return updated Node
     */  
    private Node updateNode(Node currentNode){
        int currentIndex = this.getOrderNodes().indexOf(currentNode);
        // If the currentCode is not divided by 2, reset the currentNode to currentNode - 1
        if (this.getOrderNodes().indexOf(currentNode) % 2 == 1){
            currentIndex -= 1;
            currentNode = this.getOrderNodes(). get(currentIndex);
        }
        return currentNode;
    }
        
    /**
     * Method to resume the game from saved progress.
     * @param playerName
     * @return Player
     */
    public Player resume(String playerName) {
        String userFileName = playerName + "_save.txt";
        File saveFile = new File(userFileName);
        
        if (saveFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(userFileName))) {
                System.out.println("\nReading saved file: " + userFileName);
        
                String nameLine = reader.readLine();
                String reputationLine = reader.readLine();
                String moneyLine = reader.readLine();
                String missionLine = reader.readLine();
        
                // Parse the player's attributes
                String name = nameLine.split(":")[1].trim();
                int reputation = Integer.parseInt(reputationLine.split(":")[1].trim());
                double money = Double.parseDouble(moneyLine.split(":")[1].trim());
                String nameCurrentNode = missionLine.replace("Last completed mission: ", "").trim();
        
                // Search for the Node with the matching name
                for (Node node : orderNodes) {
                    if (node.getName().equals(nameCurrentNode)) {
                        Node currentNode = node;
                        // Create the Player object
                        Player player = new Player(name, money, reputation, currentNode); 
                        System.out.println("Game resumed.");
       
                        return player;
                    }
                }
        
                // If no matching Node is found
                System.err.println("No matching node found for the last completed mission: " + nameCurrentNode);
                return null;
            } catch (IOException | NumberFormatException e) {
                System.err.println("Failed to resume the game: " + e.getMessage());
                return null; // Return null in case of an error
            }
        } else {
            // If no saved game is found
            System.out.println("No saved game found. Starting a new game.");
            return null;
        }
    }
    /**
     * Starts game for player
     * @return player
     */
    public Player startGame() {
        System.out.println("Choose 1 to start a new game or 2 to resume where you left");
        int response = getUserInput(scanner, 1, 2);

        //String response = scanner.nextLine().toUpperCase();
        if (response == 2) {
            System.out.println("\nType in your last username");
            scanner.nextLine();
            String username = scanner.nextLine();
    
            // Check in existing file if there is anything saved. If not create a new user
            Player player = resume(username);

            System.out.println("\nStarting Reputation: " + player.getCurrentReputation());
            System.out.println("Starting Money: $" + player.getCurrentMoney());
            System.out.println("Starting Mission: " + player.getCurrentNode().getName());
    
            System.out.println("\nPress any keys to continue");
            scanner.nextLine();
                
            return player;
    
        } else {
            // If that create new player using default values
            System.out.println("\nType in your new username");
            scanner.nextLine();
            String username = scanner.nextLine();
            Player player = new Player(username, orderNodes.get(0)); // Initializing the current Node to the start Node
            players.add(player);

            System.out.println("\nStarting Reputation: " + player.getCurrentReputation());
            System.out.println("Starting Money: $" + player.getCurrentMoney());
            System.out.println("Starting Mission: " + player.getCurrentNode().getName());
    
                    
            System.out.println("\nPress any keys to continue");
            scanner.nextLine();

            return player;
        }

    }
        /**
         * Method saves and stores progress
         * @param player
         */
    public void saveAndStoreProgress(Player player) {
            String userFileName = player.getName().replaceAll("[^a-zA-Z0-9_]","") + "_save.txt";
    
            // Create new file or overwrite existing files
            try (BufferedWriter out = new BufferedWriter(new FileWriter(userFileName))) {
                System.out.println("\n***************************");
                System.out.println("Saving game progress for " + player.getName() + "...");
                out.write("Player name:" + player.getName() + "\n");
                out.write("Reputation:" + player.getCurrentReputation() + "\n");
                out.write("Money:" + player.getCurrentMoney() + "\n");
                out.write("Last completed mission: " + player.getCurrentNode().getName() + "\n");

                // Printing out the player's progress
                System.out.println("Player Reputation: " + player.getCurrentReputation());
                System.out.println("Player Money: $" + player.getCurrentMoney());
                System.out.println("Last Completed Mission: " + player.getCurrentNode().getName());

                out.flush();
                System.out.println("\nGame progress saved successfully.");
        } catch (IOException e) {
                System.err.println("Failed to save the game: " + e.getMessage());
                e.printStackTrace();
        }
    }
}
