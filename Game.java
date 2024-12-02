import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final String SAVE_FILE = "./save_game.txt";
    private ArrayList<Player> users;
    private ArrayList<MissionPart> missions;
    private Player player;
    private Scanner userInput;

    // Constructor
    public Game(Player player) {
        this.player = player;
        this.users = new ArrayList<>();
        this.missions = new ArrayList<>();
        this.userInput = new Scanner(System.in);
    }
    public Player getPlayer(){
        return player;
    }
    /**
     * Method to start the game.
     */
    public boolean startGame(Player player) {
        System.out.println("Type in 'start' or 'resume'.");
        String response = userInput.nextLine().toUpperCase();

        if (response.equals("RESUME")) {
            resume();
        } else {
            System.out.println("Welcome to the game!");
            Mission firstMission = new Mission("Mission 1: The Car Heist", 0, player.getReputation(), "Prove yourself to Vinnie by stealing a high-end car from a wealthy neighborhood.");
            firstMission.completeMission(0, 0, userInput);
            saveAndStoreProgress();
            System.out.println("Do you want to move on?");
            if(userInput.nextLine().toUpperCase().equals("YES")){
                Mission secondMission = new Mission("Mission 2: The Warehouse Raid", 0, player.getReputation(), "After proving yourself in the car heist, Vinnie gives you a tougher job. You need to break into the Iron Hounds' warehouse to steal a stash of valuable electronics. The warehouse has guards, so you'll need to choose your approach carefully.");
                secondMission.completeMission(0, 0, userInput);
            }
        }
        return true;
    }

    /**
     * Method to add a mission or side quest.
     */
    public void addChoice(MissionPart choice) {
        System.out.println("Which mission or side quest do you want to pick?");
        String response = userInput.nextLine().toUpperCase();
        while (true) {
            if (response.contains("MISSION") || response.contains("SIDEQUEST")) {
                if (choice instanceof MissionPart || choice instanceof SideQuest) {
                    missions.add(choice);
                    System.out.println("Choice added successfully!");
                } else {
                    System.out.println("Invalid. Please choose a valid mission or side quest.");
                }
            } else {
                System.out.println("You must choose either a mission or a side quest.");
            }

            ArrayList<Integer>selections = new ArrayList<>( );
            selections.add(1);
            selections.add(2);
            selections.add(3);
        }
        
    }
    /**
     * Validate User's Input
     * @param scanner
     * @return true or false for validation
     */
    public boolean validateUserInput(Scanner scanner){
        while (true) {
            System.out.print("\n Enter your choice: ");
            if(!scanner.hasNext("MISSION") || !scanner.hasNext("SIDEQUEST")){
                System.out.println("Invalid. Please type in mission or side quest.");
                scanner.next();
                continue;
            }else{
                return true;
            }
        }
    }
    /**
     * Method to add a user to the game.
     */
    public void addUser(Player user) {
        if (user != null) {
            users.add(user);
            System.out.println("User added: " + user.getName());
        } else {
            System.out.println("Cannot add a null user.");
        }
    }

    /**
     * Method to check if the player wins.
     */
    public void checkWinStatus() {
        if (player.getReputation() >= 40 || player.getMoney() >= 800) {
            System.out.println(player.getName() + " wins the game!");
        } else {
            System.out.println("Not enough reputation points or money to win.");
        }
    }

    /**
     * Method to save and store progress of the game.
     */
    public void saveAndStoreProgress() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            System.out.println("Saving game progress...");
            out.write("Player name:" + player.getName() + "\n");
            out.write("Reputation:" + player.getReputation() + "\n");
            out.write("Money:" + player.getMoney() + "\n");
            out.flush();
            System.out.println("Game progress saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save the game: " + e.getMessage());
        }
    }

    /**
     * Method to resume the game from saved progress.
     */
    public void resume() {
        File saveFile = new File(SAVE_FILE);
        if (saveFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
                String name = reader.readLine().split(":")[1];
                int money = Integer.parseInt(reader.readLine().split(":")[1]);
                this.player = new Player(name,money);
                System.out.println("Game resumed successfully.");
            } catch (IOException e) {
                System.err.println("Failed to resume the game: " + e.getMessage());
            }
        } else {
            System.out.println("No saved game found. Starting a new game.");
        }
    }

    /**
     * Method to load the game with saved progress.
     */
    public void loadPlayerProgress() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String name = reader.readLine().split(":")[1];
            int money = Integer.parseInt(reader.readLine().split(":")[1]);  
            player.setName(name);
            player.setMoney(money);          
            System.out.println("Game loaded successfully");
        } catch (IOException e) {
            System.err.println("Failed to resume the game: " + e.getMessage());
        }
         
    }

    /**
     * Method to end the game.
     */
    public void endGame() {
        System.out.println("The game has ended. Thank you for playing!");
    }
}
