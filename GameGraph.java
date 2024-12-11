import java.util.*;
import java.io.*;

/**
 * GameGraph represents the core structure of the game, managing nodes (missions and side quests),
 * players, and their connections through edges (choices).
 */
class GameGraph {
    private ArrayList<Player> players;
    private Map<Node, List<Choice>> graph;
    private ArrayList<Node> sideQuests;
    private ArrayList<Node> orderNodes;
    private Scanner scanner;
    private ArrayList<Mission> missions;

    /**
     * Constructor to initialize the game graph with necessary data structures.
     */
    public GameGraph() {
        this.scanner = new Scanner(System.in);
        graph = new HashMap<>();
        this.sideQuests = new ArrayList<>();
        this.orderNodes = new ArrayList<>();
        this.players = new ArrayList<>();
        this.missions = new ArrayList<>();
    }

    /**
     * Adds a mission to the list of missions.
     *
     * @param mission the mission to be added
     */
    public void addMission(Mission mission) {
        this.missions.add(mission);
    }

    /**
     * Adds a side quest to the list of side quests.
     *
     * @param sideQuest the side quest to be added
     */
    public void addSideQuest(Node sideQuest) {
        this.sideQuests.add(sideQuest);
    }

    /**
     * Adds a node (representing a mission or side quest) to the graph.
     *
     * @param node the node to be added
     */
    public void addNode(Node node) {
        graph.putIfAbsent(node, new ArrayList<>());
        orderNodes.add(node);
    }

    /**
     * Adds an edge (choice) between a node and its possible outcomes.
     *
     * @param node1  the starting node
     * @param choice the choice leading to another node or outcome
     */
    public void addEdge(Node node1, Choice choice) {
        if (graph.containsKey(node1)) {
            graph.get(node1).add(choice);
        }
    }

    /**
     * Retrieves the choices associated with a given node.
     *
     * @param node the node whose choices are to be retrieved
     * @return a list of choices for the given node
     */
    public List<Choice> getChoices(Node node) {
        return graph.getOrDefault(node, new ArrayList<>());
    }

    /**
     * @return the scanner used for user input throughout the game
     */
    public Scanner getScanner() {
        return this.scanner;
    }

    /**
     * @return the list of all missions in the game
     */
    public ArrayList<Mission> getMissions() {
        return this.missions;
    }

    /**
     * @return the list of all side quests in the game
     */
    public ArrayList<Node> getSideQuests() {
        return this.sideQuests;
    }

    /**
     * @return the list of all nodes in the game in their order of addition
     */
    public ArrayList<Node> getOrderNodes() {
        return this.orderNodes;
    }

    /**
     * @return the list of all players in the game
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Describes the game to the player, providing an overview of the storyline and objectives.
     */
    public void describeGame() {
        System.out.println("\n***************************");
        System.out.println("\nYou've just moved to a new neighborhood and, in a twist of fate, found yourself entangled with a notorious criminal gang.");
        System.out.println("\nUnder the command of the ruthless yet cunning Vinnie, you're tasked with completing three high-stakes missions.");
        System.out.println("But beware, each mission demands a specific amount of money and reputation points to unlock. Don't worry, you can always tackle side quests to boost your stats.");
        System.out.println("\nRemember, you can pause the game at any moment to save your progress and resume later");
        System.out.println("\nWin the game: Finish all 3 missions.\n");
        System.out.println("Good luck, and may you succeed in completing all the missions! Press any key to continue");

        scanner.nextLine();
    }

    /**
     * Ends the game, thanking the player and closing the scanner.
     */
    public void endGame() {
        System.out.println("\nThank you for playing the game!");
        scanner.close();
    }

    /**
     * Displays the available choices for a given list of choices.
     *
     * @param choices the list of choices to be displayed
     */
    public void displayChoices(List<Choice> choices) {
        System.out.println("\nYour Choices:");

        for (int i = 0; i < choices.size(); i++) {
            Choice co = choices.get(i);
            System.out.println((i + 1) + ". " + co.getName() + " - " + co.getDescription());
        }
    }

