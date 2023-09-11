package Test;

import logic.Coordinate;
import logic.LogicExecuteAlgorithm;

public class testAlgoritmo {

    public static void main(String[] args) {
        int data [][] = {{0,0,0,1,0,0,0,0},{0,0,0,0,0,0,1,0},{1,1,1,1,1,0,1,0},{0,0,0,0,0,0,1,0},{0,1,1,1,1,1,1,0},{0,0,0,0,0,0,0,1},};
        Coordinate start = new Coordinate(5,0);
        Coordinate end = new Coordinate(4,7);

        LogicExecuteAlgorithm logicExecuteAlgorithm = new LogicExecuteAlgorithm(start,end,data,6,8);
        logicExecuteAlgorithm.executeAlgorithm();
    }
}
