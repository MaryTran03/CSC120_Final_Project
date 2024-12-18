
import java.io.*;
import java.util.ArrayList;
//import java.util.Hashtable;
import java.util.Scanner;

public class Draft {
   // private static final String SAVE_FILE = "./save_game.txt";
    private ArrayList<Player> users;
    //private Hashtable<String, Boolean> progress;
    private ArrayList<MissionPart> missions;

    //private Player player;
    private Scanner userInput;

    // Constructor
    public Draft(Player player) {
        //this.player = player;
        this.users = new ArrayList<>();
       // this.progress = new Hashtable<>();
        this.userInput = new Scanner(System.in);
        this.missions = new ArrayList<>();
    }

    // public Player getPlayer() {
    //     return player;
    // }

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
    // public void checkWinStatus() {
    //     if (player.getReputation() >= 40 || player.getMoney() >= 800) {
    //         System.out.println(player.getName() + " wins the game!");
    //         endGame();
    //     } else {
    //         System.out.println("Not enough reputation points or money to win.");
    //     }
    // }

    /**
     * Method to save and store progress of the game.
     */
    public void saveAndStoreProgress() {
        String userFileName = player.getName().replaceAll("[^a-zA-Z0-9_]","") + "_save.txt";
        try (BufferedWriter out = new BufferedWriter(new FileWriter(userFileName))) {
            System.out.println("Saving game progress for" + player.getName() + "...");
            out.write("Player name:" + player.getName() + "\n");
            out.write("Reputation:" + player.getReputation() + "\n");
            out.write("Money:" + player.getMoney() + "\n");
            if(missions != null && !missions.isEmpty()){
                out.write("Last completed Mission:" + missions.get(missions.size()-1).getName() + "\n");
            }else{
                out.write("Last completed Mission:None\n");
            }
            out.flush();
            if(!users.contains(player)){
                addUser(player);

            }
            System.out.println("Game progress saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save the game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to resume the game from saved progress.
     */
    public void resume(String playerName) {
        String userFileName = playerName + "_save.txt";
        File saveFile = new File(userFileName);
        if (saveFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(userFileName))) {
                System.out.println("Reading saved file: "+ userFileName);
                String nameLine = reader.readLine();
                String reputationLine = reader.readLine();
                String moneyLine = reader.readLine();
                String missionLine = reader.readLine();

                String name = nameLine.split(":")[1].trim();
                int reputation = Integer.parseInt( reputationLine.split(":")[1].trim());
                double money = Double.parseDouble( moneyLine.split(":")[1].trim());
                
                player.setName(name);
                player.setMoney(money);
                player.setReputation(reputation);
                String lastMission = (missionLine != null && !missionLine.equals("Last Completed Mission:None"))
                ? missionLine.split(":")[1].trim() : "None";

                System.out.println("Player Name: " + player.getName());
                System.out.println("Player Reputation: " + player.getReputation());
                System.out.println("Player Money: " + player.getMoney());
                System.out.println("Last Completed Mission: " + lastMission);
                System.out.println("Game resumed successfully.");
                //checkWinStatus();
            } catch (IOException | NumberFormatException e) {
                System.err.println("Failed to resume the game: " + e.getMessage());
            }
        } else {
            System.out.println("No saved game found. Starting a new game.");
            startGame(player);
        }
    }

    /**
     * Method to pause the game
     */
    public void pauseGame() {
        saveAndStoreProgress();
        System.out.println("Game paused.");
        
    }

    /**
     * Method to end the game.
     */
    public void endGame() {
        System.out.println("The game has ended. Thank you for playing!");
    }
}
