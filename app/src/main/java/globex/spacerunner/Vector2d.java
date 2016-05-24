package globex.spacerunner;

public class Vector2d {

    private int x, y;

    public Vector2d(int value) {
        this(value, value);
    }

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d vector2d) {
        this.x = vector2d.x;
        this.y = vector2d.y;
    }

    public void copy(Vector2d vector2d) {
        this.x = vector2d.x;
        this.y = vector2d.y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d add(int x, int y){
        this.x = this.x+x;
        this.y = this.y+y;
        return new Vector2d(this.x, this.y);
    }

    public Vector2d add(Vector2d other) {
        this.x += other.x;
        this.y += other.y;
        return new Vector2d(this.x, this.y);
    }

    public void incrementX (int i){
        this.x = this.x+i;
    }

    public void incrementY (int i){
        this.y = this.y+i;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2d sub(Vector2d other) {
        int x = this.x - other.x;
        int y = this.y - other.y;
        return new Vector2d(x, y);
    }

    public Vector2d multiply(int value) {
        return new Vector2d(value * x, value * y);
    }

    public double dotProduct(Vector2d other) {
        return other.x * x + other.y * y;
    }

    public int distance (Vector2d v){
        int x = Math.abs(v.getX()-this.x);
        int y = Math.abs(v.getY()-this.y);
        return (int)Math.sqrt((x*x)+(y*y));
    }

    public int getLength() {
        return (int) Math.sqrt(dotProduct(this));
    }

    public Vector2d normalize() {
        int magnitude = getLength();
        if ( magnitude == 0 ) {
            magnitude = 1;
        }
        x = x / magnitude;
        y = y / magnitude;
        return this;
    }

    @Override
    public String toString() {
        return "Vector2d("+Integer.toString(this.x)+"," +Integer.toString(this.y)+")";
    }
}