package reusable.button;

import processing.core.PApplet;

/**
 * Simple rectangular button for detecting mouseClick events. Is not translate friendly. Takes raw
 * coordinates.
 *
 * @author tgmeow
 */
public class Button {
  /**
   * Contructor for a Button
   *
   * @param p parent PApplet
   * @param x true x location for mouse press AND DRAW
   * @param y true y location for mouse press AND DRAW
   */
  public Button(PApplet p, int x, int y, int width, int height) {
    this.parent = p;
    this.trueX = x;
    this.trueY = y;
    this.width = width;
    this.height = height;
  }

  /** Draw the Button at the x, y given in contructor */
  public void draw() {
    parent.rect(trueX, trueY, width, height);
  }

  public void reposition(int x, int y) {
    trueX = x;
    trueY = y;
  }

  /**
   * Determines if the given coordinate is located inside the Button. Use in processing mouse
   * events.
   *
   * @param mouseX true x coordinate
   * @param mouseY true y coordinate
   * @return is the given coordinate inside the button?
   */
  public boolean contains(int mouseX, int mouseY) {
    return mouseX >= trueX
        && mouseX <= trueX + width
        && mouseY >= trueY
        && mouseY <= trueY + height;
  }

  //Parent PApplet
  private PApplet parent;

  //trueX, trueY are the true untranslated locations of the Button. The button is drawn here.
  //width, height are the dimensions of the button
  private int trueX, trueY, width, height;
}
