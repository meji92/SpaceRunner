package globex.spacerunner;

/**
 * Created by meji on 13/03/16.
 */
public class Rubish extends Square {

    public Rubish(Vector2d pos, int dirX, int dirY){
        this.size = 1;
        this.pos = pos;
        this.dir = new Vector2d(dirX, dirY);
    }
}
