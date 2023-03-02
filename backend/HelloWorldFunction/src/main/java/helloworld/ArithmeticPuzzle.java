package helloworld;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.Random;

/**
 *
 * Based off of the Genetic Algorithm tutorial at
 * <a href="https://towardsdatascience.com/introduction-to-genetic-algorithms-including-example-code-e396e98d8bf3">towardsdatascience.com</a>
 *
 */

public class ArithmeticPuzzle extends ArithmeticPuzzleBase{

    private String augend;
    private String addend;
    private String sum;
    private Population population;
    private Individual fittest;
    private Individual secondFittest;
    private GeneticAlgorithmConfig config;
    private int generationCount = 0;

    /** Returns the solution to the arithmetic puzzle, or
     * Collections.EMPTY_LIST if no solutions was calculated.
     * Any solution to such arithmetic puzzles maps each of the unique
     * characters from the augend, addend, or sum to a unique digit [0-9].  A
     * solution is therefore represented by a List<Character> of length 10,
     * such that the character corresponding to digit x is placed in the List
     * at index x.  If no character maps to a given digit, the List contains a
     * "space" at that index.
     *
     */
    public class Solution implements SolutionI {

        private final String augend;
        private final String addend;
        private final String sum;
        private final List<Character> solution;
        private final Map<Character, Integer> map = new HashMap<>();

        public Solution(String augend, String addend, String sum) {
            this.augend = augend;
            this.addend = addend;
            this.sum = sum;
            if (population.getFittest().getFitness() == 0){
                this.solution = population.getFittest().getGenes();
            } else {
                this.solution = new ArrayList<>();
            }

            if (!solution.isEmpty()){
                for (int i = 0; i < 10; i++){
                    map.put(solution.get(i), i);
                }
            }


        }


        @Override
        public List<Character> solution() {
            return solution;
        }

        @Override
        public String getAugend() {
            return augend;
        }

        @Override
        public String getAddend() {
            return addend;
        }

        @Override
        public String getSum() {
            return sum;
        }

        /** Returns the number of generations required to produce a solution.
         */
        @Override
        public int nGenerations() {
            return generationCount;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Will try to solve the puzzle: " ).append(augend).append(" + ").append(addend).append(" = ").append(sum)
                    .append("using this GeneticAlgorithmConfig instance: ").append(config.toString());
                    if(solution.isEmpty()){
                        sb.append("Sorry could not find a solution in ").append(nGenerations()).append(" generations");
                    }else{
                        sb.append("The number of generations required to produce a solution is: ").append(nGenerations()).append(".  ");
                    }

                    if (!solution.isEmpty()){
                        for (char c : augend.toCharArray()){
                            sb.append(map.get(c));
                        }
                        sb.append(" + ");
                        for (char c : addend.toCharArray()){
                            sb.append(map.get(c));
                        }
                        sb.append(" = ");
                        for (char c : sum.toCharArray()){
                            sb.append(map.get(c));
                        }
                    }




            return sb.toString();
        }
    }
    /**
     * Constructor.  Specifies the arithmetic puzzle to be solved in terms of an
     * augend, addend, and sum.
     * <p>
     * Representation: all characters are in the range A-Z, with each letter
     * represents a unique digit.  The puzzle to be solved specifies that the
     * augend and addend (each representing a number in base 10) sum to the
     * specified sum (also a number in base 10).  Each of these numbers is
     * represented with the most significant letter (digit) in position 0, next
     * most significant letter (digit) in position 1, and so on.  The numbers
     * need not be the same length: an "empty" digit is represented by the
     * "space" character.
     * <p>
     * Addition: Augend + Addend = Sum
     *
     * @param augend
     * @param addend
     * @param sum
     */
    public ArithmeticPuzzle(String augend, String addend, String sum) {
        super(augend, addend, sum);
        this.augend = augend;
        this.addend = addend;
        this.sum = sum;
    }

