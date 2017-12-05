package reusable.events;

/**
 * This is different enough from Runnable that I don't know how make them relate to each other lol
 *
 * @author tgmeow
 */
public class TimedRunnable implements Comparable<TimedRunnable> {

  /**
   * Constructor, takes a time and a Runnable
   *
   * @param time Time in seconds to execute this Runnable
   * @param runner Executes this runnable when the time is reached
   */
  public TimedRunnable(double time, GuitarRunnable runner) {
    this.runner = runner;
    this.time = time;
  }

  public void run() {
    if (runner != null) {
      runner.run();
    }
  }

  @Override
  public int compareTo(TimedRunnable o) { // TODO Auto-generated method stub
    return Double.compare(time, o.time);
  }

  /**
   * Get the time target of this Timed Runnable
   *
   * @return
   */
  public double getTime() {
    return time;
  }

  //The runnable that this should use
  private GuitarRunnable runner = null;

  //The time value in seconds associated with this runnable
  private double time = 0;
}
