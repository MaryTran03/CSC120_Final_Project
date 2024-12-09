import java.util.Hashtable;

class Node {
    private Hashtable<String, String> node;
    private String name;
    private double minMoney;
    private int minReputation; 

    // Method to get the description by key 
    public String getDescription(String key) { return this.node.get(key); }

    // Constructor
    public Node(String description, String key, String name, int minMoney, int minReputation) {
        this.node = new Hashtable<>();
        this.node.put(key, description);
        this.name = name;
        this.minMoney = minMoney;
        this.minReputation = minReputation;
    }

    // Getters
    public String getName() { return this.name; }
    public double getMinMoney() { return this.minMoney; }
    public int getMinReputation() { return this.minReputation; }

    @Override
    public String toString(){
        return this.name + " : " + this.getDescription(node.keys().nextElement());
    }
}    