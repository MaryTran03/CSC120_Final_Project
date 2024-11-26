import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user's name
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Start the game with an initial amount of money (can be customized)
        double initialMoney = 200; // Starting money for example
        Player player = new Player(name, initialMoney);
        
        // Initialize the game and start
        Game game = new Game(player);
        game.startGame(player);

        // Close the scanner to avoid resource leaks
        scanner.close();
    }
}
