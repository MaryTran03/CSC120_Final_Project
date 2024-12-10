
public class Player{
    private String name;
    private int currentReputation;   // Starts at 0
    private double currentMoney;     // Starting amount of money
    private Node currentNode; // Current Mission
   
    // For old players
    public Player(String name, double currentMoney, int currentReputation, Node currentNode) {
        this.currentNode = currentNode;
        this.name = name;
        this.currentReputation = 0;  // Start reputation at 0
        this.currentMoney = currentMoney;   // Starting money at $100
       
    }

    // For new players with default values for current reputation
    public Player(String name, Node currentNode) {
        this.currentNode = currentNode;
        this.name = name;
        this.currentReputation = 0;  // Start reputation at 0
        this.currentMoney = 150;   // Starting money at $150
       
    }
    // Getters and Setters
    public String getName() { return name; }
    public Node getCurrentNode() { return this.currentNode ; }
    public void setCurrentNode(Node currentNode) { this.currentNode = currentNode ; }


    public void setName(String name) { this.name = name; }

    public int getCurrentReputation() { return currentReputation; }
    public void setCurrentReputation(int reputation) { this.currentReputation = reputation; }

    public double getCurrentMoney() { return currentMoney; }
    public void setCurrentMoney(double money) { this.currentMoney = money; }

    public String toString() {
        return "Name" + name + " Reputation: " + currentReputation + " Money: $" + currentMoney;
    }
}
