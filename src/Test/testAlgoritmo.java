package Test;

import logic.Coordinate;
import logic.LogicExecuteAlgorithm;

public class testAlgoritmo {

    public static void main(String[] args) {
        int data [][] = {{1,1,0,1},{0,0,0,0},{0,1,0,1},{0,0,0,0}};
        Coordinate start = new Coordinate(3,0);
        Coordinate end = new Coordinate(0,3);

        LogicExecuteAlgorithm logicExecuteAlgorithm = new LogicExecuteAlgorithm(start,end,data,3,3);
        logicExecuteAlgorithm.executeAlgorithm();
        //logicExecuteAlgorithm.printOpenSet();

    }
}
