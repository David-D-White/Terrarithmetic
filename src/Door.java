import java.awt.*;
import java.io.*;
/**
 * The Door class is an Obstacle that will take the player from one map to another. 
 * <p>
 * Door
 * <p>
 * Date: 2016/05/18
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/18
 */ 
public class Door extends Obstacle 
{
  /**This is the file name of the map that will be loaded. */
  private String mapName;
  /**This is the starting x coordinate when the map is loaded.*/
  private int startX;
  /**This is the starting y coordinate when the map is loaded.*/
  private int startY;
  /**This boolean variable is representative of whether or not this door is locked.*/
  private boolean locked;
  /**The Sprite used as a background for the text returned if this Door is locked.*/
  private Sprite back;
  /**The String value returned if this door is locked.*/
  private String lockText;
  /**This boolean value is representative of whether or not this Door marks the end of the game.*/
  private boolean winGate;
  
  /**
   * This constructor creates an animated instance of the Door class with a specified Sprites for animation, reference of the file, starting x and y coordinate.
   * @param anim[] This is the set of sprites that will be used to display the animation of the door.
   * @param mapName This is the map that is being loaded.
   * @param startX This is the starting x coordinate of the next map.
   * @param startY This is the starting y coordinate of the next map.
   */ 
  public Door (Sprite [] anim, String mapName, int startX, int startY)
  {
    super (anim, true);
    this.mapName = mapName;
    this.startX = startX;
    this.startY = startY;
    setAnimating (false);
  }
  
  /**
   * This constructor creates an animated instance of the Door class with a specified Sprites for animation, reference of the file, starting x and y coordinate, with the specified background and the specified lock text..
   * @param anim[] This is the set of sprites that will be used to display the animation of the door.
   * @param mapName This is the map that is being loaded.
   * @param startX This is the starting x coordinate of the next map.
   * @param startY This is the starting y coordinate of the next map.
   * @param back The Sprite used for the background of the text returned is locked.
   * @param lockText the String returned if this Door is locked.
   */ 
  public Door (Sprite [] anim, String mapName, int startX, int startY, Sprite back, String lockText)
  {
    super (anim, true);
    this.mapName = mapName;
    this.startX = startX;
    this.startY = startY;
    setAnimating (false);
    locked = true;
    this.back = back;
    this.lockText = lockText;
  }
  
  /**
   * This constructor creates an unanimated instance of the Door class with a specified sprite, reference of the file, starting x and y coordinate.
   * @param tile This is the tile that will be used to draw the door.
   * @param mapName This is the map that is being loaded.
   * @param startX This is the starting x coordinate of the next map.
   * @param startY This is the starting y coordinate of the next map.
   */ 
  public Door (Sprite tile, String mapName, int startX, int startY)
  {
    super (tile, true);
    this.mapName = mapName;
    this.startX = startX;
    this.startY = startY;
  }
  
  /**
   * This method returns a boolean value representative of whether or not this door marks the end of the game.
   * @return true if this Door marks the end of the game.
   */ 
  public boolean isWinGate ()
  {
    return winGate;
  }
  
  /**
   * This method sets the Door as a marker for the end of the game.
   */ 
  public void setWinGate()
  {
    winGate = true;
  }
  
  /**
   * This method sets the Door to be locked.
   * @param lock whether or not this Door should be locked.
   */ 
  public void setLocked (boolean lock)
  {
    locked = lock;
  }
  
  /**
   * This method returns whether or not this Door is locked.
   * @return true if the Door is locked.
   */ 
  public boolean locked()
  {
    return locked;
  }
  
  /**
   * This method returns the background of the text for when this Door is locked.
   * @return Sprite the background of the Door text.
   */ 
  public Sprite background()
  {
    return back;
  }
  
  /**
   * This method returns the String of text displayed when this Door is locked.
   * @return String the text to be displayed if this Door is locked.
   */ 
  public String lockText ()
  {
    return lockText;
  }
  
  /**
   * This method returns the starting x coordinate of the Door.
   * @return int the starting x coordinate of the Door.
   */ 
  public int startX ()
  {
    return startX;
  }
  
  /**
   * This method returns the starting y coordinate of the Door.
   * @return int the starting y coordinate of the Door.
   */ 
  public int startY()
  {
    return startY;
  }
  
  /**
   * This method returns the name of the file loaded by the Door.
   * @return String the name of the file loaded by the Door.
   */ 
  public String mapName ()
  {
    return mapName;
  }
  
  /**
   * This method returns true if the Door has finished animating and is in the open position.
   * @return true if the Door has finished animating and is in the open position.
   */ 
  public boolean isOpen ()
  {
    return currentFrame() == animationLength() - Tile.FRAME_LENGTH || !isAnim() ;
  }
  
  /**
   * This is an overriden method which will be called when the Tile is interacted with.
   * @param panel This is the OpenWorldPanel that will be used during the interaction.
   */
  @Override
  public void interact(OpenWorldPanel panel)
  {
    panel.openDoor(this);
  }
  
  /**
   * This method returns the tile type as an integer.
   * @return int the tile type as an integer.
   */
  @Override
  public int getTileType()
  {
    return Map.DOOR;
  }
}