package edu.odu.cs.cs361.animations;



import java.awt.Color;
import java.util.List;

import edu.odu.cs.AlgAE.Animations.LocalJavaAnimation;
import edu.odu.cs.AlgAE.Common.Snapshot.Entity.Directions;
import edu.odu.cs.AlgAE.Server.MenuFunction;
import edu.odu.cs.AlgAE.Server.MemoryModel.Component;
import edu.odu.cs.AlgAE.Server.MemoryModel.Connection;
import edu.odu.cs.AlgAE.Server.Rendering.Renderer;
import edu.odu.cs.AlgAE.Server.Utilities.SimpleReference;


public class ArrayListAnimation extends LocalJavaAnimation {

	public ArrayListAnimation() {
		super("Array Operations");
	}

	@Override
	public String about() {
		return "Demonstration of ArrayList implementation algorithms,\n" +
		"prepared for CS 361, Advanced Data Structures\n" +
		"and Algorithms, Old Dominion University\n" +
		"Summer 2024";
	}

	private ArrayList<Integer> a = new ArrayList<Integer>();
	
	
	
	
	@Override
	public void buildMenu() {
		
		
		
		registerStartingAction(new MenuFunction() {
			
			@Override
			public void selected() {
				generateRandomVector(3);
				globalVar("a", a);
			}
		});
		
		register ("Generate an ArrayList", new MenuFunction() {
			@Override
			public void selected() {
				randomVectorGenerated();
			}
		});

				
		register ("a.add(element)", new MenuFunction() {
			@Override
			public void selected() {
				String value = promptForInput("Value to add:", "[0-9]+");
				try {
					Integer v = Integer.parseInt(value);
					a.add (v.intValue());
				} catch (Exception e) {
					// do nothing
				}
			}
		});

		register ("a.add(index, element)", new MenuFunction() {
			@Override
			public void selected() {
				String value = promptForInput("Value to add:", "[0-9]+");
				try {
					Integer v = Integer.parseInt(value);
                    value = promptForInput("position at which to add:", "[0-9]+");
                    int index = Integer.parseInt(value);
                    if (index >= 0 && index <= a.size()) {
					    a.add (index, v.intValue());
                    }
				} catch (Exception e) {
					// do nothing
				}
			}
		});


		register ("a.remove(index)", new MenuFunction() {
			@Override
			public void selected() {
				String value = promptForInput("position from which to remove:", "[0-9]+");
				try {
                    int index = Integer.parseInt(value);
                    if (index >= 0 && index <= a.size()) {
					    a.remove (index);
                    }
				} catch (Exception e) {
					// do nothing
				}
			}
		});



    }
	
	public void randomVectorGenerated()
	{
		String value = promptForInput("How many elements?", "\\d+");
		int n = Integer.parseInt(value);
		generateRandomVector(n);
	}

	public void generateRandomVector(int n)
	{
		a.quick_clear();
		for (int i = 0; i < n; ++i)
			a.quick_add ((int)(25.0*Math.random()));
	}

	
	
	public static void main (String[] args) {
		ArrayListAnimation demo = new ArrayListAnimation();
		demo.runAsMain();
	}
	


}
