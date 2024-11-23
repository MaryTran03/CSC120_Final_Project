public class Choice {
    private String name;
    private int reputation; // The reputation reward. 
    private int money; // The money reward. Can be negative
    private double probability; // Probability of success between 0.0 and 1.0
    private String description;

    public Choice(String name, String description, int money, int reputation, double probability){
        this.name = name;
        this.description = description;
        this.money = money;
        this.reputation = reputation;
        this.probability = probability;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return this.description; }
    public int getReputation() { return this.reputation; }
    public int getMoney() { return this.money; }
    public double getProbability() { return this.probability; }

    // Setters
    public void setReputation(int newReputation) {this.reputation = newReputation; }
    public void setMoney(int newMoney) {this.money = newMoney; }

}
