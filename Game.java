import java.io.*;
import java.util.Scanner;

public class Game {
    private Mission mission;
    private static final String save_file = "save_game.txt";
    /**
     * Method to check status for win.
     */
    public void checkWinStatus(){
        if (points == 40 || money == 800){
            System.out.println("You win.");
        } else{
            
        }
    } 
    
    public static void pause(){
        boolean pause = false;
        System.out.println("The game is paused. Do you want to save the progress?");
        Scanner userInput = new Scanner(System.in);
        String response = userInput.nextLine().toUpperCase();
        if (response.equals("YES")){
           try {
                pause = true;
            } catch (Exception e) {
 
            }
        }
    }

    public static void resume(){
        System.out.println("The game continues.");
    }
}
