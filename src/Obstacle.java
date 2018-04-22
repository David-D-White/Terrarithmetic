import java.awt.*;
/**
 * The Obstacle class is a subclass of the Tile class. The Obstacle class is created so that tiles cannot be walked over/under by the player.
 * <p>
 * Obstacle
 * <p>
 * Date: 2016/05/11
 * @author David White and Sepehr Hosseini 
 * @version 2 2016/05/11
 */ 
public class Obstacle extends Tile
{ 
  /**
   * This constructor creates a non-animated instance of the Obstacle class which cannot be interacted with.
   * @param tile This defines the picture on the face of the Tile. 
   */ 
  public Obstacle(Sprite tile) 
  {
    super (tile, true, false);
  }
  /**
   * This constructor creates a non-animated instance of the Obstacle class that has the option of being interacted with.
   * @param tile this defines the picture on the face of the tile. 
   * @param isInteract this variable defines whether or not the tile can be interacted with.  
   */ 
  public Obstacle(Sprite tile, boolean isInteract) 
  {
    super (tile, true, isInteract);
  }
  /**
   * This constructor creates an animated instance of the Obstacle class which cannot be interacted with.
   * @param anim This array holds the Sprites used during the animation of the object.
   */ 
  public Obstacle(Sprite [] anim)
  {
    super (anim, true, false);
  }
  /**
   * This constructor creates an animated instance of the Obstacle class which has the option of being interacted with.
   * @param anim This array holds the Sprites used during the animation of the object.
   * @param isInteract this variable defines whether or not the tile can be interacted with. 
   */ 
  public Obstacle(Sprite [] anim, boolean isInteract)
  {
    super (anim, true, isInteract);
  }
  
  /**
   * This method returns the tile type as an integer.
   * @return int the tile type as an integer.
   */ 
  @Override
  public int getTileType ()
  {
    return Map.OBSTACLE;
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