package main.java;

import java.util.LinkedList;

import processing.core.PApplet;
import main.java.StringComponent;
import reusable.audio.StdAudio;

/**
 * Singleton pattern for the StringManager. Wraps the StringComponents to create helper methods that
 * can run StringComponent methods in bulk.
 *
 * @author tgmeow
 */
public enum StringManagerSingleton {
  INSTANCE;

  /**
   * A PApplet MUST be passed in (at least once) on the first getInstance. Throws an exception if
   * the parent is missing
   *
   * @return Singleton instance of the StringManager
   */
  public static StringManagerSingleton getInstance() {
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
  public static StringManagerSingleton setInstance(PApplet p) {
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
  public void addString(float frequency) {
    float roundedLabelFreq = Math.round(frequency * 10) / 10.0F;
    String label = strings.size() + " [" + roundedLabelFreq + "]";
    float xStep = 5;
    float yHeight = 80;
    strings.add(new StringComponent(parent, frequency, label, xStep, yHeight));
  }

  /**
   * Pluck the string specified by index TODO OUT OF RANGE BEHAVIOUR
   *
   * @param index which string to pluck
   */
  public void pluck(int index) {
    if (index >= 0 && index < strings.size()) {
      strings.get(index).pluck();
    }
  }

  /** Pluck all the strings */
  public void pluckAll() {
    strings.stream().forEach((str) -> str.pluck());
  }

  /**
   * Tic one of the strings (advance simulation one step) TODO OUT OF RANGE BEHAVIOUR
   *
   * @param index which string to tic
   */
  public void tic(int index) {
    if (index >= 0 && index < strings.size()) {
      strings.get(index).tic();
    }
  }

  /** Tic all the strings (advance simulation one step) */
  public void ticAll() {
    strings.stream().forEach((str) -> str.tic());
  }

  /** Tic all the strings AND plays the combined sound (samples) in StdAudio */
  public void ticPlayAll() {
    float sum = (float) strings.stream().mapToDouble(str -> str.ticAndSample()).sum();
    StdAudio.play(sum);
  }

  /**
   * Tic every string one cycle. One cycle is the length of that string. This just looks nice for
   * demonstration purposes.
   */
  public void ticAllOneCycle() {
    strings
        .stream()
        .forEach(
            (str) -> {
              for (int i = 0; i < str.size(); ++i) {
                str.tic();
              }
            });
  }

  /** Reset the instance to the initial state. Releases the parent object. */
  public void reset() {
    strings.clear();
    parent = null;
  }

  //Private variables

  private static PApplet parent;

  //Data structure for our guitar strings
  private static LinkedList<StringComponent> strings = new LinkedList<StringComponent>();
}
