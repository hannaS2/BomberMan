
public class Player {
    private float x;
    private float y;
    private float v;
    private int direction;
    private int bombCount;
    private int bombPower;

    public Player(float x, float y, float v, int direction, int bombCount, int bombPower){
        this.x = x;
        this.y = y;
        this.v = v;
        this.direction = direction;
        this.bombCount = bombCount;
        this.bombPower = bombPower;
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setV(float v) {
        this.v = v;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public void setBombPower(int bombPower) {
        this.bombPower = bombPower;
    }

    public float getX() {

        return x;
    }

    public float getY() {
        return y;
    }

    public float getV() {
        return v;
    }

    public int getDirection() {
        return direction;
    }

    public int getBombCount() {
        return bombCount;
    }

    public int getBombPower() {
        return bombPower;
    }


}
