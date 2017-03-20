


	/** Class for kmeans clustering
	* created by Keke Chen (keke.chen@wright.edu)
	* For Cloud Computing Labs
	* Feb. 2014
	*/
	import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.io.StringReader;

	public class KMeans 
	{
	  // Data members
	  private double [][] _data; // Array of all records in dataset
	  private int [] _label;  // generated cluster labels
	  private int [] _withLabel; // if original labels exist, load them to _withLabel
	                              // by comparing _label and _withLabel, we can compute accuracy. 
	                              // However, the accuracy function is not defined yet.
	  private double [][] _centroids; // centroids: the center of clusters
	  private int _nrows, _ndims; // the number of rows and dimensions
	  private int _numClusters; // the number of clusters;
	  ArrayList<ArrayList<Double>> arrayLists = new ArrayList<ArrayList<Double>>();
	  
	  // Constructor; loads records from file <fileName>. 
	  // if labels do not exist, set labelname to null
	  @SuppressWarnings("resource")
	public KMeans() throws IOException 
	  {
		  BufferedReader CSVFile = null;
			CSVFile = new BufferedReader(new FileReader("LandL.csv"));
			String dataRow;
			dataRow = CSVFile.readLine();		// Skips Header
		   	
	    	while ((dataRow = CSVFile.readLine()) != null){
					String [] temp = dataRow.split(",");
					ArrayList<Double> toLoad = new ArrayList<Double>();
					toLoad.add(Double.parseDouble(temp[0]));
					toLoad.add(Double.parseDouble(temp[1]));
					toLoad.add(Double.parseDouble(temp[2]));
					arrayLists.add(toLoad);
				}
	    	
	    	  _data = new double[arrayLists.size()][];
	    	  _nrows = arrayLists.size();
	    	  _ndims = arrayLists.get(0).size()-1;
	    	  for(int i =0; i < arrayLists.size(); i++){
		    		_data[i] = new double [arrayLists.get(0).size()-1];
	    	  }
	    	  
	    	for(int i =0; i < arrayLists.size(); i++){
	    		
	    		for(int j = 1; j  <arrayLists.get(0).size();j++){
	    			double temp = arrayLists.get(i).get(j);
	    			_data[i][j-1]=temp;
	    		}
	    	}
	}

	 
	  
	  // Perform k-means clustering with the specified number of clusters and
	  // Eucliden distance metric. 
	  // niter is the maximum number of iterations. If it is set to -1, the kmeans iteration is only terminated by the convergence condition.
	  // centroids are the initial centroids. It is optional. If set to null, the initial centroids will be generated randomly.
	  public void clustering(int numClusters, int niter, double [][] centroids) 
	  {
	      _numClusters = numClusters;
	      if (centroids !=null)
	          _centroids = centroids;
	      else{
	        // randomly selected centroids
	        _centroids = new double[_numClusters][];

	        ArrayList idx= new ArrayList();
	        for (int i=0; i<numClusters; i++){
	          int c;
	          do{
	            c = (int) (Math.random()*_nrows);
	          }while(idx.contains(c)); // avoid duplicates
	          idx.add(c);

	          // copy the value from _data[c]
	          _centroids[i] = new double[_ndims];
	          for (int j=0; j<_ndims; j++)
	            _centroids[i][j] = _data[c][j];
	        }
	        System.out.println("selected random centroids");

	      }

	      double [][] c1 = _centroids;
	      double threshold = 0.001;
	      int round=0;

	      while (true){
	        // update _centroids with the last round results
	        _centroids = c1;

	        //assign record to the closest centroid
	        _label = new int[_nrows];
	        for (int i=0; i<_nrows; i++){
	          _label[i] = closest(_data[i]);
	        }
	        
	        // recompute centroids based on the assignments  
	        c1 = updateCentroids();
	        round ++;
	        if ((niter >0 && round >=niter) || converge(_centroids, c1, threshold))
	          break;
	      }

	      System.out.println("Clustering converges at round " + round);
	  }

	  // find the closest centroid for the record v 
	  private int closest(double [] v){
	    double mindist = dist(v, _centroids[0]);
	    int label =0;
	    for (int i=1; i<_numClusters; i++){
	      double t = dist(v, _centroids[i]);
	      if (mindist>t){
	        mindist = t;
	        label = i;
	      }
	    }
	    return label;
	  }

	  // compute Euclidean distance between two vectors v1 and v2
	  private double dist(double [] v1, double [] v2){
	    double sum=0;
	    for (int i=0; i<_ndims; i++){
	      double d = v1[i]-v2[i];
	      sum += d*d;
	    }
	    return Math.sqrt(sum);
	  }

	  // according to the cluster labels, recompute the centroids 
	  // the centroid is updated by averaging its members in the cluster.
	  // this only applies to Euclidean distance as the similarity measure.

	  private double [][] updateCentroids(){
	    // initialize centroids and set to 0
		  System.out.println("Updateing ....");
	    double [][] newc = new double [_numClusters][]; //new centroids 
	    int [] counts = new int[_numClusters]; // sizes of the clusters

	    // intialize
	    for (int i=0; i<_numClusters; i++){
	      counts[i] =0;
	      newc[i] = new double [_ndims];
	      for (int j=0; j<_ndims; j++)
	        newc[i][j] =0;
	    }


	    for (int i=0; i<_nrows; i++){
	      int cn = _label[i]; // the cluster membership id for record i
	      for (int j=0; j<_ndims; j++){
	        newc[cn][j] += _data[i][j]; // update that centroid by adding the member data record
	      }
	      counts[cn]++;
	    }

	    // finally get the average
	    for (int i=0; i< _numClusters; i++){
	      for (int j=0; j<_ndims; j++){
	        newc[i][j]/= counts[i];
	      }
	    } 

	    return newc;
	  }

	  // check convergence condition
	  // max{dist(c1[i], c2[i]), i=1..numClusters < threshold
	  private boolean converge(double [][] c1, double [][] c2, double threshold){
	    // c1 and c2 are two sets of centroids 
	    double maxv = 0;
	    for (int i=0; i< _numClusters; i++){
	        double d= dist(c1[i], c2[i]);
	        if (maxv<d)
	            maxv = d;
	    } 

	    if (maxv <threshold)
	      return true;
	    else
	      return false;
	    
	  }
	  public double[][] getCentroids()
	  {
	    return _centroids;
	  }

	  public int [] getLabel()
	  {
	    return _label;
	  }

	  public int nrows(){
	    return _nrows;
	  }

	  public void printResults(){
	      System.out.println("Label:");
	     for (int i=0; i<_nrows; i++)
	        System.out.println(_label[i]);
	      System.out.println("Centroids:");
	     for (int i=0; i<_numClusters; i++){
	        for(int j=0; j<_ndims; j++)
	           System.out.print(_centroids[i][j] + " ");
	         System.out.println();
	     }

	  }
	  
	  public void createClusterCSV() throws FileNotFoundException{
			
			 File file = new File ("Clusters.csv");                               		   
		        java.io.PrintStream ps = new java.io.PrintStream(file);          			
		        if(file.exists()){                                               			
		        	ps.print("Long" + "," + "Lat");
		        	ps.println(""); 
		        	for (int i = 0; i < _numClusters; i++ ){  							
		        			ps.print(_centroids[i][0] + "," + _centroids[i][1]);  
		        			ps.println(""); 
			        	}                                                        			 
		        }   
		        ps.close();                                                       
	    }
	  
	  public void createOutputCSV() throws FileNotFoundException{
			
			 File file = new File ("Output.csv");                               		   
		        java.io.PrintStream ps = new java.io.PrintStream(file);          			
		        if(file.exists()){                                               			
		        	ps.print("id" + "," +"Long" + "," + "Lat" + "," + "Lat and Long");
		        	ps.println(""); 
		        	for (int i = 0; i < arrayLists.size(); i++ ){  							
		        		ps.print(arrayLists.get(i).get(0) + "," + arrayLists.get(i).get(1) + "," + arrayLists.get(i).get(2) + "," + identifier(arrayLists.get(i).get(3)));  
	        			ps.println(""); 
			        	}                                                        			 
		        }   
		        ps.close();                                                       
	    }

	  public String identifier(double doub){
		String zero = "a";
		String one = "B";
		double bit = 512;		// 2 to the 10 power
		String output = null;
		int num = (int) doub;
		
		for(int i = 0; i < 10; i++){
			if(doub/bit >=1){
				if(output == null){
					output = one;
					doub = doub-bit;
					bit = bit/2;
				}else{
					output = output + one;
					doub = doub-bit;
					bit = bit/2;
				}
			}else{
				if(output == null){
					output = zero;
					bit = bit/2;
				}else{
					output = output + zero;
					bit = bit/2;
				}
			}
		}
		return output;
	 }
	  public void addCentroidToArray(){
		  for(int i = 0; i < arrayLists.size(); i++){
			  double distance = 100;
			  double index = 0;
			  for (int j = 0; j < _numClusters; j++){
				  if(dist(_data[i],_centroids[j])<distance ){
					  index = j;
					  distance = dist(_data[i],_centroids[j]);
				  }
			  }
			  arrayLists.get(i).add(index);
		  }
	  }

	  public static void main( String[] astrArgs ) throws IOException 
	  {
	    /**
	     * The code commented out here is just an example of how to use
	     * the provided functions and constructors.
	     * 
	     */
	     KMeans KM = new KMeans();
	     KM.clustering(100, 1000, null); // clusters, maximum iterations, leave null
	     KM.printResults();
	     KM.createClusterCSV();
	     KM.addCentroidToArray();
	     KM.createOutputCSV();
	    

	  }
	}

