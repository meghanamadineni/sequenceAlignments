import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

class Inputs {
	int method;
	HashMap<String, String> queries;
	HashMap<String, String> database;
	String alphabet;
	int score[][];
	int outputLength;
	int gap;	
}
public class hw1 {
	@SuppressWarnings("resource")
	public static void main(String args[]) throws FileNotFoundException, IOException{
		Inputs input = new Inputs();
		input.method = Integer.parseInt(args[0]);
		input.queries = fileToHashMap(args[1]);
		input.database = fileToHashMap(args[2]);
		if(!args[3].contains(".txt")) args[3] = args[3]+".txt";
		input.alphabet = new BufferedReader(new FileReader(args[3])).readLine();
		input.alphabet = input.alphabet.trim();
		input.score = readScoringMatrix(args[4], input.alphabet);
		input.outputLength = Integer.parseInt(args[5]); 
		input.gap = Integer.parseInt(args[6]);
		List<AlignDetails> solution = new ArrayList<>();
		AlignDetails solutionArray[] = new AlignDetails[input.database.size()*input.queries.size()];
		List<AlignDetails> finalSolution;
		if(input.method == 1){ 
			GlobalAlignment ga = new GlobalAlignment();
			solution = ga.start(input);
			solution.toArray(solutionArray);
		}
		if(input.method == 2){ 
			LocalAlignment la = new LocalAlignment();
			solution = la.start(input);
			solution.toArray(solutionArray);
		}
		if(input.method == 3){ 
			DoveTailAlignment dt = new DoveTailAlignment();
			solution = dt.start(input);
			solution.toArray(solutionArray);
		}
		finalSolution = topMatches(solutionArray, input.outputLength);
		printSolution(finalSolution);
		printToFile(finalSolution);
	}
	

	private static void printToFile(List<AlignDetails> finalSolution) {
		String filename = "output"+new Date().getTime()+".txt";
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filename, true));
			for(AlignDetails matchScore: finalSolution){
				bw.write(""+matchScore.score);
				bw.newLine();
				bw.flush();
				bw.write(matchScore.idQuery + " " + matchScore.startIndexQuery + " " + matchScore.stringQuery);
				bw.newLine();
				bw.flush();
				bw.write(matchScore.idDatabase + " " + matchScore.startIndexDatabase + " " + matchScore.stringDatabase);
				bw.newLine();
				bw.flush();
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{                      
			if (bw != null)
			{
				try {
					bw.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}


	private static void printSolution(List<AlignDetails> finalSolution) {
		for(AlignDetails matchScore: finalSolution){
			System.out.println("Score = " + matchScore.score);
			System.out.println(matchScore.idQuery + " " + matchScore.startIndexQuery + " " + matchScore.stringQuery);
			System.out.println(matchScore.idDatabase + " " + matchScore.startIndexDatabase + " " + matchScore.stringDatabase);
		}
	}
	

	private static List<AlignDetails> topMatches(AlignDetails[] sol, int nearestNeighbours) {
		Arrays.sort(sol);
		if(nearestNeighbours > sol.length) nearestNeighbours = sol.length;
		return Arrays.asList(sol).subList(0, nearestNeighbours);
	}


	public static int[][] readScoringMatrix(String filename, String alphabet) {
		if(!filename.contains(".txt")) filename = filename+".txt";
		int size = alphabet.length();
	    int sm[][] = new int[size][size];
	    int i=0;
		try
		  {
		    BufferedReader text = new BufferedReader(new FileReader(filename));
		    String line;
		    
		    while ((line = text.readLine()) != null)
		    {
		    	line = line.trim();
		    	String temp[] = line.split("\\s+");
		    	
		    	for(int j=0;j<size;j++){
		    		sm[i][j] = Integer.parseInt(temp[j]);
		    	}
		    	i++;
		    	
		    }
		    text.close();
		  }
		  catch (Exception e)
		  {
		    System.err.format("Exception occurred trying to read '%s'.", filename);
		    e.printStackTrace();
		  }
		return sm;
	}

	public static HashMap<String,String> fileToHashMap(String filename){
		if(!filename.contains(".txt")) filename = filename+".txt";
		HashMap<String, String> hash_map = new HashMap<String, String>();
		String id="";
		StringBuilder str=new StringBuilder();
		try
		  {
		    BufferedReader text = new BufferedReader(new FileReader(filename));
		    String line;
		    while ((line = text.readLine()) != null)
		    {
		    	line = line.trim();
		    	if(line.startsWith(">", 0)){
		    		if(id!="" && str.length()!=0){
		    			hash_map.put(id, str.toString());
		    			id="";
		    			str.setLength(0);
		    		}
		    		id = line.split(" ")[0].substring(5);
		    	}
		    	else{
		    		str.append(line);
		    	}
		      
		    }
		    hash_map.put(id, str.toString());
		    text.close();
		  }
		  catch (Exception e)
		  {
		    System.err.format("Exception occurred trying to read '%s'.", filename);
		    e.printStackTrace();
		  }
		return hash_map;
	}
}



