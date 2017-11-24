package reusable.keymap;

import main.java.StringManagerSingleton;

/**
 * Runnable class that plucks all strings
 * @author tgmeow
 *
 */
public class AllStringsRunner extends Runnable {
	
	/**
	 * Plucks all the strings in the StringManagerSingleton
	 */
  @Override
  public void run() {
    StringManagerSingleton.getInstance().pluckAll();
  }

  /**
   * Returns "ASR"
   */
  @Override
  public String getComparableID() {
    return "ASR";
  }
}
