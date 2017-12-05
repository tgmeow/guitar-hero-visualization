package reusable.events;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.lang.Runnable;

import main.java.StringManager;
import reusable.guitar.GuitarString;
import reusable.keymap.StringRunner;

/**
 * Singleton pattern EventQueue object. Takes TimedRunnables and executes them when the target time
 * is reached or elapsed
 *
 * @author tgmeow
 */
public enum EventQueue {
  INSTANCE;

  /** @return EventQueue instance */
  public static EventQueue getInstance() {
    return INSTANCE;
  }

  /**
   * Add a runnable event set to play at a given time
   *
   * @param time Time in seconds to run the runnable
   * @param runner Runnable that gets run when the running time >= time.
   */
  public void addEvent(double time, GuitarRunnable runner) {
    eventQueue.add(new TimedRunnable(time, runner));
  }

  /** @param runner Adds this timed runnable to the event queue */
  public void addEvent(TimedRunnable runner) {
    eventQueue.add(runner);
  }

  /**
   * Load the song from a file. Structure is time stringIndex where time is a double and stringIndex
   * is an integer. Order does not matter
   *
   * @param file the File to read from
   */
  public void loadStringEventsFile(File file) {
    reset(); //start clean
    try {
      Scanner scan = new Scanner(file);
      //currentSongFName = file.getName();

      double time = 0;
      int string = 0;
      while (scan.hasNextDouble()) {
        //read in values from the file
        time = scan.nextDouble();
        if (scan.hasNextInt()) {
          string = scan.nextInt();
        }
        //add values to the event queue
        addEvent(time, new StringRunner(string));
      }

      scan.close();

    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    //do not start immediately
    pauseEventsTics();
  }

  /** Toggle function for event timer, event run */
  public void togglePause() {
    playEventsTics = !playEventsTics;
    if (playEventsTics) {
      spawnTicThread(true);
    }
  }

  /**
   * Pause the event timer and prevent running any other events. Maintains the state of the event
   * queue but allows new events
   */
  public void pauseEventsTics() {
    playEventsTics = false;
  }

  /** Unpause the event timer and continue running events */
  public void unPauseEvents() {
    playEventsTics = true;
  }

  /** Start the event timer at zero and begin running the events */
  public void beginEvents() {
    eventTimer = 0;
    playEventsTics = true;
    spawnTicThread(true);
  }

  /** Reset the events and event variables to their clean initial state. */
  public void reset() {
    eventTimer = 0;
    eventQueue.clear();
    playEventsTics = false;
    //TODO
  }

  /** Check if the top event(s) are ready to be played and remove it/them after playing */
  public void playEvents() {
    if (playEventsTics) {
      if (!eventQueue.isEmpty()) {
        while (!eventQueue.isEmpty() && eventQueue.peek().getTime() <= eventTimer) {
          eventQueue.remove().run();
        }
      } else {
        playEventsTics = false; //Stop trying to play events if we run out. ?? //TODO
      }
    }
  }

  /** @param time Tic the event timer by some amount */
  public void ticEventTimer(double time) {
    if (playEventsTics) {
      eventTimer += time;
    }
  }

  /**
   * Check if any events are ready to be played and then increment the tic time after
   *
   * @param time amount of time to tic
   */
  public void playEventsTic(double time) {
    playEvents();
    ticEventTimer(time);
    //System.out.println(eventTimer);
  }

  /** Use calls to this to ensure there will only be one thread running */
  public void spawnTicThread(boolean activityMonitor) {
    //System.out.println("Trying to start a thread");
    if (!isTiccing) {
      threadedTic();
      if (!isMonitoring && activityMonitor) {
        threadedStringActivity();
      }
    }
  }

  /**
   * Do not call this. Thread that runs the tic, play and check events via StringManager. Since each
   * tic happens every 0.022675737 milliseconds, I'll need to set an integer delay and do multiple
   * tics per delay
   */
  private void threadedTic() {
    isTiccing = true;
    System.out.println("Started tic thread");
    Thread ticThread =
        new Thread(
            new Runnable() {

              @Override
              public void run() {
                while (isTiccing) {
                  if (!pauseTic) {
                    long diff = System.currentTimeMillis() - lastThreadTic;
                    lastThreadTic += diff;
                    int ticCount = (int) ((diff) * (GuitarString.SAMPLE_RATE / 1000.0));
                    //For 60 FPS ideal speed, should be approx 3000 tic count.
                    //Introduce a ticCountMax in case there is a CPU bottleneck
                    if (ticCount > THREAD_TIC_MAX) ticCount = THREAD_TIC_MAX;
                    for (int i = 0; i < ticCount; ++i) {
                      //tic and play strings and check for events
                      StringManager.getInstance().ticPlayEvents();
                    }
                  }
                  try {
                    Thread.sleep(THREAD_TIC_DELAY);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                    isTiccing = false;
                    break;
                  }
                  // delay(THREAD_TIC_DELAY);
                }
                isTiccing = false;
                System.out.println("Stopped tic thread");
              }
            });
    ticThread.start();
  }

  /**
   * Monitors the the string activity. Created with tic thread, dies when no tic activity. No string
   * active -> no tic
   */
  private void threadedStringActivity() {
    isMonitoring = true;
    System.out.println("Started Activity thread");
    Thread activityThread =
        new Thread(
            new Runnable() {

              @Override
              public void run() {
                try {
                  Thread.sleep(
                      THREAD_ACTIVITY_DELAY); //use an initial delay to ensure string is plucked
                  while (isTiccing) {
                    isTiccing = StringManager.getInstance().checkInactiveStrings();
                    Thread.sleep(THREAD_ACTIVITY_DELAY);
                  }
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }

                isMonitoring = false;
                System.out.println("Stopped Activity thread");
              }
            });
    activityThread.start();
  }

  public boolean isTiccing() {
    return isTiccing;
  }

  //Private

  //Controls run events or not
  private boolean playEventsTics = false;

  //timer for the local event queue
  private double eventTimer = 0;

  //A structure to hold the queued up timer events. Insertion order does not matter, only play time.
  private PriorityQueue<TimedRunnable> eventQueue = new PriorityQueue<TimedRunnable>();

  private final int THREAD_TIC_MAX = 10000;
  //Target delay. Due to threading, actual delay may not be accurate.
  private long lastThreadTic = 0;
  private final int THREAD_TIC_DELAY = 1;

  //Check thread activity every 2 seconds
  private final int THREAD_ACTIVITY_DELAY = 2000;

  private volatile boolean isTiccing = false;
  private volatile boolean isMonitoring = false;

  //tic strings or not
  private volatile boolean pauseTic = false;
}
