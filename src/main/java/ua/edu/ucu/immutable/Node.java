package ua.edu.ucu.immutable;


public class Node {
    public Object value;
    public Node next;
    public Node(Object v) {
        value = v;
    }
    public Node() {
        value = null;
    }

    public void setNext(Node newNode) {
        next = newNode;
    }
}
