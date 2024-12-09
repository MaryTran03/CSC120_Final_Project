# CSC120_Final_Project Description

Win the game: Finish all 3 missions. There are two mission parts within a mission

## Mission 1: The Car Heist
Objective: Prove yourself to Vinnie by stealing a high-end car from a wealthy neighborhood.
Description: Vinnie needs a luxury car for a deal and sends you to steal one from a nightclub parking lot. However, the vehicle is parked near security guards, requiring you to approach quietly and hotwire it. Vinnie 

### Requirements: 
$200, 0 reputation point

### Key Choices:
- Steal the cheaper car or more expensive car
    Probability of success: 1.00 (assuming zero risk of being caught by the police)
    Reputation + 10 
    Money +$100
- Joyride and Racing: 
    Probability of success: 0.5 (racing increases the risk of being caught by the police)
(success) Avoid the police + return the car
    Reputation + 10
    Money +$100
(caught) Paid bail and lose reputation
    Money: -$200
    Reputation: -10


## Mission 2: The Warehouse Raid
Objective: Infiltrate the Iron Hounds’ warehouse and steal valuable goods for Vinnie. Need 10 reputation points to unlock
Description: After proving yourself in the car heist, Vinnie gives you a tougher job. You need to break into the Iron Hounds' warehouse to steal a stash of valuable electronics. The warehouse has guards, so you'll need to choose your approach carefully.

### Requirements: 
$200, 10 reputation point


### Key Choices
- Collecting the Stash:
    - Quick Grab: Take one item and leave quickly to avoid detection.
    Outcome: Money +$100, Reputation +5, low risk of detection.
    - Full Stash: Take as many items as possible for a higher reward.
    Outcome: Money +$300, Reputation +10, higher risk of triggering the alarm.
        If the alarm triggers: Police arrive, leading to a chase with potential penalties (Money -$100 if caught).
- Escape Route:
    - Direct Route: The fastest way out but guarded by the Iron Hounds.
    Outcome if successful: You escape quickly with minimal loss.
    Risk: Moderate chance of encountering rival gang members.
        If caught: Money -$50, Reputation -5.
    - Stealth Route: A slower but safer way out through the back alleys.
    Outcome: You escape unnoticed.
    Reward: Money +$50 for avoiding detection.

## Mission 3: The Final Heist
Objective: Pull off a bank heist with Vinnie’s crew as the getaway driver. Description: After proving your skills in the car heist and warehouse raid, Vinnie trusts you with the crew's biggest job yet—a bank heist. Your role is crucial as the getaway driver. You'll need to navigate through Rivertown while avoiding police and rival gang interference to ensure a clean escape.

### Requirements: 
$200, 30 reputation point


### Key Choices
- Police Chase:
    Evade the Police: Use your driving skills to outmaneuver police cars and escape.
        Outcome: If successful, you get away cleanly.
        Risk: If caught, you lose part of the loot (Money -$100) and Reputation -10.
        Fight Back: Use a weapon to disable pursuing police cars.
    Outcome: Slower but ensures a higher chance of escape.
        Risk: Lowers Reputation -5 (seen as reckless).
- Split the Loot:
    Stay Loyal: Share the loot evenly with the crew.
        Outcome: Reputation +10 and Vinnie’s trust, ensuring future jobs with the crew.
    Betray the Crew: Escape with the entire loot.
        Outcome: Money +$500 but Reputation -20 (Vinnie and his crew will hunt you down in future missions).


## Side Quests (to either increase reputation points or money): 
- Side Quests are available if the user cant unlock the next sub-missions

## Side Quest 1: Debt Collection 
Objective: Help Vinnie collect overdue payments from local business owners who owe him money. Description: Vinnie isn’t pleased with your recent actions and wants you to prove yourself useful. He assigns you to collect “protection fees” from two local businesses in Rivertown. Approach them to demand payment, but handle it carefully—being too aggressive may backfire.

### Key Choices:
- Polite but Firm: Approach the business owner with a firm but nonthreatening attitude. This will lower your chance of increasing your reputation and reduce the chance of them resisting or calling the police.
- Intimidating: Use intimidation to collect the payment faster. However, this increases the risk of the owner calling the police, which could cost you more money in bail if caught.
Outcomes:

Successful Collection:
- Polite but Firm: Reputation +5, Money +$100.
- Intimidating: Reputation +10, Money +$150, with a 20% chance of police involvement (Money -$100 for bail).
- Failed Collection (if police are called): Money -$100, Reputation -5 (for getting caught).

## Side Quest 2: Burger King
- Earnings: $15/hr
- Total money earned: 15 hours worked + no increase in reputation

### Key Choices: 
- How many hours do you want to work? 

### Ideas for the code reviews
- Method for the repeated lines, consider overloading 
- Different missions: instances as opposed to subclasses
