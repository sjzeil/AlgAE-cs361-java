package edu.odu.cs.cs361.animations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.odu.cs.AlgAE.Animations.LocalJavaAnimation;
import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MenuFunction;
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;
import edu.odu.cs.AlgAE.Server.Utilities.DiscreteInteger;

public class CS361ArraysAnimation extends LocalJavaAnimation {

    public CS361ArraysAnimation() {
        super("Array Operations");
    }

    @Override
    public String about() {
        return "Demonstration of Array Manipulation Algorithms,\n" +
                "prepared for CS 361, Advanced Data Structures\n" +
                "and Algorithms, Old Dominion University\n" +
                "Summer 2014";
    }

    private DiscreteInteger[] array = new DiscreteInteger[0];

    private class ArrayContainer implements CanBeRendered<ArrayContainer>, Renderer<ArrayContainer> {

        @Override
        public Renderer<ArrayContainer> getRenderer() {
            return this;
        }

        @Override
        public Color getColor(ArrayContainer obj) {
            return Color.white;
        }

        @Override
        public List<Component> getComponents(ArrayContainer obj) {
            ArrayList<Component> c = new ArrayList<Component>();
            c.add(new Component(array));
            return c;
        }

        @Override
        public List<Connection> getConnections(ArrayContainer obj) {
            return new ArrayList<Connection>();
        }

        @Override
        public String getValue(ArrayContainer obj) {
            return "";
        }

        @Override
        public Boolean getClosedOnConnections() {
            return false;
        }

        @Override
        public Directions getDirection() {
            return Directions.Horizontal;
        }

        @Override
        public Double getSpacing() {
            return 1.0;
        }

    }


    @Override
    public void buildMenu() {

        registerStartingAction(new MenuFunction() {

            @Override
            public void selected() {
                generateRandomArray(8);
                globalVar("array", new ArrayContainer());
            }
        });

        register("binary search", new MenuFunction() {
            @Override
            public void selected() {
                String value = promptForInput("Value to search for:", "[0-9]+");
                try {
                    Integer v = Integer.parseInt(value);
                    int k = Utilities.binarySearch(array, v);
                    out.println("binarySearch returned " + k);
                } catch (Exception e) {
                    // do nothing
                }
            }
        });

        register("ordered sequential search", new MenuFunction() {
            @Override
            public void selected() {
                String value = promptForInput("Value to search for:", "[0-9]+");
                try {
                    Integer v = Integer.parseInt(value);
                    int k = Utilities.orderedSequential(array, new DiscreteInteger(v));
                    out.println("orderedSequential returned " + k);
                } catch (Exception e) {
                    // do nothing
                }
            }
        });

        register("sequential search", new MenuFunction() {
            @Override
            public void selected() {
                String value = promptForInput("Value to search for:", "[0-9]+");
                try {
                    Integer v = Integer.parseInt(value);
                    int k = Utilities.sequential(array, new DiscreteInteger(v));
                    out.println("sequential returned " + k);
                } catch (Exception e) {
                    System.err.println("Unexpected exception from animated code: " + e);
                }
            }
        });

        final DiscreteInteger size = new DiscreteInteger(999);

        register("insert in order", new MenuFunction() {

            @Override
            public void selected() {
                String value = "";
                if (size.get() >= array.length) {
                    size.set(array.length - 2);
                    array[array.length - 1] = new DiscreteInteger(999);
                    array[array.length - 2] = new DiscreteInteger(999);
                    value = promptForInput("For the purpose of this demonstration\nwe will assume that the\nlast two slots in the array are\nunused.\n\nValue to add:", "[0-9]+");
                } else {
                    value = promptForInput("Value to add:", "[0-9]+");
                }
                try {
                    Integer v = Integer.parseInt(value);
                    Utilities.insertInOrder(v, array, size.get());
                    size.set(size.get() + 1);
                } catch (Exception e) {
                    // do nothing
                }
            }
        });

        register("array copy", new MenuFunction() {
            @Override
            public void selected() {
                String value = promptForInput("Position to copy from:", "[0-9]+");
                try {
                    Integer srcPos = Integer.parseInt(value);
                    value = promptForInput("Position to copy into:", "[0-9]+");
                    Integer destPos = Integer.parseInt(value);
                    value = promptForInput("How many elements to copy?", "[0-9]+");
                    Integer len = Integer.parseInt(value);
                    Utilities.arraycopy(array, srcPos, array, destPos, len);
                } catch (Exception e) {
                    // do nothing
                }
            }
        });

        register("generate an array", new MenuFunction() {
            @Override
            public void selected() {
                randomArrayGenerated();
            }
        });

    }

    public void randomArrayGenerated() {
        String value = promptForInput("How many elements?", "\\d+");
        int n = Integer.parseInt(value);
        generateRandomArray(n);
    }

    public void generateRandomArray(int n) {
        array = new DiscreteInteger[n];

        if (n > 0) {
            array[0] = new DiscreteInteger((int) (5.0 * Math.random()));
        }
        for (int i = 1; i < n; ++i) {
            array[i] = new DiscreteInteger(array[i - 1].get() + ((int) (5.0 * Math.random())));
        }
    }

    public static void main(String[] args) {
        CS361ArraysAnimation demo = new CS361ArraysAnimation();
        demo.runAsMain();
    }

}