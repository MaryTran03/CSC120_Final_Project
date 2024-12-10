/**
 * Player class typed by Nimco
 */
public class Player{
    private String name;
    private int currentReputation;   // Starts at 0
    private double currentMoney;     // Starting amount of money
    private Node currentNode; // Current Mission
   
    /**
     * Constructor for old players
     * @param name
     * @param currentMoney
     * @param currentReputation
     * @param currentNode
     */
    public Player(String name, double currentMoney, int currentReputation, Node currentNode) {
        this.currentNode = currentNode;
        this.name = name;
        this.currentReputation = 0;  // Start reputation at 0
        this.currentMoney = currentMoney;   // Starting money at $100
       
    }

    /**
     * Constructor for new players with default values for current reputation
     * @param name
     * @param currentNode
     */
    public Player(String name, Node currentNode) {
        this.currentNode = currentNode;
        this.name = name;
        this.currentReputation = 0;  // Start reputation at 0
        this.currentMoney = 150;   // Starting money at $150
       
    }
    // Getters and Setters
    /**
     * Getter for name
     * @return
     */
    public String getName() { return name; }
    /**
     * Node getter
     * @return currentNode
     */
    public Node getCurrentNode() { return this.currentNode ; }
    /**
     * Node setter
     * @param currentNode
     */
    public void setCurrentNode(Node currentNode) { this.currentNode = currentNode ; }

    /**
     * Name setter
     * @param name
     */
    public void setName(String name) { this.name = name; }
    /**
     * Reputation getter
     * @return currentReputation
     */
    public int getCurrentReputation() { return currentReputation; }
    /**
     * Reputation setter
     * @param reputation
     */
    public void setCurrentReputation(int reputation) { this.currentReputation = reputation; }
    /**
     * Current money accessor
     * @return currentMoney
     */
    public double getCurrentMoney() { return currentMoney; }
    /**
     * Method changes currentMoney
     * @param money
     */
    public void setCurrentMoney(double money) { this.currentMoney = money; }
    /**
     * To string method
     * @return toString of player information
     */
    public String toString() {
        return "Name" + name + " Reputation: " + currentReputation + " Money: $" + currentMoney;
    }
}
