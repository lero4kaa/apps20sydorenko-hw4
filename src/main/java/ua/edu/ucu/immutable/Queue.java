package ua.edu.ucu.immutable;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue {
    public ImmutableLinkedList generalQueue;

    public Queue() {
        generalQueue = new ImmutableLinkedList();

    }

    public Object peek() {
        return generalQueue.getFirst();
    }

    public Object dequeue() {
        Object result = generalQueue.getFirst();
        generalQueue = generalQueue.removeFirst();
        return result;
    }

    public void enqueue(Object e) {
        if (generalQueue.getFirst() == null) {
            generalQueue = generalQueue.set(0, e);
        } else {
            generalQueue = generalQueue.addLast(e);
        }
    }

    public int size() {
        return generalQueue.size();
    }

    public <T> Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !generalQueue.isEmpty();
            }

            @Override
            public T next() {
                if (generalQueue.isEmpty()) {
                    throw new NoSuchElementException();
                }
                return (T) dequeue();
            }

        };
    }
}
