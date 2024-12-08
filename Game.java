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
            Mission firstMission = new Mission("The Car Heist", 0, player.getReputation(),
                    "Prove yourself to Vinnie by stealing a high-end car from a wealthy neighborhood.");
            MissionPart firstMissionPart = new MissionPart(firstMission.getName());
            missions.add(firstMissionPart);
            progress.put(firstMissionPart.getName(), false);
            firstMission.completeMission(0, 0, userInput);
            saveAndStoreProgress();
            System.out.println("Do you want to move on?");
            if (userInput.nextLine().toUpperCase().equals("YES")) {
                Mission secondMission = new Mission("The Warehouse Raid", 0, 10,
                        "After proving yourself in the car heist," +
                                "Vinnie gives you a tougher job. You need to break into the Iron Hounds' warehouse to steal a stash of valuable electronics."
                                +
                                "The warehouse has guards, so you'll need to choose your approach carefully.");
                MissionPart secondMissionPart = new MissionPart(secondMission.getName());
                missions.add(secondMissionPart);
                progress.put(secondMissionPart.getName(), false);
                secondMission.completeMission((int) player.getMoney(), player.getReputation(), userInput);
                if (userInput.nextLine().toUpperCase().equals("YES")) {
                    checkWinStatus();
                }
                System.out.println("Do you want to move on?");
                if (userInput.nextLine().toUpperCase().equals("YES")) {
                    Mission thirdMission = new Mission("The Final Heist", 0, 30,
                            "After proving your skills in the car heist and warehouse raid," +
                                    "Vinnie trusts you with the crew's biggest job yet—a bank heist." +
                                    "Your role is crucial as the getaway driver." +
                                    "You'll need to navigate through Rivertown while avoiding police and rival gang interference to ensure a clean escape.");
                    MissionPart thirdMissionPart = new MissionPart(thirdMission.getName());
                    missions.add(thirdMissionPart);
                    progress.put(thirdMissionPart.getName(), false);
                    thirdMission.completeMission((int) player.getMoney(), player.getReputation(), userInput);
                    System.out.println("Do you want to check your win status?");
                    if (userInput.nextLine().toUpperCase().equals("YES")) {
                        checkWinStatus();
                    }
                } else {
                    pauseGame();
                    System.out.println("The game is paused. Do you want to continue?");
                    if (userInput.nextLine().toUpperCase().equals("YES")) {
                        resume(player.getName());
                    }
                }
            } else {
                pauseGame();
                System.out.println("The game is paused. Do you want to continue?");
                if (userInput.nextLine().toUpperCase().equals("YES")) {
                    resume(player.getName());
                }
            }
        }
        return true;
    }

    /**
     * Method to add a mission or side quest.
     * 
     * @param choice
     */
    public void addMissionorSideQuest(MissionPart choice) {
        System.out.println("Which mission or side quest do you want to pick?");
        String response = userInput.nextLine().toUpperCase();
        while (true) {
            if (validateUserInput(userInput, response)) {
                System.out.println("Type in 1, 2, 3.");
                int i = userInput.nextInt();
                if (i - 1 <= 2 && i - 1 >= 0) {
                    choice.addChoice(choice.getChoices().get(i).getName(), choice.getChoices().get(i).getDescription(),
                            choice.getChoices().get(i).getMoney(), choice.getChoices().get(i).getReputation(),
                            choice.getChoices().get(i).getProbability());
                    missions.add(choice);
                } else {
                    System.out.println("Invalid. Please choose a valid number.");
                }
                System.out.println("Choice added successfully!");

            }

        }

    }

    /**
     * Validate User's Input
     * 
     * @param scanner
     * @return true or false for validation
     */
    public boolean validateUserInput(Scanner scanner, String response) {
        while (true) {
            System.out.print("\n Enter your choice: ");
            if (!scanner.hasNext("MISSION") || !scanner.hasNext("SIDEQUEST")) {
                System.out.println("Invalid. Please type in mission or side quest.");
                scanner.next();
                continue;
            } else {
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
     * Method to load the game with saved progress.
     */
    /*public void loadPlayerProgress(String playerName) {
        String userFileName = playerName + "_save.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(userFileName))) {
            String name = reader.readLine().split(":")[1];
            int reputation = Integer.parseInt(reader.readLine().split(":")[1]);
            double doubleMoney = Double.parseDouble(reader.readLine().split(":")[1]); 
            int money = (int) Math.round(doubleMoney);
            player.setName(name);
            player.setReputation(reputation);
            player.setMoney(money);
            String lastMission = reader.readLine().split(":")[1].strip();
            System.out.println(lastMission);
            progress.put(lastMission, true);
            int i = 0;
            System.out.println(missions.size());
            while(i<missions.size() && !missions.get(i).getName().equals(lastMission)){
                progress.put(missions.get(i).getName(), true);
                i++;
            }
            System.out.println(progress);
            System.out.println("Game loaded successfully");
        } catch (IOException e) {
            System.err.println("Failed to load the game: " + e.getMessage());
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
