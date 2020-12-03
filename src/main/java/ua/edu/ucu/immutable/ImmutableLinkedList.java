package ua.edu.ucu.immutable;


public class ImmutableLinkedList implements ImmutableList {
    private Node head;
    private Node tail;
    private int size = 0;
    private Object[] generalArray;

    public ImmutableLinkedList() {
        head = new Node();
        tail = head;
        size = 0;
    }

    private ImmutableLinkedList(Node head, int size) {
        this.head = head;
        this.size = size;
    }

    public ImmutableLinkedList(Object[] values) {
        generalArray = values.clone();
        head = new Node(values[0]);
        size += 1;
        Node curNode = head;
        for (int i = 1; i < values.length; i++) {
            Node newNode = new Node(values[i]);
            curNode.setNext(newNode);
            curNode = newNode;
            size += 1;
        }
        tail = curNode;
    }


    @Override
    public ImmutableLinkedList add(Object e) {
        Object[] temp = {e};
        return addAll(size, temp);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        Object[] temp = {e};
        return addAll(index, temp);
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (0 > index || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds.");
        }

        Node newHead = new Node(head.value);
        Node curOldNode = head;
        Node curNewNode = newHead;
        int newSize = 1;

        if (index == 0) {
            newHead = new Node(c[0]);
            curNewNode = newHead;
        }

        for (int i = 0; i < index-1; i++) {
            curNewNode.setNext(new Node(curOldNode.next.value));
            curNewNode = curNewNode.next;
            curOldNode = curOldNode.next;
            newSize += 1;
        }

        for (int i = 0; i < c.length; i++) {
            curNewNode.setNext(new Node(c[i]));
            curNewNode = curNewNode.next;
            newSize += 1;
        }

        if (size - newSize + c.length == 0) {
            return new ImmutableLinkedList(newHead, newSize);
        }

        if (index == 0) {
            curNewNode.setNext(new Node(curOldNode.value));
            curNewNode = curNewNode.next;
        }

        int alreadySize = newSize;
        for (int i = 0; i < size - (alreadySize-c.length); i++) {
            curNewNode.setNext(new Node(curOldNode.next.value));
            curNewNode = curNewNode.next;
            curOldNode = curOldNode.next;
            newSize += 1;
        }

        if (index == 0) {
            newHead = newHead.next;
        }

        return new ImmutableLinkedList(newHead, newSize);
    }

    @Override
    public Object get(int index) {
        if (0 > index || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds.");
        }
        Node curNode = head;
        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }
        return curNode.value;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if ((index == 0) & (head.next == null)) {
            return new ImmutableLinkedList(new Node(), 0);
        }

        if (0 > index || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds.");
        }

        Node newHead = new Node(head.value);
        Node curOldNode = head;
        Node curNewNode = newHead;
        int newSize = 1;

        if (index == 0) {
            newHead = new Node(head.next.value);
            curNewNode = newHead;
        }

        for (int i = 0; i < index-1; i++) {
            curNewNode.setNext(new Node(curOldNode.next.value));
            curNewNode = curNewNode.next;
            curOldNode = curOldNode.next;
            newSize += 1;
        }

        curOldNode = curOldNode.next;
        int actualSize = newSize;

        for (int i = 0; i < size-actualSize-1; i++) {
            curNewNode.setNext(new Node(curOldNode.next.value));
            curNewNode = curNewNode.next;
            curOldNode = curOldNode.next;
            newSize += 1;
        }

        return new ImmutableLinkedList(newHead, newSize);
    }
    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (0 > index || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds.");
        }

        Node newHead = new Node(head.value);
        Node curOldNode = head;
        Node curNewNode = newHead;
        int newSize = 1;

        if (index == 0) {
            newHead = new Node(e);
            curNewNode = newHead;
        }

        for (int i = 0; i < index-1; i++) {
            curNewNode.setNext(new Node(curOldNode.next.value));
            curNewNode = curNewNode.next;
            curOldNode = curOldNode.next;
            newSize += 1;
        }


        if (index != 0) {
            curOldNode = curOldNode.next;
            curNewNode.setNext(new Node(e));
            curNewNode = curNewNode.next;
            newSize += 1;
        }
        int actualSize = newSize;

        for (int i = 0; i < size-actualSize; i++) {
            curNewNode.setNext(new Node(curOldNode.next.value));
            curNewNode = curNewNode.next;
            curOldNode = curOldNode.next;
            newSize += 1;
        }


        return new ImmutableLinkedList(newHead, newSize);
    }

    @Override
    public int indexOf(Object e) {
        Node curNode = head;
        for (int i = 0; i < size; i++) {
            if (curNode.value == e) {
                return i;
            }
            curNode = curNode.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] resultArray = new Object[size];
        Node curNode = head;
        for (int i = 0; i < size; i++) {
            resultArray[i] = curNode.value;
            curNode = curNode.next;
        }
        return resultArray;
    }

    @Override
    public String toString() {
        StringBuffer bf = new StringBuffer();
        Node curNode = head;
        for (int i = 0; i < size; i++) {
            bf.append(curNode.value + ",");
            curNode = curNode.next;
        }
        if (bf.length() > 0) {
            bf.deleteCharAt(bf.length()-1);
        }
        return bf.toString();
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return head.value;
    }

    public Object getLast() {
        if (size == 0) {
            return get(0);
        }
        return get(size-1);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(size-1);
    }
}