    /** Returns the best Solution found by a genetic algorithm for the arithmetic
     * puzzle problem specified by the requirements document.
     *
     * @param gac contains properties needed by a genetic algorithm
     * @see GeneticAlgorithmConfig
     */
    @Override
    public SolutionI solveIt(GeneticAlgorithmConfig gac) {
        this.config = gac;

        int populationSize = gac.getInitialPopulationSize();
        int maxGenerations = gac.getMaxGenerations();

        GeneticAlgorithmConfig.SelectionType selectionType = gac.getSelectionType();
        double mutationProbability = gac.getMutationProbability();
        double crossoverProbability = gac.getCrossoverProbability();
        this.population = new Population(augend, addend, sum, populationSize);

        Random rn = new Random();

        population.calculatePopulationFitness();

        while (population.getIntFittest() > 0 && generationCount < maxGenerations){
            ++generationCount;

            if (selectionType == GeneticAlgorithmConfig.SelectionType.ROULETTE) {
                rouletteSelection();
            }
            else if (selectionType == GeneticAlgorithmConfig.SelectionType.TOURNAMENT) {
                tournamentSelection();
            }

            if (rn.nextDouble() <= crossoverProbability){
                crossover();
            }

            if (rn.nextDouble() <= mutationProbability){
                mutation(augend, addend, sum);
            }

            addFittestOffspring();

            population.calculatePopulationFitness();

        }


        return new Solution(augend, addend, sum);
    }
    /**
     * Selects two individuals from the population using tournament selection.
     * The individuals are stored in the class fields fittest and secondFittest.
     */
    private void tournamentSelection() {
        Random rn = new Random();
        // tournament size is 10 percent of the population size
        int tournamentSize = (int) (population.getPopSize() * 0.1);
        Population tournament = new Population(augend, addend, sum, tournamentSize);

        // Fill the tournament population with randomly selected individuals
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = rn.nextInt(population.getPopSize());
            tournament.setIndividual(i, population.getIndividual(randomIndex));
        }

        // Find the fittest individual in the tournament
        tournament.calculatePopulationFitness();
        fittest = tournament.getFittest();

