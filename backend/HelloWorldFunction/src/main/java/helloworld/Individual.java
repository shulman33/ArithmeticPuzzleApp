package helloworld;

import java.util.*;

/**
 *
 * Based off of the Genetic Algorithm tutorial at
 * <a href="https://towardsdatascience.com/introduction-to-genetic-algorithms-including-example-code-e396e98d8bf3">towardsdatascience.com</a>
 *
 */

public class Individual {

    private int fitness = Integer.MAX_VALUE;
    private final String augend;
    private final String addend;
    private final String sum;
    private final List<Character> genes = new ArrayList<>(10);

    public Individual(String augend, String addend, String sum) {
        this.augend = augend;
        this.addend = addend;
        this.sum = sum;

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            genes.add(' ');
        }

        // place the characters in augend, addend, and sum into the genes list
        for (int i = 0; i < augend.length(); i++) {
            int randomIndex = random.nextInt(10);
            if (genes.get(randomIndex) == ' ') {
                genes.set(randomIndex, augend.charAt(i));
            } else {
                while (genes.get(randomIndex) != ' ') {
                    randomIndex = random.nextInt(10);
                }
                genes.set(randomIndex, augend.charAt(i));
            }
        }

        for (int i = 0; i < addend.length(); i++) {
            int randomIndex = random.nextInt(10);
            if (genes.get(randomIndex) == ' ') {
                genes.set(randomIndex, addend.charAt(i));
            } else {
                while (genes.get(randomIndex) != ' ') {
                    randomIndex = random.nextInt(10);
                }
                genes.set(randomIndex, addend.charAt(i));
            }
        }

        for (int i = 0; i < sum.length(); i++) {
            int randomIndex = random.nextInt(10);
            if (genes.get(randomIndex) == ' ') {
                genes.set(randomIndex, sum.charAt(i));
            } else {
                while (genes.get(randomIndex) != ' ') {
                    randomIndex = random.nextInt(10);
                }
                genes.set(randomIndex, sum.charAt(i));
            }
        }

        fitness = Integer.MAX_VALUE;
    }

    public void calcFitness() {
        StringBuilder augendIndex = new StringBuilder();
        StringBuilder addendIndex = new StringBuilder();
        StringBuilder sumIndex = new StringBuilder();
        int augendInt;
        int addendInt;
        int sumInt;

        // create a map to store the index of each character in the genes list
        Map<Character, Integer> geneIndexMap = new HashMap<>();
        for (int i = 0; i < genes.size(); i++) {
            geneIndexMap.put(genes.get(i), i);
        }

        for (char c : this.augend.toCharArray()) {
            augendIndex.append(geneIndexMap.get(c));
        }

        for (char c : this.addend.toCharArray()) {
            addendIndex.append(geneIndexMap.get(c));
        }

        for (char c : this.sum.toCharArray()) {
            sumIndex.append(geneIndexMap.get(c));
        }

        try {
            augendInt = Integer.parseInt(augendIndex.toString());
            addendInt = Integer.parseInt(addendIndex.toString());
            sumInt = Integer.parseInt(sumIndex.toString());
        } catch (NumberFormatException e) {
            System.out.println("You done fucked up");
            System.out.println("You're lucky i'm even giving you a stack trace");
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

        this.fitness = Math.abs(augendInt + addendInt - sumInt);
    }


    public int getFitness() {
        return fitness;
    }

    public ArrayList<Character> getGenes() {
        return (ArrayList<Character>) genes;
    }
}
