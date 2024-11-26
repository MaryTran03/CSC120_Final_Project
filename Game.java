import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final String SAVE_FILE = "save_game.dat";
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

    /**
     * Method to start the game.
     */
    public boolean startGame() {
        System.out.println("Type in 'start' or 'resume'.");
        String response = userInput.nextLine().toUpperCase();

        if (response.equals("RESUME")) {
            resume();
        } else {
            System.out.println("Welcome to the game!");
            Mission firstMission = new Mission("Mission 1: The Car Heist".player.getMoney(),player.getReputation(), "Prove yourself to Vinnie by stealing a high-end car from a wealthy neighborhood.");
            firstMission.completeMission(0, 0, userInput);;
        }
        return true;
    }

    /**
     * Method to add a mission or side quest.
     */
    public void addChoice(MissionPart choice) {
        System.out.println("Which mission or side quest do you want to pick?");
        String response = userInput.nextLine().toUpperCase();

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
    }
    /**
     * Validate User's Input
     * @param scanner
     * @return true or false for validation
     */
    public boolean validateUserInput(Scanner scanner){
        boolean validated = false;
        while (true) {
            System.out.print("\n Enter your choice: ");
            if(!scanner.hasNext("MISSION") || !scanner.hasNext("SIDEQUEST")){
                System.out.println();
            }
        }
        validated = true;

        return validated;
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
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(this);
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
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
                Game savedGame = (Game) in.readObject();
                this.users = savedGame.users;
                this.missions = savedGame.missions;
                this.player = savedGame.player;
                System.out.println("Game resumed successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Failed to resume the game: " + e.getMessage());
            }
        } else {
            System.out.println("No saved game found. Starting a new game.");
        }
    }

    /**
     * Method to end the game.
     */
    public void endGame() {
        System.out.println("The game has ended. Thank you for playing!");
    }
}
