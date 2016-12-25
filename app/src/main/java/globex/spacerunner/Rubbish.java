package globex.spacerunner;

import java.util.ArrayList;

public class Rubbish extends Square {

    public Rubbish(Vector2d pos, int dirX, int dirY){
        this.size = 10;
        this.position = pos;
        this.direction = new Vector2d(dirX, dirY);
        this.center = new Vector2d(position.getX()+size/2,position.getY()+size/2);
    }

    public Rubbish(){
        this.size = 10;
        this.position = new Vector2d((int) (Math.random() * MainActivity.normalizedWidth), 0 - (this.size+(int)(Math.random()*(MainActivity.normalizedHeight/2))));
        this.direction = new Vector2d((int)(Math.random()*10),(int)(Math.random()*10)+2);
        this.center = new Vector2d(position.getX()+size/2,position.getY()+size/2);
    }

    @Override
    public ArrayList<Rubbish> getRubbishAndReload(){
        return new ArrayList<Rubbish>();
    }
}
