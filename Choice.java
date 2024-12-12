/**
 * The Choice class represents a single decision a player can make within a node.
 * Each choice has a name, description, rewards (money and reputation), 
 * a success probability, and an optional next node if the choice is successful.
 */
public class Choice {
    private String description; 
    private String name; 
    private double successProbability; 
    private int moneyReward; 
    private int reputationReward; 
    private Node nextNode; // The next node to transition to if the choice is successful

    /**
     * Constructor to initialize a full-featured choice with rewards, success probability, and next node.
     * 
     * @param name The name of the choice.
     * @param description The description of the choice.
     * @param moneyReward The money reward if the choice is successful.
     * @param reputationReward The reputation reward if the choice is successful.
     * @param successProbability The probability of success (0.0 to 1.0).
     * @param nextNode The next node to transition to if the choice is successful.
     */
    public Choice(String name, String description, int moneyReward, int reputationReward, double successProbability, Node nextNode) {
        this.description = description;
        this.name = name;
        this.successProbability = successProbability;
        this.moneyReward = moneyReward;
        this.reputationReward = reputationReward;
        this.nextNode = nextNode;
    }

    /**
     * Overloaded constructor to create a choice without specifying a next node. These are the Choices for SideQuests 
     * where the logic of the game will determines the next Node.
     * 
     * @param name The name of the choice.
     * @param description The description of the choice.
     * @param moneyReward The money reward if the choice is successful.
     * @param reputationReward The reputation reward if the choice is successful.
     * @param successProbability The probability of success (0.0 to 1.0).
     */
    public Choice(String name, String description, int moneyReward, int reputationReward, double successProbability) {
        this.description = description;
        this.name = name;
        this.successProbability = successProbability;
        this.moneyReward = moneyReward;
        this.reputationReward = reputationReward;
    }

    /**
     * Overloaded constructor to create a choice with no rewards and direct transition to the next node.
     * 
     * @param name The name of the choice.
     * @param description The description of the choice.
     * @param nextNode The next node to transition to after this choice.
     */
    public Choice(String name, String description, Node nextNode) {
        this.name = name;
        this.description = description;
        this.nextNode = nextNode;
        this.moneyReward = 0;
        this.reputationReward = 0;
        this.successProbability = 1.0;
    }

    /**
     * @return The name of the choice.
     */
    public String getName() { return this.name; }

    /**
     * @return The description of the choice.
     */
    public String getDescription() { return description; }

    /**
     * @return The probability of success for this choice.
     */
    public double getSuccessProbability() { return successProbability; }

    /**
     * @return The money reward for this choice.
     */
    public int getMoneyReward() { return moneyReward; }

    /**
     * @return The reputation reward for this choice.
     */
    public int getReputationReward() { return reputationReward; }

    /**
     * @return The next node to transition to if the choice is successful.
     */
    public Node getNextNode() { return nextNode; }

    
    /**
     * Overload the println method
     * @return A String that contains the most relevant information for each choice
     */
    public String toString(){
        return this.name + "---- Money Reward: $" + this.moneyReward + " Reputation Reward: $" + this.reputationReward + " Probability of success :" + this.getSuccessProbability() + " If successful, go to " + this.getNextNode();
    }
}
