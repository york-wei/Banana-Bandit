package Game.Utilities;

public class Score implements Comparable<Score> {

    private String name;
    private int score;

    public Score(String name, int score) {
        //Process name
        this.name = name.toUpperCase().trim();
        this.score = score;
    }

    //Used by tree map to sort scores from greatest to least; if the scores are the same, then sort alphabetically by name.
    @Override
    public int compareTo(Score o) {
        if (this.score - o.score == 0)
            return this.name.compareTo(o.name);
        return o.score - this.score;
    }

    //Getters
    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

}
