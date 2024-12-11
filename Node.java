class Node {
    private String name;
    private double minMoney;
    private int minReputation; 
    private String description;
    private int mission;

    // Constructor
    public Node(String description, String name, int minMoney, int minReputation, int mission) {
        this.name = name;
        this.minMoney = minMoney;
        this.minReputation = minReputation;
        this.description = description;
        this.mission = mission;
    }

    public Node(String description, String name) {
        this.name = name;
        this.minMoney = 0;
        this.minReputation = 0;
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
        return this.getDescription();
    }
}    