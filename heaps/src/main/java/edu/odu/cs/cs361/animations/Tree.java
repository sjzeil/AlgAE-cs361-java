package edu.odu.cs.cs361.animations;

import java.awt.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;

public class Tree implements Renderer<Tree>, CanBeRendered<Tree> {

    public class TreeNode implements Renderer<TreeNode>, CanBeRendered<TreeNode> {
		int index;
		
		TreeNode (int index) {
			this.index = index;
		}

        		@Override
		public Color getColor(TreeNode obj) {
			return Color.cyan;
		}

		@Override
		public List<Component> getComponents(TreeNode obj) {
			List<Component> results = new LinkedList<Component>();
			return results;
		}
		
		@Override
		public List<Connection> getConnections(TreeNode t) {
			LinkedList<Connection> results = new LinkedList<Connection>();
			int iLeft = 2 * t.index + 1;
			int iRight = 2 * t.index + 2;
			TreeNode left = (iLeft < size) ? nodes.get(iLeft) : null;
			TreeNode right = (iRight < size) ? nodes.get(iRight) : null;
			
			Connection leftC = new Connection(left, 215, 215);
			Connection rightC = new Connection(right, 145, 145);

			results.add (leftC);
			results.add (rightC);
			return results;
		}
		
		@Override
		public String getValue(TreeNode t) {
			Object v = mirrors[t.index];
			return v.toString();
		}

        @Override
        public Boolean getClosedOnConnections() {
            return false;
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
        public Renderer<TreeNode> getRenderer() {
            return this;
        }
			
	}
	
	



    private Object[] mirrors;
    private int size;
    private ArrayList<TreeNode> nodes;

    public Tree(Object[] mirroring) {
        mirrors = mirroring;
        size = 0;
        nodes = new ArrayList<>();
    }

    public void setSize(int n) {
        size = n;
        while (n > nodes.size()) {
            nodes.add(new TreeNode(nodes.size()));
        }
    }


    @Override
    public Color getColor(Tree obj) {
        return new Color(java.awt.Color.TRANSLUCENT);
    }

    @Override
    public List<Component> getComponents(Tree t) {
        java.util.LinkedList<Component> comps = new LinkedList<Component>();
        for (int i = 0; i < size; ++i)
            comps.add (new Component(nodes.get(i)));
        return comps;
    }

    @Override
    public List<Connection> getConnections(Tree t) {
        LinkedList<Connection> conns =  new LinkedList<Connection>();
        return conns;
    }

    @Override
    public String getValue(Tree obj) {
        return "";
    }

    @Override
    public Boolean getClosedOnConnections() {
        return true;
    }

    @Override
    public Directions getDirection() {
        return Directions.VerticalTree;
    }

    @Override
    public Double getSpacing() {
        return 2.0;
    }

    @Override
    public Renderer<Tree> getRenderer() {
        return this;
    }

}
