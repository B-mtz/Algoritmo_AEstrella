package logic;

public class Node {
    private Coordinate coordinate,origin;
    private int coste,heuristic, total;
    public Node(Coordinate coordinate, int coste, int heuristic, int total, Coordinate origin) {
        this.coordinate = coordinate;
        this.coste = coste;
        this.heuristic = heuristic;
        this.total = total;
        this.origin = origin;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getOrigin() {
        return origin;
    }

    public void setOrigin(Coordinate origin) {
        this.origin = origin;
    }

    public int getCoste() {
        return coste;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public int getTotal() {
        return total;
    }

}
