package reusable.keymap;

import java.util.Set;
import java.util.TreeSet;

import main.java.StringManager;
import reusable.events.Runnable;

/**
 * Allows for plucks of multiple strings in one Runnable for uniquely identifiable chords.
 *
 * @author tgmeow
 */
public class StringRunner extends Runnable {

  /**
   * Constructor for the MultiStringRunner
   *
   * @param items a Set of the string indexes to pluck
   */
  public StringRunner(Set<Integer> items) {
    indexes = items;
  }
  
  /**
   * Constructor for the MultiStringRunner
   *
   * @param a the string to pluck
   */
  public StringRunner(int a) {
    indexes.add(a);
  }
  
  /**
   * Constructor for the MultiStringRunner
   *
   * @param a the string to pluck
   */
  public StringRunner(int a, int b) {
    indexes.add(a);
    indexes.add(b);
  }
  
  /**
   * Constructor for the MultiStringRunner
   *
   * @param a the string to pluck
   */
  public StringRunner(int a, int b, int c) {
    indexes.add(a);
    indexes.add(b);
    indexes.add(c);
  }
  
  
  /**
   * Constructor for the MultiStringRunner
   *
   * @param a the string to pluck
   */
  public StringRunner(int a, int b, int c, int d) {
    indexes.add(a);
    indexes.add(b);
    indexes.add(c);
    indexes.add(d);
  }
  

  /** Plucks each string in the TreeSet */
  @Override
  public void run() {
    indexes.stream().forEach(index -> StringManager.getInstance().pluck(index));
  }

  /**
   * ID is generated with MSR + size + each index in the Set, no punctuation + sum of indexes Ex.
   * SR4035917 This is just me being silly, not guaranteed to be unique, but it's pretty good.
   * Chances of collision should be small enough for this purpose.
   */
  @Override
  public String getComparableID() {
    String ID = "SR" + indexes.size();
    int sum = 0;
    for (int index : indexes) {
      ID += index;
      sum += index;
    }
    return ID + sum;
  }

  private Set<Integer> indexes = new TreeSet<Integer>();
}
