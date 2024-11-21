import java.io.*;
import java.util.Scanner;

public class Game {
    private Mission mission;
    /**
     * Method to start the game.
     */
    public static void startGame(){
        boolean started = false;
        System.out.println("Click on start or resume.");
        Scanner userInput = new Scanner(System.in);
        String response = userInput.nextLine().toUpperCase();
        if (response.equals("RESUME")){
            this.resume();
        }else{
            System.out.println("Welcome to the game.");
        }
    }
    /**
     * Method to check status for win.
     */
    public void checkWinStatus(){
        if (points == 40 || money == 800){
            System.out.println("You win.");
        } else {
            System.out.println("You do not have enough reputaion points or money to win.");
        }
    } 
    
     /**
     * Method to pause the game.
     */
    public static void pause(){
        boolean pause = false;
        try {
            saveandStoreProgress();
            System.in.read();
        } catch {
            throw new runtimeException("Can't pause the game.");
        }
    }

    /**
     * Method to resume the game.
     */
    public static void resume(){
        boolean resumed = false;
        if(){
            System.out.println("The game continues.");
            resumed = true;
        }else{
            throw new runtimeException("Can't resume the game.");
        }
    }

    /**
     * Method to save and store progress of the game.
     */
    public static void saveandStoreProgress(){
        try{
        } catch {
             throw new runtimeException("Can't save the game.");
        }
    }

     /**
     * Method to end the game.
     */
    public static void endGame(){

    }
}
