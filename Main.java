import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create the first mission to test
        Mission mission1 = new Mission("The Car Heist",100,0, "Vinnie needs a luxury car for a deal and sends you to steal one from a nightclub parking lot. However, the vehicle is parked near security guards, requiring you to approach quietly and hotwire it.");

        MissionPart part1_1 = new MissionPart("Steal a luxury car for Vinnie");
        part1_1.addChoice("Steal the cheaper car", 
            "Lower rewards from Vinnie but with 0% probability of being caught by the guard.", 100, 10, 1.0); // 100% chance
        part1_1.addChoice("Steal the luxury car", 
            "More rewards from Vinnie but with 30% probability being caught by the guard.", 300, 15, 0.7); // 70% chance of success
        mission1.addMissionPart(part1_1);

        MissionPart part1_2 = new MissionPart("Great! Now your dream car is in your hands. You still have one hour before the deadline.");
        part1_2.addChoice("Return the car to Vinnie", 
            "Lower risk of being caught by the police but no fun ... ",100, 10, 0.9); // 70% chance
        part1_2.addChoice("Joyride and race!", 
            "Higher risk of being caught by the police but more fun!", 100, 10, 0.5); // Always happens
        mission1.addMissionPart(part1_2);

        // Create the second misison with at least 10 reputation points to unlock
        
        Mission mission2 = new Mission("The Warehouse Raid", 200, 10, "After proving yourself in the car heist, Vinnie gives you a tougher job. You need to break into the Iron Hounds' warehouse to steal a stash of valuable electronics. The warehouse has guards, so you'll need to choose your approach carefully.");

        // Add Mission Part 2_1: Collecting the Stash
        MissionPart part2_1 = new MissionPart("Infiltrate the Iron Hounds’ warehouse and decide how much to take.");
        part2_1.addChoice("Quick Grab", 
            "Take one item and leave quickly to avoid detection. Low risk.", 
            100, 5, 100); // 0% chance of success. The alarm is triggered regardless
        part2_1.addChoice("Full Stash", 
            "Take as many items as possible for a higher reward. Higher risk of triggering the alarm.", 
            300, 10, 100); // 0% chance of success. The alarm is triggered regardless
        mission2.addMissionPart(part2_1);

        
        // Add Mission Part 2_2: Handling the Alarm (If triggered during Full Stash)
        MissionPart part2_2 = new MissionPart("The alarm was triggered!.");
        part2_2.addChoice("Run and drop some loot", 
            "Escape but lose some money.", 
            -100, -5, 1.0); // Always succeeds but penalizes
        part2_2.addChoice("Fight the guards", 
            "Defeat the guards to escape with all the loot. High risk.", 
            0, 0, 0.5); // 50% chance of success
        mission2.addMissionPart(part2_2);

        // Add Mission Part 2_3: Escape Route
        MissionPart part2_3 = new MissionPart("The guards are alerted! Decide how to escape.");
        part2_3.addChoice("Direct Route", 
            "The fastest way out but guarded by the Iron Hounds. Moderate chance of being caught by rival gang members.", 
            0, 0, 0.7); // 70% chance of success
        part2_3.addChoice("Stealth Route", 
            "A slower but safer way out through the back alleys. Escape unnoticed.", 
            50, 0, 1.0); // 100% chance of success
        mission2.addMissionPart(part2_3);


        // Create Mission 3 with a reputation requirement of 30 to unlock
        Mission mission3 = new Mission("The Final Heist", 30, 0, "After proving your skills in the car heist and warehouse raid, Vinnie trusts you with the crew's biggest job yet: a bank heist. Your role is crucial as the getaway driver. You'll need to navigate through Rivertown while avoiding police and rival gang interference to ensure a clean escape.");

        // Add Mission Part 3_1: Police Chase
        MissionPart part3_1 = new MissionPart("The police are on your tail! Decide how to handle the chase.");
        part3_1.addChoice("Evade the Police", 
            "Use your driving skills to outmaneuver police cars and escape. Risk: Lose part of the loot if caught.", 
            0, 0, 0.7); // 70% chance of success
        part3_1.addChoice("Fight Back", 
            "Use a weapon to disable pursuing police cars. Outcome: Slower but higher chance of escape.", 
            0, -5, 1.0); // 100% chance of success but reputation -5
        mission3.addMissionPart(part3_1);

        // Add Mission Part 3_2: Split the Loot
        MissionPart part3_2 = new MissionPart("The job is done! Now decide how to split the loot.");
        part3_2.addChoice("Stay Loyal", 
            "Share the loot evenly with the crew. Gain their trust and ensure future jobs.", 
            100, 10, 1.0); // 100% chance of success, reputation +10
        part3_2.addChoice("Betray the Crew", 
            "Escape with the entire loot but anger the crew. Risk: Lose reputation and gain money.", 
            500, -30, 1.0); // 100% success, reputation -20, money +500
        mission3.addMissionPart(part3_2);

        
        // Add Side Quest 1: Bad Debt Collection
        SideQuest badDebt = new SideQuest("Bad Debt Collection", "Vinnie isn’t pleased with your recent actions and wants you to prove yourself useful. He assigns you to collect protection fees from two local businesses in Rivertown. Approach them to demand payment, but handle it carefully as being too aggressive may backfire.");
        badDebt.addChoice("Polite", "Approach the business owner with a firm but nonthreatening attitude. This will lower your chance of increasing your reputation and reduce the chance of them resisting or calling the police."
                        , 100, 5, 0.9);
        badDebt.addChoice("Intimidating", "Use intimidation to collect the payment faster and increase your reputation more. However, this increases the risk of the owner calling the police, which could cost you more money in bail if caught."
                        , 100, 5, 0.6);
        
        // Add Side Quest 2: Burger King
        SideQuest burgerKing = new SideQuest("Burger King", "Work at a local shop to save up some money. However, this doesn't increase your reputation points with Vinnie", 14.75);

        // Test
        //mission3.completeMission(200, 30, scanner);
        badDebt.completeSideQuest(scanner);
        burgerKing.completeSideQuest(scanner);


        scanner.close();

    }
}
