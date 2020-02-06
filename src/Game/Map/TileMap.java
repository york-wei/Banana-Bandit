package Game.Map;

public class TileMap {

    //0 --> wall
    //1 --> path
    //2 --> intersection
    // 27x28 map
    private int [] [] map = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0},
            {0, 1, 1, 2, 0, 1, 0, 0, 0, 2, 1, 2, 0, 1, 0, 2, 1, 2, 0, 0, 0, 1, 0, 2, 1, 1, 0},
            {0, 1, 0, 1, 2, 2, 1, 0, 0, 1, 0, 2, 1, 2, 1, 2, 0, 1, 0, 0, 1, 2, 2, 1, 0, 1, 0},
            {0, 1, 0, 0, 1, 0, 2, 1, 1, 2, 1, 2, 0, 1, 0, 2, 1, 2, 1, 1, 2, 0, 1, 0, 0, 1, 0},
            {0, 1, 2, 1, 2, 2, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 2, 2, 1, 2, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 2, 2, 2, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 2, 1, 2, 2, 1, 2, 1, 2, 0, 0, 1, 0, 1, 0, 0, 2, 1, 2, 1, 2, 2, 1, 2, 0, 0},
            {0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 2, 1, 0, 1, 2, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0},
            {0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0},
            {0, 1, 2, 1, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 1, 2, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0},
            {0, 1, 2, 1, 2, 2, 1, 2, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 2, 1, 2, 2, 1, 2, 1, 0},
            {0, 0, 1, 0, 1, 0, 0, 1, 0, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0, 1, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 1, 0, 1, 0, 0, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 0, 0, 1, 0, 1, 0, 0},
            {0, 1, 2, 1, 2, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 2, 1, 2, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 2, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 2, 1, 2, 1, 1, 2, 0, 1, 0, 1, 0, 2, 1, 1, 2, 1, 2, 0, 0, 0, 1, 0},
            {0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 2, 1, 2, 2, 2, 1, 2, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 0, 1, 2, 1, 0, 0, 1, 0, 0, 1, 2, 1, 0, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 1, 1, 1, 2, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 2, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public Tile [] [] tileArr = new Tile [28][27];

    public TileMap() {
        //Fill tileArr with tiles, with proper coordinates
        for(int i = 0; i < 28; i++) {
            for(int j = 0; j < 27; j++) {
                tileArr[i][j] = new Tile(32*j, (32*i) + 16, j, i, map[i][j]);
            }
        }
    }

}
