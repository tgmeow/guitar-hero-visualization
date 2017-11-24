package reusable.keymap;

public abstract class Runnable implements Comparable<Runnable> {

  /** Do the action specified by the inherited class */
  public abstract void run();

  /**
   * Get comparable String that acts as an identifiable name This needs to be managed by the
   * programmer to map String->Event Must be a unique String identifier (otherwise will conflict on
   * same key)
   */
  public abstract String getComparableID();

  /**
   * Implement a compareTo method in case sorting is needed
   */
  @Override
  public int compareTo(Runnable o) { // TODO Auto-generated method stub
    return getComparableID().compareTo(o.getComparableID());
  }
}
