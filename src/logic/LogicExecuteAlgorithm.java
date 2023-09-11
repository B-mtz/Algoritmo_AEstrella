package logic;

import java.util.ArrayList;

public class LogicExecuteAlgorithm {
    private Coordinate coordinateStart, coordinateEnd;
    private int[][] data;
    private ArrayList<Node> openSet = new ArrayList<>(),openSetRegister = new ArrayList<>(),closeSet = new ArrayList<>(),discarded = new ArrayList<>();
    private ArrayList<Coordinate> routes = new ArrayList<>();
    private int heuristic, coste, total, minCosteTotal;
    private int rows, columns;

    public LogicExecuteAlgorithm(Coordinate start, Coordinate end, int[][] data, int rows, int columns) {
        this.coordinateStart = start;
        this.coordinateEnd = end;
        this.data = data;
        this.rows = rows;
        this.columns = columns;
    }
    public void executeAlgorithm() {
        createNodeStart();
        while (!openSet.isEmpty()) {
            logic(openSet.get(0));
        }
        printOpenSetRegister();
        printCloseSet();
        generateRoute();
    }

    public void logic(Node node) {
        getMinCoste();
        if (node.getTotal() == minCosteTotal) {
            //Si la casilla es diferente a la meta continuar
            if (node.getCoordinate().getX() == coordinateEnd.getX() && node.getCoordinate().getY() == coordinateEnd.getY()) {
                closeSet.add(node);
                openSet.remove(node);
            } else {
                evaluateNodes(node);
                closeSet.add(node);
                openSet.remove(node);
            }
        } else {
            discarded.add(node);
            openSet.remove(node);
        }
    }

    //Validamos que haya espacio libre a los lados sin exceder los limites
    private void evaluateNodes(Node node) {
        int alone = 0;
        Coordinate destiny;
        //Derecha
        if (node.getCoordinate().getY() + 1 < columns) {
            destiny = new Coordinate(node.getCoordinate().getX(), node.getCoordinate().getY() + 1);
            if (validateSpace(destiny) && node.getCoordinate().getY() + 1 < columns) {
                //Creamos el nuevo nodo vecino
                evaluation(node, destiny);
            }
        }
        //Abajo
        if (node.getCoordinate().getX() + 1 < rows) {
            destiny = new Coordinate(node.getCoordinate().getX() + 1, node.getCoordinate().getY());
            if (validateSpace(destiny) && node.getCoordinate().getX() + 1 < rows) {
                //Creamos el nuevo nodo vecino
                evaluation(node, destiny);
            }
        }
        //Izquierda
        if (node.getCoordinate().getY() != 0) {
            destiny = new Coordinate(node.getCoordinate().getX(), node.getCoordinate().getY() - 1);
            if (validateSpace(destiny) && node.getCoordinate().getY() - 1 >= 0) {
                //Creamos el nuevo nodo vecino
                evaluation(node, destiny);
            }
        }
        //Arriba
        if (node.getCoordinate().getX() != 0) {
            destiny = new Coordinate(node.getCoordinate().getX() - 1, node.getCoordinate().getY());
            if (validateSpace(destiny) && node.getCoordinate().getX() - 1 >= 0) {
                //Creamos el nuevo nodo vecino
                evaluation(node, destiny);
            }
        }
    }

    //Evalua el openSet y el CloseSet
    public void evaluation(Node node, Coordinate destiny) {
        boolean flag = false;
        Node auxNode = createNode(node.getCoordinate(), destiny);
        //Se valida que no este en OpenSet, en caso de estar, se eliminar
        if (!validateOpenSet(auxNode)) {
            //Se valida que no este en closeSet, en caso de estar, se elimina
            if (closeSet.size() == 0) {
                openSet.add(auxNode);
                openSetRegister.add(auxNode);
            } else {
                for (Node actualNode : closeSet) {
                    if (actualNode.getCoordinate().getX() == destiny.getX() && actualNode.getCoordinate().getY() == destiny.getY()) {
                        flag = true;
                    }
                }
                if (!flag) {
                    openSetRegister.add(auxNode);
                    openSet.add(auxNode);
                }
            }
        } else {
            openSet.remove(0);
        }
    }

