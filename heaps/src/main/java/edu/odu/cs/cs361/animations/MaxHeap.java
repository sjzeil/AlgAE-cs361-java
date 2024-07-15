package edu.odu.cs.cs361.animations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;//!
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;//!

class MaxHeap<E>
        implements Renderer<MaxHeap<E>>, CanBeRendered<MaxHeap<E>> {
    private Object[] heap; // Pointer to the heap array
    private Comparator<E> compare; // comparator to use when comparing elements
    private int n; // Number of things now in heap
    private Tree tree; // !

    // Constructor for an empty heap, natural ordering
    public MaxHeap() {
        heap = new Object[11];
        compare = new Comparator<E>() {
            @Override
            public int compare(E left, E right) {
                Comparable<E> leftComp = (Comparable<E>) left;
                E rightComp = (E) right;
                return leftComp.compareTo(rightComp);
            }
        };
        tree = new Tree(heap);// !
    }

    // Constructor for an empty heap, overriding ordering
    public MaxHeap(Comparator<E> comparator) {
        heap = new Object[11];
        compare = comparator;
        tree = new Tree(heap);// !
    }

    // Constructor supporting preloading of heap contents
    public MaxHeap(Collection<? extends E> h) {
        heap = new Object[h.size()];
        n = 0;
        for (E e : h) {
            heap[n] = e;
            ++n;
        }
        compare = new Comparator<E>() {
            @Override
            public int compare(E left, E right) {
                Comparable<E> leftComp = (Comparable<E>) left;
                E rightComp = (E) right;
                return leftComp.compareTo(rightComp);
            }
        };
        buildHeap();
        tree = new Tree(heap);// !
        tree.setSize(n);// !
    }

    // Return current size of the heap
    public int heapSize() {
        return n;
    }

    // Return root element without removing
    public E peek() {
        return (E) heap[0];
    }

    // Return true if pos a leaf position, false otherwise
    public boolean isLeaf(int pos) {
        return (n / 2 <= pos) && (pos < n);
    }

    // Return position for left child of pos
    public static int leftChild(int pos) {
        return 2 * pos + 1;
    }

    // Return position for right child of pos
    public static int rightChild(int pos) {
        return 2 * pos + 2;
    }

    // Return position for parent
    public static int parent(int pos) {
        return (pos - 1) / 2;
    }

    // Insert val into heap
    public void insert(E key) {
        if (n >= heap.length) {
            int newCapacity = Math.max(1, 2 * heap.length);
            Object[] newHeap = new Object[newCapacity];
            System.arraycopy(heap, 0, newHeap, 0, n);
            heap = newHeap;
        }
        heap[n] = key;
        n++;
        siftUp(n - 1);
    }

    // Heapify contents of Heap
    protected void buildHeap() {
        for (int i = parent(n - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    // Moves an element down to its correct place
    private void siftDown(int pos) {
        assert (0 <= pos && pos < n) : "Invalid heap position";
        while (!isLeaf(pos)) {
            int child = leftChild(pos);
            if ((child + 1 < n) && isGreaterThan(child + 1, child)) {
                child = child + 1; // child is now index with the lesser value
            }
            if (!isGreaterThan(child, pos)) {
                return; // stop early
            }
            swap(pos, child);
            pos = child; // keep sifting down
        }
    }

    // Moves an element up to its correct place
    private void siftUp(int pos) {
        assert (0 <= pos && pos < n) : "Invalid heap position";
        while (pos > 0) {
            int parent = parent(pos);
            if (isGreaterThan(parent, pos)) {
                return; // stop early
            }
            swap(pos, parent);
            pos = parent; // keep sifting up
        }
    }

    // Remove and return root
    public E remove() {
        assert n > 0 : "Heap is empty; cannot remove";
        n--;
        swap(0, n); // Swap maximum with last value
        if (n > 0)
            siftDown(0); // Put new heap root val in correct place
        return (E) heap[n];
    }

    // Remove and return element at specified position
    public E remove(int pos) {
        assert (0 <= pos && pos < n) : "Invalid heap position";
        n--;
        swap(pos, n); // Swap with last value
        update(pos); // Move other value to correct position
        return (E) heap[n];
    }

    // Modify the value at the given position
    public void modify(int pos, E newVal) {
        assert (0 <= pos && pos < n) : "Invalid heap position";
        heap[pos] = newVal;
        update(pos);
    }

    // The value at pos has been changed, restore the heap property
    private void update(int pos) {
        siftUp(pos); // priority goes up
        siftDown(pos); // unimportant goes down
    }

    // swaps the elements at two positions
    private void swap(int pos1, int pos2) {
        Object temp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = temp;
    }

    // does comparison used for checking heap validity
    private boolean isGreaterThan(int pos1, int pos2) {
        E e1 = (E) heap[pos1];
        E e2 = (E) heap[pos2];
        return compare.compare(e1, e2) > 0;
    }

    public void clear() {
        heap = new Object[11];
        n = 0;
    }

    public static <T> void heapSort(ArrayList<T> seq) {
        MaxHeap<T> h = new MaxHeap<>(seq);
        while (h.heapSize() > 1) {
            h.remove();
        }
        for (int i = 0; i < seq.size(); ++i) {
            seq.set(i, (T) h.heap[i]);
        }
    }



















    

    ///////////////////////////////////////////// Convenience //!

    public void buildFrom(E[] values) {
        n = 0;
        for (E e : values) {
            heap[n] = e;
            ++n;
        }
        buildHeap();
        tree.setSize(n);
    }

    public void unHeap(E[] values) {
        n = 0;
        for (E e : values) {
            heap[n] = e;
            ++n;
        }
        tree.setSize(n);
    }

    public Object[] getArr() {
        return heap;
    }

    ///////////////////////////////////////////// Rendering //!
    @Override
    public Renderer<MaxHeap<E>> getRenderer() {
        return this;
    }

    @Override
    public Boolean getClosedOnConnections() {
        return false;
    }

    @Override
    public Color getColor(MaxHeap<E> h) {
        return null;
    }

    @Override
    public List<Component> getComponents(MaxHeap<E> h) {
        java.util.LinkedList<Component> comps = new LinkedList<Component>();
        comps.add(new Component(heap, "heap"));
        comps.add(new Component(n, "n"));
        comps.add(new Component(tree));
        return comps;
    }

    @Override
    public List<Connection> getConnections(MaxHeap<E> h) {
        return new LinkedList<>();
    }

    @Override
    public Directions getDirection() {
        return Directions.Vertical;
    }

    @Override
    public Double getSpacing() {
        return null;
    }

    @Override
    public String getValue(MaxHeap<E> h) {
        return "";
    }
}
