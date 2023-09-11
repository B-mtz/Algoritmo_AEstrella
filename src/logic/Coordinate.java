package logic;

public class Coordinate {
    private int x, y;
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String retunCoordinate(){
        String aux = x +","+ y;
        return aux;
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
}
