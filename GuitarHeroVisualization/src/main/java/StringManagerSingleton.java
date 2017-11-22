package main.java;

import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import main.java.StringComponent;
import reusable.audio.StdAudio;

public enum StringManagerSingleton {
	INSTANCE;
//	private StringManagerComponent(PApplet p){
//		this.parent = p;
//		this.strings = new LinkedList<StringComponent>();
//	}
	
	
	public static StringManagerSingleton getInstance(){
		if(strings == null){
			strings = new LinkedList<StringComponent>();
		}
		return INSTANCE;
	}
	
	//Only need to pass in the PApplet on the first getInstance
	public static StringManagerSingleton getInstance(PApplet p){
		parent = p;
		if(strings == null){
			strings = new LinkedList<StringComponent>();
		}
		return INSTANCE;
	}
	
	public void draw(){
		//draw strings
		int spacing = 50;
		int y = spacing;
		for(Iterator<StringComponent> it = strings.iterator(); it.hasNext();){
			parent.pushMatrix();
				parent.translate(100, y);
				it.next().draw();
			parent.popMatrix();
			y += spacing;
		}
		
	}
	
	public void addString(float frequency){
		float rounded = Math.round(frequency*10)/10.0F;
		strings.add(new StringComponent(parent, frequency, strings.size() + "[" + rounded + "]", 5F, 80F));
	}
	
	public void pluck(int index){
		if(index >= 0 && index < strings.size()){
			strings.get(index).pluck();
		} else{
			//TODO
		}
	}
	
	public void pluckAll(){
		for(Iterator<StringComponent> it = strings.iterator(); it.hasNext();){
			it.next().pluck();
		}
	}
	
	public void tic(int index){
		if(index >= 0 && index < strings.size()){
			strings.get(index).tic();
		} else{
			//TODO
		}
	}
	
	public void ticAll(){
		//TODO
		for(Iterator<StringComponent> it = strings.iterator(); it.hasNext();){
			it.next().tic();
		}
	}
	
	public void ticPlayAll(){
		float sum = 0;
		for(Iterator<StringComponent> it = strings.iterator(); it.hasNext();){
			StringComponent ret = it.next();
			ret.tic();
			sum += ret.sample();
		}
		StdAudio.play(sum);
	}
	
	public void ticAllOneCycle(){
		for(Iterator<StringComponent> it = strings.iterator(); it.hasNext();){
			StringComponent ret = it.next();
			for(int i = 0; i < ret.size(); ++i){
				ret.tic();
			}
		}
	}

	
	//Private variables
	
	private static PApplet parent;
	
	//Holds our guitar strings
	private static LinkedList<StringComponent> strings;
}
