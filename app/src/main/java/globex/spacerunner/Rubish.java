package globex.spacerunner;

import java.util.ArrayList;

public class Rubish extends Square {

    public Rubish(Vector2d pos, int dirX, int dirY){
        this.size = (int)Math.random()*10;
        this.pos = pos;
        this.dir = new Vector2d(dirX, dirY);
    }

    public Rubish(){
        this.size = (int)Math.random()*10;
        this.pos = new Vector2d((int) (Math.random() * MainActivity.screenWidth), 0 - (this.size+(int)(Math.random()*(MainActivity.screenHeight/2))));
        this.dir = new Vector2d((int)(Math.random()*10),(int)(Math.random()*10)+2);
    }

    @Override
    public ArrayList<Rubish> getRubish(){
        return new ArrayList<Rubish>();
    }
}
