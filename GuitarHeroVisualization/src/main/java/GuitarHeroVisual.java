/** */
package main.java;

import java.util.TreeSet;

import processing.core.PApplet;
import reusable.guitar.GuitarString;
import reusable.keymap.AllStringsRunner;
import reusable.keymap.KeyMapSingleton;
import reusable.keymap.MultiStringRunner;
import reusable.keymap.SingleStringRunner;

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
    StringManagerSingleton.setInstance(this);
    for (int i = 0; i < NUM_STRINGS; i++) {
      float factor = (float) Math.pow(2, i / 12.0);
      StringManagerSingleton.getInstance().addString(CONCERT_A * factor);
    }

    KeyMapSingleton keyMap = KeyMapSingleton.getInstance();
    keyMap.addRunnable('a', new AllStringsRunner());
    //keyMap.addRunnable('q', new SingleStringRunner(0));
    char[] keys = new char[] {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\'};
    for (int i = 0; i < keys.length; ++i) {
      keyMap.addRunnable(keys[i], new SingleStringRunner(i));
    }

    TreeSet<Integer> chordA = new TreeSet<Integer>();
    chordA.add(0);
    chordA.add(4);
    chordA.add(7);
    keyMap.addRunnable('z', new MultiStringRunner(chordA));
  }

  /** Main draw loop. Target fps is 60, but 30 is also fine. */
  public void draw() {
    background(255);

    StringManagerSingleton.getInstance().draw();

    //44100 / 60fps = 735 tics per frame for real time
    //Play 2x (slightly faster) to fill the StdAudio buffer more quickly and prevent the weird pauses???
    //Or maybe just fast enough (since its not always 60fps) to deliver 44100 samples/sec
    if (!pauseTic) {
      //
      float boundedFrameRate = frameRate < MIN_FRAME_RATE ? MIN_FRAME_RATE : frameRate;
      for (int i = 0; i < GuitarString.SAMPLE_RATE / boundedFrameRate; ++i) {
        StringManagerSingleton.getInstance().ticPlayAll();
      }
    }
    //if (frameCount % 10 == 0) System.out.println(frameRate);
  }

  /** Called when a key gets pressed */
  public void keyPressed() {
    if (key == ' ') {
      pauseTic = !pauseTic;
    } else if (key == '\n') {
      if (pauseTic) { //only let this run if the display is paused
        StringManagerSingleton.getInstance().ticAllOneCycle();
      }
    } else KeyMapSingleton.getInstance().run(key);
  }

  //Pauses the execution of tics
  private boolean pauseTic = false;

  //use a minimum frame rate to prevent infinite tic loop
  private final int MIN_FRAME_RATE = 15;

  //Base frequency for strings higher frequencies results in faster displaying
  private final float CONCERT_A = 440.0F;

  //13 notes = full scale
  private final int NUM_STRINGS = 13;
}
