package main.java;

import java.util.LinkedList;
import processing.core.PApplet;

import main.java.StringComponent;
import reusable.audio.StdAudio;
import reusable.events.EventQueue;
import reusable.guitar.GuitarString;

/**
 * Singleton pattern for the StringManager. Wraps the StringComponents to create helper methods that
 * can run StringComponent methods in bulk.
 *
 * @author tgmeow
 */
public enum StringManager {
  INSTANCE;

  /**
   * A PApplet MUST be passed in (at least once) on the first getInstance. Throws an exception if
   * the parent is missing
   *
   * @return Singleton instance of the StringManager
   */
  public static StringManager getInstance() {
    //This is a slightly ugly solution, but it ensures that a PApplet is passed in and
    //simplifies future calls to retrieve the singleton
    if (parent == null) {
      throw new IllegalStateException("Unable to return instance without parent PApplet.");
    }
    return INSTANCE;
  }

  /**
   * A PApplet MUST be passed in (at least once) when it is first used.
   *
   * @return Singleton instance of the StringManager
   */
  public static StringManager setInstance(PApplet p) {
    parent = p;
    return INSTANCE;
  }

  /**
   * Draw the strings at (LOCAL POSITIONING). TODO currently draw the strings at locally specified
   * positioning
   */
  public void draw() {
    //TODO remove local constants
    int spacingY = 50;
    int spacingX = 100;

    parent.pushMatrix();
    parent.translate(spacingX, spacingY);
    strings
        .stream()
        .forEach(
            (str) -> {
              str.draw();
              parent.translate(0, spacingY);
            });
    parent.popMatrix();
  }

  /**
   * Add a string of the specified frequency to the list of strings
   *
   * @param frequency of the string
   */
  public void addString(double frequency) {
    float roundedLabelFreq = Math.round(frequency * 10) / 10.0F;
    String label = strings.size() + " [" + roundedLabelFreq + "]";
    float xStep = 5;
    float yHeight = 40;
    strings.add(new StringComponent(parent, frequency, label, xStep, yHeight));
  }

  /**
   * Pluck the string specified by index TODO OUT OF RANGE BEHAVIOUR
   *
   * @param index which string to pluck
   */
  public void pluck(int index) {
    hasActivity = true;
    if (useThreads) {
      EventQueue.getInstance().spawnTicThread(true);
    }
    if (index >= 0 && index < strings.size()) {
      strings.get(index).pluck();
    }
  }

  /** Pluck all the strings */
  public void pluckAll() {
    hasActivity = true;
    if (useThreads) {
      EventQueue.getInstance().spawnTicThread(true);
    }
    strings.stream().forEach((str) -> str.pluck());
  }

  /**
   * Tic one of the strings (advance simulation one step) TODO OUT OF RANGE BEHAVIOUR
   *
   * @param index which string to tic
   */
  public void tic(int index) {
    if (strings.get(index).checkActivity()) {
      if (index >= 0 && index < strings.size()) {
        strings.get(index).tic();
      }
    }
  }

  /** Tic all the strings (advance simulation one step) */
  public void ticAll() {
    if (hasActivity) {
      strings.stream().forEach((str) -> str.tic());
    }
  }

  /** Tic all the strings AND plays the combined sound (samples) in StdAudio */
  public void ticPlayAll() {
    //if not activity, nothing to play
    if (hasActivity) {
      float sum = (float) strings.stream().mapToDouble(str -> str.ticAndSample()).sum();
      StdAudio.play(sum);
    }
  }

  /**
   * Tic every string one cycle. One cycle is the length of that string. This just looks nice for
   * demonstration purposes.
   */
  public void ticAllOneCycle() {
    if (hasActivity) {
      strings
          .stream()
          .forEach(
              (str) -> {
                for (int i = 0; i < str.size(); ++i) {
                  str.tic();
                }
              });
    }
  }

  /**
   * Called once per draw cycle to play the strings "live" in "real time" and handle events in the
   * local event queue if any
   */
  public void playStringsLive() {
    if (hasActivity) {
      float boundedFRate = parent.frameRate < MIN_FRAME_RATE ? MIN_FRAME_RATE : parent.frameRate;
      for (int i = 0; i < GuitarString.SAMPLE_RATE / boundedFRate; ++i) {
        ticPlayEvents();
      }
    }
  }

  /** Tic each string once and play the sound, increment the time and check events */
  public void ticPlayEvents() {
    //if no activity, nothing to play but we still want to increment time
    EventQueue.getInstance().playEventsTic(GuitarString.TIME_STEP);
    if (hasActivity) {
      ticPlayAll();
    }
  }

  /**
   * Check if the strings have been inactive for long enough to mark their inactivity. Intended to
   * be called periodically along side the tic call.
   */
  public boolean checkInactiveStrings() {
    hasActivity = false; //initially set to inactive until we find one that is active
    activityCount = 0;
    for (StringComponent str : strings) {
      boolean act = str.checkActivity();
      if (act) {
        ++activityCount;
      }
      hasActivity = act || hasActivity;
    }
    return hasActivity;
  }

  /** Reset the instance to the initial state. Releases the parent object. */
  public void reset() {
    strings.clear();
    parent = null;
    hasActivity = false;
    activityCount = 0;
  }

  public boolean getActivity() {
    return hasActivity;
  }

  public int getActivityCount() {
    return activityCount;
  }

  public void useThreads(boolean val) {
    useThreads = val;
  }

  //Private variables
  //by default use threads for tics
  private boolean useThreads = true;

  //async access to mark inactivity
  private volatile boolean hasActivity = false;

  private int activityCount = 0;

  private static PApplet parent = null;

  //Data structure for our guitar strings
  private LinkedList<StringComponent> strings = new LinkedList<StringComponent>();

  //use a minimum frame rate to prevent infinite tic loop. Introduces audio stuttering when performance suffers
  private final int MIN_FRAME_RATE = 15;
}
