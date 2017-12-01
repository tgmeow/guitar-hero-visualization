package reusable.events;

/**
 * Runnable that toggles the pause variable in the EventQueue
 *
 * @author tgmeow
 */
public class PlayPauseRunnable extends Runnable {

  @Override
  public void run() {
    EventQueue.getInstance().togglePause();
  }

  @Override
  public String getComparableID() {
    return "EventQueueTogglePause";
  }
}
