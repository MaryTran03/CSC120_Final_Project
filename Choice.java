/**
 * Choice Class
 */
public class Choice {
    private String description;
    private String name;
    private double successProbability;
    private int moneyReward;
    private int reputationReward;
    private Node nextNode; // The next Node if successful

    /**
     * Constructor for Choice
     * @param name
     * @param description
     * @param moneyReward
     * @param reputationReward
     * @param successProbability
     * @param nextNode
     */
    public Choice(String name, String description,  int moneyReward, int reputationReward, double successProbability,Node nextNode) {
        this.description = description;
        this.name = name;
        this.successProbability = successProbability;
        this.moneyReward = moneyReward;
        this.reputationReward = reputationReward;
        this.nextNode = nextNode; // The next Node if successful
    }
    /**
     * Overload constructor for simple choices in Side Quest
     * @param name
     * @param description
     * @param moneyReward
     * @param reputationReward
     * @param successProbability
     */
    public Choice(String name, String description,  int moneyReward, int reputationReward, double successProbability) {
        this.description = description;
        this.name = name;
        this.successProbability = successProbability;
        this.moneyReward = moneyReward;
        this.reputationReward = reputationReward;
    }

    // Getters
    /**
     * Getter for Name
     * @return name
     */
    public String getName() { return this.name; }
    /**
     * Description getter
     * @return description of choice
     */
    public String getDescription() { return description; }
    /**
     * Predictor getter
     * @return probability of success
     */
    public double getSuccessProbability() { return successProbability; }
    /**
     * Money reward getter
     * @return moneyReward
     */
    public int getMoneyReward() { return moneyReward; }
    /**
     * Reputation getter
     * @return reputationReward
     */
    public int getReputationReward() { return reputationReward; }
    /**
     * Node getter
     * @return nextNode
     */
    public Node getNextNode() { return nextNode; }

    // Setters
    /** 
     * Node setter
     */ 
    public void setNextNodeNull() { this.nextNode = null;}
}
