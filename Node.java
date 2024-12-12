class Node {
    private String name;
    private double minMoney;
    private int minReputation; 
    private String description;
    private int mission;

    
    /**
     * The Node class represents a mission part or a sidequest in the game.
     * that the player must progress through.
     */

    // Constructor
    public Node(String description, String name, int minMoney, int minReputation, int mission) {
        this.name = name;
        this.minMoney = minMoney;
        this.minReputation = minReputation;
        this.description = description;
        this.mission = mission;
    }

    // Overload the constructor for Side Quest which has virtually no minimum reputation and money to unlock. Used to help users gain points to get the next Node
    public Node(String description, String name) {
        this.name = name;
        this.minMoney = -100000;
        this.minReputation = -100000;
        this.description = description;
        this.mission = -1; // Set mission to -1 because SideQuest does not belong to any mission
    }

    // Getters
    public String getName() { return this.name; }
    public int getMission() { return this.mission; }
    public double getMinMoney() { return this.minMoney; }
    public int getMinReputation() { return this.minReputation; }
    public String getDescription() { return this.description; }


    @Override
    public String toString(){
        return this.getName();
    }
}    