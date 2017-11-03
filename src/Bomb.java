public class Bomb {
    private int x;
    private int y;
    private long time;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        this.time = System.currentTimeMillis();
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

    public long getTime() {
        return time;
    }
}
