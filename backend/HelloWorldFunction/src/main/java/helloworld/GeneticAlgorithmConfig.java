package helloworld;

/** A holder for multiple pieces of state that can drive a genetic algorithm's
 * execution.
 *
 * Students MAY NOT modify this class in any way!
 * 
 * @author Avraham Leff
 */

public class GeneticAlgorithmConfig {

	public enum SelectionType {
		ROULETTE, TOURNAMENT;
	}

  /** Constructor
   *
   * @param initialPopulationSize size of the first-generation population, must
   * be > 0.
   * @param maxGenerations the genetic algorithm stops if the number of
   * generations exceeds this value, must be > 0.
   * @param selectionType specifies how parents are chosen to reproduce
   * @param mutationProbability the probability that a chromosone instance will
   * mutate, must be greater than 0.0 and not exceed 1.0
   * @param crossoverProbability the probability that two parents selected to
   * reproduce will have a child that is a mixture of the parents' genes;
   * otherwise, the parents two children that are duplicates of the two
   * parents, must be be >= 0.0 and <= 1.0
   */
  public GeneticAlgorithmConfig(final int initialPopulationSize,
                                final int maxGenerations,
                                final SelectionType selectionType,
                                final double mutationProbability,
                                final double crossoverProbability)
  {
    if (initialPopulationSize < 1) {
      throw new IllegalArgumentException("initialPopulationSize must be > 0: "+
                                         initialPopulationSize);
    }
    if (maxGenerations < 1) {
      throw new IllegalArgumentException("maxGenerations must be > 0: "+
                                         maxGenerations);
    }
    if (!(mutationProbability > 0.0)) {
      throw new IllegalArgumentException("mutationProbability must be > 0.0:"+
                                         mutationProbability);
    }
    if (!(mutationProbability <= 1.0)) {
      throw new IllegalArgumentException("mutationProbability must be <= 1.0:"+
                                         mutationProbability);
    }
    if (!(crossoverProbability >= 0.0)) {
      throw new IllegalArgumentException("crossoverProbability must be >= 0.0:"+
                                         crossoverProbability);
    }
    if (!(crossoverProbability <= 1.0)) {
      throw new IllegalArgumentException("crossoverProbability must be <= 1.0:"+
                                         crossoverProbability);
    }
    if (null == selectionType) {
      throw new IllegalArgumentException("selectionType can't be null");
    }

    this.initialPopulationSize = initialPopulationSize;
    this.maxGenerations = maxGenerations;
    this.mutationProbability = mutationProbability;
    this.crossoverProbability = crossoverProbability;
    this.selectionType = selectionType;
  }

  @Override
  public String toString() {
    // @fixme consider using a StringBuilder!
    return "{initial population size=" + initialPopulationSize +
      ", max generations=" + maxGenerations +
      ", mutation probability="+ mutationProbability +
      ", crossover probability="+crossoverProbability+
      ", selection type="+selectionType + "}";
  }

  public int getInitialPopulationSize() { return initialPopulationSize; }
  public int getMaxGenerations() { return maxGenerations; }
  public double getMutationProbability() { return mutationProbability; }
  public double getCrossoverProbability() { return crossoverProbability; }
  public SelectionType getSelectionType() { return selectionType; }


  private final int initialPopulationSize;
  private final int maxGenerations;
  private final double mutationProbability;
  private final double crossoverProbability;
  private final SelectionType selectionType;
}
