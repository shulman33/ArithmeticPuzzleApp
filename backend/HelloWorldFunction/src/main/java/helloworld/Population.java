package helloworld;

import java.util.*;

/**
 *
 * Based off of the Genetic Algorithm tutorial at
 * <a href="https://towardsdatascience.com/introduction-to-genetic-algorithms-including-example-code-e396e98d8bf3">towardsdatascience.com</a>
 *
 */

public class Population {

    int popSize;
    int fittest;
    List<Individual> individuals = new ArrayList<>();

    public Population(String augend, String addend, String sum, int popSize) {
        this.popSize = popSize;
        for (int i = 0; i < popSize; i++) {
            individuals.add(new Individual(augend, addend, sum));
        }
    }

    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    public int getIntFittest() {
        return fittest;
    }

    public Individual getFittest() {
        int maxFit = Integer.MAX_VALUE;
        int maxFitIndex = 0;

        for (int i = 0; i < individuals.size(); i++) {
            if (maxFit >= individuals.get(i).getFitness()) {
                maxFit = individuals.get(i).getFitness();
                maxFitIndex = i;
            }
        }
        this.fittest = individuals.get(maxFitIndex).getFitness();
        return individuals.get(maxFitIndex);
    }

    public ArrayList<Individual> getIndividuals() {
        return (ArrayList<Individual>) individuals;
    }

    public void setIndividual(int index, Individual individual) {
        individuals.set(index, individual);
    }

    public Individual getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;

        for (int i = 0; i < individuals.size(); i++) {
            if (individuals.get(i).getFitness() < individuals.get(maxFit1).getFitness()) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals.get(i).getFitness() > individuals.get(maxFit2).getFitness()) {
                maxFit2 = i;
            }
        }
        return individuals.get(maxFit2);
    }

    public int getLeastFittestIndex() {
        int minFitVal = 0;
        int minFitIndex = 0;

        for (int i = 0; i < individuals.size(); i++) {
            if (minFitVal <= individuals.get(i).getFitness()) {
                minFitVal = individuals.get(i).getFitness();
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    public void calculatePopulationFitness() {

        for (int i = 0; i < individuals.size(); i++) {
            individuals.get(i).calcFitness();
        }
        getFittest();
    }

    public int getPopSize() {
        return popSize;
    }
}
