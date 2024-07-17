package edu.odu.cs.cs361.animations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;//!
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;//!

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;//!

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
                @SuppressWarnings("unchecked")
                Comparable<E> leftComp = (Comparable<E>) left;
                E rightComp = (E) right;
                return leftComp.compareTo(rightComp);
            }
        };
        tree = new Tree(this);// !
    }

    // Constructor for an empty heap, overriding ordering
    public MaxHeap(Comparator<E> comparator) {
        heap = new Object[11];
        compare = comparator;
        tree = new Tree(this);// !
    }

    // Constructor supporting preloading of heap contents
    public MaxHeap(Collection<? extends E> h) {
        ActivationRecord aRec = activate(getClass());//!
        aRec.refParam("h", h);//!
        aRec.refVar("this", this);//!
        aRec.breakHere("Constructing a heap");//!
        heap = new Object[h.size()];
        n = 0;
        aRec.breakHere("Allocated the array, now fill it.");//!
        for (E e : h) {
            heap[n] = e;
            ++n;
        }
        compare = new Comparator<E>() {
            @Override
            public int compare(E left, E right) {
                @SuppressWarnings("unchecked")
                Comparable<E> leftComp = (Comparable<E>) left;
                E rightComp = (E) right;
                return leftComp.compareTo(rightComp);
            }
        };
        tree = new Tree(this);//!
        aRec.breakHere("Now turn that array into a heap.");//!
        buildHeap();
        aRec.breakHere("Done constructing the heap.");//!
    }

    // Return current size of the heap
    public int heapSize() {
        return n;
    }

    // Return root element without removing
    @SuppressWarnings("unchecked")
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
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("key", key);//!
	    aRec.breakHere("starting insert");//!
    
        if (n >= heap.length) {
            aRec.breakHere("expand array to make room");//!
            int newCapacity = Math.max(1, 2 * heap.length);
            Object[] newHeap = new Object[newCapacity];
            System.arraycopy(heap, 0, newHeap, 0, n);
            heap = newHeap;
        }
        aRec.breakHere("add new key to end of array");//!
        heap[n] = key;
        n++;
        aRec.breakHere("ready to sift up");//!
        siftUp(n - 1);
        aRec.breakHere("insert complete");//!
    }

    // Heapify contents of Heap
    protected void buildHeap() {
        ActivationRecord aRec = activate(getClass());//!
        aRec.breakHere("sift down each of the non-leaf nodes");//!
        for (int i = parent(n - 1); i >= 0; i--) {
            aRec.pushScope();//!
            aRec.var("i", i);//!
            aRec.breakHere("Sift down node[" + i + "]");//!
            siftDown(i);
            aRec.pushScope();//!
        }
        aRec.breakHere("Done with buildHeap");//!
    }

    // Moves an element down to its correct place
    private void siftDown(int pos) {
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("pos", pos);//!
        aRec.breakHere("start sifting down from pos " + pos);//!
        while (!isLeaf(pos)) {
            int child = leftChild(pos);
            aRec.pushScope();//!
            aRec.var("child", child);//!
            aRec.highlight(heap[pos], Color.yellow);//!
            tree.highlightNode(pos, Color.yellow, aRec);//!
            aRec.highlight(heap[child], Color.green);//!
            tree.highlightNode(child, Color.green, aRec);//!
            if (child + 1 < n) { //!
                aRec.highlight(heap[child+1], Color.green);//!
                tree.highlightNode(child+1, Color.green, aRec);//!    
            }//!
            aRec.breakHere("Compare children to see which is larger");//!
            aRec.clearRenderings();
            if ((child + 1 < n) && isGreaterThan(child + 1, child)) {
                child = child + 1; // child is now index with the larger value
            }
            aRec.highlight(heap[pos], Color.yellow);//!
            tree.highlightNode(pos, Color.yellow, aRec);//!
            aRec.highlight(heap[child], Color.green);//!
            tree.highlightNode(child, Color.green, aRec);//!
            aRec.breakHere("Compare pos to larger child.");//!
            if (!isGreaterThan(child, pos)) {
                aRec.popScope();//!
                return; // stop early
            }
            aRec.breakHere("Swap pos and child");//!
            swap(pos, child);
            aRec.breakHere("Keep sifting down");//!
            pos = child; // keep sifting down
            aRec.popScope();//!
        }
    }

    // Moves an element up to its correct place
    private void siftUp(int pos) {
        ActivationRecord aRec = activate(getClass());//!
        aRec.param("pos", pos);//!
        aRec.breakHere("start sifting up from pos " + pos);//!
        while (pos > 0) {
            int parent = parent(pos);
            aRec.pushScope();//!
            aRec.var("parent", parent);//!
            aRec.highlight(heap[pos], Color.yellow);//!
            tree.highlightNode(pos, Color.yellow, aRec);//!
            aRec.highlight(heap[parent], Color.green);//!
            tree.highlightNode(parent, Color.green, aRec);//!
            aRec.breakHere("Compare pos and parent");//!
            if (isGreaterThan(parent, pos)) {
                aRec.popScope();//!
                return; // stop early
            }
            aRec.breakHere("Swap pos and parent");//!
            swap(pos, parent);
            aRec.breakHere("Keep sifting up");//!
            pos = parent; // keep sifting up
            aRec.popScope();//!
            aRec.breakHere("Done sifting down");//!
        }
        aRec.breakHere("Done sifting up");//!
    }

    // Remove and return root
    public E remove() {
        ActivationRecord aRec = activate(getClass());//!
        aRec.breakHere("starting remove");//!
        n--;
        aRec.breakHere("swap root and last node");//!
        swap(0, n); // Swap maximum with last value
        if (n > 0) {
            aRec.breakHere("now sift the root down");//!
            siftDown(0); // Put new heap root val in correct place
        }
        aRec.breakHere("Done with remove. Return the prior largest value.");//!
        @SuppressWarnings("unchecked")
        E result = (E) heap[n];
        return result;
    }

    // Remove and return element at specified position
    public E remove(int pos) {
        assert (0 <= pos && pos < n) : "Invalid heap position";
        n--;
        swap(pos, n); // Swap with last value
        update(pos); // Move other value to correct position
        @SuppressWarnings("unchecked")
        E result = (E) heap[n];
        return result;
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
    @SuppressWarnings("unchecked")
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
        ActivationRecord aRec = activate(MaxHeap.class);//!
        aRec.refParam("seq", seq);//!
        aRec.breakHere("starting heapSort - create the heap");//!
        MaxHeap<T> h = new MaxHeap<>(seq);
        aRec.refVar("h", h);
        while (h.heapSize() > 1) {
            aRec.breakHere("Remove largest element from heap.");//!
            h.remove();
        }
        aRec.breakHere("Copy elements form heap bak to original sequence.");//!
        for (int i = 0; i < seq.size(); ++i) {
            @SuppressWarnings("unchecked")
            T t = (T) h.heap[i];
            seq.set(i, t);
        }
        aRec.breakHere("Heap sort completed.");//!
    }



















    

    ///////////////////////////////////////////// Convenience //!

    public void buildFrom(E[] values) {
        n = 0;
        for (E e : values) {
            heap[n] = e;
            ++n;
        }
        for (int i = n; i < heap.length; ++i) {
            heap[i] = null;
        }
        buildHeap();
    }

    public void unHeap(E[] values) {
        n = 0;
        for (E e : values) {
            heap[n] = e;
            ++n;
        }
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
        return new Color(255,255,200);
    }

    @Override
    public List<Component> getComponents(MaxHeap<E> h) {
        java.util.LinkedList<Component> comps = new LinkedList<Component>();
        comps.add(new Component(n, "n"));
        comps.add(new Component(heap, "heap"));
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
