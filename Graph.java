public class Graph {
	ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();	// will hold edges, each index is a node
	boolean[] active;		// Tells if node is active
	ArrayList<Integer> disNodes;
	
	
	public Graph(int num) {		// Constructor
		active = new boolean[num];	//sets number active nodes
		for (int i = 0; i < num; i++){		//
			ArrayList<Integer> temp =new ArrayList<Integer>();		
			graph.add(temp);		// adds arraylist for edges at node i
			active[i]=true;			// makes every node active
		}
	}
	
	public void addEdge (int i, int j){		// adds edge to each node
		graph.get(i).add(j);
		graph.get(j).add(i);
	}
	
	public boolean isConnected(int a, int b){				// returns if the node is connected
		for(int i =0; i < graph.get(a).size(); i++){	// cycles through that nodes edges
			if(graph.get(a).get(i)==b && active[b] == true){					// if it contains number and is active
				return true;
			}
		}
		return false;
	}
	
	public void setConnectedInactive(int a){
		active[a] = false;						// each node is connected to itself
		for(int i =0; i < graph.get(a).size(); i++){	// sets every node connected to the node inactive
			active[graph.get(a).get(i)] = false;
		}
	}
	
	public void disconnectedNodes(){			// returns list of disconnected nodes
		disNodes = new ArrayList<Integer>();				// creates new object for class to use
		for (int i = 0; i < graph.size(); i++ ){		// cycles through each node
			if(active[i]){								// if that node is active
				disNodes.add(i);						// add it to the disConnected Nodes
				setConnectedInactive(i);				// Set all connected NOdes as inactive
			}
		}
		
	}
	
	public int size(){
		return active.length;
	}
	
}
