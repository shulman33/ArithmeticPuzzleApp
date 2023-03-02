package helloworld;

/** Defines an abstract base class for solving the "arithmetic puzzle" problem
 * using a genetic algorithm (as described by the requirements document).  Also
 * defines an inner interface, and uses it as part of the ArithmeticPuzzleI API
 * definition.
 *
 * Students MAY NOT change the public API of this class, nor may they add ANY
 * constructor.
 *
 * @author Avraham Leff
 */

import java.util.*;

public abstract class ArithmeticPuzzleBase {

  public interface SolutionI {
    /** Returns the solution to the arithmetic puzzle, or
     * Collections.EMPTY_LIST if no solutions was calculated.
     *
     * Any solution to such arithmetic puzzles maps each of the unique
     * characters from the augend, addend, or sum to a unique digit [0-9].  A
     * solution is therefore represented by a List<Character> of length 10,
     * such that the character corresponding to digit x is placed in the List
     * at index x.  If no character maps to a given digit, the List contains a
     * "space" at that index.
     *
     * See the requirements doc for a simple example.
     */
    public List<Character> solution();

    public String getAugend();
    public String getAddend();
    public String getSum();

    /** Returns the number of generations required to produce a solution.
     */
    public int nGenerations();
  } // inner SolutionI interface


  /** Constructor.  Specifies the arithmetic puzzle to be solved in terms of an
   * augend, addend, and sum.
   * 
   * Representation: all characters are in the range A-Z, with each letter
   * represents a unique digit.  The puzzle to be solved specifies that the
   * augend and addend (each representing a number in base 10) sum to the
   * specified sum (also a number in base 10).  Each of these numbers is
   * represented with the most significant letter (digit) in position 0, next
   * most significant letter (digit) in position 1, and so on.  The numbers
   * need not be the same length: an "empty" digit is represented by the
   * "space" character.
   *
   * Addition: Augend + Addend = Sum
   *
   * @param augend
   * @param addend
   * @param sum
   */
  public ArithmeticPuzzleBase(final String augend,
                              final String addend,
                              final String sum)
  {
    // ArithmeticPuzzle (your implementation class) should provide its
    // implementation in its constructor: DO NOT add any code here!
  }

  /** Returns the best Solution found by a genetic algorithm for the arithmetic
   * puzzle problem specified by the requirements document.
   *
   * @param gac contains properties needed by a genetic algorithm
   * @see GeneticAlgorithmConfig
   */
  public abstract SolutionI solveIt(GeneticAlgorithmConfig gac);

} // ArithmeticPuzzleBase
