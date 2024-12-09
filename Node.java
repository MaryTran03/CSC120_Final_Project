class Node {
    private String name;
    private double minMoney;
    private int minReputation; 
    private String description;

    // Constructor
    public Node(String description, String key, String name, int minMoney, int minReputation) {
        this.name = name;
        this.minMoney = minMoney;
        this.minReputation = minReputation;
        this.description = description;
    }

    // Getters
    public String getName() { return this.name; }
    public double getMinMoney() { return this.minMoney; }
    public int getMinReputation() { return this.minReputation; }
    public String getDescription() { return this.description; }


    @Override
    public String toString(){
        return this.getDescription();
    }
}    