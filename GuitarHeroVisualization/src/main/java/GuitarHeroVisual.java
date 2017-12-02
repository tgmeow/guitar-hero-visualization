/** */
package main.java;

import java.io.File;

import processing.core.PApplet;
import processing.event.MouseEvent;
import reusable.events.EventQueue;
import reusable.events.LoadSongRunnable;
import reusable.events.PlayPauseRunnable;
import reusable.keymap.AllStringsRunner;
import reusable.keymap.KeyMap;
import reusable.keymap.StringRunner;
import reusable.menu.Menu;
import reusable.menu.RunnableButtonController;

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
    size(1300, 700, P2D);
  }

  /** Set up the applet. Initialize vars here? */
  public void setup() {
    fill(0);
    stroke(0);
    StringManager.setInstance(this);
    for (int i = 0; i < NUM_STRINGS; i++) {
      double factor = Math.pow(2, i / 12.0);
      StringManager.getInstance().addString(BASE_FREQ * factor);
    }

    KeyMap keyMap = KeyMap.getInstance();
    keyMap.addRunnable('a', new AllStringsRunner());
    //keyMap.addRunnable('q', new SingleStringRunner(0));
    char[] keys = new char[] {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\'};
    for (int i = 0; i < keys.length; ++i) {
      keyMap.addRunnable(keys[i], new StringRunner(i));
    }

    keyMap.addRunnable('z', new StringRunner(0, 4, 7));

    Menu.setInstance(this);
    Menu myMenu = Menu.getInstance();
    myMenu.addController(
        "PlayPauseEvents",
        new RunnableButtonController(this, "Play/Pause", new PlayPauseRunnable()));
    myMenu.addController(
        "LoadSong0", new RunnableButtonController(this, "Load Song", new LoadSongRunnable(this)));
    //myMenu.addController("Button0", new ButtonController(this, "Button0"));

    //EventQueue.getInstance().addEvent(2, new StringRunner(chordA));
    //EventQueue.getInstance().beginEvents();

    lastActive = System.currentTimeMillis();
    noLoop();
    redraw();
  }

  /**
   * Callback method for selecting an input
   *
   * @param selection
   */
  public void loadSongFile(File selection) {
    if (selection == null) {
      println("Window was closed or the user hit cancel.");
    } else {
      println("User selected " + selection.getAbsolutePath());
      EventQueue.getInstance().loadStringEventsFile(selection);
      ackEvent();
    }
  }

  /** Main draw loop. Target fps is 60, but 30 is also fine. */
  public void draw() {
    if (!EventQueue.getInstance().isTiccing() && System.currentTimeMillis() - lastActive >= 300) {
      noLoop();
      println("no draw activity");
    }
    //	  if(!userActivity && !EventQueue.getInstance().isTiccing()){
    //		  return;
    //	  }
    background(255);
    //Process menu data at the beginning
    //    Menu menuSing = Menu.getInstance();
    //    if ((boolean) menuSing.getControllerValue("Button0")) {
    //      KeyMap.getInstance().run('q');
    //    }

    pushMatrix();
    //Enable scrolling
    translate(0, verticalScroll);
    StringManager.getInstance().draw();

    popMatrix();

    //if (frameCount % 10 == 0) System.out.println(frameRate);

    //Draw menu after
    Menu.getInstance().draw();
  }

  /** Called when a key gets pressed */
  public void keyPressed() {
    ackEvent();
    if (key == ' ') {
      pauseTic = !pauseTic;
      EventQueue.getInstance().togglePause();
    }
    if (key == '.') {
      EventQueue.getInstance().beginEvents();
    } else if (key == '\n') {
      if (pauseTic) { //only let this run if the display is paused
        StringManager.getInstance().ticAllOneCycle();
      }
    } else KeyMap.getInstance().run(key);
  }

  /** Handle mouse presses */
  public void mousePressed() {
    ackEvent();
    Menu.getInstance().pressUpdate(mouseX, mouseY);
  }

  /** Handle mouse releases */
  public void mouseReleased() {
    Menu.getInstance().releaseUpdate(mouseX, mouseY);
  }

  /** Handle mouse wheel scrolling */
  public void mouseWheel(MouseEvent event) {
    ackEvent();
    float e = event.getCount();
    verticalScroll -= 10 * e;
    if (verticalScroll >= MAX_SCROLL) verticalScroll = MAX_SCROLL;
    else if (verticalScroll <= MIN_SCROLL) verticalScroll = MIN_SCROLL;
  }

  private void ackEvent() {
    loop();
    lastActive = System.currentTimeMillis();
  }

  //Pauses the execution of tics
  private boolean pauseTic = false;

  //Base frequency for strings higher frequencies results in faster displaying
  private final double BASE_FREQ = 110.0F;

  //13 notes = full scale
  private final int NUM_STRINGS = 37;

  //Scroll bounds
  private final int MAX_SCROLL = 0;
  private float verticalScroll = 0;
  private final int MIN_SCROLL = -42 * (NUM_STRINGS + 1);

  private long lastActive = 0;
}
