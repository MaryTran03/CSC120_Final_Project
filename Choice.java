public class Choice {
    private String description;
    private String name;
    private double successProbability;
    private int moneyReward;
    private int reputationReward;
    private Node nextNode; // The next Node if successful

    // Constructor
    public Choice(String name, String description,  int moneyReward, int reputationReward, double successProbability,Node nextNode) {
        this.description = description;
        this.name = name;
        this.successProbability = successProbability;
        this.moneyReward = moneyReward;
        this.reputationReward = reputationReward;
        this.nextNode = nextNode; // The next Node if successful
    }

    // Overload constructor for simple choices in Side Quest
    public Choice(String name, String description,  int moneyReward, int reputationReward, double successProbability) {
        this.description = description;
        this.name = name;
        this.successProbability = successProbability;
        this.moneyReward = moneyReward;
        this.reputationReward = reputationReward;
    }

    // Getters
    public String getName() { return this.name; }
    public String getDescription() { return description; }
    public double getSuccessProbability() { return successProbability; }
    public int getMoneyReward() { return moneyReward; }
    public int getReputationReward() { return reputationReward; }
    public Node getNextNode() { return nextNode; }

    // Setters
    public void setNextNodeNull() { this.nextNode = null;}
}
