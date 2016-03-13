package globex.spacerunner;

import android.util.Log;

import java.util.ArrayList;

public class Square {
    protected int size;
    protected Vector2d pos;
    protected Vector2d dir;


    public Square(int x, int y, int dirX, int dirY, int size){
        this.size = size;
        this.pos = new Vector2d(x,y);
        this.dir = new Vector2d(dirX, dirY);
    }

    public Square(){
        this.size = (int)(Math.random()*100);
        this.pos = new Vector2d((int) (Math.random() * MainActivity.screenWidth), 0 - (this.size+(int)(Math.random()*(MainActivity.screenHeight/2))));
        this.dir = new Vector2d((int)(Math.random()*10),(int)(Math.random()*10)+2);
    }

    public Integer distanceTo (Square a){
        return (this.pos.distance(a.getPos()));
    }

    public boolean collision (Square a){
        if (pos.distance(a.getPos())<=(a.getSize()+this.getSize())){
            Log.d("Pos", a.getPos().toString());
            Log.d("Pos", pos.toString());
            Log.d("Pos", Integer.toString(a.getSize()));
            Log.d("Pos", Integer.toString(this.getSize()));
            Log.d("Collision", "si");
        }else{
            Log.d("No Collision", "no");
        }
        return (pos.distance(a.getPos())<=(a.getSize()+this.getSize()));
    }

    public void changeDir (){
        dir.set(dir.getX()*(-1), dir.getY());
    }

    public void update (int x, int y){
        pos.increment(x + dir.getX(), y + dir.getY());
    }

    public void update (){
        pos.increment(dir.getX(),dir.getY());
    }

    public void reloadSquare(){
        this.size = (int)(Math.random()*100);
        this.pos.set((int) (Math.random() * MainActivity.screenWidth), 0 - (this.size+(int)(Math.random()*(MainActivity.screenHeight/2))));
        this.dir.set((int) (Math.random() * 10), (int) (Math.random() * 10) + 1);
    }

    public void updateX (int i){
        pos.incrementX(i);
    }

    public void updateY (int i){
        pos.incrementY(i);
    }

    @Override
    public String toString() {
        return ("Pos("+Integer.toString(pos.getX())+","+Integer.toString(pos.getY())+");Dir("+Integer.toString(dir.getX())+","+Integer.toString(dir.getY())+")");
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPos(Vector2d pos) {
        this.pos = pos;
    }

    public void setPos(int posX, int posY) {
        this.pos.set(posX, posY);
    }

    public void setDir(Vector2d dir) {
        this.dir = dir;
    }

    public int getSize() {
        return size;
    }

    public Vector2d getPos() {
        return pos;
    }

    public Vector2d getDir() {
        return dir;
    }

    public ArrayList<Rubish> getRubish(){
        this.size = 1;
        ArrayList retArray = new ArrayList<Rubish>();
        for (int i=0; i<=this.size; i++){
            retArray.add(new Rubish(this.pos,this.dir.getX()+(i*dir.getX()),this.dir.getY()));
        }

        return retArray;
    }
}
