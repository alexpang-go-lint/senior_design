/*Author: Patrick Merker
 *
 */

import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {
		ArrayList<ArrayList<String>> arrayLists = new ArrayList<ArrayList<String>>(); //makes a 2d ArrayList that allows it to take in as many different predictions
		ProcessResults process = new ProcessResults();
		process.readFile(arrayLists); 			//reads the file in Results.csv and adds the predictions to arrayLists
		CheckSim sim = new CheckSim();
		arrayLists = sim.makeCollumns(arrayLists); //compares collumns into an arraylist rather then Tranversing down SEE README
		arrayLists = sim.process(arrayLists); // swaps it back to format below to allow for voting
		process.getPrediction(arrayLists); // Uses voting to determine final output
		process.createSubmission(arrayLists); // creats submission .csv file
		System.out.println("File complete"); 
		
	}

}
