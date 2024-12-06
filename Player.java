
public class Player{
    private String name;
    private int reputation;   // Starts at 0
    private double money;     // Starting amount of money
   
    public Player(String name, double money) {
        this.name = name;
        this.reputation = 0;  // Start reputation at 0
        this.money = money;   // Starting money
       
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getReputation() { return reputation; }
    public void setReputation(int reputation) { this.reputation = reputation; }

    public double getMoney() { return money; }
    public void setMoney(double money) { this.money = money; }


    public void printStatus() {
        System.out.println("Name: " + name);
        System.out.println("Reputation: " + reputation);
        System.out.println("Money: " + money);
    }
}
