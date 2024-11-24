import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final String SAVE_FILE = "save_game.dat";
    private ArrayList<User> users;
    private ArrayList<Mission> missions;
    private User user;
    private MissionPart choice;
    private int reputation;
    private int money;

    public Game(){
        missions = new ArrayList<Mission>();
        addUsers(user);
        addChoice(choice);
    }

    public void addChoice(MissionPart choice){
        System.out.println("Which mission or side quest do you want to pick?");
        Scanner userInput = new Scanner(System.in);
        String response = userInput.nextLine().toUpperCase();
        if(response.contains("MISSION") ||response.contains("SIDEQUEST") ){
            if(choice instanceof MissionPart || choice instanceof SideQuest){
                missions.add(choice);
            }else{
                System.out.println("Invalid. Please choose a valid side quest or mission.");
            }
        }else{
            System.out.println("You have to choose only a mission or a side quest");
        }
    }

    /**
     * Method to start the game.
     */
    public boolean startGame() {
        boolean started = false;
        System.out.println("Click on start or resume.");
        Scanner userInput = new Scanner(System.in);
        String response = userInput.nextLine().toUpperCase();
        if (response.equals("RESUME")) {
            resume();
        } else {
            System.out.println("Welcome to the game.");
        }
        started = true;
        userInput.close();
        return started;
    }
    /** 
     * Method to add user
    */
    public void addUsers(User user){
        users.add(user);
    }
    /**
     * Method to check status for win.
     */
    public void checkWinStatus() {
        if (reputation == 40 || money == 800) {
            System.out.println("You win.");
        } else {
            System.out.println("You do not have enough reputaion points or money to win.");
        }
    }

    /**
     * Method to pause the game.
     */
    public static boolean pause(){
        boolean pause = false;
        try {
            pause = true;
            saveandStoreProgress();
        } catch(Exception e) {
            throw new RuntimeException("Can't pause the game.", e);
        }
        return pause;
    }

    /**
     * Method to resume the game.
     */
    public static void resume(){
        File saveFile = new File(SAVE_FILE);
         if(saveFile.exists()){
            System.out.println("The game continues.");
        }else{
            throw new RuntimeException("Can't resume the game.");
        }
    }

    /**
     * Method to save and store progress of the game.
     */
    public static void saveandStoreProgress(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))){
            out.writeObject("Data Progress");
        } catch (IOException e){
            throw new RuntimeException("Can't save the game. Starting the game again.");
        }  
             
    }
    

    /**
     * Method to end the game.
     */
    public static void endGame() {
        System.out.println("The game is over.");
    }


    /* Trang's comment
        Maybe you can add methods like addUsers()
        Also maybe create an ArrayList to store all users?s
    */
}
