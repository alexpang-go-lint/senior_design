import java.util.ArrayList;


public class CheckSim {

	
			
		public ArrayList<ArrayList<String>> getUnSimCollumns(ArrayList<ArrayList<String>> arrayLists){
			ArrayList<Column> collumns = new ArrayList<Column>();
			ArrayList<ArrayList<String>> FinishedArray = new ArrayList<ArrayList<String>>();
			for(int i = 0; i < arrayLists.get(0).size();i++){
				Column temp = new Column(arrayLists,i);
				collumns.add(temp);
			}
			
			for(int i = 0; i < collumns.size();i++){
				boolean same = false;
				for(int j = i+1; j < arrayLists.get(0).size();j++){
					if(i == j){
						continue;
					}
					if(collumns.get(i).simular(collumns.get(j))){
						same = true;
					}
				}
				if(!same)
					FinishedArray.add(collumns.get(i).column);
			}
			
			return FinishedArray;
			
		}
		
		public ArrayList<ArrayList<String>> process(ArrayList<ArrayList<String>> arrayLists){
			ArrayList<ArrayList<String>> FinishedArray = new ArrayList<ArrayList<String>>();
			for(int i = 0; i < arrayLists.get(0).size();i++){
				ArrayList<String> temp = new ArrayList<String>();
				for (int j = 0; j < arrayLists.size(); j++){
					temp.add(arrayLists.get(j).get(i));
				}
				FinishedArray.add(temp);
			}
			
			return FinishedArray;
			
			
		}

		public ArrayList<ArrayList<String>> disConnectedNodes(ArrayList<ArrayList<String>> arrayLists){
			Graph graph = new Graph(arrayLists.get(0).size());	// gets the number of columns then does not count the pump number
			ArrayList<Column> columns = new ArrayList<Column>();	// Makes an array of columns to be filled	
			ArrayList<ArrayList<String>> FinishedArray = new ArrayList<ArrayList<String>>(); // hold the final array after collumns selected
			for(int i = 0; i < arrayLists.get(0).size();i++){		// adds columns
				Column temp = new Column(arrayLists,i);
				columns.add(temp);
			}
			
			for(int i = 0; i < columns.size();i++){				// cycle through and see which columns are similar
				
				for(int j = 0; j < columns.size();j++){
					
					if(columns.get(i).simular(columns.get(j))){
						graph.addEdge(i, j);
					}
				}
				
			}
			
			graph.disconnectedNodes();		// calls object graph to get disconnected nodes
			
			for(int i = 0; i < graph.disNodes.size();i++){				// cycle through and see which columns are similar
			FinishedArray.add(columns.get(graph.disNodes.get(i)).column);
			System.out.println(graph.disNodes.get(i));
			}
			return FinishedArray;
			
		}
}
