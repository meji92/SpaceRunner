package globex.spacerunner;

public class Asteroid {
    private int size;
    private Vector2d pos;
    private Vector2d dir;


    public Asteroid (int x, int y, int dirX, int dirY, int _size){
        size = _size;
        pos = new Vector2d(x,y);
        dir = new Vector2d(dirX, dirY);
    }

    public Integer distanceTo (Asteroid a){
        return (this.pos.distance(a.getPos()));
    }

    public boolean collision (Asteroid a){
        return (pos.distance(a.getPos())<=(a.getSize()+this.getSize()));
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
