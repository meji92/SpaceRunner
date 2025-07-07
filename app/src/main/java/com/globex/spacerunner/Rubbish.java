package com.globex.spacerunner;

import java.util.ArrayList;

public class Rubbish extends Square {

    public Rubbish(Vector2d pos, int dirX, int dirY){
        super(pos.getX(), pos.getY(), dirX, dirY, 10);
        this.position = pos;
        this.direction = new Vector2d(dirX, dirY);
        this.center = new Vector2d(position.getX()+size/2,position.getY()+size/2);
    }

    @Override
    public ArrayList<Rubbish> getRubbishAndReload(GameState state){
        return new ArrayList<Rubbish>();
    }
}
