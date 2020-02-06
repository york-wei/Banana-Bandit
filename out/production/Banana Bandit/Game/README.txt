- Instructions/Game mechanics:

    - Use W, D and ENTER to navigate through menu screens
    - Use W, A, S, D to move character up, down, left, and right
    - Difficulty of levels increase by expanding the "target" radius of the guards and by adding more guards
    - Difficulty maxes out at 8
    - More power-ups are added to the map as levels get more difficult
    - Guards do not target you if you do not enter their target range and do not have the banana
    - Guards will constantly target you if you have the banana
    - Guards cannot follow you through the entrance
    - Power-ups:
        - Potions allow you to become invisible for 2 seconds
        - Watermelons double your speed for 1 second
        - Enderpearls teleport your character to a random tile on the map

- Possible bugs:

    - Changes in resolution of window (832x832) may affect visibility of some graphics elements
    - Collision between player and items may seem off because the player has to be in the center of the tile to pick up an item
    - High-score system expects that there are 10 names in the text file at all times
    - Did not test on other computers; if game is running at less than 60 updates/renders per second due to speed, some animations may look slower and counters will not keep exact time
    - Guards stack on top of each other when they're in the same position - looks like there's only one guard

- Comparison to initial proposal:

    - The number of guards do not stay at 4; changes according to level to add progression in difficulty
    - Player can only gain a point per banana stolen
    - Player gains 1 life after each successful level
    - Guards, when in idle (when the player does not have the banana) will start targeting players if they come within range. This range increases per level
    - Guards, when the player has the banana, will not stop chasing the player, no matter how far away they are