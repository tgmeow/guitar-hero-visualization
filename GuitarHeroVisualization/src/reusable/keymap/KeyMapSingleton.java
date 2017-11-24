package reusable.keymap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Singleton pattern for mapping key char (or code?) to Runnable. Many to few relationship from
 * added keys to Runners (many keys can map to the same action)
 *
 * @author tgmeow
 */
public enum KeyMapSingleton {
  INSTANCE;

  /** Constructor for KeyMapSingleton */
  public static KeyMapSingleton getInstance() {
    return INSTANCE;
  }

  /**
   * Trigger a press the specified character. Calls run on the corresponding runnables if they exist
   *
   * @param c which char was pressed
   */
  public void run(char c) {
    if (mKeyMap.containsKey(c)) {
      mKeyMap.get(c).values().stream().forEach(runnable -> runnable.run());
    }
  }

  /**
   * Get all the runnables associated with some key
   *
   * @param key
   * @return Map of what keys the char maps to. May be null.
   */
  public Map<String, Runnable> getRunnables(char key) {
    return mKeyMap.get(key);
  }

  /**
   * Adds the specified runnable to be called by the given char
   *
   * @param c the Char that runs this Runnable
   * @param runnable the Runnable to add to the Map
   */
  public void addRunnable(char c, Runnable runnable) {
    if (!mKeyMap.containsKey(c)) {
      mKeyMap.put(c, new HashMap<String, Runnable>());
    }
    mKeyMap.get(c).put(runnable.getComparableID(), runnable);
  }

  /**
   * Remove the specified key from the map
   *
   * @param key
   */
  public void removeKey(char key) {
    mKeyMap.remove(key);
  }

  /**
   * This is preferred over removeRunnables(String) because of faster runtime. Remove all Runnables
   * in the map linked to the given character that have a getComparable equal to the
   * comparableString. Cleans up empty map elements.
   *
   * @param comparableString
   */
  public void removeRunnables(char c, String comparableString) {
    mKeyMap.get(c).remove(comparableString);
    if (mKeyMap.get(c).isEmpty()) {
      mKeyMap.remove(c);
    }
  }

  /**
   * Remove all Runnables in the map that have a getComparable that is equal to the comparableString
   * Cleans up empty map elements.
   *
   * @param comparableString
   */
  public void removeRunnables(String comparableString) {
    //need to make a copy of the set because we may be modifying the key set
    Set<Character> chars = new TreeSet<Character>(mKeyMap.keySet());
    for (char c : chars) {
      mKeyMap.get(c).remove(comparableString);
      if (mKeyMap.get(c).isEmpty()) {
        mKeyMap.remove(c); //TODO Does this break the forEach pattern?
      }
    }
  }

  /** @return The total number of runnables in this KeyMap */
  public int getNumRunnables() {
    return mKeyMap.values().stream().mapToInt(map -> map.size()).sum();
  }

  //Map from character pressed to a HashMap from comparable String to Runnable items
  private HashMap<Character, HashMap<String, Runnable>> mKeyMap =
      new HashMap<Character, HashMap<String, Runnable>>();
}
