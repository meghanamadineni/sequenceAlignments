import java.util.ArrayList;
import java.util.List;

public class GlobalAlignment {
	public List<AlignDetails> start(Inputs input){
		List<AlignDetails> sequenceScores = new ArrayList<>();
		//Date startTime;
		//Date endTime;
		for(String keyQuery : input.queries.keySet()){
			//startTime = new Date();
			for(String keyDatabase : input.database.keySet()){
				AlignDetails seq = new AlignDetails();
				seq.idQuery = keyQuery;
				seq.idDatabase = keyDatabase;
				sequenceScores.add(globalAlignedSequence(input.queries.get(keyQuery), input.database.get(keyDatabase),seq, input));
			}
			//endTime = new Date();
			//System.out.println("Start time: "+ startTime.toString() + " " + "End time: "+ endTime.toString());
		}
		return sequenceScores;
	}

	private AlignDetails globalAlignedSequence(String query, String database, AlignDetails seq, Inputs input ) {
		int solution[][] = matrixConstruction(query, database, input);
		seq.score = solution[solution.length-1][solution[0].length-1];
		seq = findPath(solution, query,  database,seq, input);
		return seq;
	}

	private AlignDetails findPath(int[][] solution, String q, String d,AlignDetails seq, Inputs input) {
        StringBuilder alignedStrand1 = new StringBuilder();
        StringBuilder alignedStrand2 = new StringBuilder();
        char query[] = q.toCharArray();
        char database[] = d.toCharArray();
        int i = solution.length - 1;
        int j = solution[0].length - 1;
        int matchValue;
        while (i != 0  && j != 0) {
        	matchValue = input.score[input.alphabet.toLowerCase().indexOf(query[i-1])][input.alphabet.toLowerCase().indexOf(database[j-1])];
            if (solution[i-1][j-1] == solution[i][j] - matchValue) {
                alignedStrand1.append(query[i-1]);
                if(query[i-1]==database[j-1])
                	alignedStrand2.append(database[j-1]);
                else
                	alignedStrand2.append(".");
                i--;
                j--;
            } else if (solution[i][j-1] == solution[i][j] - input.gap) {
            	alignedStrand1.append(".");
                alignedStrand2.append(database[j-1]);
                j--;
            } else {
            	alignedStrand1.append(query[i-1]);
            	alignedStrand2.append(".");
                i--;
            }
        }

        if (i == 0) {
            for (int k = 0; k < j; k++) {
            	alignedStrand1.append(".");
            	alignedStrand2.append(database[j-k-1]);
            }
        } else {
            for (int k = 0; k < i; k++) {
            	alignedStrand1.append(query[i-k-1]);
            	alignedStrand2.append(".");
            }
        }

        seq.stringQuery = alignedStrand1.reverse().toString();
        seq.stringDatabase = alignedStrand2.reverse().toString();
        seq.startIndexQuery = 0;
        seq.startIndexDatabase = 0;
        return seq;
	}

	public int[][] matrixConstruction(String q, String d,Inputs input){
		int val;
		char query[] = q.toCharArray();
		char database[] = d.toCharArray();
		int solution[][] = new int[query.length+1][database.length+1];
	        solution[0][0] = 0;
	        for (int i = 1; i < database.length+1; i++) {
	            solution[0][i] = solution[0][i-1] + input.gap;
	        }
	        for (int i = 1; i < query.length+1; i++) {
	            solution[i][0] = solution[i-1][0] + input.gap;
	        }
	        for (int i = 1; i < query.length+1; i++) {
	        	for (int j = 1; j < database.length+1; j++) {
	                val = input.score[input.alphabet.toLowerCase().indexOf(query[i-1])][input.alphabet.toLowerCase().indexOf(database[j-1])];
	                solution[i][j] = Math.max(solution[i][j-1] + input.gap, Math.max(solution[i-1][j] + input.gap, solution[i-1][j-1] + val));
	            }
	        }
	        return solution;	
	}	
}
