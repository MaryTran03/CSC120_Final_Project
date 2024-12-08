import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Game {
    private static final String SAVE_FILE = "./save_game.txt";
    private ArrayList<Player> users;
    private Hashtable<String, Boolean> progress;
    private ArrayList<MissionPart> missions;

    private Player player;
    private Scanner userInput;

    // Constructor
    public Game(Player player) {
        this.player = player;
        this.users = new ArrayList<>();
        this.progress = new Hashtable<>();
        this.userInput = new Scanner(System.in);
        this.missions = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Method to start the game.
     */
    public boolean startGame(Player player) {
        System.out.println("Type in 'start' or 'resume'.");
        String response = userInput.nextLine().toUpperCase();
        if (response.equals("RESUME")) {
            resume(player.getName());
        } else {
            users.add(player);
            System.out.println("Welcome to the game!");
        }
        return true;
    }

   

    /**
     * Method to add a user to the game.
     */
    public void addUser(Player user) {
        if (user != null) {
            this.users.add(user);
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
            endGame();
        } else {
            System.out.println("Not enough reputation points or money to win.");
        }
    }

    /**
     * Method to save and store progress of the game.
     */
    public void saveAndStoreProgress() {
        String userFileName = player.getName() + "_save.txt";
        try (BufferedWriter out = new BufferedWriter(new FileWriter(userFileName))) {
            System.out.println("Saving game progress for" + player.getName() + "...");
            out.write("Player name:" + player.getName() + "\n");
            out.write("Reputation:" + player.getReputation() + "\n");
            out.write("Money:" + player.getMoney() + "\n");
            out.write("Last completed Mission:" + missions.getLast().getName());
            out.flush();
            addUser(player);
            System.out.println("Game progress saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save the game: " + e.getMessage());
        }
    }

    /**
     * Method to resume the game from saved progress.
     */
    public void resume(String playerName) {
        String userFileName = player.getName() + "_save.txt";
        File saveFile = new File(userFileName);
        if (saveFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
                String name = reader.readLine().split(":")[1];
                int money = Integer.parseInt(reader.readLine().split(":")[1]);
                this.player = new Player(name, money);
                progress.getOrDefault(saveFile, false);
                System.out.println("Game resumed successfully.");
                checkWinStatus();
            } catch (IOException e) {
                System.err.println("Failed to resume the game: " + e.getMessage());
            }
        } else {
            System.out.println("No saved game found. Starting a new game.");
        }
    }

    /**
     * Method to pause the game
     */
    public void pauseGame() {
        System.out.println("Do you want to save?");
        String response = userInput.nextLine().toUpperCase();
        if(response.toUpperCase().equals("YES")){
            saveAndStoreProgress();
            System.out.println("Game saved and paused successfully");
        }/*else{
            loadPlayerProgress(player.getName());
        }
       */
    }

    /**
     * Method to end the game.
     */
    public void endGame() {
        System.out.println("The game has ended. Thank you for playing!");
    }
}
