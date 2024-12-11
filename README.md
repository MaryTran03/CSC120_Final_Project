## Design justification
- We decided to use a Map<Node (Mission Part), List<Choice>> as the core structure of our game. The Map allows us to dynamically associate each mission part (node) with its corresponding choices, enabling easy retrieval of choices for a specific mission. This structure eliminates the need for separate hardcoded relationships, simplifying the logic for traversing between nodes and executing choices. An alternative design involving separate classes for MissionPart and Choice. However, it is more complex. For example, managing transitions and conditions for each choice would require additional logic layers and dependencies between classes. Using a Map ensures the relationships remain straightforward and adaptable, while also improving runtime efficiency by leveraging the constant-time lookup of hash-based maps. 

- We also considered creating a SideQuest subclass for Node. We ended up with overloading the Node constructor and set default values for certain paramters (minMoney = minReputation = 0). In fact, when handling Side Quest in other methods in Game, we disregard these requirements. 

## Additional Reflection Questions
- What was your overall approach to tackling this project?
    Our overall approach was to break the project into smaller, manageable components and tackle them incrementally. We started by defining the core classes and their relationships (e.g., Player, Node, Choice, Mission, and SideQuest) to establish a solid foundation. Since our first architecture diagram, we have cut 2 repetitive classes. Next, we focused on implementing the game logic using a GameGraph, ensuring that the traversal, input validation, and state management worked as expected. We regularly tested each feature to identify logic errors and fix bugs early in the development process. 

- What new thing(s) did you learn / figure out in completing this project?
    - Through this project, we learned how to design and implement graph-based game structures to represent dynamic, interconnected gameplay. We also deepened our understanding of Java collections like HashMap, ArrayList, and Set for storing and managing nodes, choices, and player data. It was challengeing we incorporate probability and use each Choice object as an edge.
    - Additionally, we improved our ability to handle file I/O for saving and resuming game progress, ensuring persistence across sessions.

- Is there anything that you wish you had implemented differently?
    - Be more mindful when collaborating in Github and deal with merge conflicts
    - We wish we had initially integrated more dynamic methods for validating and transitioning between nodes. Early on, we relied on repetitive logic (loops and classes) for handling side quests and progression, which was less efficient than using Maps (Nodes and Edges).
    - We wish we had agreed on a consistent method to deal with user input (by asking for 1,2,3 to avoid different spelling errors)

- If you had unlimited time, what additional features would you implement?
    - Add a graphical user interface (GUI) to improve user interaction and visualization.
    - Implement a branching story system with consequences that affect future missions based on player choices.
    - Add multiplayer functionality where players can collaborate or compete in missions.
    - Develop a statistics tracker to analyze player progress and decisions over time.
    - Add timed missions instead of simplying choose option 1 or 2 to proceed

- What was the most helpful piece of feedback you received while working on your project? Who gave it to you?
    The most helpful feedback came from a peer, who suggested consolidating redundant code into reusable methods, especially for handling side quests and node transitions. This advice significantly improved the maintainability of the codebase and allowed us to focus on expanding the game's features rather than managing repetitive logic.

- If you could go back in time and give your past self some advice about this project, what hints would you give?
    - Use Github as a tool to collaborate with 2 other teammates
    - Regularly test smaller components rather than waiting to test the system as a whole.
    - Keep the player experience in mind when designing input and feedback systems.
    - Use detailed comments and documentation throughout development to save time when revisiting complex parts of the code.

- If you worked with a team: please comment on how your team dynamics influenced your experience working on this project.
    Luckily our team had some prior experience using Git branches; however, the workflow still has room for improvement. One member focuses more on implementing user-related features, one member focuses more on the backend logic of map, one member focuses on formatting, logic of the storyline, and catching exceptions