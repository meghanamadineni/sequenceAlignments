import java.util.ArrayList;
import java.util.List;

public class LocalAlignment {
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
				sequenceScores.add(localAlignedSequence(input.queries.get(keyQuery), input.database.get(keyDatabase), seq, input));
			}
//			endTime = new Date();
//			System.out.println("Start time: "+ startTime.toString() + " " + "End time: "+ endTime.toString());
		}
		return sequenceScores;
	}

	private AlignDetails localAlignedSequence(String query, String database, AlignDetails seq, Inputs input) {
		int solution[][] = matrixConstruction(query, database, seq, input);
		seq.score = solution[seq.startIndexQuery][seq.startIndexDatabase];
		seq = findPath(solution, query,  database,seq, input);
		return seq;
	}

	private AlignDetails findPath(int[][] solution, String q, String d, AlignDetails seq, Inputs input) {
		StringBuilder alignedStrand1 = new StringBuilder();
        StringBuilder alignedStrand2 = new StringBuilder();
        char query[] = q.toCharArray();
        char database[] = d.toCharArray();
		 int val, i = seq.startIndexQuery, j = seq.startIndexDatabase;
	        while (i != 0  && j != 0 && solution[i][j] != 0) {
	        	val = input.score[input.alphabet.toLowerCase().indexOf(query[i-1])][input.alphabet.toLowerCase().indexOf(database[j-1])];
	            if (solution[i-1][j-1] == solution[i][j] - val) {
	                alignedStrand1.append(query[i-1]);
	                if(query[i-1] == database[j-1])
	                	alignedStrand2.append(database[j-1]);
	                else
	                	alignedStrand2.append(".");
	                i -= 1;
	                j -= 1;
	            } else if (solution[i][j-1] == solution[i][j] - input.gap) {
	                alignedStrand1.append(".");
	                alignedStrand2.append(database[j-1]);
	                j -= 1;
	            } else {
	                alignedStrand1.append(query[i-1]);
	                alignedStrand2.append(".");
	                i -= 1;
	            }
	        }


        seq.stringQuery = alignedStrand1.reverse().toString();
        seq.stringDatabase = alignedStrand2.reverse().toString();
        seq.startIndexQuery = i;
        seq.startIndexDatabase = j;
        return seq;
	}

	public int[][] matrixConstruction(String q, String d, AlignDetails seq, Inputs input){
		int indexq = 0, indexd = 0;
		int val;
		char query[] = q.toCharArray();
		char database[] = d.toCharArray();
		int[][] solution = new int[query.length+1][database.length+1];
		solution[0][0] = 0;
		for (int i = 1; i < database.length+1; i++) {
			solution[0][i] = 0;
		}
		for (int i = 1; i < query.length+1; i++) {
			solution[i][0] = 0;
		}
		int max_value=Integer.MIN_VALUE;
		for (int i = 1; i < query.length+1; i++) {
			for (int j = 1; j < database.length+1; j++) {
	        	val = input.score[input.alphabet.toLowerCase().indexOf(query[i-1])][input.alphabet.toLowerCase().indexOf(database[j-1])];
				solution[i][j] = Math.max(0, Math.max(solution[i][j-1] + input.gap, Math.max(solution[i-1][j] + input.gap, solution[i-1][j-1] + val)));
				if(solution[i][j]>max_value)
				{
					max_value=solution[i][j];
					indexq=i;
					indexd=j;
				}
			}
		}
		seq.startIndexQuery = indexq;
		seq.startIndexDatabase = indexd;
		return solution;		
	}
}
