//package project2;
package opt.test;


import dist.DiscreteDependencyTree;
import dist.DiscretePermutationDistribution;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import opt.*;
import opt.example.*;
import opt.ga.*;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

import java.util.Arrays;
import java.util.Random;

/**
 * A test using the Traveling Salesman problem
 * @author Andrew Guillory gtg008g@mail.gatech.edu and james7132
 * @version 1.0
 * Updated by Derrick Williams
 */
public class TravelingSalesmanTestUpdated {

    public static void main(String[] args) {
        if(args.length < 2)
        {
            System.out.println("Provide a input size and repeat count");
            System.exit(0);
        }
        int N = Integer.parseInt(args[0]);
        if(N < 0)
        {
            System.out.println(" N cannot be negaitve.");
            System.exit(0);
        }
        Random random = new Random();

        // Run simulation up to N by doubling k
        for (int n = 1; n <= N; n*=2){
            // create the random points
            double[][] points = new double[n][2];
            for (int i = 0; i < points.length; i++) {
                points[i][0] = random.nextDouble();
                points[i][1] = random.nextDouble();
            }
            int iterations = Integer.parseInt(args[1]);
            // for rhc, sa, and ga we use a permutation based encoding
            TravelingSalesmanEvaluationFunction ef = new TravelingSalesmanRouteEvaluationFunction(points);
            Distribution odd = new DiscretePermutationDistribution(n);
            NeighborFunction nf = new SwapNeighbor();
            MutationFunction mf = new SwapMutation();
            CrossoverFunction cf = new TravelingSalesmanCrossOver(ef);
            HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
            GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);

            System.out.printf("Current City count: %d\n", n);
            System.out.println("Randomized Hill Climbing\n---------------------------------");
            double sumEf   = 0;
            double sumTime = 0;
            for(int i = 0; i < iterations; i++)
            {
                RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);
                long t = System.nanoTime();
                FixedIterationTrainer fit = new FixedIterationTrainer(rhc, 200000);
                fit.train();
                System.out.println(ef.value(rhc.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
                sumEf   += ef.value(rhc.getOptimal());
                sumTime += ((double)(System.nanoTime() - t))/1e9d;
            }
            double efAverage   = sumEf   / iterations;
            double timeAverage = sumTime / iterations;
            System.out.printf("Ef Average: %f %n", efAverage);
            System.out.printf("Time Average: %f %n", timeAverage); 

            System.out.println("Simulated Annealing \n---------------------------------");
            sumEf   = 0;
            sumTime = 0;
            for(int i = 0; i < iterations; i++)
            {
                SimulatedAnnealing sa = new SimulatedAnnealing(1E12, .95, hcp);
                long t = System.nanoTime();
                FixedIterationTrainer fit = new FixedIterationTrainer(sa, 200000);
                fit.train();
                System.out.println(ef.value(sa.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
                sumEf   += ef.value(sa.getOptimal());
                sumTime += ((double)(System.nanoTime() - t))/1e9d;
            }
            efAverage   = sumEf   / iterations;
            timeAverage = sumTime / iterations;
            System.out.printf("Ef Average: %f %n", efAverage);
            System.out.printf("Time Average: %f %n", timeAverage);  


            System.out.println("Genetic Algorithm\n---------------------------------");
            sumEf   = 0;
            sumTime = 0;
            for(int i = 0; i < iterations; i++)
            {
                StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, 150, 10, gap);
                long t = System.nanoTime();
                FixedIterationTrainer fit = new FixedIterationTrainer(ga, 1000);
                fit.train();
                System.out.println(ef.value(ga.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
                sumEf   += ef.value(ga.getOptimal());
                sumTime += ((double)(System.nanoTime() - t))/1e9d;
            }
            efAverage   = sumEf   / iterations;
            timeAverage = sumTime / iterations;
            System.out.printf("Ef Average: %f %n", efAverage);
            System.out.printf("Time Average: %f %n", timeAverage);  

            System.out.println("MIMIC \n---------------------------------");
            sumEf   = 0;
            sumTime = 0;

            // for mimic we use a sort encoding
            int[] ranges = new int[n];
            Arrays.fill(ranges, n);
            odd = new  DiscreteUniformDistribution(ranges);
            Distribution df = new DiscreteDependencyTree(.1, ranges);

            for(int i = 0; i < iterations; i++)
            {
                ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);
                MIMIC mimic = new MIMIC(200, 60, pop);
                long t = System.nanoTime();
                FixedIterationTrainer fit = new FixedIterationTrainer(mimic, 1000);
                fit.train();
                System.out.println(ef.value(mimic.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
                sumEf   += ef.value(mimic.getOptimal());
                sumTime += ((double)(System.nanoTime() - t))/1e9d;
            }
            efAverage   = sumEf   / iterations;
            timeAverage = sumTime / iterations;
            System.out.printf("Ef Average: %f %n", efAverage);
            System.out.printf("Time Average: %f %n", timeAverage);  
        }
    }
}
