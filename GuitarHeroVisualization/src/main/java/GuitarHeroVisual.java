/** */
package main.java;

import java.io.File;
import java.util.TreeSet;

import processing.core.PApplet;
import processing.event.MouseEvent;
import reusable.events.EventQueue;
import reusable.keymap.AllStringsRunner;
import reusable.keymap.KeyMap;
import reusable.keymap.StringRunner;
import reusable.menu.ButtonController;
import reusable.menu.MenuSingleton;

/**
 * Main Processing applet. Visualizes a guitar string
 *
 * @author tgmeow
 */
public class GuitarHeroVisual extends PApplet {

  /**
   * The main process runs the PApplet
   *
   * @param args
   */
  public static void main(String[] args) {
    PApplet.main("main.java.GuitarHeroVisual");
  }

  /** Pre-setup settings */
  public void settings() {
    size(1300, 700);
  }

  /** Set up the applet. Initialize vars here? */
  public void setup() {
    fill(0);
    stroke(0);
    StringManager.setInstance(this);
    for (int i = 0; i < NUM_STRINGS; i++) {
      float factor = (float) Math.pow(2, i / 12.0);
      StringManager.getInstance().addString(BASE_FREQ * factor);
    }

    KeyMap keyMap = KeyMap.getInstance();
    keyMap.addRunnable('a', new AllStringsRunner());
    //keyMap.addRunnable('q', new SingleStringRunner(0));
    char[] keys = new char[] {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\'};
    for (int i = 0; i < keys.length; ++i) {
      keyMap.addRunnable(keys[i], new StringRunner(i));
    }

    TreeSet<Integer> chordA = new TreeSet<Integer>();
    chordA.add(0);
    chordA.add(4);
    chordA.add(7);
    keyMap.addRunnable('z', new StringRunner(chordA));
    
    MenuSingleton.setInstance(this);
    MenuSingleton.getInstance().addController("Button1", new ButtonController(this, "Button1"));
    
    //EventQueue.getInstance().addEvent(2, new StringRunner(chordA));
    //EventQueue.getInstance().beginEvents();
	

  selectInput("Select a file to process:", "loadSongFile");

  }

  /**
   * Callback method for selecting an input
   * @param selection
   */
  public static void loadSongFile(File selection) {
	  if (selection == null) {
	    println("Window was closed or the user hit cancel.");
	  } else {
	    println("User selected " + selection.getAbsolutePath());
	    EventQueue.getInstance().loadStringEventsFile(selection.getAbsolutePath());
	  }
	}
  
  /** Main draw loop. Target fps is 60, but 30 is also fine. */
  public void draw() {
    background(255);
    //Process menu data at the beginning
    MenuSingleton menuSing = MenuSingleton.getInstance();
    if((boolean) menuSing.getControllerValue("Button1")){
    	KeyMap.getInstance().run('q');
    }
    
    
pushMatrix();
translate(0, verticalScroll);
    StringManager.getInstance().draw();

    popMatrix();
    //44100 / 60fps = 735 tics per frame for real time
    //Play 2x (slightly faster) to fill the StdAudio buffer more quickly and prevent the weird pauses???
    //Or maybe just fast enough (since its not always 60fps) to deliver 44100 samples/sec
    if (!pauseTic) {
      StringManager.getInstance().playStringsLive();
    }
    //if (frameCount % 10 == 0) System.out.println(frameRate);
    
    MenuSingleton.getInstance().draw();
  }

  /** Called when a key gets pressed */
  public void keyPressed() {
    if (key == ' ') {
      pauseTic = !pauseTic;
    } if(key == '.'){
    	EventQueue.getInstance().beginEvents();
    }else if (key == '\n') {
      if (pauseTic) { //only let this run if the display is paused
        StringManager.getInstance().ticAllOneCycle();
      }
    } else KeyMap.getInstance().run(key);
  }
  
  /**
   * Handle mouse presses
   */
  public void mousePressed(){
	  MenuSingleton.getInstance().pressUpdate(mouseX, mouseY);
  }
  
  /**
   * Handle mouse releases
   */
  public void mouseReleased(){
	  MenuSingleton.getInstance().releaseUpdate(mouseX, mouseY);
  }
  
  public void mouseWheel(MouseEvent event) {
	  float e = event.getCount();
	  verticalScroll -= 10*e;
	  if(verticalScroll >= MAX_SCROLL) verticalScroll = MAX_SCROLL;
	  else if(verticalScroll <= MIN_SCROLL) verticalScroll = MIN_SCROLL;
	}

  //Pauses the execution of tics
  private boolean pauseTic = false;

  //Base frequency for strings higher frequencies results in faster displaying
  private final float BASE_FREQ = 110.0F;

  //13 notes = full scale
  private final int NUM_STRINGS = 37;
  
  private final int MAX_SCROLL = 0;
  private float verticalScroll = 0;
  private final int MIN_SCROLL = -42 * (NUM_STRINGS+1);
}
