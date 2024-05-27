package edu.odu.cs.cs361.animations;


import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.odu.cs.AlgAE.Animations.LocalJavaAnimation;
import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MenuFunction;
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationStack;
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.CanBeRendered;
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;
//!
//!
//!
//!

public class HashWithProbing extends LocalJavaAnimation {

	public class LPHashTableRendering implements Renderer<hash_set_LP<?>> {

		@Override
		public String getValue(hash_set_LP<?> obj) {
			return "";
		}

		@Override
		public Color getColor(hash_set_LP<?> obj) {
			return null;
		}

		@Override
		public List<Component> getComponents(hash_set_LP<?> ht) {
			LinkedList<Component> comps = new LinkedList<Component>();
			comps.add (new Component(ht.hSize, "hSize"));
			comps.add (new Component(ht.theSize, "theSize"));
			comps.add (new Component(ht.shadow));
			return comps;
		}

		@Override
		public List<Connection> getConnections(hash_set_LP<?> obj) {
			return new LinkedList<Connection>();
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

	}

	
	
	class TableRendering implements Renderer<ArrayList<?>> {

		@Override
		public Color getColor(ArrayList<?> obj) {
			return null;
		}

		@Override
		public List<Component> getComponents(ArrayList<?> a) {
			LinkedList<Component> comps = new LinkedList<Component>();
			for (int i = 0; i < a.size(); ++i)
				comps.add (new Component(a.get(i), "" + i));
			return comps;
		}

		@Override
		public List<Connection> getConnections(ArrayList<?> obj) {
			return new LinkedList<Connection>();
		}

		@Override
		public String getValue(ArrayList<?> obj) {
			return "";
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
		
	}

	public HashWithProbing() {
		super("hashing with probing");
		self = this;
	}

	@Override
	public String about() {
		return "Demonstration of Hashing with Linear and Quadratic\n" +
				"Probing, prepared for CS 361, Advanced Data Structures\n" +
				"and Algorithms, Old Dominion University\n" +
				"Summer 2011";
	}

	
	
	class SillyString implements Renderer<SillyString>, CanBeRendered<SillyString>, Comparable<SillyString> {
		String s;
		
		SillyString (String ss) {s = ss;}
		
		public int hashCode()
		{
			return s.length();
		}

		public String toString()
		{
			return s;
		}
		
		public boolean equals (Object obj)
		{
			SillyString ss = (SillyString)obj;
			return s.equals(ss.s);
		}

		@Override
		public Renderer<SillyString> getRenderer() {
			return this;
		}

		@Override
		public String getValue(SillyString obj) {
			return s;
		}

		@Override
		public Color getColor(SillyString obj) {
			return null;
		}

		@Override
		public List<Component> getComponents(SillyString obj) {
			return new LinkedList<Component>();
		}

		@Override
		public List<Connection> getConnections(SillyString obj) {
			return new LinkedList<Connection>();
		}

		@Override
		public int compareTo(SillyString o) {
			return s.compareTo(o.s);
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
	}

	LocalJavaAnimation self;
	
	hash_set_LP<SillyString> linear = new hash_set_LP<HashWithProbing.SillyString>();

	
	
	
	@Override
	public void buildMenu() {
		
		
		registerStartingAction(new MenuFunction() {
			
			@Override
			public void selected() {
				generateInitialTable();
				globalVar("set", linear);
				ActivationStack stk = getMemoryModel().getActivationStack();
				stk.render(hash_set_LP.class, new LPHashTableRendering());
				stk.render(ArrayList.class, new TableRendering());
			}
		});
		
		
		register ("insert", new MenuFunction() {
			@Override
			public void selected() {
				String valuesList = promptForInput("Comma-separated list of names to insert: ", ".+");
				String[] values = valuesList.split("[ ,]+");
				for (String v: values) {
					linear.add(new SillyString(v));
				}
			}
		});


		register ("find", new MenuFunction() {
			@Override
			public void selected() {
				String value = promptForInput("Names to search for: ", ".+");
				out.println("Does " + value + " occur in the table? " +	linear.contains(new SillyString(value)));
			}
		});

		register ("erase", new MenuFunction() {
			@Override
			public void selected() {
				String value = promptForInput("Name to remove: ", ".+");
				linear.erase(new SillyString(value));
			}
		});

		register ("clear", new MenuFunction() {
			@Override
			public void selected() {
				linear.clear ();
			}
		});

	}
	
	private void generateInitialTable() {
		String valuesList = "Adams,Baker,Clark,Davies";
		String[] values = valuesList.split("[ ,]+");
		for (String v: values) {
			linear.quickInsert(new SillyString(v));
		}
	}
	
	public static void main (String[] args) {
		HashWithProbing demo = new HashWithProbing();
		demo.runAsMain();
	}

}
