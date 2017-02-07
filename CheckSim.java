import java.util.ArrayList;

public class CheckSim {

	
	public ArrayList<String> makeCollumn(ArrayList<ArrayList<String>> arrayLists, int position){
		ArrayList<String> collumn = new ArrayList<String>();
		for(int i = 0; i < arrayLists.size(); i++){
			collumn.add(arrayLists.get(i).get(position));
		}
		return collumn;
	}

		public boolean simular(ArrayList<String> collumn1, ArrayList<String> collumn2){
			int same =0;
			for(int i = 0; i < collumn1.size(); i++){
				if(collumn1.get(i).equals(collumn2.get(i))){
					same++;
				}
			}
			double percent = same/collumn1.size();
			
			if(percent<.60){
			return false;
			}
			
			return true;
		}
		
		public ArrayList<ArrayList<String>> makeCollumns(ArrayList<ArrayList<String>> arrayLists){
			ArrayList<ArrayList<String>> collumns = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> FinishedArray = new ArrayList<ArrayList<String>>();
			for(int i = 0; i < arrayLists.get(0).size();i++){
				collumns.add(makeCollumn(arrayLists,i));
			}
			
			for(int i = 0; i < collumns.size();i++){
				boolean same = false;
				for(int j = i+1; j < arrayLists.get(0).size();j++){
					if(i == j){
						continue;
					}
					if(simular(collumns.get(i),collumns.get(j))){
						same = true;
					}
				}
				if(!same)
					FinishedArray.add(collumns.get(i));
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
	
}
