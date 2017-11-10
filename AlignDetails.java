public class AlignDetails implements Comparable<AlignDetails>{
	String idQuery;
	String stringQuery;
	int startIndexQuery;
	String idDatabase;
	String stringDatabase;
	int startIndexDatabase;
	int score;
	public int compareTo(AlignDetails compareAlignDetails) {

		int compareScore = ((AlignDetails) compareAlignDetails).getScore();

		return compareScore - this.score;

	}

	public void setScore(int score) {
		this.score = score;
	}
	private int getScore() {
		return this.score;
	}
}