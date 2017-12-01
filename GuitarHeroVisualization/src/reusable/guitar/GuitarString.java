/**
 * GuitarString.java Java implementation of the GuitarString class from Professor Roth's Data
 * Structures class (CS2201) at Vanderbilt University.
 */
package reusable.guitar;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.lang.Math;

/**
 * Models a guitar string by using a LinkedList and a low-pass filter from the Karplus-Strong
 * algorithm. (Utilizes a LinkedList instead of a queue to allow for iteration through the data.)
 *
 * @author Tiger Mou
 */
public class GuitarString {
  //public methods

  /**
   * Constructor for the GuitarString. Initializes the LinkedList with zeros. The size of the
   * LinkedList is equal to the SAMPLE_RATE/frequency rounded to the nearest integer. May throw
   * IllegalArgumentException.
   *
   * @param frequency Must be a nonzero float larger than SAMPLE_RATE/2. Represents the frequency of
   *     the GuitarString.
   */
  public GuitarString(double frequency) {
    //check validity of frequency
    if (frequency <= 0 || frequency > SAMPLE_RATE / 2) {
      throw new IllegalArgumentException("Invalid frequency of GuitarString: " + frequency);
    }

    //initialize private vars
    this.ticCount = 0;
    this.frequency = frequency;
    this.mQueue = Collections.synchronizedList(new LinkedList<Float>());

    //calculate size of queue and fill with zeros
    int n = (int) Math.round(SAMPLE_RATE / frequency);
    for (int i = 0; i < n; i++) {
      mQueue.add(0.0F);
    }
  }

  /** Pluck the string - excite with white noise between -0.5 and 0.5 */
  public synchronized void pluck() {
    for (ListIterator<Float> it = mQueue.listIterator(); it.hasNext(); ) {
      it.next();
      //set operates on the last element returned by next or previous
      it.set((float) Math.random() - 0.5F);
    }
  }

  /** Advance the simulation one time step */
  public synchronized void tic() {
    ++ticCount;
    float current = mQueue.get(0);
    mQueue.remove(0);
    mQueue.add(DECAY_FACTOR * 0.5F * (current + mQueue.get(0)));
  }

  /** @return a float representing the current state of the string */
  public synchronized float sample() {
    return mQueue.get(0);
  }

  /** @return Number of tics */
  public int getTime() {
    return ticCount;
  }

  /** @return Frequency of the string */
  public double getFrequency() {
    return frequency;
  }

  /** @return An iterator to the queue/linkedlist */
  public synchronized ListIterator<Float> getIterator() {
    return mQueue.listIterator();
  }

  /** @return size of the string */
  public synchronized int getSize() {
    return mQueue.size();
  }

  //Private variables

  //Sample rate of our audio
  public static final int SAMPLE_RATE = 44100;
  
  //Tic time step
  public static final double TIME_STEP = 1.0/44100;

  //decay rate of the string
  private final float DECAY_FACTOR = 0.996F;

  //data structure to hold the string values.
  //A linked list used like a queue because need to iterate through.
  private List<Float> mQueue;

  //Number of times tic was called
  private int ticCount;

  //frequency of this string
  private double frequency;

  //estimated amount of energy in the string (could be expensive calculation)
  //TODO determine if is worth
  //private float energy;
}