    /**
     * Displays the available side quests for the player to choose from.
     *
     * @param sideQuests the list of side quests to be displayed
     */
    public void displaySideQuests(List<Node> sideQuests) {
        System.out.println("\n***************************");
        System.out.println("\nYour Choices for SideQuests:");

        for (int a = 0; a < sideQuests.size(); a++) {
            Node sq = sideQuests.get(a);
            System.out.println((a + 1) + ". " + sq.getName() + " : " + sq.getDescription());
        }

        System.out.println((sideQuests.size() + 1) + ". Pause the game");
    }

    /**
     * Retrieves and validates user input within a specified range.
     *
     * @param scanner the scanner for user input
     * @param min     the minimum valid input value
     * @param max     the maximum valid input value
     * @return the validated user input
     */
    public int getUserInput(Scanner scanner, int min, int max) {
        int input = 0;

        while (true) {
            System.out.print("\nEnter your choice: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                scanner.next();
                continue;
            }
            input = scanner.nextInt();
            if (input >= min && input <= max) {
                break;
            } else {
                System.out.println("Invalid choice. Please select a number between " + min + " and " + max + ".");
            }
        }
        return input;
    }

    /**
     * Simulates whether an action is successful based on its probability.
     *
     * @param probability the success probability (0.0 to 1.0)
     * @return true if successful, false otherwise
     */
    private boolean isSuccessful(double probability) {
        Random rand = new Random();
        return rand.nextDouble() <= probability;
    }

    /**
     * Checks if the player has enough money and reputation to access a given node.
     *
     * @param node   the node to be checked
     * @param player the player attempting to access the node
     * @return true if the player meets the requirements, false otherwise
     */
    private boolean canAccessNode(Node node, Player player) {
        return player.getCurrentMoney() >= node.getMinMoney() && player.getCurrentReputation() >= node.getMinReputation();
    }

