import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessResults {
	
		
		
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
