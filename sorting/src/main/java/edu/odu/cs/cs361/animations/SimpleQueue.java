package edu.odu.cs.cs361.animations;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;

/**
 * A light-weight implementation of the Java Queue interface,
 * using singly linked lists.
 * 
 * @author Steven Zeil
 */
public class SimpleQueue<E> implements Queue<E>, Cloneable
    , Renderer<SimpleQueue<E>>, CanBeRendered<SimpleQueue<E>>//!
 {

    private static class SimpleListNode 
    implements Renderer<SimpleListNode>, CanBeRendered<SimpleListNode>//!
    {
        public Object data;
        public SimpleListNode next;

        public SimpleListNode(Object data) {
            this.data = data;
            this.next = null;
        }

        @Override
        public Boolean getClosedOnConnections() {
            return true;
        }

        @Override
        public Color getColor(SimpleListNode arg0) {
            return Color.yellow;
        }

        @Override
        public List<Component> getComponents(SimpleListNode node) {
            List<Component> components = new java.util.ArrayList<>();
            return components;
        }

        @Override
        public List<Connection> getConnections(SimpleListNode arg0) {
            List<Connection> connections = new java.util.ArrayList<>();
            connections.add(new Connection(next, 0, 180));
            return connections;
        }

        @Override
        public Directions getDirection() {
            return Directions.HorizontalTree;
        }

        @Override
        public Double getSpacing() {
            return null;
        }

        @Override
        public String getValue(SimpleListNode node) {
            return node.data.toString();
        }

        @Override
        public Renderer<SimpleListNode> getRenderer() {
            return this;
        }
    }

    private SimpleListNode first;
    private SimpleListNode last;
    private int theSize;

    public SimpleQueue() {
        first = last = null;
        theSize = 0;
    }

    /**
     * Inserts the specified element into this queue if it is possible to do so
     * immediately without violating capacity restrictions, returning true upon
     * success and throwing an IllegalStateException if no space is currently
     * available.
     * 
     * @param e the value to be added
     * @return true if the element is added (always)
     * @throws IllegalStateException (never)
     */
    @Override
    public boolean add(E e) throws IllegalStateException {
        SimpleListNode nd = new SimpleListNode(e);
        if (first == null) {
            first = nd;
        }
        if (last != null) {
            last.next = nd;
        }
        last = nd;
        nd.next = null;
        ++theSize;
        return true;
    }

    /**
     * Inserts the specified element into this queue.
     * 
     * @param e the element to insert
     * @return true if the element is added (always)
     */
    @Override
    public boolean offer(E e) {
        return add(e);
    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     * 
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    @SuppressWarnings("unchecked")
    @Override
    public E element() throws NoSuchElementException {
        if (first != null)
            return (E) first.data;
        else
            throw new NoSuchElementException();
    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     * 
     * @return the head of this queue, or null if the queue is empty.
     */
    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        if (first != null)
            return (E) first.data;
        else
            return null;
    }

    /**
     * Retrieves and removes the head of this queue.
     * 
     * @return the former head of the queue, or null if the queue was empty
     */
    @SuppressWarnings("unchecked")
    @Override
    public E poll() {
        if (first == null)
            return null;
        SimpleListNode front = first;
        first = front.next;
        if (first == null) {
            last = null;
        }
        --theSize;
        front.next = null;
        return (E) front.data;
    }

    /**
     * Retrieves and removes the head of this queue.
     * 
     * @return the former head of the queue, or null if the queue was empty
     * @throws NoSuchElementException if this queue was empty
     */
    @Override
    public E remove() throws NoSuchElementException {
        E front = poll();
        if (front == null)
            throw new NoSuchElementException();
        return front;
    }

    /**
     * Exchanges the values of two queues.
     * 
     * @param other another queue
     */
    public void swap(SimpleQueue<E> other) {
        SimpleListNode temp = first;
        first = other.first;
        other.first = temp;
        temp = last;
        last = other.last;
        other.last = temp;
        int tmp = theSize;
        theSize = other.theSize;
        other.theSize = tmp;
    }

    @Override
    public boolean isEmpty() {
        return theSize == 0;
    }

    @Override
    public void clear() {
        first = last = null;
        theSize = 0;
    }

    @Override
    public int size() {
        return theSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimpleQueue) {
            @SuppressWarnings("unchecked")
            SimpleQueue<E> other = (SimpleQueue<E>)obj;
            if (theSize != other.theSize)
                return false;
            SimpleListNode left = first;
            SimpleListNode right = other.first;
            while (first != null) {
                if (!left.data.equals(right.data))
                    return false;
                left = left.next;
                right = right.next;
            }
            return true;
        } else
            return false;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        int count = 0;
        for (SimpleListNode current = first; current != null; 
            current = current.next) {
            if (current != first)
                buffer.append(", ");
            if (count < 100) {
                buffer.append(current.data.toString());
            } else {
                buffer.append("...");
            }
            ++count;
        }
        buffer.append(']');
        return buffer.toString();
    }

    @Override
    public boolean addAll(Collection<? extends E> coll) {
        for (E e: coll) {
            offer(e);
        }
        return true;
    }

    @Override
    public boolean contains(Object obj) {
        for (SimpleListNode current = first; first != null; first = first.next) {
            @SuppressWarnings("unchecked")
            E e = (E)current.data;
            if (e.equals(obj))
                return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> coll) {
        for (Object obj: coll) {
            if (!contains(obj))
                return false;
        }
        return true;
    }

    private class SimpleQueueIterator implements Iterator<E> {

        private SimpleListNode current;

        public SimpleQueueIterator(SimpleListNode first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            @SuppressWarnings("unchecked")
            E result = (E)current.data;
            current = current.next;
            return result;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new SimpleQueueIterator(first);
    }

    @Override
    public boolean remove(Object arg0) {
        // Not something you should be able to do to a "true" queue
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        // Not something you should be able to do to a "true" queue
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        // Not something you should be able to do to a "true" queue
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[theSize];
        int i = 0;
        for (SimpleListNode current = first; first != null; first = first.next) {
            result[i] = current.data;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] arr) {
        Object[] result = (Object[])arr;
        if (arr.length != theSize) {
            result = new Object[theSize];
        }
        int i = 0;
        for (SimpleListNode current = first; first != null; first = first.next) {
            result[i] = current.data;
        }
        @SuppressWarnings("unchecked")
        T[] resultT = (T[])result;
        return resultT;
    }

    @Override
    public Renderer<SimpleQueue<E>> getRenderer() {
        return this;
    }

    @Override
    public String getValue(SimpleQueue<E> obj) {
        return "";
    }

    @Override
    public Color getColor(SimpleQueue<E> obj) {
        return null;
    }

    @Override
    public List<Component> getComponents(SimpleQueue<E> obj) {
        List<Component> results = new java.util.ArrayList<>();
        for (SimpleListNode current = first; current != null; 
            current = current.next) {
            results.add(new Component(current));
        }
        return results;
    }

    @Override
    public List<Connection> getConnections(SimpleQueue<E> obj) {
        return new java.util.ArrayList<>();
    }

    @Override
    public Directions getDirection() {
        return Directions.Horizontal;
    }

    @Override
    public Double getSpacing() {
        return 2.0;
    }

    @Override
    public Boolean getClosedOnConnections() {
        return false;
    }

}