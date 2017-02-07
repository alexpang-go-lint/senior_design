/*Author: Patrick Merker
 *
 */

import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {
		ArrayList<ArrayList<String>> arrayLists = new ArrayList<ArrayList<String>>();
		ProcessResults process = new ProcessResults();
		process.readFile(arrayLists);
		CheckSim sim = new CheckSim();
		arrayLists = sim.makeCollumns(arrayLists);
		arrayLists = sim.process(arrayLists);
		process.getPrediction(arrayLists);
		process.createSubmission(arrayLists);
		process.createSubmission(arrayLists);
		System.out.println("File complete");
		
	}

}
