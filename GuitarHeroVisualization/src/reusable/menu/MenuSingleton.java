package reusable.menu;

import java.util.HashMap;

import org.omg.PortableInterceptor.IORInterceptor;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Singleton pattern for a menu with multiple controllers of varying types
 * @author tgmeow
 *
 */
public enum MenuSingleton {
	INSTANCE;
	
	/**
	 * May throw IllegalStateException if the parent was not passed in through setInstance. 
	 * @return the singleton pattern instance of the Menu.
	 */
	public static MenuSingleton getInstance(){
	    //This is a slightly ugly solution, but it ensures that a PApplet is passed in and
	    //simplifies future calls to retrieve the singleton
	    if (parent == null) {
	      throw new IllegalStateException("Unable to return instance without parent PApplet.");
	    }
		return INSTANCE;
	}
	
	/**
	 * MUST be called at least once to pass in the parent PApplet
	 * @param p Parent PApplet
	 * @return the Singleton instance
	 */
	public static MenuSingleton setInstance(PApplet p){
		parent = p;
		return INSTANCE;
	}
	
	
	/**
	 * Draw the menu.. TODO location unknown
	 */
	public void draw(){
		parent.textAlign(PConstants.LEFT, PConstants.CENTER);
		mMenuItems.values().stream().forEach(contr -> contr.draw());
	}
	
	/**
	 * Process a mouse press for each controller item
	 * @param x
	 * @param y
	 */
	public void pressUpdate(int x, int y){
		mMenuItems.values().stream().forEach(contr -> contr.pressUpdate(x, y));
	}
	
	/**
	 * Process a mouse release for each controller item
	 * @param x
	 * @param y
	 */
	public void releaseUpdate(int x, int y){
		mMenuItems.values().stream().forEach(contr -> contr.releaseUpdate(x, y));
	}
	
	/**
	 * Add the provided controller to the Menu
	 * @param key unique id for this controller. Ideally the key is equal to the controller display name.
	 * @param contr Controller to add
	 */
	public <T> void addController(String key, Controller<T> contr){
		contr.setPosition(parent.width - menuWidth, mMenuItems.size()*controllerHeight);
		contr.setDimensions(menuWidth, controllerHeight);
		mMenuItems.put(key, contr);
	}
	
	/**
	 * Remove a controller of the given id
	 * @param id String identifier of the controller
	 */
	public void removeController(String id){
		mMenuItems.remove(id);
	}
	
	public Object getControllerValue(String key){
		if(mMenuItems.containsKey(key)){
			return mMenuItems.get(key).getValue();
		}
		return null;
	}
	
	/**
	 * Reset the controllers to their initial states
	 */
	public void reset(){
		mMenuItems.values().stream().forEach(contr -> contr.reset());
	}
	
	
	
	
	private static PApplet parent = null;
	
	//TODO raw types help I don't actually know Java
	//Map from String ID to Controller
	private HashMap<String, Controller> mMenuItems = new HashMap<String, Controller>();
	
	//Other private variables for setting Menu layout, width, colors, etc
	
	private int curPos = PConstants.RIGHT;
	private int menuWidth = 250;
	private int controllerHeight = 40;
	
}
