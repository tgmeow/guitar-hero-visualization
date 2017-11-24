package reusable.menu;

/**
 * Abstract class that is a parent to each menu entry in the MenuSingleton. Each Controller object
 * also serves as a variable that holds data for its representative use. The variable can be
 * retrieved with a String that maps to the Controller.
 * @author tgmeow
 *
 */
public abstract class Controller<T> {
	//Protected variables are accessible through the class, package, and subclasses
	
	//The display name of this Controller. Ideally the displayName == hash key
	protected String displayName;
	
	//The true X and Y location of this Controller. Used for mousePress events
	protected int trueX, trueY;
	
	/**
	 * Draw the specified controller at 0,0 Top left corner aligned
	 */
	public abstract void draw();
	
	/**
	 * Pass in the mousePress event. Should pass in the raw coordinates.
	 * @param clickX TRUE location of mouse X
	 * @param clickY TRUE location of mouseY
	 */
	public abstract void pressUpdate(int clickX, int clickY);
	
	/**
	 * Pass in the mouseRelease event. Should pass in the raw coordinates.
	 * @param clickX TRUE location of mouse X
	 * @param clickY TRUE location of mouseY
	 */
	public abstract void releaseUpdate(int clickX, int clickY);
	
	
	/**
	 * @return the abstract typed value of the controller. Return type depends on the Controller type
	 */
	public abstract T getValue(); //TODO is this the best way to return an abstract type
	
	/**
	 * Reset the controller to its initial state
	 */
	public abstract void reset();
}
