public abstract class BaseMission {
    protected String name;
    protected String description;

    public BaseMission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public void displayDetails() {}

}
