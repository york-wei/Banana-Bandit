package Game.Utilities;

import Game.Utilities.Score;

import java.util.Comparator;

public class SortName implements Comparator<Score> {

    //Sorts scores by name alphabetically from A -> Z so binary search can be used to find a certain name
    @Override
    public int compare(Score o1, Score o2) {
        return o1.getName().compareTo(o2.getName());
    }

}