    /*
     * Move the player through the map by getting user input, executing the probability weighted choices (edges)
     * 
     * @param player the player
     */
    public void traverse(Player player){
        
        ArrayList<Node> orderNodes = this.getOrderNodes();
        Node currentNode = player.getCurrentNode(); // default to StartNode to user
        boolean userPause = false;

        while(!userPause){
            Mission currentMission = getMissions().get(currentNode.getMission() -1);

            // Only print current mission for the first part of the mission
            if (currentMission.getMissionNodes().indexOf(currentNode) == 0){
                System.out.println("Moving on to the next mission");
                System.out.println("\n***************************");
                System.out.println("Current Mission: " + currentMission.getName()); 
            }

            System.out.println("\n" + currentNode.getDescription());
            List<Choice> choices = getChoices(currentNode); 
        
            // Break if completed game
            if (currentNode == orderNodes.get(orderNodes.size() - 1)) { 
                System.out.println("\n***************************");   
                System.out.println("YOU COMPLETED ALL 3 MISSONS AND WON THE GAME!"); 
                player.setCurrentNode(currentNode);
                saveAndStoreProgress(player);
                break; 
            }
            
            // Display and get the choices
            displayChoices(choices);
            int userInput = getUserInput(scanner, 1, choices.size());

            // If the user pause (the last option)
            if (userInput == choices.size()){
                // Be careful not to update currentNode to endNode
                System.out.println("\n***************************");   
                System.out.println("Game Paused"); 
                player.setCurrentNode(currentNode);
                saveAndStoreProgress(player);
                break;
            }

            // Execute the choice with probability
            Choice selectedChoice = choices.get(userInput - 1); // Minus 1 because indexing start at 1

            // If successful
            if (isSuccessful(selectedChoice.getSuccessProbability())) { 
                Node nextNode = selectedChoice.getNextNode(); 

                // Update the Money and Reputation Points
                player.setCurrentMoney(player.getCurrentMoney() + selectedChoice.getMoneyReward());
                player.setCurrentReputation(player.getCurrentReputation() + selectedChoice.getReputationReward());

                System.out.println("\nSuccess!"); 
                System.out.println("Current Money: $" + player.getCurrentMoney());                 
                System.out.println("Current Reputation: " + player.getCurrentReputation()); 

                if (canAccessNode(nextNode, player)){
                    player.setCurrentNode(nextNode); // Move the player to the nextNode
                    currentNode = nextNode; 
                    continue;
                } else {

                    // Create the loop for user to constantly do Side Quests to meet the requirement for the next Node
                    while(!userPause){
                        System.out.println("\nYou have not earned enough money or reputation to advance to " + nextNode.getName()); 
                        System.out.println("Minimum requirements $" + nextNode.getMinMoney() + ", Reputation: " + nextNode.getMinReputation());
                        System.out.println("\nYou need to complete side quests to unlock the next mission");     

                        // Keep asking users to Side Quest unless they want to resume their misisons (and if they are eligible to)
                        displaySideQuests(sideQuests);
                        int response = getUserInput(scanner, 1, sideQuests.size() + 1); // + 1 because of the option to pause the game

                        if (response == sideQuests.size() + 1){
                            // Be careful not to update currentNode to endNode
                            System.out.println("Thank you for playing! Game Paused"); 
                            player.setCurrentNode(currentNode);
                            userPause = true;
                            saveAndStoreProgress(player);
                            break;
                        }

                        // Be careful not to update currentNode to endNode. Select Side Quest
                        Node currentSideQuest = getSideQuests().get(response - 1); // -1 because indexing starts at 0
                        displayChoices(getChoices(currentSideQuest));

                        int input = getUserInput(scanner, 1, choices.size());

                        // Pause the game
                        if (input == choices.size()){ // Pausing is always the last option
                            // Be careful not to update currentNode to endNode
                            System.out.println("\n***************************");   
                            System.out.println("Thank you for playing! Game Paused"); 
                            player.setCurrentNode(currentNode);
                            saveAndStoreProgress(player);
                            userPause = true;
                            break;
                        }

                        // Execute the choice with probability
                        Choice selected = getChoices(currentSideQuest).get(input - 1); // Minus 1 because indexing start at 1

                        // If successful
                        if (isSuccessful(selected.getSuccessProbability())) { 

                            // Update the Money and Reputation Points
                            player.setCurrentMoney(player.getCurrentMoney() + selected.getMoneyReward());
                            player.setCurrentReputation(player.getCurrentReputation() + selected.getReputationReward());
                            System.out.println("Current Money: $" + player.getCurrentMoney());                 
                            System.out.println("Current Reputation: " + player.getCurrentReputation());             
                        }

                        // After completing the sideQuest, automatically move them to the nextNode
                        if (canAccessNode(nextNode, player)){
                            System.out.println("\nYou met the requirements. Moving you to the next mission ...");
                            currentNode = nextNode;
                            break; 
                    }
                }   continue;
            }
        } 

            // If not successful
            else { 
                System.out.println("\nYou are caught and beaten! Lose 20 reputation points and pay $300 in bail or damage ."); 
                // Update the Money and Reputation Points
                player.setCurrentMoney(player.getCurrentMoney() - 300);
                player.setCurrentReputation(player.getCurrentReputation() - 20);

                System.out.println("Current Money: $" + player.getCurrentMoney());                 
                System.out.println("Current Reputation: " + player.getCurrentReputation()); 

                //Update the node;
                currentNode = updateNode(currentNode);
                System.out.println("\n You have to restart at " + currentNode.getName());
                
                if (!canAccessNode(currentNode, player)) {

                // Create the loop for user to constantly do Side Quests if they cant unlock the current mission.
                    while(!userPause){
                        System.out.println("\nYou have not earned enough money and reputation to resume at " + currentNode.getName()); 
                        System.out.println("Minimum requirements $" + currentNode.getMinMoney() + ", Reputation: " + currentNode.getMinReputation());
                        System.out.println("\nYou need to complete side quests to increase your money and reputation.");  
                        
                        // Keep asking users to Side Quest unless they want to resume their misisons (and if they are eligible to)
                        displaySideQuests(sideQuests);
                        int response = getUserInput(scanner, 1, sideQuests.size() + 1);

                        if (response == sideQuests.size() + 1){
                            // Be careful not to update currentNode to endNode
                            System.out.println("Thank you for playing! Game Paused"); 
                            player.setCurrentNode(currentNode);
                            userPause = true;
                            saveAndStoreProgress(player);
                            break;
                        }

                        // Be careful not to update currentNode to endNode
                        Node currentSideQuest = getSideQuests().get(response - 1); // -1 because indexing starts at 0
                        displayChoices(getChoices(currentSideQuest));

                        int input = getUserInput(scanner, 1, choices.size() + 1); // + 1 to include the third option for pausing
                        if (input == 3 & response == 2 | input == 2 & response == 1){
                            // Be careful not to update currentNode to endNode
                            System.out.println("\n***************************");   
                            System.out.println("Thank you for playing! Game Paused"); 
                            player.setCurrentNode(currentNode);
                            saveAndStoreProgress(player);
                            userPause = true;
                            break;
                        }

                        // Execute the choice with probability
                        Choice selected = getChoices(currentSideQuest).get(input - 1); // Minus 1 because indexing start at 1

                        // If successful
                        if (isSuccessful(selected.getSuccessProbability())) { 
                            // Update the Money and Reputation Points
                            player.setCurrentMoney(player.getCurrentMoney() + selected.getMoneyReward());
                            player.setCurrentReputation(player.getCurrentReputation() + selected.getReputationReward());
                            System.out.println("Current Money: $" + player.getCurrentMoney());                 
                            System.out.println("Current Reputation: " + player.getCurrentReputation());             
                        }

                        // After complete SideQuest check their current status. If they are eligible ask if they want to continue to do SideQuests
                        if (canAccessNode(currentNode, player)){

                                break;
                        }
                    }
                } 
            }
        }
    }

