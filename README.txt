CS4641 - HW2 - Randomized Optimization
By: Derrick Williams

Data package:
ABAGAIL was used to implement this project.  The ABAGAIL "library contains a number of interconnected Java packages that implement machine learning and artificial intelligence algorithms. These are artificial intelligence algorithms implemented for the kind of people that like to implement algorithms themselves." - Pushkar

Running Code:
1. An ABAGAIL folder is provided in the Code folder that has the java packages to compile and run the provided code.  This folder should be placed somewhere on your network where you want to run the code such as your Desktop.  
2. The java files in the Code folder and the data files in the Data folder should be placed inside the ABAGAIL folder that you just moved to the location of Desktop/ABAGAIL/src/opt/test/.
3. Navigate now to this location Desktop/ABAGAIL ('cd ABAGAIL', if you are currently in the Desktop folder).
4. Type 'ant', this will compile the files
5. Type 'java -cp ABAGAIL.jar opt.test.WhiteWineTest' to run the entire file for White Wine dataset that will run the Backprop, Randomized Hill Climbing, Simulated Annealing, and Genetic Algorithms.
6. Type 'java -cp ABAGAIL.jar opt.test.FlipFlopTestUpdated 1024 10' to run the Flip Flop tests.  The first input number is the maximum N to go up to and the second number is the number of iterations to average over for running each algorithm at the set N.  The N starts at 1 and increments up in powers of 2 to up to 1024.
7. Type 'java -cp ABAGAIL.jar opt.test.FourPeaksTestUpdated 512 256 10' to run the Four Peaks tests.  The first input number is the maximum N to go up to, the second number is the T for the function, and the third number is the number of iterations to average over for running each algorithm at the set N and T.  Note that T is never allowed to go over half the size of N in this space exploration.  N starts at 2 and increments up in powers of 2 to up to 512.  T starts at 1 and increments up in powers of 2 to up half of N as noted before.  
8. Type 'java -cp ABAGAIL.jar opt.test.TravelingSalesmanTestUpdated 256 10' to run the Traveling Salesman tests. The first input number is the maximum cities to evaluate the algorithms over and the second number is the number of iterations to average over for running each algorithm at the set N.  The N starts at 1 and increments up in powers of 2 to up to 256.
9. Type 'java -cp ABAGAIL.jar opt.test.MaxKColoringTestUpdated 256 128 64 10' to run the Max K Coloring tests.  The first input number is the maximum nodes, N, the second number is the number connections among the nodes per node, L, the third number is the number of colorings to try to max, K, and the last number is the number of iterations, I, to average over for running each algorithm at the set input parameters.  N starts at 8 and increments up in powers of 2 up to 256.  L starts at 8 and increments up in powers of 2 up to 128.  K starts at 4 and increments up in powers of 2 up to 64.


Code Folder:
ABAGAIL 							- Folder with java packages for implementing machine learning algorithms
WhiteWineTest.java 					- File to run White Wine Quality Test (BP, RHC, SA, GA)
FlipFlopTestUpdated.java 			- File to run Flip Flop Test (RHC, SA, GA, MIMIC)
FourPeaksTestUpdated.java 			- File to run Four Peaks Test (RHC, SA, GA, MIMIC)
MaxKColoringTestUpdated.java 		- File to run Max K Coloring Test (RHC, SA, GA, MIMIC)
TravelingSalesmanTestUpdated.java 	- File to run Traveling Salesman Test (RHC, SA, GA, MIMIC)

Data Folder:
winequality-white.csv				- data file with all instances for training and testing (70% training/30% testing)
winequality.names					- important information file on dataset

Results Folder:
Results.xlsx						- results from all ABAGAIL runs for WhiteWineTest and other problems

Original Files from UCI Machine Learning Respository used in WhiteWineTest:
winequality-white.csv				- original data from UCI Machine Learning Respository
winequality.names					- original name file from UCI Machine Learning Respository
