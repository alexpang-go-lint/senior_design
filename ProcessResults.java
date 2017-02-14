import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessResults {
	
	int allowed;
	
	
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

	public ArrayList<ArrayList<String>> disConnectedNodes(ArrayList<ArrayList<String>> arrayLists, int num){
		allowed = num;
		Graph graph = new Graph(arrayLists.get(0).size());	// gets the number of columns then does not count the pump number
		ArrayList<Column> columns = new ArrayList<Column>();	// Makes an array of columns to be filled	
		ArrayList<ArrayList<String>> FinishedArray = new ArrayList<ArrayList<String>>(); // hold the final array after collumns selected
		for(int i = 0; i < arrayLists.get(0).size();i++){		// adds columns
			Column temp = new Column(arrayLists,i);
			columns.add(temp);
		}
		
		for(int i = 0; i < columns.size();i++){				// cycle through and see which columns are similar
			
			for(int j = 0; j < columns.size();j++){
				
				if(columns.get(i).simular(columns.get(j),allowed)){
					graph.addEdge(i, j);
				}
			}
			
		}
		System.out.println("___________________________________________________________");
		System.out.println("                     GRAPH                                 ");
		graph.print();
		System.out.println("___________________________________________________________");
		graph.disconnectedNodes();		// calls object graph to get disconnected nodes
		
		System.out.println("***********************************************************");
		System.out.println("               Algorithms Selected                         ");
		for(int i = 0; i < graph.disNodes.size();i++){				// cycle through and see which columns are similar
		FinishedArray.add(columns.get(graph.disNodes.get(i)).column);
		System.out.println(graph.disNodes.get(i));
		}
		System.out.println("***********************************************************");
		return FinishedArray;
		
	}
		
	    @SuppressWarnings("resource")
		public void readFile(ArrayList<ArrayList<String>> arrayLists) throws IOException{ 
	    	 BufferedReader CSVFile = null;
			CSVFile = new BufferedReader(new FileReader("Results.csv"));
			String dataRow;
			dataRow = CSVFile.readLine();
				    	
	    	while ((dataRow = CSVFile.readLine()) != null){
					String [] temp = dataRow.split(",");
					ArrayList<String> toLoad = new ArrayList<String>();
					toLoad.add(temp[0]);
					for(int i = 1; i< temp.length; i++){
						toLoad.add(temp[i]);
					}
					arrayLists.add(toLoad);
				}
	}
	
	    public void getPrediction(ArrayList<ArrayList<String>> arrayLists){
			
	    	for (int i = 0; i < arrayLists.size();i++){
	    		ArrayList<String> compare = arrayLists.get(i);
	    		
				int functional = 0;
				int nonfunctional = 0;
				int repair = 0;
				
				for(int j = 0; j < compare.size();j++){
					if(compare.get(j).equals("functional"))
						functional++;
				}
				
				for(int j = 0; j < compare.size();j++){
					if(compare.get(j).equals("non functional"))
						nonfunctional++;
				}
				
				for(int j = 0; j < compare.size();j++){
					if(compare.get(j).equals("functional needs repair"))
						repair++;
				}
				
				if(functional > nonfunctional && functional > repair){
					compare.add("functional");
				}else if(nonfunctional > functional && nonfunctional > repair){
					compare.add("non functional");
				}else if(repair > nonfunctional && repair > functional){
					compare.add("functional needs repair");
				}else{
					compare.add(compare.get(1));
				}
			}
		}

	    public void createSubmission(ArrayList<ArrayList<String>> arrayLists) throws FileNotFoundException{
			
			 File file = new File ("Submission.csv");                               		   
		        java.io.PrintStream ps = new java.io.PrintStream(file);          			
		        if(file.exists()){                                               			
		        	ps.print("id" + "," + "status_group");
		        	ps.println(""); 
		        	for (int i = 0; i < arrayLists.size(); i++ ){  							
		        			ps.print(arrayLists.get(i).get(0) + "," + arrayLists.get(i).get(arrayLists.get(i).size()-1));  
		        			ps.println(""); 
			        	}                                                        			 
		        }   
		        ps.close();                                                       
	    }
}
