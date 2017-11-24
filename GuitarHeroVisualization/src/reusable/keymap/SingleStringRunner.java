package reusable.keymap;

import main.java.StringManagerSingleton;
import reusable.runnables.Runnable;

/**
 * Runnable class for processing a keypress that plucks a string. Ideally, one-to-one relationship
 * between this class and string index
 *
 * @author tgmeow
 */
public class SingleStringRunner extends Runnable {

  /**
   * Constructor for KeyMapRunner
   *
   * @param index which index item of the StringMangerSingleton to pluck
   */
  public SingleStringRunner(int index) {
    this.index = index;
  }

  /** Process the keypress. Plucks the string associated with the index */
  @Override
  public void run() {
    StringManagerSingleton.getInstance().pluck(index);
  }

  /** Return a comparable piece of data for this Runnable. "SSR" + index */
  @Override
  public String getComparableID() {
    return "SSR" + index;
  }

  /** @return the index this KeyMapRunner calls */
  public int getIndex() {
    return index;
  }

  //Index of what key this item maps to
  private int index;
}