        // Find the second fittest individual in the tournament
        Individual secondFittestInTournament;
        int randomIndex = rn.nextInt(tournamentSize);
        secondFittestInTournament = tournament.getIndividual(randomIndex);
        for (int i = 0; i < tournamentSize; i++) {
            if (i != randomIndex) {
                Individual currentIndividual = tournament.getIndividual(i);
                if (currentIndividual.getFitness() > secondFittestInTournament.getFitness()) {
                    secondFittestInTournament = currentIndividual;
                }
            }
        }
        secondFittest = secondFittestInTournament;
    }


    private void rouletteSelection() {
        List<Individual> individuals = population.getIndividuals();
        double[] fitnesses = new double[individuals.size()];

        // Calculate the fitness sum of all individuals in the population
        double fitnessSum = 0;
        for (int i = 0; i < individuals.size(); i++) {
            fitnessSum += individuals.get(i).getFitness();
        }

        // Calculate the relative fitness of each individual
        for (int i = 0; i < individuals.size(); i++) {
            fitnesses[i] = individuals.get(i).getFitness() / fitnessSum;
        }

        // Calculate the cumulative probability distribution
        double[] probabilities = new double[individuals.size()];
        probabilities[0] = fitnesses[0];
        for (int i = 1; i < individuals.size(); i++) {
            probabilities[i] = probabilities[i - 1] + fitnesses[i];
        }

        // Select two individuals
        Individual parent1 = null;
        Individual parent2 = null;
        while (parent1 == null || parent2 == null) {
            double r = Math.random();
            for (int i = 0; i < individuals.size(); i++) {
                if (r <= probabilities[i]) {
                    if (parent1 == null) {
                        parent1 = individuals.get(i);
                    } else if (parent2 == null) {
                        parent2 = individuals.get(i);
                        break;
                    }
                }
            }
        }

        // Set the selected individuals as the fittest and second fittest
        if (parent1.getFitness() < parent2.getFitness()) {
            fittest = parent1;
            secondFittest = parent2;
        } else {
            fittest = parent2;
            secondFittest = parent1;
        }
    }

    private void crossover(){

        Random rn = new Random();

        int crossoverPoint = rn.nextInt(10);

        //TODO: Optimize this code, currently it is O(n^2)
        for (int i = 0; i < crossoverPoint; i++) {
            char temp = fittest.getGenes().get(i);
            fittest.getGenes().set(i, secondFittest.getGenes().get(i));
            char afterF1 = fittest.getGenes().get(i);
            secondFittest.getGenes().set(i, temp);
            char afterF2 = secondFittest.getGenes().get(i);
            for (int j = 0; j < 10; j++) {
                if (fittest.getGenes().get(j) == afterF1 && j != i) {
                    fittest.getGenes().set(j, temp);
                }
                if (secondFittest.getGenes().get(j) == afterF2 && j != i) {
                    secondFittest.getGenes().set(j, fittest.getGenes().get(i));
                }
            }
        }

    }


    private void mutation(String augend, String addend, String sum){
        List <Character> charList = new ArrayList<>();

        for (char c : augend.toCharArray()) {
            charList.add(c);
        }

        for (char c : addend.toCharArray()) {
            charList.add(c);
        }

        for (char c : sum.toCharArray()) {
            charList.add(c);
        }

        Random rn = new Random();
        int mutationPoint = rn.nextInt(10);
        int randomChar = rn.nextInt(charList.size());
        char randomCharValue = charList.get(randomChar);

        if (fittest.getGenes().get(mutationPoint) == ' ') {
            fittest.getGenes().set(mutationPoint, randomCharValue);
            for (int i = 0; i < 10; i++) {
                if (fittest.getGenes().get(i) == randomCharValue && i != mutationPoint){
                    fittest.getGenes().set(i, ' ');
                }
            }
        }
        else {
            char temp = fittest.getGenes().get(mutationPoint);
            int randomIndex = rn.nextInt(10);
            fittest.getGenes().set(mutationPoint, ' ');
            if (fittest.getGenes().get(randomIndex) == ' '){
                fittest.getGenes().set(randomIndex, temp);
            }
            else {
                while (fittest.getGenes().get(randomIndex) != ' '){
                    randomIndex = rn.nextInt(10);
                }
                fittest.getGenes().set(randomIndex, temp);
            }

        }

        mutationPoint = rn.nextInt(10);
        randomChar = rn.nextInt(charList.size());
        randomCharValue = charList.get(randomChar);

        if (secondFittest.getGenes().get(mutationPoint) == ' ') {
            secondFittest.getGenes().set(mutationPoint, randomCharValue);
            for (int i = 0; i < 10; i++) {
                if (secondFittest.getGenes().get(i) == randomCharValue && i != mutationPoint){
                    secondFittest.getGenes().set(i, ' ');
                }
            }
        }
        else {
            char temp = secondFittest.getGenes().get(mutationPoint);
            int randomIndex = rn.nextInt(10);
            secondFittest.getGenes().set(mutationPoint, ' ');
            if (secondFittest.getGenes().get(randomIndex) == ' '){
                secondFittest.getGenes().set(randomIndex, temp);
            }
            else {
                while (secondFittest.getGenes().get(randomIndex) != ' '){
                    randomIndex = rn.nextInt(10);
                }
                secondFittest.getGenes().set(randomIndex, temp);
            }

        }

    }

    private Individual getFittestOffspring(){

        if (fittest.getFitness() > secondFittest.getFitness()){
            return fittest;
        }
        return secondFittest;
    }

    private void addFittestOffspring(){

        fittest.calcFitness();
        secondFittest.calcFitness();

        int leastFittestIndex = population.getLeastFittestIndex();

        population.getIndividuals().set(leastFittestIndex, getFittestOffspring());

    }

}
