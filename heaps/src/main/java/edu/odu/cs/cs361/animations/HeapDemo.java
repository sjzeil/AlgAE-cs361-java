package edu.odu.cs.cs361.animations;

import java.util.Random;

import edu.odu.cs.AlgAE.Animations.LocalJavaAnimation;
import edu.odu.cs.AlgAE.Server.MenuFunction;
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;

import static edu.odu.cs.AlgAE.Server.LocalServer.activate;

public class HeapDemo extends LocalJavaAnimation {

    public HeapDemo() {
        super("Heaps");
    }

    @Override
    public String about() {
        return "Demonstration of heaps and heap sort,\n" +
                "prepared for CS 361, \n" +
                "Advanced Data Structures and Algorithms,\n" +
                "Old Dominion University\n" +
                "Summer 2024";
    }

    boolean displayParentPointers = false;



    public void createSampleHeap(MaxHeap<Integer> heap) {
        ActivationRecord aRec = activate(getClass());// !
        Integer[] data = { 66, 58, 63, 55, 48, 60, 11, 14, 53, 47 };
        aRec.suppressAnimation();
        heap.buildFrom(data);
        aRec.resumeAnimation();
    }

    MaxHeap<Integer> heap = new MaxHeap<>();
    Random rand = new Random();
    boolean isAHeap = false;

    @Override
    public void buildMenu() {

        registerStartingAction(new MenuFunction() {

            @Override
            public void selected() {
                globalVar("", heap);
                createSampleHeap(heap);
                isAHeap = true;
            }

        });

        register("build heap", new MenuFunction() {

            @Override
            public void selected() {
                String szS = promptForInput("How many numbers?", "[0-9]+");
                int sz = Integer.parseInt(szS);
                Integer[] data = new Integer[sz];
                for (int i = 0; i < sz; ++i) {
                    data[i] = rand.nextInt(100);
                }

                heap.buildFrom(data);
                isAHeap = true;
            }

        });

        register("add a value", new MenuFunction() {

            @Override
            public void selected() {
                if (isAHeap) {
                    String xs = promptForInput("Comma-separated list of integers to insert:", "[0-9 ,]+");
                    String[] values = xs.split("[ ,]+");
                    for (String x0 : values) {
                        try {
                            int x = Integer.parseInt(x0);
                            heap.insert(x);
                        } catch (Exception e) {
                        }
                    }
                } else {
                    promptForInput("You must build the heap, first.", ".*");
                }
            }

        });

        register("remove largest value", new MenuFunction() {

            @Override
            public void selected() {
                if (isAHeap) {
                    heap.remove();
                } else {
                    promptForInput("You must build the heap, first.", ".*");
                }
            }

        });

        register("clear", new MenuFunction() {

            @Override
            public void selected() {
                heap.clear();
                isAHeap = true;
            }
        });

        register("heap sort", new MenuFunction() {

            @Override
            public void selected() {
                String szS = promptForInput("How many numbers?", "[0-9]+");
                int sz = Integer.parseInt(szS);
                java.util.ArrayList<Integer> values = new java.util.ArrayList<>();
                for (int i = 0; i < sz; ++i) {
                    values.add(rand.nextInt(100));
                }

                MaxHeap.heapSort(values);
            }

        });
    }

    public static void main(String[] args) {
        HeapDemo demo = new HeapDemo();
        demo.runAsMain();
    }

}
