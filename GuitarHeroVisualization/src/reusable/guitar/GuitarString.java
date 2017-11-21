/**
 * GuitarString.java
 * Java implementation of the GuitarString class from Professor Roth's Data Structures
 * class (CS2201) at Vanderbilt University.
 */

package reusable.guitar;

import java.util.LinkedList;
import java.util.ListIterator;
import java.lang.Math;


/**
 * Models a guitar string by using a LinkedList and a low-pass filter from the
 * Karplus-Strong algorithm. (Utilizes a LinkedList instead of a queue to allow for
 * iteration through the data.)
 * @author Tiger Mou
 *
 */
public class GuitarString {
	//public methods
	
	/**
	 * Constructor for the GuitarString. Initializes the LinkedList with zeros.
	 * The size of the LinkedList is equal to the SAMPLE_RATE/frequency rounded to the
	 * nearest integer. May throw IllegalArgumentException.
	 * @param frequency Must be a nonzero float larger than SAMPLE_RATE/2.
	 * Represents the frequency of the GuitarString.
	 */
	public GuitarString(float frequency){
		//check validity of frequency
	    if (frequency <= 0 || frequency > SAMPLE_RATE/2){
	        throw new IllegalArgumentException("Invalid frequency of GuitarString: " + frequency);
	    }
	    
		//initialize private vars
		this.ticCount = 0;
		this.frequency = frequency;
		this.mQueue = new LinkedList<Float>();
	    
		//calculate size of queue and fill with zeros
		int n = (int) Math.floor(SAMPLE_RATE / frequency + 0.5F);
		for (int i = 0; i < n; i++) {
			mQueue.addLast(0.0F);
		}
	}
	
	/**
	 * Pluck the string - excite with white noise between -0.5 and 0.5
	 */
	public void pluck(){
		for(ListIterator<Float> it = mQueue.listIterator(); it.hasNext();){
			it.next();
			//set operates on the last element returned by next or previous
			it.set((float)Math.random() - 0.5F);
		}
	}
	
	/**
	 * Advance the simulation one time step
	 */
	public void tic(){
		++ticCount;
		float current = mQueue.getFirst();
		mQueue.removeFirst();
		mQueue.addLast(DECAY_FACTOR*0.5F*(current+mQueue.getFirst()));
	}
	
	/**
	 * @return a float representing the current state of the string
	 */
	public float sample(){
		return mQueue.getFirst();
	}
	
	/**
	 * @return Number of tics
	 */
	public int getTime(){
		return ticCount;
	}
	
	/**
	 * @return Frequency of the string
	 */
	public float getFrequency(){
		return frequency;
	}
	
	/**
	 * @return An iterator to the queue/linkedlist
	 */
	public ListIterator<Float> getIterator(){
		return mQueue.listIterator();
	}
	
	/**
	 * @return size of the string
	 */
	public int getSize(){
		return mQueue.size();
	}
	
	
	
	//Private variables
	
	//Sample rate of our audio
	public final static int SAMPLE_RATE = 44100;

	//decay rate of the string
	private final float DECAY_FACTOR = 0.992F;
	
	//data structure to hold the string values.
	//A linked list used like a queue because need to iterate through.
	private LinkedList<Float> mQueue;

	//Number of times tic was called
	private int ticCount;
	
	//frequency of this string
	private float frequency;
	
	//estimated amount of energy in the string (could be expensive calculation)
	//TODO determine if is worth
	//private float energy;
}
