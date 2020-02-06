package Game.Map;

import Game.Utilities.Position;

public class Tile implements Comparable<Tile> {

    private Position position; //Position of the center of the tile
    private int type; //The type of tile; wall, path, or intersection

    //Booleans for items
    public boolean hasItem = false;
    public boolean chest = false;
    public boolean banana = false;
    public boolean key = false;
    public boolean potion = false;
    public boolean watermelon = false;
    public boolean enderpearl = false;

    //The 'tilemap' position
    public int tileX;
    public int tileY;

    //Variables used for pathfinding
    public int fCost;
    public int gCost;
    public int hCost;
    public Tile parent;

    //Constructor
    public Tile (int x, int y, int tileX, int tileY, int type) {

        this.position = new Position(x, y);
        this.type = type;
        this.tileX = tileX;
        this.tileY = tileY;

        //Where the banana is placed
        if(tileX == 13 && tileY == 1){
            hasItem = true;
            chest = true;
        }

    }

    //Constructor to copy a tile
    public Tile(Tile target) {
        this.position = new Position(target.position);
        this.type = target.type;
        this.tileX = target.tileX;
        this.tileY = target.tileY;
    }

    public Position getPosition() {
        return this.position;
    }

    public int getType() {
        return this.type;
    }

    //Compare coordinate positions
    public boolean equals(Tile tile) {
        return this.position == tile.position;
    }

    //Used for A* priority queue - sorts lower to higher fcost
    @Override
    public int compareTo(Tile o) {
        return this.fCost - o.fCost;
    }

}
