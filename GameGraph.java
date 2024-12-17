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
        System.out.println("Good luck, and may you succeed in completing all the missions! \nPress ENTER to continue");

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

    /**
     * Handles side quests to help the player gain money and reputation 
     * to unlock the next node.
     *
     * @param player The player attempting to progress.
     * @param targetNode The node the player is trying to unlock.
     * @return boolean indicating whether the player paused the game.
     */
    public boolean handleSideQuests(Player player, Node targetNode) {
        while (!canAccessNode(targetNode, player)) {
            System.out.println("\nYou need $" + targetNode.getMinMoney() +
                    " and " + targetNode.getMinReputation() + " reputation to unlock " + targetNode.getName());
            System.out.println("\nComplete side quests to meet the requirements.");
            
            // Display available side quests
            displaySideQuests(sideQuests);
            int response = getUserInput(scanner, 1, sideQuests.size() + 1);

            // Handle game pause
            if (response == sideQuests.size() + 1) {
                System.out.println("Game Paused. Progress saved.");
                saveAndStoreProgress(player);
                return true; // Indicate the game is paused
            }

            // Execute the selected side quest
            Node selectedQuest = sideQuests.get(response - 1);
            List<Choice> sideQuestChoices = getChoices(selectedQuest);
            displayChoices(sideQuestChoices);
            int choiceInput = getUserInput(scanner, 1, sideQuestChoices.size());

            Choice questChoice = sideQuestChoices.get(choiceInput - 1);
            if (isSuccessful(questChoice.getSuccessProbability())) {
                player.setCurrentMoney(player.getCurrentMoney() + questChoice.getMoneyReward());
                player.setCurrentReputation(player.getCurrentReputation() + questChoice.getReputationReward());
                System.out.println("Success! Money: $" + player.getCurrentMoney() + ", Reputation: " + player.getCurrentReputation());
            } else {
                System.out.println("\nSide quest failed! No rewards gained.");
            }
        }

        return false; // Player can proceed to the target node
    }

    /*
     * Move the player through the map by getting user input, executing the probability weighted choices (edges)
     * 
     * @param player the player
     */

     public void traverse(Player player) {
        Node currentNode = player.getCurrentNode(); // Default to StartNode for new user
        boolean userPause = false;
    
        while (!userPause) {
            Mission currentMission = getMissions().get(currentNode.getMission() - 1);
            
            // Check if the player has completed the game
            if (currentNode == orderNodes.get(orderNodes.size() - 1)) {
                System.out.println("\n***************************");
                System.out.println("CONGRATULATIONS! YOU COMPLETED ALL MISSIONS!");
                System.out.println("Final Stats: Money = $" + player.getCurrentMoney() + ", Reputation = " + player.getCurrentReputation());
                player.setCurrentNode(currentNode);
                saveAndStoreProgress(player);
                break;
            }
    
            // Display mission details if starting a new mission
            if (currentMission.getMissionNodes().indexOf(currentNode) == 0) {
                System.out.println("\n***************************");
                System.out.println("Current Mission: " + currentMission.getName());
                System.out.println("Your Current Stats: Money = $" + player.getCurrentMoney() + ", Reputation = " + player.getCurrentReputation());
            }
    
            // Check if the player can unlock the current node
            if (!canAccessNode(currentNode, player)) {
                System.out.println("\nYou need more money or reputation to proceed with the current mission at " + currentNode.getName());
                System.out.println("Current Stats: Money = $" + player.getCurrentMoney() + ", Reputation = " + player.getCurrentReputation());
                userPause = handleSideQuests(player, currentNode);
    
                // If the game was paused during side quests, exit the loop
                if (userPause) {
                    break;
                }
    
                // After completing side quests, recheck if the player can unlock the current node
                if (!canAccessNode(currentNode, player)) {
                    System.out.println("\nYou still cannot unlock the current node. Complete more side quests.");
                    continue;
                }
            }
    
            // Display the current node description and choices
            System.out.println("\n" + currentNode.getDescription());
            System.out.println("Your Current Stats: Money = $" + player.getCurrentMoney() + ", Reputation = " + player.getCurrentReputation());
            List<Choice> choices = getChoices(currentNode);
            displayChoices(choices);
    
            // Get user input for the next choice
            int userInput = getUserInput(scanner, 1, choices.size());
    
            // Handle game pause
            if (userInput == choices.size()) {
                System.out.println("\nGame Paused. Progress saved.");
                player.setCurrentNode(currentNode);
                saveAndStoreProgress(player);
                break;
            }
    
            // Execute the selected choice
            Choice selectedChoice = choices.get(userInput - 1);
            if (isSuccessful(selectedChoice.getSuccessProbability())) {
                // Success: Update player stats and check for next node
                player.setCurrentMoney(player.getCurrentMoney() + selectedChoice.getMoneyReward());
                player.setCurrentReputation(player.getCurrentReputation() + selectedChoice.getReputationReward());
                System.out.println("\nSuccess! Money: $" + player.getCurrentMoney() + ", Reputation: " + player.getCurrentReputation());
    
                Node nextNode = selectedChoice.getNextNode();
                // If the user can unlock the next Mission
                if (nextNode != null && canAccessNode(nextNode, player)) {
                    currentNode = nextNode; // Move to the next node
                    player.setCurrentNode(nextNode);

                // Do Side Quests if cant unlock the next mission    
                } else if (nextNode != null) {
                    System.out.println("\nYou need more money or reputation to proceed to " + nextNode.getName());
                    userPause = handleSideQuests(player, nextNode);
                    if (!userPause && canAccessNode(nextNode, player)) {
                        currentNode = nextNode; // Move to the next node after side quests
                        player.setCurrentNode(nextNode);
                    }
                }
            } else {
                // Failure: Apply penalties and restart at the current mission's first node
                System.out.println("\nYou failed! Lost $100 and 10 reputation points.");
                player.setCurrentMoney(player.getCurrentMoney() - 100);
                player.setCurrentReputation(player.getCurrentReputation() - 10);
                System.out.println("Current Stats: Money = $" + player.getCurrentMoney() + ", Reputation = " + player.getCurrentReputation());
    
                // Reset the current node to the first part of the mission
                currentNode = updateNode(currentNode);
                player.setCurrentNode(currentNode);
                System.out.println("\nRestarting at " + currentNode.getName());
    
                if (!canAccessNode(currentNode, player)) {
                    System.out.println("\nYou need to complete side quests to resume.");
                    userPause = handleSideQuests(player, currentNode);
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
                if(nameLine == null || reputationLine == null ||moneyLine == null || missionLine == null){
                    return new Player(playerName, 200.0, 0, orderNodes.get(0));
                }
                // Parse the player's attributes
                String name = nameLine.split(":")[1].trim();
                int reputation = 0;
                try{
                    reputation = Integer.parseInt(reputationLine.split(":")[1].trim());
                }catch (NumberFormatException e){
                    System.out.println("Invalid reputation points value in save file. Setting it to 0.");
                }
                double money = Double.parseDouble(moneyLine.split(":")[1].trim());
                String nameCurrentNode = missionLine.replace("Last completed mission: ", "").trim();
                Node currentNode = null;
                // Search for the Node with the matching name
                for (Node node : orderNodes) {
                    if (node.getName().equals(nameCurrentNode)) {
                        currentNode = node;
                        break;
                    }
                }
                
                // If no matching Node is found
                //System.err.println("No matching node found for the last completed mission: " + nameCurrentNode);
                //return null;
                if(currentNode == null){
                    System.err.println("No matching node found for the last completed mission: " + nameCurrentNode);
                    currentNode = orderNodes.get(0);
                }
                 // Create the Player object
                 Player player = new Player(name, money, reputation, currentNode);
                 System.out.println("Game resumed.");
                 return player;
            } catch (IOException | NumberFormatException e) {
                System.err.println("Failed to resume the game: " + e.getMessage());
                e.printStackTrace();
                //return null; // Return null in case of an error
                return new Player(playerName, 200.0, 0, orderNodes.get(0));
            }
        } else {
            // If no saved game is found
            System.out.println("No saved game found. Starting a new game.");            
            return new Player(playerName, 200.0, 0, orderNodes.get(0));
            //return null;
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
            System.out.println("\nPress ENTER to continue");
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
            System.out.println("\nPress ENTER to continue");
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

            out.write(String.format("Player name: %s\n", player.getName()));
            out.write(String.format("Reputation: %d\n" ,player.getCurrentReputation()));
            out.write(String.format("Money: %.2f\n" ,player.getCurrentMoney()));
            out.write(String.format("Last completed mission: %s\n" ,player.getCurrentNode().getName()));

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
