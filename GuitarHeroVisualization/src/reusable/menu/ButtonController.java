package reusable.menu;

import processing.core.PApplet;
import reusable.button.Button;

/**
 * This Controller simulates a single button press that will trigger a single event. After the press
 * data is retrieved, it is reset. Both the press and release must be within the button for it to
 * register the click event
 *
 * @author tgmeow
 */
public class ButtonController extends Controller<Boolean> {

  public ButtonController(PApplet p, String displayName) {
	  mButton = new Button(p, getButtonX(), getButtonY(), buttonWidth, buttonHeight);
    this.parent = p;
    this.displayName = displayName;
  }
  
  @Override
  public void draw() {
    // TODO Draw button label, button, current value
	  parent.text(displayName, trueX, getStateY());
	  mButton.draw();
	  parent.text(String.valueOf(currentValue), getStateX(), getStateY());
  }
  
  @Override
  public void setPosition(int x, int y){
	  this.trueX = x;
	  this.trueY = y;
	  mButton.reposition(getButtonX(), getButtonY());
  }
  
  @Override
  public void setDimensions(int x,int y){
	  this.allocX = x;
	  this.allocY = y;
	  mButton.reposition(getButtonX(), getButtonY());
  }

  @Override
  public void pressUpdate(int clickX, int clickY) {
    isPressed = mButton.contains(clickX, clickY);
  }

  @Override
  public void releaseUpdate(int clickX, int clickY) {
    if (isPressed) {
      currentValue = mButton.contains(clickX, clickY);
    }
    isPressed = false;
  }

  @Override
  public Boolean getValue() {
    boolean temp = currentValue;
    reset();
    return temp;
  }

  @Override
  public void reset() {
    currentValue = initialValue;
  }

  private PApplet parent;

  private Button mButton; //TODO button dimensions

  //Initial value and current value will always start at false since its a single event
  private boolean initialValue = false, currentValue = false, isPressed = false;
}