    /**
     * Updates the current node for a player based on their progress.
     * 
     * @param currentNode The player's current node.
     * @return The updated node, which is the first node of the next mission.
     */
    private Node updateNode(Node currentNode) {
        int missionIndex = currentNode.getMission();
        Mission mission = this.getMissions().get(missionIndex - 1);
        Node updatedNode = mission.getMissionNodes().get(0);
        return updatedNode;
    }

    /**
     * Resumes the game for a returning player by reading their saved progress.
     * 
     * @param playerName The name of the player whose progress is to be resumed.
     * @return A Player object initialized with the saved progress, or null if no saved progress is found.
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
     * Starts a new game or resumes a saved game based on the player's choice.
     * 
     * @return A Player object initialized based on the choice (new game or resumed game).
     */
    public Player startGame() {
        System.out.println("Choose 1 to start a new game or 2 to resume where you left");
        int response = getUserInput(scanner, 1, 2);

        if (response == 2) {
            System.out.println("\nType in your last username");
            scanner.nextLine(); // Clear the buffer
            String username = scanner.nextLine();

            // Resume the saved game
            Player player = resume(username);

            System.out.println("\nStarting Reputation: " + player.getCurrentReputation());
            System.out.println("Starting Money: $" + player.getCurrentMoney());
            System.out.println("Starting Mission: " + player.getCurrentNode().getName());
            System.out.println("\nPress any key to continue");
            scanner.nextLine();

            return player;

        } else {
            // Start a new game
            System.out.println("\nType in your new username");
            scanner.nextLine(); // Clear the buffer
            String username = scanner.nextLine();
            Player player = new Player(username, orderNodes.get(0)); // Initializing the current Node to the start Node
            players.add(player);

            System.out.println("\nStarting Reputation: " + player.getCurrentReputation());
            System.out.println("Starting Money: $" + player.getCurrentMoney());
            System.out.println("Starting Mission: " + player.getCurrentNode().getName());
            System.out.println("\nPress any key to continue");
            scanner.nextLine();

            return player;
        }
    }

    /**
     * Saves the player's progress to a file.
     * 
     * @param player The player whose progress is to be saved.
     */
    public void saveAndStoreProgress(Player player) {
        String userFileName = player.getName().replaceAll("[^a-zA-Z0-9_]", "") + "_save.txt";

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
