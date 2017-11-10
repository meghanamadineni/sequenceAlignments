# Sequence Alignments
##Introduction##
In this project the following are implemented
1. Global alignment
2. Local alignment
3. End space free (dove-tail) alignment
according to Needleman-Wunsch and Smith-Waterman methods. 
##Inputs
This program will get the following as input:
1. an integer that denotes which alignment method will be used. Use 1, 2, and 3
for global, local, and dove-tail alignment respectively.
2. name of the file that contains query sequences in Fasta format
3. name of the file that contains database sequences in Fasta format
4. name of the file that contains the alphabet
5. name of the file that contains the scoring matrix for the given alphabet
6. a positive integer k that indicates the number of nearest neighbors
7. a negative integer m that indicates the gap penalty

Sample input files: Sample query, database, alphabet, and scoring matrix files
are on the e-learning website.
##Output
Output: This program will output the top k alignments over all query/database
sequence pairs along with their score. Each alignment in the output contains
the score, ids of the sequences, and the starting positions of the alignment. It will
look like the following:
Score = 47
id1 36 AQT..KNGQGWVPSNYITPV
id2 39 ARLNDKEGYVPRNLLGLYP.
Here id1 and id2 are ids, 36 and 39 are starting positions.
##Execution
> javac hw1.java
>java hw1 queryfile datafile alphabet scorematrix 10 -3
to run global alignment and return top 10 results with a gap penalty of -3.
