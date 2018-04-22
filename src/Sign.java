import java.awt.*;
import javax.swing.*;
/**
 * The Sign class is used to create a Sign with specified text.
 * <p>
 * Sign
 * <p>
 * Date: 2016/05/18
 * @author David White and Sepehr Hosseini 
 * @version 2 2016/05/18
 */ 
public class Sign extends Obstacle
{
  /**This String holds the text that will be displayed on the sign.*/
  private String text;
  /**This String holds the background used for the Sign.*/
  private Sprite background;
  /**This holds the current page on the Sign.*/
  private int currentPage;
  
  /**
   * This constructs a Sign based on the specified Tile, with a specified animation, a specified text on the Sign, and the specified panel that it will add the sign to. 
   * @param tile the Tile that is used to display the text on the sign. 
   * @param background the background image on the Sign.
   * @param text the text that will be displayed on the Sign.
   */
  public Sign (Sprite tile, Sprite background, String text)
  {
    super (tile, true);
    this.text = text;
    this.background = background;
  }
  
  /**
   * This method returns true if there are no more pages on the Sign left to be displayed.
   * @return true if there are no more pages on the Sign left to be displayed.
   */
  public boolean finished()
  {
    return currentPage == text.split("%").length - 1;
  }
  
  /**
   * This method sets the text on the Sign.
   * @param text the text that will be set on the Sign.
   */
  public void setText (String text)
  {
    this.text = text;
  }
  
  /**
   * This method increments the page on the Sign.
   */
  public void nextPage ()
  {
    currentPage ++;
    if (currentPage == text.split("%").length)
      currentPage = 0;
  }
  
  /**
   * This method returns the text that will be displayed on the Sign.
   * @return String the text on the Sign.
   */
  public String text()
  {
    return text.split("%") [currentPage];
  }
  
  /**
   * This method returns the full text that is displayed on the Sign.
   * @return String the full text on the Sign.
   */
  public String textFull()
  {
    return text;
  }
  
  /**
   * This method returns the Sign's background image.
   * @return Sprite the Sign's background image.
   */
  public Sprite background ()
  {
    return background;
  }
  
  /**
   * This overriden method returns the tile type of a Sign as an integer.
   * @return int the integer value of a Sign.
   */
  @Override
  public int getTileType ()
  {
    return Map.SIGN;
  }
   
  /**
   * This is an overriden method which will be called when the Sign is interacted with.
   * @param panel This is the OpenWorldPanel that will be used during the interaction.
   */
   @Override 
   public void interact(OpenWorldPanel panel)
   {
     currentPage = 0;
     panel.signDialogue(this);
   }
}