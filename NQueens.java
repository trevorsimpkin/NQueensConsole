import java.util.*;
import java.lang.*;

public class NQueens { 
    public static void main (String[] args) {
        int size = 50;
        int popSize = 100;
        int totFitness = 0;
        int maxIter = 100000;
        double mutation = 0.001;
        String selectionMethod = "truncate";
        if(args.length>0){
            size = Integer.parseInt(args[0]);
            popSize = Integer.parseInt(args[1]);
            maxIter = Integer.parseInt(args[2]);
            mutation = Double.parseDouble(args[3]);
            if (args[4].equals("roulette")) {
                selectionMethod = "roulette";
            }   
        }
        Qboard[] population = new Qboard[popSize];
        int numOfPotentialClashes = ((size-1)*(size)/2);
        for (int i = 0; i <population.length; i++) {
            population[i]= new Qboard(size);
        } 
        Arrays.sort(population);
        int topFit = population[0].getFitness();
        System.out.println("Hello welcome to Trevor Simpkin's N-Queens Genetic Algorithm.");
        System.out.println("Here are your initial conditions.");
        System.out.println("Boardsize (and # of Queens): " + size);
        System.out.println("Population size:             " + popSize);
        System.out.println("Max number of iterations:    " + maxIter);
        System.out.println("Mutation rate:               " + mutation);
        System.out.println("Number of potential clashes: " + numOfPotentialClashes);
        System.out.println("Initial highest fitness:     " + population[0].getFitness());
        System.out.println("Selection method:            " + selectionMethod);
        System.out.println("Initial board printed below:");
        population[0].printBoard();
        int iterations = 0;
        if (selectionMethod.equals("truncate")) {
            while(topFit!=numOfPotentialClashes&&iterations<maxIter) {
                truncateSelection(population, mutation, size);
                topFit = population[0].getFitness();
                iterations++;
                if ((iterations)%(maxIter/10)==0) {
                    System.out.println(((double)iterations/maxIter)*100.00 + "%");
                    System.out.println("Top Fitness: " + topFit);
                    population[0].printBoard();
                }
            }
        } else {
            while(topFit!=numOfPotentialClashes&&iterations<maxIter) {
                totFitness = sumFitness(population);
                setProbs(population, totFitness);
                population = roulleteSelection(population, mutation, size);
                
                topFit = population[0].getFitness();
                iterations++;
                if ((iterations)%(maxIter/10)==0) {
                    System.out.println(((double)iterations/maxIter)*100.00 + "%");
                    System.out.println("Top Fitness: " + topFit);
                    population[0].printBoard();
                }
            }
        }
        System.out.println("******************************************");
        System.out.println("Genetic Algorithm finished running....");
        System.out.println("Resulting highest fitness:     " + population[0].getFitness());
        System.out.println("Number of iterations:          " + iterations); 
        System.out.println("Final board printed below:");        
        population[0].printBoard();
    }
    public static int sumFitness (Qboard[] population) {
        int sum = 0;
        for (int i = 0; i <population.length; i++) {
            sum+=population[i].getFitness();
        }
        return sum; 
    }
    public static void setProbs (Qboard[] population, int totfitness) {
        double totProb = 0.0;
        for (int i = 0; i <population.length; i++) {
            totProb += population[i].calcProb(totfitness);
            population[i].setProb(totProb);
        } 
    }
    public static Qboard [] roulleteSelection(Qboard[] population, double mutation, int size) {
        Qboard[] newPopulation = new Qboard[population.length];
        double rand;
        double prob;
        int crossover; 
        int j;
        int halfpop = population.length/2;
        for (int i =0; i < halfpop; i++) {
            rand = Math.random();
            j = 0;
            prob = population[j].getProb();
            while (rand>prob&&j<population.length-1) {
                prob += population[j].getProb();
                j++;
            }
            
            newPopulation[i] = population[j];
        }
        mateBoard(newPopulation, mutation, size);
        Arrays.sort(newPopulation);
        return newPopulation;         
    }

    public static void truncateSelection(Qboard[] population, double mutation, int size) {
        randomShuffleHalf(population);
        mateBoard(population, mutation, size);
        Arrays.sort(population);
        
    }
    public static void randomShuffleHalf(Qboard[] population) {
        int halfpop = population.length/2;
        int rand;
        Qboard temp;
        for (int i = 0; i<halfpop; i++){                 
            rand = (int) (Math.random() * halfpop);
            temp = population[i];
            population[i] = population[rand];
            population[rand] = temp; 
        }
    }
    
    public static void mateBoard(Qboard[] population, double mutation, int size) {
        int crossover; 
        int halfpop = population.length/2;        
        for (int j=0; j<halfpop; j+=2) {
            crossover = (int)((Math.random()*size));           
            population[halfpop+j-1]= new Qboard(population[j], population[j+1], crossover, mutation, size); 
            population[halfpop+j]= new Qboard(population[j+1], population[j], crossover, mutation, size); 
        }
        if (population[population.length-1]==null) population[population.length-1]=population[0]; 
    }
}
