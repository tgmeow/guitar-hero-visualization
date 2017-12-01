package reusable.events;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

import reusable.keymap.StringRunner;

public enum EventQueue {
  INSTANCE;

  public static EventQueue getInstance() {
    return INSTANCE;
  }

  /**
   * Add a runnable event set to play at a given time
   *
   * @param time Time in seconds to run the runnable
   * @param runner Runnable that gets run when the running time >= time.
   */
  public void addEvent(double time, Runnable runner) {
    eventQueue.add(new TimedRunnable(time, runner));
  }

  /** @param runner */
  public void addEvent(TimedRunnable runner) {
    eventQueue.add(runner);
  }
  
  public void loadStringEventsFile(String filePath){
	  reset(); //start clean
	  //TODO selectInput to select/open file. Then parse/read through entire file and add events (if possible) display errors and invalid lines in console
	  Scanner scan;
	    File file = new File(filePath);
	    try {
	        scan = new Scanner(file);

	        double time = 0;
        	int string = 0;
	        while(scan.hasNextDouble())
	        {
	        	//read in values from the file
	        	time = scan.nextDouble();
	            if(scan.hasNextInt()){
	            	string = scan.nextInt();
	            }
	            //add values to the event queue
	            addEvent(time, new StringRunner(string));
	        }

	    } catch (FileNotFoundException e1) {
	            e1.printStackTrace();
	    }
	  //do not start immediately
	    pauseEvents();
  }

  /**
   * Pause the event timer and prevent running any other events. Maintains the state of the event
   * queue but allows new events
   */
  public void pauseEvents() {
    playEvents = false;
  }

  /** Unpause the event timer and continue running events */
  public void unPauseEvents() {
    playEvents = true;
  }

  /** Start the event timer at zero and begin running the events */
  public void beginEvents() {
    eventTimer = 0;
    playEvents = true;
  }

  /** Reset the events and event variables to their clean initial state. */
  public void reset() {
    eventTimer = 0;
    eventQueue.clear();
    playEvents = false;
  }

  /**
   * Check if the top event(s) are ready to be played and remove it/them after playing
   */
  public void playEvents() {
    if (playEvents) {
      while (!eventQueue.isEmpty() && eventQueue.peek().getTime() <= eventTimer) {
        eventQueue.remove().run();
      }
    }
  }

  /**
   * @param time Tic the event timer by some amount
   */
  public void ticEventTimer(double time) {
    if (playEvents) {
      eventTimer += time;
    }
  }
  
  /**
   * Check if any events are ready to be played and then increment the tic time after
   * @param time amount of time to tic
   */
  public void playEventsTic(double time) {
    playEvents();
    ticEventTimer(time);
    //System.out.println(eventTimer);
  }
  
  //Private 

  //Controls run events/tic
  private boolean playEvents = false;

  //timer for the local event queue
  private double eventTimer = 0;

  //A structure to hold the queued up timer events. Insertion order does not matter, only play time.
  private PriorityQueue<TimedRunnable> eventQueue = new PriorityQueue<TimedRunnable>();
}
