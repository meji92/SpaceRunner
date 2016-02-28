package globex.spacerunner;

public class Square {
    private int size;
    private Vector2d pos;
    private Vector2d dir;


    public Square(int x, int y, int dirX, int dirY, int size){
        this.size = size;
        this.pos = new Vector2d(x,y);
        this.dir = new Vector2d(dirX, dirY);
    }

    public Square(int x, int y){
        this.size = (int)(Math.random()*100);
        this.pos.set(x,y);
        this.dir.set((int)(Math.random()*10),(int)(Math.random()*10)+1);
    }

    public Integer distanceTo (Square a){
        return (this.pos.distance(a.getPos()));
    }

    public boolean collision (Square a){
        return (pos.distance(a.getPos())<=(a.getSize()+this.getSize()));
    }

    public void changeDir (){
        dir.set(dir.getX()*(-1), dir.getY());
    }

    public void update (int x, int y){
        pos.increment(x+dir.getX(),y+dir.getY());
    }

    public void update (){
        pos.increment(dir.getX(),dir.getY());
    }

    public void reloadSquare(int x, int y){
        this.size = (int)(Math.random()*100);
        this.pos.set(x,y);
        this.dir.set((int)(Math.random()*10),(int)(Math.random()*10)+1);
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
}
