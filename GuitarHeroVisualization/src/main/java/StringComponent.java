package main.java;

import java.util.ListIterator;

import processing.core.PApplet;
import reusable.guitar.GuitarString;

/**
 * A Processing-specific wrapper class for the generic GuitarString class. Mirrors the GuitarString
 * class but with drawing capabilities.
 *
 * @author tgmeow
 */
public class StringComponent {

  /**
   * Constructor for a String Component. Should only really be called by StringManagerSingleton, but
   * I can let that slide for now. Creates an instance of the GuitarString object with the specified
   * parameters
   *
   * @param p Parent PApplet object
   * @param frequency Frequency of the string
   * @param label A label for this string ("" to display nothing)
   * @param xStepDist Horizontal distance between each point of the string data
   * @param yHeight Vertical span of the string visualization
   */
  public StringComponent(
      PApplet p, double frequency, String label, float xStepDist, float yHeight) {
    this.label = label;
    this.xStepDist = xStepDist;
    this.yHeight = yHeight;
    this.parent = p;
    mString = new GuitarString(frequency);
  }

  /**
   * Draw is (currently) a thread blocking process that iterates through the ENTIRE list and draws
   * points. Draws String at (0,0) 1st/4th quadrant between xStepDist*numPoints and Y between
   * -yHeight/2 and yHeight/2 (mult by yHeight) with string label, which is the string number and
   * freq "0 [440.3]
   */
  public void draw() {
    if (!this.draw) {
      return;
    }
    //TODO REMOVE LOCAL VARIABLE(S)
    int LABEL_OFFSET = 80;
    parent.text(label, 0, 0);

    if (activeString) {
      if (mString.getSize() > 1) {
        synchronized (mString) {
          ListIterator<Float> it = mString.getIterator();
          float pY = it.next() * yHeight; //initialize first y value to prev

          //draw all the points
          for (float x = xStepDist + LABEL_OFFSET; it.hasNext(); x += xStepDist) {
            float y = it.next() * yHeight; //increments the iterator
            parent.line(x - xStepDist, pY, x, y);
            pY = y;
          }
        }
      }
    } else {
      //String is inactive. Can cheat a little by drawing a flat line lol
      float x = xStepDist + LABEL_OFFSET;
      parent.line(x - xStepDist, 0, x + xStepDist * mString.getSize(), 0);
    }
  }

  /** Enables the draw method */
  public void enableDraw() {
    this.draw = true;
  }

  /** Disables the draw method */
  public void disableDraw() {
    this.draw = false;
  }

  /** (Pluck the string - excite with white noise between -0.5 and 0.5) */
  public void pluck() {
    activeString = true;
    lastPlucked = System.currentTimeMillis();
    this.mString.pluck();
  }

  /** Tic the GuitarString object (Advance the simulation one time step) */
  public void tic() {
    if (activeString) {
      mString.tic();
    }
  }

  /**
   * Sample the GuitarString object
   *
   * @return a float representing the current state of the string
   */
  public float sample() {
    if (activeString) {
      return mString.sample();
    }
    return 0;
  }

  /**
   * Does tic and then sample in one method
   *
   * @return a float representing the current state of the string
   */
  public float ticAndSample() {
    tic();
    return sample();
  }

  /**
   * Get the length of the GuitarString object.
   *
   * @return the size of the GuitarString object
   */
  public int size() {
    return mString.getSize();
  }

  public boolean checkActivity() {
    activeString = (System.currentTimeMillis() - lastPlucked < INACTIVE_TIME);
    return activeString;
  }

  private volatile boolean activeString = false;

  //Keeps track of when the string was last plucked. If time since is > InactiveTime, tic does nothing
  private long lastPlucked = 0;

  //10 seconds after ticing, the string should be inactive. Max ringing for.. 12 seconds?
  private final long INACTIVE_TIME = 12000;

  //label of the drawn string
  private String label;

  //horizontal step size between points
  private float xStepDist;

  //vertical span of the drawn string
  private float yHeight;

  //disable or enable draw
  private boolean draw = true;

  //parent object
  private PApplet parent;

  //GuitarString data object
  private GuitarString mString;
}
