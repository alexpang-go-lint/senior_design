/*Author: Patrick Merker
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	static int allowed = 13365;		// 60 percent = 8910, 75% = 11138, 90% = 13365, 100% = 14850
	static boolean found = false;
	
	private static boolean runProcess() throws IOException, FileNotFoundException {
		ArrayList<ArrayList<String>> arrayLists = new ArrayList<ArrayList<String>>();
		ProcessResults process = new ProcessResults();
		process.readFile(arrayLists);
		arrayLists = process.disConnectedNodes(arrayLists,allowed);
		arrayLists = process.process(arrayLists);
		if(arrayLists.get(0).size()==4){
			found = true;
		}
		process.getPrediction(arrayLists);
		process.createSubmission(arrayLists);
		
		return found;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		//runProcess();
		while (!runProcess()){
			allowed = allowed - 112;
		}
		System.out.println("File complete");
		double num = 0.00;
		num = allowed;
		System.out.println("Total Percent Simular : " + num/ 14850);
		
	}

	

}
