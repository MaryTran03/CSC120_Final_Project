public class Main {
    public static void main(String[] args) {
        // Setting up the game
        GameGraph game = new GameGraph();

        // Create Mission
        Mission carHeist = new Mission("Car Heist");
        Mission warehouseRaid = new Mission("Warehouse Raid");
        Mission bankRob = new Mission("The Final Heist");
        Mission end = new Mission("The End");

        game.addMission(carHeist);
        game.addMission(warehouseRaid);
        game.addMission(bankRob);
        game.addMission(end);
        
        // Create the nodes
        Node mission1_choose_car = new Node("Vinnie needs a luxury car for a deal and sends you to steal one from a nightclub parking lot.", 
        "Car Heist Choose Car", 200, 0,1); 
        Node mission1_repair = new Node("Unfortunately, the car breaks down. Repair it?", 
        "Car Heist Repair Shop", 200, 0,1); 
        Node mission1_repair_shop = new Node("Repair it at the cheaper or more expensive shop?", 
        "Car Heist Repair Shop", 200, 0,1); 
        Node mission1_police_stop = new Node("The car draws the eyes of everyone else, including the police unfortunately.He wants to check your license, and ownership certificate.", 
        "Car Heist Police Stop", 200, 0,1); 
        Node mission1_deliver_joy_ride = new Node("However, you have some free time before delivering it to Vinnie. It is a beautiful day in Florida ..." , 
        "Car Heist Deliver/ Joy Ride", 200, 0,1); 

        Node mission2_collect_stash = new Node("After proving yourself in the car heist, Vinnie gives you a tougher job. You need to break into the Iron Hounds' warehouse to steal a stash of valuable electronics.", 
        "The Warehouse Raid Collect The Stash", 200, 10,2); 
        Node mission2_alarm_triggered = new Node("You took so long, so the alarm is triggered. You have way too many things to carry" , 
        "The Warehouse Raid Alarm Triggered", 200, 10,2); 
        Node mission2_escape_route = new Node("The warehouse has guards, but you are strong and run (kinda) fast!" , 
        "The Warehouse Raid Escape Route", 200, 10,2); 

        Node mission3_police_escape = new Node("After proving your skills in the car heist and warehouse raid, Vinnie trusts you with the crew's biggest job yet—a bank heist. \n Your role is crucial as the getaway driver. The police is behind you but your car is equipped with these tear gas and guns to fight back", 
        "The Final Heist Police Escape", 200, 30,3); 
        Node mission3_split_loot = new Node("You avoided the police and escaped with $5 million in cash ... The crew is wayyyy behind you" , 
        "The final Heist Split Loot", 200, 30,3); 
        Node endNode = new Node("Congrats! You completed all missions!", 
        "The End", 0, 0,4);

        Node badDebt = new Node("Vinnie assigns you to collect protection fees from two local businesses in Rivertown. Earn reputation and money", "Bad Debt Collection");

        // Create the choices
        Choice choice1_1_cheap = new Choice("Steal the cheaper car (Mercedes)", 
        "You are more confident in handling these cars", 
        100, 8, 1, mission1_repair); // 100% chance
        Choice choice1_1_repair_y = new Choice("Repair it", 
        "You are willing to pay out of your own pocket", 
        0, 0, 1, mission1_repair_shop); // 100% chance
        Choice choice1_1_repair_n = new Choice("No repair", 
        "It's just a hiccup why worry. It's still a good car", 
        0, -2, 1, mission1_deliver_joy_ride); // 100% chance
        Choice choice1_1_repair_cheap = new Choice("Choose the average repair shop", 
        "It's still a decend national franchise. Still work but cost less", 
        -100, 10, 1, mission1_deliver_joy_ride); // 100% chance
        Choice choice1_1_repair_luxury = new Choice("Choose the fancier repair shop", 
        "You are willing to pay out of your own pocket more!", 
        -150, 10, 1, mission1_deliver_joy_ride); // 100% chance
        Choice choice1_1_luxury = new Choice("Steal the luxury car (Ferrari)", 
            "Looks cooler but harder to drive", 
            100, 10, 1, mission1_police_stop); // 100% chance of success
        Choice choice1_1_fake = new Choice("Show him your fake certificates", 
            "These fake certificates work in New York, they should work in Florida too?",
            0, 10, 0, mission1_deliver_joy_ride); // 0% chance
        Choice choice1_1_escape = new Choice("Outrun the police", 
        "Show them your racing skills!",
        0, 0, 1, mission1_deliver_joy_ride); // 100% chance
        Choice choice1_2_return = new Choice("Return the car to Vinnie", 
        "Lower risk of being caught by the police but no fun ... ",
        100, 10, 0.9, mission2_collect_stash); // 90% chance
        Choice choice1_2_joyride = new Choice("Joyride and race!", 
        "Higher risk of being caught by the police but more fun!", 
        100, 10, 0.5, mission2_collect_stash); // 50% chance

        Choice choice2_quick = new Choice("Quick Grab", 
            "Take a few item and leave quickly to avoid detection. Low risk.", 
            100, 5, 1, mission2_escape_route); // 0% chance of success. The alarm is triggered regardless
        Choice choice2_full = new Choice("Full Stash", 
            "Take as many items as possible for a higher reward. Higher risk of triggering the alarm.", 
            300, 10, 0.75, mission2_alarm_triggered); // 0% chance of success. The alarm is triggered regardless
        Choice choice2_drop = new Choice("Drop some items", 
            "Drop some items and run faster (earn lower reputation points)", 
            300, 10, 0.95, mission2_escape_route); // 0% chance of success. The alarm is triggered regardless
        Choice choice2_bring = new Choice("Bring all", 
            "Bring them all with you: Slower but keep the reputation point", 
            300, 10, 0.93, mission2_escape_route); // 0% chance of success. The alarm is triggered regardless
        Choice choice2_direct = new Choice("Choose the shorter but guarded way", 
            "High risks high reward", 
            -100, -5, 0.9, mission3_police_escape); // Always succeeds but penalizes
        Choice choice2_indirect = new Choice("Chose the longer but unguarded way", 
            "Lower risks, lower reward", 
            0, 0, 0.7, mission3_police_escape); // 50% chance of success
        
        Choice choice3_evade = new Choice ("Evade the Police", 
        "Use your driving skills to outmaneuver police cars and escape. Risk: Lose part of the loot if caught.", 
        0, 0, 0.7, mission3_split_loot); // 70% chance of success
        Choice choice3_fight = new Choice("Fight Back", 
            "Use a weapon to disable pursuing police cars. Outcome: Slower but higher chance of escape.", 
            0, -5, 0.3, mission3_split_loot); // 30% chance of success but reputation -5
        Choice choice3_loyal = new Choice("Stay Loyal", 
            "Share the loot evenly with the crew. Gain their trust and ensure future jobs.", 
            100, 10, 1.0, endNode); // 100% chance of success, reputation +10
        Choice choice3_betray = new Choice("Betray the Crew", 
            "Escape with the entire loot but anger the crew. Risk: Lose reputation and gain money.", 
            500, -30, 1.0, endNode); // 100% success, reputation -20, money +500

        Choice pause = new Choice("Pause","Pause and end the game early",endNode);
        Choice polite = new Choice("Polite", "Firm but nonthreatening attitude. Earn less money, less reputation, lower chance of them calling the pollice"
        , 100, 10, 0.9);
        Choice intimidating = new Choice("Intimidating", "Earn more money, more reputation but higher risk of the owner calling the police"
        , 150, 15, 0.7);


        // Add Node to missions
        carHeist.addMissionNode(mission1_choose_car);
        carHeist.addMissionNode(mission1_repair);
        carHeist.addMissionNode(mission1_repair_shop);
        carHeist.addMissionNode(mission1_police_stop);
        carHeist.addMissionNode(mission1_deliver_joy_ride);

        warehouseRaid.addMissionNode(mission2_collect_stash);
        warehouseRaid.addMissionNode(mission2_alarm_triggered);
        warehouseRaid.addMissionNode(mission2_escape_route);

        bankRob.addMissionNode(mission3_police_escape);
        bankRob.addMissionNode(mission3_split_loot);

        end.addMissionNode(endNode);

        // Add Nodes to game
        game.addNode(mission1_choose_car);
        game.addNode(mission1_repair);
        game.addNode(mission1_repair_shop);
        game.addNode(mission1_police_stop);
        game.addNode(mission1_deliver_joy_ride);

        game.addNode(mission2_collect_stash);
        game.addNode(mission2_alarm_triggered);
        game.addNode(mission2_escape_route);

        game.addNode(mission3_police_escape);
        game.addNode(mission3_split_loot);
        game.addNode(badDebt);
        game.addSideQuest(badDebt);

        // End Node must be the last Node
        game.addNode(endNode);


        // Adding choices as edges with probabilities
        game.addEdge(mission1_choose_car, choice1_1_cheap);
        game.addEdge(mission1_choose_car, choice1_1_luxury);
        game.addEdge(mission1_repair, choice1_1_repair_y);
        game.addEdge(mission1_repair, choice1_1_repair_n);
        game.addEdge(mission1_repair_shop, choice1_1_repair_cheap);
        game.addEdge(mission1_repair_shop, choice1_1_repair_luxury);
        game.addEdge(mission1_police_stop, choice1_1_fake);
        game.addEdge(mission1_police_stop, choice1_1_escape);
        game.addEdge(mission1_deliver_joy_ride, choice1_2_joyride);
        game.addEdge(mission1_deliver_joy_ride, choice1_2_return);

        game.addEdge(mission2_collect_stash, choice2_quick);
        game.addEdge(mission2_collect_stash, choice2_full);
        game.addEdge(mission2_alarm_triggered, choice2_bring);
        game.addEdge(mission2_alarm_triggered, choice2_drop);
        game.addEdge(mission2_escape_route, choice2_direct);
        game.addEdge(mission2_escape_route, choice2_indirect);
        
        game.addEdge(mission3_police_escape, choice3_evade);
        game.addEdge(mission3_police_escape, choice3_fight);
        game.addEdge(mission3_split_loot, choice3_loyal);
        game.addEdge(mission3_split_loot, choice3_betray);

        game.addEdge(badDebt, polite);
        game.addEdge(badDebt, intimidating);

        // Add the option to pause the game and lead directly to the end of the game
        for (Node node : game.getOrderNodes()){
            game.addEdge(node, pause);
        }


        // Run this if you want to see all the choices 
        
        // for (Node node: game.getOrderNodes()){
        //     for (Choice choice: game.getChoices(node)){
        //         System.out.println("\n" + node.getName());
        //         System.out.println(choice);
        //     }
        // }

        // Run the game

        Player player = game.startGame();
        if (player.getCurrentNode().getName() == "The End"){
            System.out.println("You already completed the game!");
            System.out.println("Do you want to start again? You will lose your current money and reputation points. \n1: Yes\n2: No");
            int input = game.getUserInput(game.getScanner(),1,2);
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
