/**
 * The Node class represents a specific part of a mission or a side quest in the game.
 * A Node can have associated requirements like minimum money and reputation needed to access it.
 * It also contains descriptive information about the current game scenario.
 */
class Node {
    private String name;             // The name of the Node
    private double minMoney;         // The minimum amount of money required to access the Node
    private int minReputation;       // The minimum reputation points required to access the Node
    private String description;      // Description of the Node (e.g., game instructions or story progression)
    private int mission;             // The mission index to which this Node belongs (-1 for side quests)

    /**
     * Constructs a Node object representing a mission part.
     *
     * @param description   The description of the Node (e.g., story or task details).
     * @param name          The name of the Node.
     * @param minMoney      The minimum money required to access the Node.
     * @param minReputation The minimum reputation points required to access the Node.
     * @param mission       The mission index that this Node belongs to.
     */
    public Node(String description, String name, int minMoney, int minReputation, int mission) {
        this.name = name;
        this.minMoney = minMoney;
        this.minReputation = minReputation;
        this.description = description;
        this.mission = mission;
    }

    /**
     * Overloaded constructor for a Side Quest Node with no access requirements.
     * This constructor sets default values for minimum money and reputation to allow universal access.
     *
     * @param description The description of the Side Quest Node.
     * @param name        The name of the Side Quest Node.
     */
    public Node(String description, String name) {
        this.name = name;
        this.minMoney = -100000;          // Default value indicating no minimum money required
        this.minReputation = -100000;     // Default value indicating no minimum reputation required
        this.description = description;
        this.mission = -1;                // Indicates that the Node does not belong to any specific mission
    }

    /**
     * Gets the name of the Node.
     *
     * @return The name of the Node.
     */
    public String getName() { return this.name; }

    /**
     * Gets the mission index to which this Node belongs.
     *
     * @return The mission index, or -1 if it is a Side Quest Node.
     */
    public int getMission() { return this.mission; }

    /**
     * Gets the minimum money required to unlock this Node.
     *
     * @return The minimum money required.
     */
    public double getMinMoney() { return this.minMoney; }

    /**
     * Gets the minimum reputation points required to unlock this Node.
     *
     * @return The minimum reputation required.
     */
    public int getMinReputation() { return this.minReputation; }

    /**
     * Gets the description of the Node.
     *
     * @return The description of the Node.
     */
    public String getDescription() { return this.description; }

    /**
     * Overrides the toString method to return the name of the Node.
     *
     * @return The name of the Node as a String.
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
