package reusable.events;

/**
 * Abstract comparable Runnable that can be "run"
 *
 * @author tgmeow
 */
public abstract class GuitarRunnable implements Comparable<GuitarRunnable> {

  /** Do the action specified by the inherited class */
  public abstract void run();

  /**
   * Get comparable String that acts as an identifiable name This needs to be managed by the
   * programmer to map String->Event Must be a unique String identifier (otherwise will conflict on
   * same key)
   */
  public abstract String getComparableID();

  /** Implement a compareTo method in case sorting is needed */
  @Override
  public int compareTo(GuitarRunnable o) {
    return getComparableID().compareTo(o.getComparableID());
  }
}
