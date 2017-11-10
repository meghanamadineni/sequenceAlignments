# Sequence Alignments
## Introduction
In this project the following are implemented <br />
1. Global alignment<br />
2. Local alignment<br />
3. End space free (dove-tail) alignment<br />according to Needleman-Wunsch and Smith-Waterman methods. 
## Inputs
This program will get the following as input:<br />
1. an integer that denotes which alignment method will be used. Use 1, 2, and 3
for global, local, and dove-tail alignment respectively.<br />
2. name of the file that contains query sequences in Fasta format.<br />
3. name of the file that contains database sequences in Fasta format.<br />
4. name of the file that contains the alphabet.<br />
5. name of the file that contains the scoring matrix for the given alphabet.<br />
6. a positive integer k that indicates the number of nearest neighbors.<br />
7. a negative integer m that indicates the gap penalty.<br />

Sample input files: Sample query, database, alphabet, and scoring matrix files
are on the e-learning website.
## Output
Output: This program will output the top k alignments over all query/database
sequence pairs along with their score. Each alignment in the output contains
the score, ids of the sequences, and the starting positions of the alignment. It will
look like the following:<br />
Score = 47<br />
id1 36 AQT..KNGQGWVPSNYITPV<br />
id2 39 ARLNDKEGYVPRNLLGLYP.<br />
Here id1 and id2 are ids, 36 and 39 are starting positions.
## Execution
> javac hw1.java <br />
>java hw1 queryfile datafile alphabet scorematrix 10 -3 <br />
to run global alignment and return top 10 results with a gap penalty of -3.
