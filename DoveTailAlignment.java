import java.util.ArrayList;
import java.util.List;

public class DoveTailAlignment {
	
	public List<AlignDetails> start(Inputs input){
		List<AlignDetails> AlignDetailsScores = new ArrayList<>();
//		Date startTime;
//		Date endTime;
		for(String keyQuery : input.queries.keySet()){
//			startTime = new Date();
			for(String keyDatabase : input.database.keySet()){
				AlignDetails seq = new AlignDetails();
				seq.idQuery = keyQuery;
				seq.idDatabase = keyDatabase;
				AlignDetailsScores.add(doveTailAlignedAlignDetails(input.queries.get(keyQuery), input.database.get(keyDatabase), seq, input));
			}
//			endTime = new Date();
//			System.out.println("Start time: "+ startTime.toString() + " " + "End time: "+ endTime.toString());
		}
		return AlignDetailsScores;
	}

	private AlignDetails doveTailAlignedAlignDetails(String query, String database, AlignDetails seq, Inputs input) {
		int solution[][] = matrixConstruction(query, database, input);
		findIndex(solution, query.length(), database.length(), seq);
		seq.score = solution[seq.startIndexQuery][seq.startIndexDatabase];
		seq = findPath(solution, query,  database,seq, input);
		return seq;
	}

	private AlignDetails findPath(int[][] solution, String q, String d, AlignDetails seq, Inputs input) {
		StringBuilder alignedStrand1 = new StringBuilder();
        StringBuilder alignedStrand2 = new StringBuilder();
        char query[] = q.toCharArray();
        char database[] = d.toCharArray();
        int i = seq.startIndexQuery, j = seq.startIndexDatabase, val;
	        while (i != 0  && j != 0 && solution[i][j] != 0) {
	        	val = input.score[input.alphabet.toLowerCase().indexOf(query[i-1])][input.alphabet.toLowerCase().indexOf(database[j-1])];
	            if (solution[i-1][j-1] == solution[i][j] - val) {
	                alignedStrand1.append(query[i-1]);
	                if(query[i-1]==database[j-1])
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

	private void findIndex(int[][] solution, int qLength, int dLength , AlignDetails seq)
	{
		int index1 = 0;
		int index2 = 0;
		int max=Integer.MIN_VALUE;
		for(int i=0;i<qLength+1;i++)
		{
			if(solution[i][dLength] >max)
			{
				max=solution[i][dLength];
				index1=i;
				index2=dLength;
			}
		}
		for(int j=0;j<dLength+1;j++)
		{
			if(solution[qLength][j] >max)
			{
				max=solution[qLength][j];
				index1=qLength;
				index2=j;
			}
		}
		seq.startIndexQuery = index1;
		seq.startIndexDatabase = index2;
	}
		
	public int[][] matrixConstruction(String q, String d, Inputs input){
		char query[] = q.toCharArray();
		char database[] = d.toCharArray();
		int val,i,j;
		int[][] solution = new int[query.length+1][database.length+1];
		for (i = 0; i < database.length+1; i++) {
			solution[0][i] = 0;
		}
		for (i = 1; i < query.length+1; i++) {
			solution[i][0] = 0;
		}
		for (i = 1; i < query.length+1; i++) {
			for (j = 1; j < database.length+1; j++) {
	        	val = input.score[input.alphabet.toLowerCase().indexOf(query[i-1])][input.alphabet.toLowerCase().indexOf(database[j-1])];
				solution[i][j] = Math.max(solution[i][j-1] + input.gap, Math.max(solution[i-1][j] + input.gap, solution[i-1][j-1] + val));	
			}
		}
		return solution;		
	}
}
