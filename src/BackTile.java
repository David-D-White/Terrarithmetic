/**
 * The BackTile class is a subclass of the Tile class. The BackTile class is created so that tiles can be created
 * that are not obstacles, so they can be walked over/under by the player.
 * <p>
 * BackTile
 * <p>
 * Date: 2016/05/11
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/11
 */ 
import java.awt.*;
public class BackTile extends Tile
{
  /**
   * This constructor creates a non-animated instance of the BackTile class.
   * @param tile This defines the picture on the face of the tile. 
   */ 
  public BackTile (Sprite tile)
  {
    super (tile,false,false);
  }
  /**
   * This constructor creates an animated instance of the BackTile class.
   * @param anim This array holds the Sprites used during the animation of the object.
   */ 
  public BackTile (Sprite [] anim)
  {
    super (anim,false,false);
  }
  
  /**
   * This method returns the tile type as an integer.
   * @return int the tile type as an integer.
   */
  @Override
  public int getTileType ()
  {
    return Map.BACK_TILE;
  }
  
  
  /**
   * This is an overriden method which will be called when the Tile is interacted with.
   * @param panel This is the OpenWorldPanel that will be used during the interaction.
   */
  @Override
  public void interact (OpenWorldPanel panel)
  {
  }
}