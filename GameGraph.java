import java.util.*;
import java.io.*;

class GameGraph {
    private ArrayList<Player> players;
    private Map<Node, List<Choice>> graph;
    private ArrayList<SideQuest> sideQuests;
    private ArrayList<Node> orderNodes;
    private Scanner scanner;

    public GameGraph() {
        this.scanner = new Scanner(System.in);
        graph = new HashMap<>();
        this.sideQuests = new ArrayList<SideQuest>();
        this.orderNodes = new ArrayList<Node>();
        this.players = new ArrayList<Player>();
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

    // Describe the game
    public void describeGame(){
        System.out.println("WELCOME TO THE GAME");
    }

    // End game 
    public void endGame(){
        System.out.println("Thank you for player the game!");
        scanner.close();
    }

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

    /*
     * Return the player
     */
    

    private boolean canAccessNode(Node node, Player player) { 
        return player.getCurrentMoney() >= node.getMinMoney() && player.getCurrentReputation() >= node.getMinReputation(); 
    }

    /*
     * Return the players currentMoney, currentReputation, currentNodeIndex
     */
    public void traverse(Player player){
        
        ArrayList<Node> orderNodes = this.getOrderNodes();
        Node currentNode = player.getCurrentNode(); // default to StartNode to user
        boolean userPause = false;
        boolean isFirstIteration = true;

        while(!userPause){
            System.out.println("\n***************************");
            System.out.println("Current Mission: " + currentNode.getName()); 
            List<Choice> choices = getChoices(currentNode); 


            if (isFirstIteration) {
                isFirstIteration = false; // Skip the pause check in the first iteration
            } else {
                // Check if the player wants to pause the game
                System.out.println("Enter `PAUSE` to end the game early and save your progress. If not press any key to proceed.");
                scanner.nextLine();
                String input1 = scanner.nextLine();

                if (input1.equalsIgnoreCase("pause")) {
                    System.out.println("\n Game paused. Progress saved");
                    player.setCurrentNode(currentNode);
                    System.out.println("Current Money: $" + player.getCurrentMoney());                 
                    System.out.println("Current Reputation: " + player.getCurrentReputation()); 
                    System.out.println("Current Mission: " + player.getCurrentNode().getName());  
                    saveAndStoreProgress(player);

                    break; // Exit the loop
                }
            }
        
            // Break if end game
            if (choices.isEmpty()) { 
                System.out.println("CONGRATS! YOU COMPLETED THE GAME"); 
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

                System.out.println("\n Success! Moving to the next part"); 
                System.out.println("Current Money: $" + player.getCurrentMoney());                 
                System.out.println("Current Reputation: " + player.getCurrentReputation()); 

                if (canAccessNode(nextNode, player)){
                    currentNode = nextNode; 
                    continue;
                } else {
                    while(!userPause){
                        System.out.println("\n You have not earned enough money or reputation to advance to " + nextNode.getName()); 
                        System.out.println("Minimum requirements $" + nextNode.getMinMoney() + ", Reputation: " + nextNode.getMinReputation());
                        System.out.println("\n You need to complete side quests to unlock the next misson");     

                        
                        // Check if the player wants to pause the game
                        System.out.println("Or enter `PAUSE` to end the game early and save your progress. If not, press any key to proceed.");
                        scanner.nextLine();
                        String input2= scanner.nextLine();
                        if (input2.equalsIgnoreCase("pause")){
                            System.out.println("Game paused. Progress saved");
                            player.setCurrentNode(currentNode);
                            userPause = true;
                            saveAndStoreProgress(player);

                            break;
                        }
                        
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

                            // Check if the player wants to pause the game
                            System.out.println("Or enter `PAUSE` to end the game early and save your progress. If not, press any key to proceed.");
                            scanner.nextLine();
                            String input2= scanner.nextLine();
                            if (input2.equalsIgnoreCase("pause")){
                                System.out.println("Game paused. Progress saved");
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
        
    /**
     * Method to resume the game from saved progress.
     */
        public Player resume(String playerName) {
            String userFileName = playerName + "_save.txt";
            File saveFile = new File(userFileName);
        
            if (saveFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(userFileName))) {
                    System.out.println("\n Reading saved file: " + userFileName);
        
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
        
                            System.out.println("Player Name: " + player.getName());
                            System.out.println("Player Reputation: " + player.getCurrentReputation());
                            System.out.println("Player Money: $" + player.getCurrentMoney());
                            System.out.println("Last Completed Mission: " + player.getCurrentNode().getName());
                            System.out.println("Game resumed successfully.");
        
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
        
        public Player startGame() {
            System.out.println("Type in 'start' or 'resume'.");
            String response = scanner.nextLine().toUpperCase();
            if (response.equals("RESUME")) {
                System.out.println("Type in your last username");
                String username = scanner.nextLine();
    
                // Check in existing file if there is anything saved. If not create a new user
                Player player = resume(username);
                return player;
    
            } else {
                // If that create new player using default values
                System.out.println("Type in your new username");
                String username = scanner.nextLine();
                Player player = new Player(username, orderNodes.get(0)); // Initializing the current Node to the start Node
                players.add(player);
    
                System.out.println("Welcome to the game!");
                return player;
    
            }
        }

        public void saveAndStoreProgress(Player player) {
            String userFileName = player.getName().replaceAll("[^a-zA-Z0-9_]","") + "_save.txt";
    
            // Create new file or overwrite existing files
            try (BufferedWriter out = new BufferedWriter(new FileWriter(userFileName))) {
                System.out.println("Saving game progress for" + player.getName() + "...");
                out.write("Player name:" + player.getName() + "\n");
                out.write("Reputation:" + player.getCurrentReputation() + "\n");
                out.write("Money:" + player.getCurrentMoney() + "\n");
                out.write("Last completed mission: " + player.getCurrentNode().getName() + "\n");
                
                out.flush();
                System.out.println("Game progress saved successfully.");
            } catch (IOException e) {
                System.err.println("Failed to save the game: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
