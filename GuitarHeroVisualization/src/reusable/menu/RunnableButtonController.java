package reusable.menu;

import processing.core.PApplet;
import reusable.button.Button;
import reusable.events.Runnable;

/**
 * Runs the runnable item when the button is clicked.
 * @author tgmeow
 *
 */
public class RunnableButtonController extends Controller<Boolean> {

	  public RunnableButtonController(PApplet p, String displayName, Runnable runner) {
		  mRunnable = runner;
		  mButton = new Button(p, getButtonX(), getButtonY(), buttonWidth, buttonHeight);
	    this.parent = p;
	    this.displayName = displayName;
	  }
	  
	  @Override
	  public void draw() {
	    // TODO Draw button label, button, current value
		  parent.text(displayName, trueX, getStateY());
		  mButton.draw();
		  parent.text("Runnable", getStateX(), getStateY());
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
	      if(mButton.contains(clickX, clickY)){
	    	  if(mRunnable != null){
	    		  mRunnable.run();
	    	  }
	      }
	    }
	    isPressed = false;
	  }

	  @Override
	  public Boolean getValue() {
	    return false;
	  }

	  @Override
	  public void reset() {
		  //nothing to reset. The runnable should stay I think?
	  }

	  private PApplet parent;

	  private Button mButton; //TODO button dimensions

	  //Initial value and current value will always start at false since its a single event
	  private boolean isPressed = false;
	
	  private Runnable mRunnable = null;

}
