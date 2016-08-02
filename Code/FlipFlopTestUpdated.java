//package project2;
package opt.test;



import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import opt.*;
import opt.example.ContinuousPeaksEvaluationFunction;
import opt.example.CountOnesEvaluationFunction;
import opt.example.FlipFlopEvaluationFunction;
import opt.example.FourPeaksEvaluationFunction;
import opt.ga.*;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

import java.util.Arrays;

/**
 * A test using the FlipFlop evaluation function
 * @author James Liu and James7132
 * @version 1.0
 * Updated by Derrick Williams
 */
public class FlipFlopTestUpdated {

    public static void main(String[] args) {
        if(args.length < 2)
        {
            System.out.println("Provide a input size and repeat counter");
            System.exit(0);
        }
        int N = Integer.parseInt(args[0]);
        if(N < 0)
        {
            System.out.println(" N cannot be negaitve.");
            System.exit(0);
        }

        int iterations = Integer.parseInt(args[1]);

        // Run simulation up to N by doubling n
        for (int n = 1; n <= N; n*=2){

            int[] ranges = new int[n];
            Arrays.fill(ranges, 2);
            EvaluationFunction ef = new FlipFlopEvaluationFunction();
            Distribution odd = new DiscreteUniformDistribution(ranges);
            NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);
            MutationFunction mf = new DiscreteChangeOneMutation(ranges);
            CrossoverFunction cf = new SingleCrossOver();
            Distribution df = new DiscreteDependencyTree(.1, ranges);
            HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
            GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
            ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);

            System.out.printf("Current Input Size: %d\n", n);
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
                SimulatedAnnealing sa = new SimulatedAnnealing(100, .95, hcp);
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
                StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, 100, 20, gap);
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
            for(int i = 0; i < iterations; i++)
            {
                MIMIC mimic = new MIMIC(200, 100, pop);
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
