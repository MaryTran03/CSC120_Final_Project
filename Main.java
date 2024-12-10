public class Main {
    public static void main(String[] args) {
        // Setting up the game
        GameGraph game = new GameGraph();

        // Create the nodes
        Node mission1_1 = new Node("Vinnie needs a luxury car for a deal and sends you to steal one from a nightclub parking lot.", 
        "Car Heist Part 1", 200, 0); 
        Node mission1_2 = new Node("However, you have some free time before delivering it to Vinnie. It is a beautiful day in Florida ..." , 
        "Car Heist Part 2", 200, 0); 
        Node mission2_1 = new Node("After proving yourself in the car heist, Vinnie gives you a tougher job. You need to break into the Iron Hounds' warehouse to steal a stash of valuable electronics.", 
        "The Warehouse Raid Part 1", 200, 10); 
        Node mission2_2 = new Node("The warehouse has guards, but you are strong and run (kinda) fast!" , 
        "The Warehouse Raid Part 2", 200, 10); 
        Node mission3_1 = new Node("After proving your skills in the car heist and warehouse raid, Vinnie trusts you with the crew's biggest job yet—a bank heist. \n Your role is crucial as the getaway driver. The police is behind you but your car is equipped with these tear gas and guns to fight back", 
        "The Final Heist Part 1", 200, 30); 
        Node mission3_2 = new Node("You avoided the police and escaped with $5 million in cash ... The crew is wayyyy behind you" , 
        "The final Heist Part 2", 200, 30); 
        Node endNode = new Node("Congrats! You completed all missions!", 
        "The End", 0, 0);

        // Adding mission parts as nodes
        game.addNode(mission1_1);
        game.addNode(mission1_2);
        game.addNode(mission2_1);
        game.addNode(mission2_2);
        game.addNode(mission3_1);
        game.addNode(mission3_2);
        game.addNode(endNode);

        // Add choices
        Choice choice1_1_cheap = new Choice("Steal the cheaper car", 
            "Lower rewards from Vinnie but with lower probability of being caught by the guard.", 
            100, 10, 1, mission1_2); // 100% chance
        Choice choice1_1_luxury = new Choice("Steal the luxury car", 
            "More rewards from Vinnie but with higher probability being caught by the guard.", 
            300, 15, 0.6, mission1_2); // 70% chance of success
        Choice choice1_2_return = new Choice("Return the car to Vinnie", 
            "Lower risk of being caught by the police but no fun ... ",
            100, 10, 0.9, mission2_1); // 70% chance
        Choice choice1_2_joyride = new Choice("Joyride and race!", 
            "Higher risk of being caught by the police but more fun!", 
            100, 10, 0.5, mission2_1); // Always happens


        Choice choice2_1_quick = new Choice("Quick Grab", 
            "Take one item and leave quickly to avoid detection. Low risk.", 
            100, 5, 1, mission2_2); // 0% chance of success. The alarm is triggered regardless
        Choice choice2_1_full = new Choice("Full Stash", 
            "Take as many items as possible for a higher reward. Higher risk of triggering the alarm.", 
            300, 10, 0.75, mission2_2); // 0% chance of success. The alarm is triggered regardless
        Choice choice2_2_run = new Choice("Run and drop some loot", 
            "Escape but lose some money.", 
            -100, -5, 1.0, mission3_1); // Always succeeds but penalizes
        Choice choice2_2_fight = new Choice("Fight the guards", 
            "Defeat the guards to escape with all the loot. High risk.", 
            0, 0, 0.5, mission3_1); // 50% chance of success

        Choice choice3_1_evade = new Choice ("Evade the Police", 
            "Use your driving skills to outmaneuver police cars and escape. Risk: Lose part of the loot if caught.", 
            0, 0, 0.7, mission3_2); // 70% chance of success
        Choice choice3_1_fight = new Choice("Fight Back", 
            "Use a weapon to disable pursuing police cars. Outcome: Slower but higher chance of escape.", 
            0, -5, 0.3, mission3_2); // 30% chance of success but reputation -5
        Choice choice3_2_loyal = new Choice("Stay Loyal", 
            "Share the loot evenly with the crew. Gain their trust and ensure future jobs.", 
            100, 10, 1.0, endNode); // 100% chance of success, reputation +10
        Choice choice3_2_betray = new Choice("Betray the Crew", 
            "Escape with the entire loot but anger the crew. Risk: Lose reputation and gain money.", 
            500, -30, 1.0, endNode); // 100% success, reputation -20, money +500


        // Adding choices as edges with probabilities
        game.addEdge(mission1_1, choice1_1_cheap);
        game.addEdge(mission1_1, choice1_1_luxury);
        game.addEdge(mission1_2, choice1_2_joyride);
        game.addEdge(mission1_2, choice1_2_return);

        game.addEdge(mission2_1, choice2_1_full);
        game.addEdge(mission2_1, choice2_1_quick);
        game.addEdge(mission2_2, choice2_2_run);
        game.addEdge(mission2_2, choice2_2_fight);

        game.addEdge(mission3_1, choice3_1_evade);
        game.addEdge(mission3_1, choice3_1_fight);
        game.addEdge(mission3_2, choice3_2_loyal);
        game.addEdge(mission3_2, choice3_2_betray);

        // Add the SideQuest
        SideQuest badDebt = new SideQuest("Bad Debt Collection", "Vinnie isn’t pleased with your recent actions and wants you to prove yourself useful. He assigns you to collect protection fees from two local businesses in Rivertown. Approach them to demand payment, but handle it carefully as being too aggressive may backfire.");
        badDebt.addChoice("Polite", "Approach the business owner with a firm but nonthreatening attitude. This will lower your chance of increasing your reputation and reduce the chance of them resisting or calling the police."
                        , 100, 10, 0.9);
        badDebt.addChoice("Intimidating", "Use intimidation to collect the payment faster and increase your reputation more. However, this increases the risk of the owner calling the police, which could cost you more money in bail if caught."
                        , 150, 15, 0.7);
        
        // Add Side Quest 2: Burger King
        SideQuest burgerKing = new SideQuest("Burger King", "Work at a local shop to save up some money. \n However, this doesn't increase your reputation points with Vinnie", 14.75);
        game.addSideQuest(burgerKing);
        game.addSideQuest(badDebt);

        // Run the game

        Player player = game.startGame();
        if (player.getCurrentNode().getName() == "The End"){
            System.out.println("You already completed the game!");
            System.out.println("Do you want to start again? You will lose your current money and reputation points. \n1: Yes\n2: No");
            int input = SideQuest.validateInput(game.getScanner(),1,2);
            if (input == 1){
                // Resetting progress
                player.setCurrentMoney(200);
                player.setCurrentReputation(0);
                player.setCurrentNode(game.getOrderNodes().get(0));
                
                // Resume
                game.describeGame();
                game.traverse(player);
                game.endGame();        
            } else {
                System.out.println("Come back if you want to replay the game");

            }
        } else {
            game.describeGame();
            game.traverse(player);
            game.endGame();    
        }    
    }
}
