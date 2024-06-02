package edu.odu.cs.cs361.animations;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.odu.cs.AlgAE.Animations.LocalJavaAnimation;
import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MenuFunction;
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;
import edu.odu.cs.AlgAE.Server.Utilities.SimpleReference;

public class TreeTraversals extends LocalJavaAnimation {

	public TreeTraversals() {
		super("Tree Traversals");
	}

	@Override
	public String about() {
		return "Demonstration of tree traversals,\n" +
				"prepared for CS 361, \n" +
				"Advanced Data Structures and Algorithms,\n" +
				"Old Dominion University\n" +
				"Summer 2011";
	}


	class BinNodeRendering implements Renderer<BinNode<String>> {
		
		@Override
		public Color getColor(BinNode<String> obj) {
			return Color.cyan;
		}

		@Override
		public List<Component> getComponents(BinNode<String> obj) {
			List<Component> results = new LinkedList<Component>();
			return results;
		}
		
		@Override
		public List<Connection> getConnections(BinNode<String> t) {
			LinkedList<Connection> results = new LinkedList<Connection>();
			Connection leftC = new Connection(t.left, 215, 215);
			Connection rightC = new Connection(t.right, 145, 145);
			results.add (leftC);
			results.add (rightC);
			return results;
		}
		
		@Override
		public String getValue(BinNode<String> t) {
			return "" + t.value;
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
            return null;
        }
			
	}
	
	
	class BinaryTreeRendering implements Renderer<BinaryTrees> {

		@Override
		public Color getColor(BinaryTrees obj) {
			return edu.odu.cs.AlgAE.Common.Snapshot.Color.transparent;
		}

		@Override
		public List<Component> getComponents(BinaryTrees tree) {
            List<Component> results = new LinkedList<Component>();
            results.add(new Component(tree.rootRef, "root"));
            addTreeNodes(tree.root, results);
			return results;
		}

		private void addTreeNodes(BinNode<String> root, List<Component> results) {
            if (root != null) {
                results.add(new Component(root));
                addTreeNodes(root.left, results);
                addTreeNodes(root.right, results);
            }
        }

        @Override
		public List<Connection> getConnections(BinaryTrees bt) {
			LinkedList<Connection> conn = new LinkedList<Connection>();
			return conn;
		}


		@Override
		public String getValue(BinaryTrees obj) {
			return "";
		}

        @Override
        public Boolean getClosedOnConnections() {
            return false;
        }

        @Override
        public Directions getDirection() {
            return Directions.VerticalTree;
        }

        @Override
        public Double getSpacing() {
            return 2.5;
        }
		
	}

	
	
	
	public void quickInsert (BinNode<String> t, String element)
	{ 
		int comp = element.compareTo(t.value);
		if (comp < 0) 
		{
			if (t.left != null)
			{
				quickInsert (t.left, element);
			}
			else
			{
				t.left = new BinNode<String>(element);
			}
		} 
		else
		{
			if (t.right != null)
			{
				quickInsert (t.right, element);
			}
			else
			{
				t.right = new BinNode<String>(element);
			}
		} 
	}

	public void quickInsert (BinaryTrees tree, String element)
	{ 
		if (tree.root != null)
			quickInsert(tree.root, element);
		else
			tree.root = new BinNode<String>(element);
	}

		

	BinaryTrees bt = new BinaryTrees();
	Random rand = new Random();
	String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	@Override
	public void buildMenu() {
		
		registerStartingAction(new MenuFunction() {

			@Override
			public void selected() {
				globalVar("",bt);
				bt.root = createSampleTree1();
                bt.rootRef.set(bt.root);
				getMemoryModel().render(BinNode.class, new BinNodeRendering());
				getMemoryModel().render(BinaryTrees.class, new BinaryTreeRendering());
			}
			
		});
		

		register("pre-order traversal", new MenuFunction() {

			@Override
			public void selected() {
				bt.preorderOutput(bt.root, " ");
				out.println("");
			}
			
		});
		
		register("post-order traversal", new MenuFunction() {

			@Override
			public void selected() {
				bt.postorderOutput(bt.root, " ");
				out.println("");
			}
			
		});
		
		register("in-order traversal", new MenuFunction() {

			@Override
			public void selected() {
				bt.inorderOutput(bt.root, " ");
				out.println("");
			}
			
		});

		register("level-order traversal", new MenuFunction() {

			@Override
			public void selected() {
				bt.levelorderOutput(bt.root, " ");
				out.println("");
			}
			
		});

		register("create a random tree", new MenuFunction() {

			@Override
			public void selected() {
				bt.root = null;
				String nNodesS = promptForInput("How many nodes?", "[0-9]+");
				int nNodes = Integer.parseInt(nNodesS);
				for (int i = 0; i < nNodes; ++i) {
					int k = rand.nextInt(26);
					String c = chars.substring(k, k+1);
					quickInsert (bt, c);
				}
                bt.rootRef.set(bt.root);
			}
		});

	}
	

	public BinNode<String> createSampleTree1()//!
	{
		BinNode<String> c13 = new BinNode<String>("13", null, null);//!
		BinNode<String> a = new BinNode<String>("a", null, null);//!
		BinNode<String> x = new BinNode<String>("X", null, null);//!
		BinNode<String> c1 = new BinNode<String>("1", null, null);//!
		BinNode<String> oplus = new BinNode<String>("+", c13, a);//!
		BinNode<String> ominus = new BinNode<String>("-", x, c1);//!
		BinNode<String> otimes = new BinNode<String>("*", oplus, ominus);//!
		return otimes;
	}
	
	
	public static void main (String[] args) {
		TreeTraversals demo = new TreeTraversals();
		demo.runAsMain();
	}

}
