import java.util.ArrayList;

/**
 * The Mission class represents a single mission in the game.
 * Each mission consists of a series of nodes (tasks or decisions) 
 * that the player must progress through.
 */
public class Mission {
    private String name; // The name of the mission
    private ArrayList<Node> missionNodes; // The list of nodes (parts) in this mission

    /**
     * Constructor to initialize a new mission with the specified name.
     * 
     * @param name The name of the mission.
     */
    public Mission(String name) {
        this.name = name;
        this.missionNodes = new ArrayList<>();
    }

    /**
     * Adds a node to the mission.
     * 
     * @param node The node to be added to this mission.
     */
    public void addMissionNode(Node node) {
        this.missionNodes.add(node);
    }

    /**
     * @return The name of the mission.
     */
    public String getName() { return this.name; }

    /**
     * @return The list of nodes (mission parts) in this mission.
     */
    public ArrayList<Node> getMissionNodes() { return this.missionNodes; }
}
