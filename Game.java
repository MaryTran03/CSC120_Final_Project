import java.io.*;
import java.util.Scanner;

public class Game {
    private Mission mission;
    
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
    
    public static void pause(){
        boolean pause = false;
        System.out.println("The game is paused. Do you want to save the progress?");
        Scanner userInput = new Scanner(System.in);
        pause = true;
        String response = userInput.nextLine().toUpperCase();
        if (response.equals("YES")){
           try {
               saveProgress();
            } catch{
                throw new runtimeException("Can't save the game on pause.");
            }
        }
    }

    public static void resume(){
        boolean resumed = false;
        if(){
            System.out.println("The game continues.");
            resumed = true;
        }else{
            throw new runtimeException("Can't resume the game.");
        }
    }

    public static void saveandStoreProgress(){
        
    }

    public static void endGame(){

    }
}