    //Valida que el nodo no este en OpenSet
    public boolean validateOpenSet(Node node) {
        boolean flag = false;
        for (Node actualNode : openSet) {
            if (actualNode.getCoordinate().getX() == node.getCoordinate().getX() && actualNode.getCoordinate().getY() == node.getCoordinate().getY()) {
                flag = true;
                if (node.getTotal() > actualNode.getTotal()) {
                    node.setOrigin(actualNode.getOrigin());
                } else {
                    actualNode.setOrigin(node.getOrigin());
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }
    //Valida que no existan muros en la casilla
    public boolean validateSpace(Coordinate destiny){
        if (data[destiny.getX()][destiny.getY()] == 0){
            return true;
        }else{
            return false;
        }
    }

    //Se crea el nodo del inicio
    private void createNodeStart() {
        coste = calulateHeuristic(coordinateStart, coordinateStart);
        heuristic = calulateHeuristic(coordinateStart, coordinateEnd);
        total = coste + heuristic;
        Node start = new Node(coordinateStart, coste, heuristic, total, coordinateStart);
        openSet.add(start);
        openSetRegister.add(start);
    }
    //Crea nodos
    private Node createNode(Coordinate origin, Coordinate destiny) {
        coste = calulateHeuristic(coordinateStart, destiny);
        heuristic = calulateHeuristic(destiny, coordinateEnd);
        total = coste + heuristic;
        return new Node(destiny, coste, heuristic, total, origin);
    }
    //Obtiene el coste minimo
    private void getMinCoste(){
        minCosteTotal = Integer.MAX_VALUE;
        for (Node node : openSet) {
            if (node.getTotal() < minCosteTotal) {
                minCosteTotal = node.getTotal();
            }
        }
    }
    //Calcula la heuristica
    private int calulateHeuristic(Coordinate origin, Coordinate destiny){
        int x = origin.getX() - destiny.getX();
        int y = origin.getY() - destiny.getY();
        int aux = Math.abs(x) +Math.abs(y);
        return aux;
    }

    //imprime openSet y CloseT
    public void printOpenSetRegister(){
        System.out.println("Size openSet: "+openSetRegister.size());
        for (Node node : openSetRegister){
            System.out.println(node.getCoordinate().retunCoordinate()+" - "+node.getCoste()+" - "+node.getHeuristic()+" - "+node.getTotal()+" - "+node.getOrigin().retunCoordinate());
        }
    }
    public void printCloseSet(){
        System.out.println("Size CloseSet: "+closeSet.size());
        for (Node node : closeSet){
            System.out.println(node.getCoordinate().retunCoordinate()+" - "+node.getCoste()+" - "+node.getHeuristic()+" - "+node.getTotal()+" - "+node.getOrigin().retunCoordinate());
        }
        System.out.println(" ");
    }

    //Crea la ruta apartir de closeSet
    public void generateRoute() {
        Coordinate aux = closeSet.get(closeSet.size()-1).getOrigin();
        routes.add(closeSet.get(closeSet.size()-1).getCoordinate());
        for (int i = closeSet.size()-2; i >0; i--) {
            if (aux.getX() == closeSet.get(i).getCoordinate().getX() && aux.getY() == closeSet.get(i).getCoordinate().getY()){
                routes.add(closeSet.get(i).getCoordinate());
                aux = closeSet.get(i).getOrigin();
            }
        }
        routes.add(closeSet.get(0).getCoordinate());
        printRoute();
    }
    private void printRoute(){
        System.out.println("Route: ");
        for (Coordinate coordinate : routes){
            System.out.print("("+coordinate.retunCoordinate()+")  ");
        }
    }

    public ArrayList<Node> getOpenSetRegister() {
        return openSetRegister;
    }
    public ArrayList<Node> getCloseSet() {
        return closeSet;
    }
    public ArrayList<Coordinate> getRoutes() {
        return routes;
    }
}


