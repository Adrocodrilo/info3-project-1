import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.awt.event.WindowEvent;
import java.io.File;

public class Maze{
    
    private int width;
    private int height;
    private int depth;
    private Node[][][] nodes;

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getDepth(){
        return this.depth;
    }

    public boolean isExitSpace(int xIndex, int yIndex, int zIndex){
        return this.nodes[xIndex][yIndex][zIndex].isExit;
    }

    public Node getStartingSpace(){
        return this.nodes[0][0][0];
    }

    public Maze(String filename) throws Exception{
        Scanner scan = new Scanner(new File(filename));

        String[] split = scan.nextLine().split(",");

        // Setting the dimensions of the map and nodes
        this.width = Integer.parseInt(split[0]);
        this.height = Integer.parseInt(split[1]);
        this.depth = Integer.parseInt(split[2]);
        this.nodes = new Node[this.width][this.height][this.depth];

        // Reseting the nodes IDS
        Node.resetNodesCount();

       // We create each node in the map
        for(int k = 0; k < this.depth; k++){
            for(int j = 0; j < this.height; j++){
                for(int i = 0; i < this.width; i++){
                    String data = scan.nextLine();
                    Node n = new Node(data, i, j, k);
                    this.nodes[i][j][k] = n;
                }
            }
        }

        // We establish the exit space
        split = scan.nextLine().split(",");
        this.setExitSpace(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));

        scan.close();
    }

    public Node moveNorth(Node node){
        if(node.north){
            return this.nodes[node.xIndex][node.yIndex - 1][node.zIndex];
        }
        return node;
    }

    public Node moveSouth(Node node){
        if(node.south){
            return this.nodes[node.xIndex][node.yIndex + 1][node.zIndex];
        }
        return node;
    }

    public Node moveWest(Node node){
        if(node.west){
            return this.nodes[node.xIndex - 1][node.yIndex][node.zIndex];
        }
        return node;
    }

    public Node moveEast(Node node){
        if(node.east){
            return this.nodes[node.xIndex + 1][node.yIndex][node.zIndex];
        }
        return node;
    }

    public Node moveUp(Node node){
        if(node.up){
            return this.nodes[node.xIndex][node.yIndex][node.zIndex + 1];
        }
        return node;
    }

    public Node moveDown(Node node){
        if(node.down){
            return this.nodes[node.xIndex][node.yIndex][node.zIndex - 1];
        }
        return node;
    }

    private void setExitSpace(int xIndex, int yIndex, int zIndex){
        this.nodes[xIndex][yIndex][zIndex].isExit = true;
    }
}