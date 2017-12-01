package reusable.events;

/**
 * This is different enough from Runnable that I don't know how make them relate to each other 
 * @author tgmeow
 *
 */
public class TimedRunnable implements Comparable<TimedRunnable> {

  public TimedRunnable(double time, Runnable runner) {
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

  public double getTime() {
    return time;
  }

  //The runnable that this should use
  private Runnable runner = null;

  //The time value in seconds associated with this runnable
  private double time = 0;
}
