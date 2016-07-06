package globex.spacerunner;

import android.util.Log;

import java.util.ArrayList;

public class Square {
    protected int size;
    protected Vector2d position;
    protected Vector2d direction;
    protected Vector2d center;


    public Square(int x, int y, int dirX, int dirY, int size){
        this.size = size;
        this.position = new Vector2d(x,y);
        this.center = new Vector2d(x+size/2,y+size/2);
        this.direction = new Vector2d(dirX, dirY);
    }

    public Square(){
        this.size = (int)(Math.random()*100);
        this.position = new Vector2d((int) (Math.random() * MainActivity.normalizedWidth), 0 - (this.size+(int)(Math.random()*(MainActivity.normalizedHeight/2))));
        this.center = new Vector2d(position.getX()+size/2,position.getY()+size/2);
        this.direction = new Vector2d((int)(Math.random()*10),(int)(Math.random()*10)+2);
    }

    public Square(int size){
        this.size = size;
        this.position = new Vector2d((int) (Math.random() * MainActivity.normalizedWidth), 0 - (this.size+(int)(Math.random()*(MainActivity.normalizedHeight/4))));
        this.center = new Vector2d(position.getX()+size/2,position.getY()+size/2);
        this.direction = new Vector2d((int)(Math.random()*10),(int)(Math.random()*10)+2);
    }

    public Integer distanceTo (Square a){
        return (this.position.distance(a.getPosition()));
    }

    public boolean collision (Square a){
        return (center.distance(a.getCenter())<(size/2+a.getSize()/2));
        //return ((Math.abs(center.getX()-a.getCenter().getX())<(size/2+a.getSize()/2))&&(Math.abs(center.getY()-a.getCenter().getY())<(size/2+a.getSize()/2)));
        //return (position.distance(a.getPosition())<=(a.getSize()+this.getSize()));
    }

    public void changeDir (){
        direction.set(direction.getX() * (-1), direction.getY());
    }

    public void update (int x, int y){
        position.add(x + direction.getX(), y + direction.getY());
        center.set(position.getX() + size / 2, position.getY() + size / 2);
    }

    public void update (){
        position.add(direction.getX(), direction.getY());
        center.set(position.getX() + size / 2, position.getY() + size / 2);
    }

    public void reloadSquare(){
        this.size = (int)(Math.random()*100);
        this.position.set((int) (Math.random() * MainActivity.normalizedWidth), 0 - (this.size + (int) (Math.random() * (MainActivity.normalizedHeight / 4))));
        this.direction.set((int) (Math.random() * 10), (int) (Math.random() * 10) + 2);
    }

    public void updateX (int i){
        position.incrementX(i);
    }

    public void updateY (int i){
        position.incrementY(i);
    }

    @Override
    public String toString() {
        return ("Pos("+Integer.toString(position.getX())+","+Integer.toString(position.getY())+");Dir("+Integer.toString(direction.getX())+","+Integer.toString(direction.getY())+")"+" Rubish?: "+Boolean.toString(this instanceof Rubish));
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setPosition(int posX, int posY) {
        this.position.set(posX, posY);
        center.set(position.getX()+size/2,position.getY()+size/2);
    }

    public void setDirection(Vector2d direction) {
        this.direction = direction;
    }

    public Vector2d getCenter() {
        return center;
    }

    public void setCenter(Vector2d center) {
        this.center = center;
    }

    public int getSize() {
        return size;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getDirection() {return direction; }

    public Vector2d getPosValue() {
        Vector2d retValue = new Vector2d(position.getX(), position.getY());
        return retValue;
    }

    public Vector2d getDirValue() {
        Vector2d retValue = new Vector2d(direction.getX(), direction.getY());
        return retValue;
    }

    public ArrayList<Rubish> getRubishAndReload(){
        ArrayList retArray = new ArrayList<Rubish>();
        this.position.setY(this.position.getY()+this.size/2+10);
        for (int i=0; i<=this.size/10; i++){
            retArray.add(new Rubish(this.getPosValue(),this.direction.getX()+i,(int)(Math.random()*10)+2));
        }
        this.reloadSquare();
        return retArray;
    }
}
