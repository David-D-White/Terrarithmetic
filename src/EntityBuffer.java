import java.awt.image.*;
import java.awt.*;
import java.util.*;
/**
 * The EntityBuffer class is used for taking information from the Entity components of the game and formatting it into a display.
 * <p>
 * EntityBuffer
 * <p>
 * Date: 2016/05/26
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/26
 */
public class EntityBuffer extends Buffer
{
  /**This stores the array of NPC objects to be displayed by this buffer. */
  private NPC [] computerCharacters;
  /**The numerical values that correspond to each NPC to track their movement. */
  private int [] modNPC;
  /**Numerical x value for the top left corner of this buffer in relation to game components. */
  private int viewX =0;
  /**Numerical y value for the top left corner of this buffer in relation to game components. */
  private int viewY = 0;
  
  /**
   * This constructor creates an instance of the EntityBuffer class to show the specified NPC objects.
   * @param computerCharacters[] the NPC objects that are passed in to be shown.
   */
  public EntityBuffer (NPC [] computerCharacters)
  {
    super (1000, 600, 64);
    this.computerCharacters = computerCharacters;
    modNPC = new int [computerCharacters.length];
  }
  
  /**
   * This method is intended to center the view of the buffer around the specified x and y coordinates in relation to the game components passed into it.
   * @param x the x coordinate.
   * @param y the y coordinate.
   */
  public void centerView (int x, int y)
  {
    viewX = x - 8;
    viewY = y - 5;
    update();
  }
  
  /**
   * This method moves the character at the specified index in the NPC array based on the direction of the NPC.
   * @param x the index of the character to be moved.
   */
  public void moveCharacter (int x)
  {
    if (computerCharacters [x].direction() == Entity.NORTH || computerCharacters [x].direction() == Entity.WEST)
      modNPC[x]--;
    else 
    {
      if (computerCharacters [x].direction() == Entity.SOUTH || computerCharacters [x].direction() == Entity.EAST)
        modNPC[x]++;
    }
  }
  
  /**
   * This method resets the movement of the character.
   * @param x the index of the character to be moved.
   */
  public void resetCharacter (int x)
  {
    modNPC [x] = 0;
  }
  
  /**
   * This method is intended to update the buffer image based on the game components.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   */ 
  public void update()
  {
    Graphics2D g2D = view.createGraphics();
    g2D.setBackground (new Color (0,0,0,0));
    g2D.clearRect(0,0,view.getWidth(),view.getHeight());
    for (int x = 0; x < computerCharacters.length; x++)
    {
      if (computerCharacters [x].direction() == Entity.NORTH || computerCharacters[x].direction() == Entity.SOUTH)
        g2D.drawImage(computerCharacters[x].retrieveFrame().image(), (computerCharacters[x].getX() - viewX) * tileSize, (computerCharacters[x].getY() - viewY) * tileSize - tileSize/4 + modNPC [x], null);
      else
      {
        if (computerCharacters [x].direction() == Entity.WEST || computerCharacters[x].direction() == Entity.EAST)
          g2D.drawImage(computerCharacters[x].retrieveFrame().image(), (computerCharacters[x].getX() - viewX) * tileSize + modNPC [x], (computerCharacters[x].getY() - viewY) * tileSize - tileSize/4, null);
      }
    }
  }
}