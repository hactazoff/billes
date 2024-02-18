import guilines.IJeuDesBilles;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class PathFinding {
    public enum NodeTypes {
        EMPTY, WALL,
        START, END,
        OPEN, CLOSED
    }

    public class Node {
        public int x, y;
        public NodeTypes type = NodeTypes.EMPTY;
        public Node[] getNeighbours() {
            var v = new ArrayList<Node>();
            if(x > 0)
                v.add(nodes[y * width + x - 1]);
            if(x < width - 1)
                v.add(nodes[y * width + x + 1]);
            if(y > 0)
                v.add(nodes[(y - 1) * width + x]);
            if(y < height - 1)
                v.add(nodes[(y + 1) * width + x]);
            return v.toArray(new Node[0]);
        }
        public Node parent;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getG() {
            return getDistance(this, getStart());
        }
        public int getH() {
            return getDistance(this, getEnd());
        }
        public int getF() {
            return getG() + getH();
        }
        public boolean equals(Node n) {
            return x == n.x && y == n.y;
        }
    }

    private Node[] nodes;
    private final int width;
    private final int height;

    public PathFinding(Grille grille, Point start, Point end) {
        width = grille.getWidth();
        height = grille.getHeight();
        nodes = new Node[width * height];
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                setNode(x, y, grille.getValeur(y, x) == null ? NodeTypes.EMPTY : NodeTypes.WALL);
        setStart(start.x, start.y);
        setEnd(end.x, end.y);
    }

    public void setNode(int x, int y, NodeTypes type) {
        nodes[y * width + x] = new Node(x, y);
        nodes[y * width + x].type = type;
    }

    public void setStart(int x, int y) {
        var start = getStart();
        if(start != null)
            start.type = NodeTypes.EMPTY;
        nodes[y * width + x].type = NodeTypes.START;
    }

    public Node getStart() {
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                if(nodes[y * width + x].type == NodeTypes.START)
                    return nodes[y * width + x];
        return null;
    }

    public void setEnd(int x, int y) {
        var end = getEnd();
        if(end != null)
            end.type = NodeTypes.EMPTY;
        nodes[y * width + x].type = NodeTypes.END;
    }

    public Node getEnd() {
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                if(nodes[y * width + x].type == NodeTypes.END)
                    return nodes[y * width + x];
        return null;
    }

    public int getDistance(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public Node[] getPath() {
        System.out.print("getPath");
        var start = getStart();
        var end = getEnd();
        if(start == null || end == null)
            return new Node[0];
        var open = new ArrayList<Node>();
        var closed = new ArrayList<Node>();
        open.add(start);
        while(!open.isEmpty()) {
            System.out.print(".");
            var current = open.getFirst();
            for(var node : open)
                if(node.getF() < current.getF() || (node.getF() == current.getF() && node.getH() < current.getH()))
                    current = node;
            open.remove(current);
            closed.add(current);
            if(current.equals(end)) {
                var path = new ArrayList<Node>();
                while(current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                System.out.println();
                return path.toArray(new Node[0]);
            }
            for(var neighbour : current.getNeighbours()) {
                if(neighbour.type == NodeTypes.WALL || closed.contains(neighbour))
                    continue;
                var g = current.getG() + getDistance(current, neighbour);
                if(!open.contains(neighbour) || g < neighbour.getG()) {
                    neighbour.parent = current;
                    if(!open.contains(neighbour))
                        open.add(neighbour);
                }
            }
        }
        System.out.println(" rip.");
        return new Node[0];
    }
}