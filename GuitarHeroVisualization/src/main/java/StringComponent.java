package main.java;

import java.util.ListIterator;

import processing.core.PApplet;
import reusable.guitar.GuitarString;

public class StringComponent{
	
	public StringComponent(PApplet p, float frequency, String label, float xStepDist, float yHeight){
		this.label = label;
		this.xStepDist = xStepDist;
		this.yHeight = yHeight;
		this.parent = p;
		mString = new GuitarString(frequency);
	}
	
	//Currently: draw is a BLOCKING process that iterates through the ENTIRE list and draws points.
	//TODO draw String at 0, 0 1st/4th quadrant between xStepDist*numPoints
	//and Y between -yHeight/2 and yHeight/2 (mult by yHeight)
	//now with string label, which is the string number and freq "1 [440.3]
	public void draw(){
		if(this.draw){
			if(mString.getSize() > 1){
				int LABEL_OFFSET = 80; 
				parent.text(label, 0, 0);
				
				ListIterator<Float> it = mString.getIterator();
				float pY = it.next() * yHeight;	//initialize first y value to prev
				
				//draw all the points
				for(float x = xStepDist + LABEL_OFFSET; it.hasNext(); x += xStepDist){
					float y = it.next() * yHeight;	//increments the iterator
					parent.line(x-xStepDist, pY, x, y);
					pY = y;
				}
			}
		}
	}
	
	public void enableDraw(){
		this.draw = true;
	}
	
	public void disableDraw(){
		this.draw = false;
	}
	
	public void pluck(){
		this.mString.pluck();
	}
	
	public void tic(){
		mString.tic();
	}
	
	public float sample(){
		return mString.sample();
	}
	
	public int size(){
		return mString.getSize();
	}
	
	private String label;
	private float xStepDist;
	private float yHeight;
	
	private PApplet parent;
	private boolean draw = true;
	private GuitarString mString;

}
