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
		StringManagerSingleton.getInstance(this);
		for (int i = 0; i < NUM_STRINGS; i++) {
			float factor = (float)Math.pow(2, i / 12.0);
			StringManagerSingleton.getInstance().addString(CONCERT_A * factor);
		}
	}
	
	public void draw(){
		background(255);
		
		StringManagerSingleton.getInstance().draw();
		
		//44100 / 60fps = 735 tics per frame for real time
		//Play 2x (slightly faster) to fill the StdAudio buffer more quickly and prevent the weird pauses???
		//Or maybe just fast enough (since its not always 60fps) to deliver 44100 samples/sec
		if(!pauseTic){
			//
			float boundedFrameRate = frameRate<MIN_FRAME_RATE ? MIN_FRAME_RATE : frameRate;
			for(int i = 0; i < GuitarString.SAMPLE_RATE/boundedFrameRate; ++i){
				StringManagerSingleton.getInstance().ticPlayAll();
			}
		}
		//if(frameCount%10==0) System.out.println(frameRate);
	}
	
	public void keyPressed() {
		if(key == 'a'){
			StringManagerSingleton.getInstance().pluckAll();
		}
		else if(key == ' '){
			pauseTic = !pauseTic;
		}
		else if(key == '\n'){
			if(pauseTic){ //only let this run if the display is paused
				StringManagerSingleton.getInstance().ticAllOneCycle();
			}
		}
		//keys qwertyuiop[]\ play the 13 strings
		else if(key == 'q'){
			StringManagerSingleton.getInstance().pluck(0);
		}
		else if(key == 'w'){
			StringManagerSingleton.getInstance().pluck(1);
		}
		else if(key == 'e'){
			StringManagerSingleton.getInstance().pluck(2);
		}
		else if(key == 'r'){
			StringManagerSingleton.getInstance().pluck(3);
		}
		else if(key == 't'){
			StringManagerSingleton.getInstance().pluck(4);
		}
		else if(key == 'y'){
			StringManagerSingleton.getInstance().pluck(5);
		}
		else if(key == 'u'){
			StringManagerSingleton.getInstance().pluck(6);
		}
		else if(key == 'i'){
			StringManagerSingleton.getInstance().pluck(7);
		}
		else if(key == 'o'){
			StringManagerSingleton.getInstance().pluck(8);
		}
		else if(key == 'p'){
			StringManagerSingleton.getInstance().pluck(9);
		}
		else if(key == '['){
			StringManagerSingleton.getInstance().pluck(10);
		}
		else if(key == ']'){
			StringManagerSingleton.getInstance().pluck(11);
		}
		else if(key == '\\'){
			StringManagerSingleton.getInstance().pluck(12);
		}
	}

	
	
	private boolean pauseTic = false;
	private final int MIN_FRAME_RATE = 15; //use a minimum frame rate to prevent infinite tic loop
	private final float CONCERT_A = 440.0F;	//higher frequencies results in faster displaying
	private final int NUM_STRINGS = 13;		//13 notes = full scale
	
}
