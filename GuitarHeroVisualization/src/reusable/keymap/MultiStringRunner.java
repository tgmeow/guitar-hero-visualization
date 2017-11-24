package reusable.keymap;

import java.util.Set;
import java.util.TreeSet;

import main.java.StringManagerSingleton;

/**
 * Allows for plucks of multiple strings in one Runnable for uniquely identifiable chords.
 *
 * @author tgmeow
 */
public class MultiStringRunner extends Runnable {

  /**
   * Constructor for the MultiStringRunner
   *
   * @param items a Set of the string indexes to pluck
   */
  public MultiStringRunner(Set<Integer> items) {
    indexes = items;
  }

  /** Plucks each string in the TreeSet */
  @Override
  public void run() {
    indexes.stream().forEach(index -> StringManagerSingleton.getInstance().pluck(index));
  }

  /**
   * ID is generated with MSR + size + each index in the Set, no punctuation + sum of indexes Ex.
   * MSR4035917 This is just me being silly, not guaranteed to be unique, but it's pretty good.
   * Chances of collision should be small enough for this purpose.
   */
  @Override
  public String getComparableID() {
    String ID = "MSR" + indexes.size();
    int sum = 0;
    for (int index : indexes) {
      ID += index;
      sum += index;
    }
    return ID + sum;
  }

  private Set<Integer> indexes = new TreeSet<Integer>();
}
