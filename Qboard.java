import java.util.*;
import java.lang.*;

public class Qboard implements Comparable<Qboard>{
   private int [] board;
   private int fitness = 0;
   private double prob = 0;

    public Qboard (int size) {       
        board = randomBoard(size);
        fitness = calcFitness();
    }
    public Qboard(Qboard parent1, Qboard parent2,int l, double mutation, int boardsize) {
            board = new int[boardsize];        
            for (int i = 0; i < l; i++) {
                board[i]=parent1.boardIndex(i);
            }
        for (int i = l; i<board.length; i++) {                
            board[i]=parent2.boardIndex(i);
        } 
        double rand = Math.random();
        if (rand < mutation) {
            int randInd = (int)(Math.random()*boardsize);
            board[randInd]= (int)((Math.random()*boardsize)+1);
        }
        fitness = calcFitness();;
    } 
    
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[j]==i+1){
                    System.out.print("|Q");        
                }
                else {
                    System.out.print("| ");
                }
            }
        System.out.println("|");
        } 

    }
    public void printarray() {
        for (int i = 0; i < board.length; i++){
            System.out.print(board[i]+",");
        }
        System.out.println();
    }
    
    public int boardIndex(int i) {
        return board[i];
    }
    public double calcProb(int totalFit) {     
        prob = (double) fitness / totalFit;
        return prob;
    }
    public int getFitness() {
        return fitness;
    }
    public double getProb() {
        return prob;
    }
    public void setProb(double newProb) {
        prob = newProb; 
    } 
    public int compareTo(Qboard compareBoard) {
        int compareQuantity = compareBoard.getFitness();
        return compareQuantity -this.fitness;
    }
    private int calcFitness() {
        int clashes = 0; 
        int numOfPotentialClashes = ((board.length-1)*(board.length)/2);
        int [] blankboard = new int[board.length];
        int dx = 0;
        int dy = 0;
        //for (int i = 0; i < board.length; i++) {
        //    if(blankboard[board[i]-1]!=0) clashes+=2;
        //    else {            
        //        blankboard[board[i]-1]=1;
        //    }
        //}
        List<Integer> list = new ArrayList<Integer>();
        for(Integer val: board) {
            if(!list.contains(val)) {
                list.add(val);
            }
        }
        Integer[] noDuplicates = list.toArray(new Integer[0]);
        clashes+=(board.length-noDuplicates.length);
        for (int j = 1; j < board.length+1; j++) {
            for (int k = 1; k <board.length+1; k++) {
                if(j!=k) {
                    dx = Math.abs(j-k);
                    dy = Math.abs(board[j-1] - board[k-1]);
                    if (dx==dy) clashes++;                
                }    
            }   
        }    
        return numOfPotentialClashes -clashes;   
    }
    private int [] randomBoard(int boardsize) {
        int [] board = new int[boardsize];
        int rand = (int) ((Math.random() * boardsize) + 1);
        int cont = -1;
        for (int i = 1; i <=boardsize; i++){                
                cont = -1;  
                while (cont==-1) {
                    rand = (int) ((Math.random() * boardsize) + 1);
                    if (board[rand-1]==0) {
                        board[rand-1]=i;
                        cont=1;
                    }
                }
        } 
        /*for (int i =0; i<boardsize; i++) {
            rand = (int) ((Math.random() * boardsize) + 1);
            board[i] = rand;
        }*/
        return board;    
    }
}




