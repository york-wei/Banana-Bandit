package Game.Utilities;

public class Position {

    public int x;
    public int y;

    //Constructor
    public Position(int x, int y) {

        this.x = x;
        this.y = y;

    }

    //Constructor for copying Position objects
    public Position(Position position) {

        this.x = position.x;
        this.y = position.y;

    }

    //Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Verify if 2 sets of coordinates are the same
    public boolean equals(Position c){
        return this.x == c.x && this.y == c.y;
    }

    //Overridden toString
    public String toString() {
        return this.x + ", " + this.y;
    }

    //Calculates if one position is within a certain pixel distance of another position.
    public boolean within(Position position, int distance){
        return Math.abs(this.x - position.x) < distance && Math.abs(this.y - position.y) < distance;
    }

}
