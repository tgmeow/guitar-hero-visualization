package reusable.events;

import processing.core.PApplet;

public class LoadSongRunnable extends GuitarRunnable {

  public LoadSongRunnable(PApplet p) {
    parent = p;
  }

  @Override
  public void run() {
    //"loadSongFile" is the PApplet style callback that calls the public method in the main
    parent.selectInput("Select a file to process:", "loadSongFile");
  }

  @Override
  public String getComparableID() {
    return "LoadSongRunnable";
  }

  PApplet parent;
}
