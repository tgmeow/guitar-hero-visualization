package reusable.keymap;

import java.util.Set;
import java.util.TreeSet;

import main.java.StringManagerSingleton;

public class MultiStringRunner extends Runnable{

public  MultiStringRunner(Set<Integer> items) {
 indexes = items;
}
	
  @Override
  public void run() {
    indexes.stream().forEach(index -> StringManagerSingleton.getInstance().pluck(index));
  }

  /**
   * ID is generated with MSR + size + each index in the Set, no punctuation + sum of indexes
   * Ex. MSR4035917
   * This is just me being silly, not guaranteed to be unique, but it's pretty good. Chances of
   * collision should be small enough for this purpose.
   */
  @Override
  public String getComparableID() {
	  String ID = "MSR" + indexes.size();
	  int sum = 0;
	  for(int index : indexes){
		  ID += index;
		  sum += index;
	  }
    return ID + sum; 
  }
	
  private Set<Integer> indexes = new TreeSet<Integer>();
}
