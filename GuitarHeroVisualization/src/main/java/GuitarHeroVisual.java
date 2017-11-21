/**
 * 
 */
package main.java;

import processing.core.PApplet;
import reusable.guitar.GuitarString;
//import reusable.audio.StdAudio;

/**
 * @author tgmeow
 *
 */
public class GuitarHeroVisual extends PApplet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main("main.java.GuitarHeroVisual");
	}
	
	public void settings(){
		size(1300, 700);
	}
	
	public void setup(){
		fill(0);
		stroke(0);
		mStringManager = new StringManagerComponent(this);
		for (int i = 0; i < NUM_STRINGS; i++) {
			float factor = (float)Math.pow(2, (i - 24) / 12.0);
			mStringManager.addString(CONCERT_A * factor);
		}
	}
	
	public void draw(){
		background(255);
		
		mStringManager.draw();
		
		//44100 / 60fps = 735 tics per frame for real time
		//Play 2x (slightly faster) to fill the StdAudio buffer more quickly and prevent the weird pauses???
		//Or maybe just fast enough (since its not always 60fps) to deliver 44100 samples/sec
		if(!pauseTic){
			for(int i = 0; i < GuitarString.SAMPLE_RATE/frameRate; ++i){
				mStringManager.ticPlayAll();
			}
		}
		//if(frameCount%10==0) System.out.println(frameRate);
	}
	
	public void keyPressed() {
		if(key == 'a'){
			mStringManager.pluckAll();
		}
		else if(key == ' '){
			pauseTic = !pauseTic;
		}
		else if(key == '\n'){
			if(pauseTic){ //only let this run if the display is paused
				mStringManager.ticAllOneCycle();
			}
		}
		//keys qwertyuiop[]\ play the 13 strings
		else if(key == 'q'){
			mStringManager.pluck(0);
		}
		else if(key == 'w'){
			mStringManager.pluck(1);
		}
		else if(key == 'e'){
			mStringManager.pluck(2);
		}
		else if(key == 'r'){
			mStringManager.pluck(3);
		}
		else if(key == 't'){
			mStringManager.pluck(4);
		}
		else if(key == 'y'){
			mStringManager.pluck(5);
		}
		else if(key == 'u'){
			mStringManager.pluck(6);
		}
		else if(key == 'i'){
			mStringManager.pluck(7);
		}
		else if(key == 'o'){
			mStringManager.pluck(8);
		}
		else if(key == 'p'){
			mStringManager.pluck(9);
		}
		else if(key == '['){
			mStringManager.pluck(10);
		}
		else if(key == ']'){
			mStringManager.pluck(11);
		}
		else if(key == '\\'){
			mStringManager.pluck(12);
		}
	}

	
	private boolean pauseTic = false;
	private final float CONCERT_A = 880.0F;	//higher frequencies results in faster displaying
	private final int NUM_STRINGS = 13;		//13 notes = full scale
	private StringManagerComponent mStringManager;
	
}
