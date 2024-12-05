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
            resume();
        } else {
            users.add(player);
            System.out.println("Welcome to the game!");
            Mission firstMission = new Mission("Mission 1: The Car Heist", 0, player.getReputation(),
                    "Prove yourself to Vinnie by stealing a high-end car from a wealthy neighborhood.");
            MissionPart firstMissionPart = new MissionPart(firstMission.getName());
            missions.add(firstMissionPart);
            firstMission.completeMission(0, 0, userInput);
            saveAndStoreProgress();
            System.out.println("Do you want to move on?");
            if (userInput.nextLine().toUpperCase().equals("YES")) {
                Mission secondMission = new Mission("Mission 2: The Warehouse Raid", 0, 10,"After proving yourself in the car heist,"+ 
                "Vinnie gives you a tougher job. You need to break into the Iron Hounds' warehouse to steal a stash of valuable electronics."+
                "The warehouse has guards, so you'll need to choose your approach carefully.");
                MissionPart secondMissionPart = new MissionPart(secondMission.getName());
                missions.add(secondMissionPart);
                secondMission.completeMission((int) player.getMoney(), player.getReputation(), userInput);
                if(userInput.nextLine().toUpperCase().equals("YES")){
                    checkWinStatus();
                }
                System.out.println("Do you want to move on?");
                if (userInput.nextLine().toUpperCase().equals("YES")) {
                    checkWinStatus();
                    Mission thirdMission = new Mission("Mission 3: The Final Heist", 0, 30,
                    "After proving your skills in the car heist and warehouse raid,"+
                    "Vinnie trusts you with the crew's biggest job yet—a bank heist."+
                    "Your role is crucial as the getaway driver." +"
                    You'll need to navigate through Rivertown while avoiding police and rival gang interference to ensure a clean escape.");
                    MissionPart thirdMissionPart = new MissionPart(thirdMission.getName());
                    missions.add(thirdMissionPart);
                    thirdMission.completeMission((int) player.getMoney(), player.getReputation(), userInput);
                    System.out.println("Do you want to check your win status?");
                    if(userInput.nextLine().toUpperCase().equals("YES")){
                        checkWinStatus();
                    }
                 } else {
                    pauseGame();
                }
            } else {
                pauseGame();
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
            addUser(player);
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
                this.player = new Player(name, money);
                System.out.println("Game resumed successfully.");
                checkWinStatus();
                missions.get(missions.size() - 1);
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

    public void pauseGame() {
        saveAndStoreProgress();
        System.out.println("Game paused and the progress is saved.");
    }

    /**
     * Method to end the game.
     */
    public void endGame() {
        System.out.println("The game has ended. Thank you for playing!");
    }
}
