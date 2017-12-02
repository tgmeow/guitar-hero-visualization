package reusable.keymap;

import main.java.StringManager;
import reusable.events.GuitarRunnable;

/**
 * Runnable class that plucks all strings
 *
 * @author tgmeow
 */
public class AllStringsRunner extends GuitarRunnable {

  /** Plucks all the strings in the StringManagerSingleton */
  @Override
  public void run() {
    StringManager.getInstance().pluckAll();
  }

  /** Returns "ASR" */
  @Override
  public String getComparableID() {
    return "ASR";
  }
}
