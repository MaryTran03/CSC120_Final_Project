import java.io.*;
import java.util.Scanner;

public class Game {
    private static final String save_file = "save_game.txt";
    /**
     * Method to check status for win.
     */
    public void checkWinStatus(){
        if( ){
            System.out.println("You win.");
        } else if(){

        }else{
            
        }
    } 
    
    public static void pause(){
        System.out.println("The game is paused. Do you want to save the progress?");
        Scanner userInput = new Scanner(System.in);
        String response = userInput.nextLine().toUpperCase();
        if(response.equals("YES")){
           try {
                
            } catch (Exception e) {
 
            }
        }else{

        }
    }

    public static void resume(){
        System.out.println("The game continues.");
    }
}
