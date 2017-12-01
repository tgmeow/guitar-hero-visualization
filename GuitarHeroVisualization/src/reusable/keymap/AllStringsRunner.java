package reusable.keymap;

import main.java.StringManager;
import reusable.events.Runnable;

/**
 * Runnable class that plucks all strings
 *
 * @author tgmeow
 */
public class AllStringsRunner extends Runnable {

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
