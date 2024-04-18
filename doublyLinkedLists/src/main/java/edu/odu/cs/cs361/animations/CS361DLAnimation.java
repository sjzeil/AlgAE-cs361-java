package edu.odu.cs.cs361.animations;



import static edu.odu.cs.AlgAE.Server.LocalServer.activate;

import java.util.Arrays;
import java.util.ListIterator;

import edu.odu.cs.AlgAE.Animations.LocalJavaAnimation;
import edu.odu.cs.AlgAE.Server.MenuFunction;
import edu.odu.cs.AlgAE.Server.MemoryModel.ActivationRecord;

public class CS361DLAnimation extends LocalJavaAnimation {

	public CS361DLAnimation() {
		super("Doubly Linked List Operations");
	}

	@Override
	public String about() {
		return "Demonstration of Doubly Linked List Algorithms,\n" +
		"prepared for CS 361, Advanced Data Structures\n" +
		"and Algorithms, Old Dominion University\n" +
		"Summer 2014";
	}


	private LinkedList<String> list;
	private ListIterator<String> iter;
	
	
	@Override
	public void buildMenu() {
		
		registerStartingAction(new MenuFunction() {
			
			
			@Override
			public void selected() {
				String[] initialContent = {"Adams", "Baker", "Charles"};
				list = new LinkedList<>(Arrays.asList(initialContent));
				iter = list.listIterator();
				globalVar("list", list);
				globalVar("iter", iter);
			}
		});
		
		register ("addLast", new MenuFunction() {
			@Override
			public void selected() {
				String value = promptForInput("Value to add:", ".+");
				list.addLast(value);
			}
		});


		register ("addFirst", new MenuFunction() {
			@Override
			public void selected() {
				String value = promptForInput("Value to add:", ".+");
				list.addFirst (value);
			}
		});
		
		
		register ("removeLast", new MenuFunction() {
			@Override
			public void selected() {
				list.removeLast();
                list.resetIterator(iter);
			}
		});


		register ("removeFirst", new MenuFunction() {
			@Override
			public void selected() {
				list.removeFirst ();
                list.resetIterator(iter);
			}
		});
		
		register ("clear", new MenuFunction() {
			@Override
			public void selected() {
				list.clear ();
                list.resetIterator(iter);
			}
		});

	
		register ("Reset the list", new MenuFunction() {
			@Override
			public void selected() {
				String[] initialContent = {"Adams", "Baker", "Charles"};
                list.clear();
                list.addAll(Arrays.asList(initialContent));
                list.resetIterator(iter);
			}
		});

				
		register ("traverse (print)", new MenuFunction() {
			@Override
			public void selected() {
				traverse(list);
			}
		});

		register ("move iter (search)", new MenuFunction() {
			@Override
			public void selected() {
                String value = promptForInput("Value to search for:", ".+");//!
                searchList(value);
            }
		});

		register ("iter = list.listIterator()", new MenuFunction() {
			@Override
			public void selected() {
				list.resetIterator(iter);
			}
		});


		register ("iter.next()", new MenuFunction() {
			@Override
			public void selected() {
				try {
					iter.next();
				} catch (Exception e) {
					// ignore
				}
			}
		});

		register ("iter.previous()", new MenuFunction() {
			@Override
			public void selected() {
				try {
					iter.previous();
				} catch (Exception e) {
					// ignore
				}
			}
		});

		register ("iter.add(value)", new MenuFunction() {
			@Override
			public void selected() {
				String value = promptForInput("Value to insert:", ".+");
				try {
					iter.add(value);
				} catch (Exception e) {
					// ignore
				}
			}
		});

		register ("iter.remove()", new MenuFunction() {
			@Override
			public void selected() {
				try {
					iter.remove();
				} catch (Exception e) {
					// ignore
				}
			}
		});

	}
		
    void searchList(String value) {
        ActivationRecord aRec = activate(getClass());//!
        aRec.var("value", value).breakHere("begin moving forward");//!
        while (iter.hasNext()) {
            String v = iter.next();
            aRec.var("v", v).breakHere("Is this what we want?");//!
            if (v.equals(value)) {
                aRec.breakHere("Yes!");//!
                out.println("Found it!");
                break;
            }
        }
    }

	void traverse(LinkedList<String> aList) //!void traverse(const list<string>& alist)
	{
		ActivationRecord aRec = activate(getClass());//!
	    aRec.refParam("aList",aList).breakHere("starting traversal");//!
	    ListIterator<String> pos = aList.listIterator();//!    list<string>::const_iterator pos = alist.begin();
	    aRec.var("pos", pos).breakHere("Got the starting position");//!
	    while (pos.hasNext())
		{
	        aRec.breakHere("Ready to step forward");//!
            String value = pos.next();
	        aRec.var("value", value).breakHere("Stepped forward");//!
            out.println(value);
		}
	    aRec.breakHere("Traversal completed");//!
	}

	
	public static void main (String[] args) {
		CS361DLAnimation demo = new CS361DLAnimation();
		demo.runAsMain();
	}

}
