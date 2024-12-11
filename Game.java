import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<MissionPart> missions;

    private Player player;
    private Scanner userInput;

    // Constructor
    public Game(Player player) {
        //this.player = player;
        this.players = new ArrayList<>();
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
            players.add(player);
            System.out.println("Welcome to the game!");
        }
        return true;
    }

   

    /**
     * Method to add a user to the game.
     */
    public void addUser(Player player) {
        if (player != null) {
            this.players.add(player);
            System.out.println("User added: " + player.getName());
        } else {
            System.out.println("Cannot add a null user.");
        }
    }

    /**
     * Method to save and store progress of the game.
     */
    public void saveAndStoreProgress() {
        String userFileName = player.getName().replaceAll("[^a-zA-Z0-9_]","") + "_save.txt";

        // Create new file or overwrite existing files
        try (BufferedWriter out = new BufferedWriter(new FileWriter(userFileName))) {
            System.out.println("Saving game progress for" + player.getName() + "...");
            out.write("Player name:" + player.getName() + "\n");
            out.write("Reputation:" + player.getReputation() + "\n");
            out.write("Money:" + player.getMoney() + "\n");
            if(missions != null && !missions.isEmpty()){
                out.write("Last completed Mission:" + missions.get(missions.size()-1).getName() + "\n");
            }else{
                out.write("Last completed Mission: None\n");
            }
            out.flush();
            if(!players.contains(player)){
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

                String name = (nameLine != null && nameLine.contains(":"))?nameLine.split(":")[1].trim():null;
                int reputation = (reputationLine != null && nameLine.contains(":"))?Integer.parseInt(reputationLine.split(":")[1].trim()):0;
                double money = (moneyLine != null && moneyLine.contains(":"))?Double.parseDouble( moneyLine.split(":")[1].trim()):0;
                if(player == null){
                    player = new Player(name, money);
                }
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
            if(player == null){
                player = new Player(playerName, 200.0);
            }
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
