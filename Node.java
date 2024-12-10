/**
 * Node class typed by Trang
 */
class Node {
    private String name;
    private double minMoney;
    private int minReputation; 
    private String description;

    // Constructor
    /**
     * Constructor for Node
     * @param description
     * @param name
     * @param minMoney
     * @param minReputation
     */
    public Node(String description, String name, int minMoney, int minReputation) {
        this.name = name;
        this.minMoney = minMoney;
        this.minReputation = minReputation;
        this.description = description;
    }

    // Getters
    /**
     * Name getter
     * @return name of node
     */
    public String getName() { return this.name; }
    /**
     * Accessor for minimum money
     * @return minMoney
     */
    public double getMinMoney() { return this.minMoney; }
    /**
     * Node reputation getter
     * @return minReputation
     */
    public int getMinReputation() { return this.minReputation; }
    /**
     * Description getter
     * @return description of Node
     */
    public String getDescription() { return this.description; }


    @Override
    /**
     * Overridden toString method
     * @return string description
     */
    public String toString(){
        return this.getDescription();
    }
}    