import java.awt.image.*;
import java.util.*;
import java.io.*;
import java.awt.*;
/**
 * This is an abstract class that defines the basic attributes and behaviours of all Tiles. 
 * <p>
 * Tile
 * <p>
 * Date: 2016/05/18
 * @author David White and Sepehr Hosseini 
 * @version 2 2016/05/18
 */ 
public abstract class Tile
{
  /**The image for the Tile.*/
  private Sprite tile;
  /**This holds all the Sprites used for animation.*/
  private Sprite [] anim;
  /**This stores the current frame that the animation is on.*/
  private double currentFrame;
  /**This stores whether or not the Tile is an obstacle.*/
  private boolean isObstacle;
  /**This stores whether or not the Tile is interactive.*/
  private boolean isInteract;
  /**This stores whether or not a Tile is animated.*/
  private boolean isAnim;
  /**This stores the speed of the animation.*/
  static final double FRAME_LENGTH = 0.25;
  /**This stores whether or not the Tile is being interacted with.*/
  private boolean interacting;
  /**This stores whether or not the Tile is being animated.*/
  private boolean animating;
  
  /**
   * This constructs an unanimated instance of the Tile class using a specified Sprite, and has the option of being an Obstacle and/or interactive. 
   * @param tile the Sprite that will be used to create the tile.
   * @param isObstacle boolean value of whether or not this Tile is considered an Obstacle.
   * @param isInteract boolean value of whether or not this Tile will be interactive.
   */
  public Tile (Sprite tile, boolean isObstacle,boolean isInteract)
  {
    isAnim = false;
    this.tile = tile;
    anim = new Sprite [] {tile};
    this.isObstacle = isObstacle;
    this.isInteract = isInteract;
  }
  /**
   * This constructs an animated instance of the Tile class using a specified Sprite, and has the option of being an Obstacle and/or interactive. 
   * @param anim This array holds the Sprites used during the animation of the object.
   * @param isObstacle boolean value of whether or not this Tile is considered an Obstacle.
   * @param isInteract boolean value of whether or not this Tile will be interactive.
   */
  public Tile (Sprite [] anim, boolean isObstacle,boolean isInteract)
  {
    isAnim = true;
    this.tile = anim [0];
    this.anim = anim;
    this.isObstacle = isObstacle;
    this.isInteract = isInteract;
    setAnimating (true);
  }
  
  /**
   * This method returns true if the Tile is currently animating.
   * @return true if the Tile is currently animating.
   */
  public boolean animating()
  {
    return animating;
  }
  
  /**
   * This method stores whether or not the Tile is animating.
   * @param animating stores whether or not the Tile is animating.
   */
  public void setAnimating (boolean animating)
  {
    this.animating= animating;
  }
  
  /**
   * This method loads a selection of Sprites based on a specified file path and returns the values as an array.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>s </b> This is used to point to the Scanner class.
   * <p>
   * <b>numSprites </b> This is the number of Sprites in the Sprite array for animation.
   * <p>
   * <b>anim </b> This is the Sprite array that contains the Sprites which will be loaded for the animation from the file.
   * @return Sprite [] an array of Sprites used for the animation. 
   * @param file the file path used to load the Sprites.
   * @throws IOException when reading from a file.
   */
  public static Sprite [] loadAnim (String file) throws java.io.IOException
  {
    Scanner s = new Scanner (new File (".//resources/AnimatedTiles/" + file + "/"+file+".txt"));
    int numSprites = s.nextInt();
    Sprite[] anim = new Sprite [numSprites];
    for (int x = 0; x < numSprites; x++)
    {
      anim [x] = new Sprite ("/AnimatedTiles/" + file + "/"+file+x+".png");
      
    }
    return anim;
  } 
  
  /**
   * This method returns the Sprite of this Tile.
   * @return Sprite the sprite of this Tile.
   */
  public Sprite getSprite()
  {
    return tile;
  }
  
  /**
   * This method returns the length of the animation of this Tile.
   * @return int the number of frames in this Tile's animation.
   */
  public int animLength()
  {
    return anim.length;
  }
  
  /**
   * This method returns image of the Sprite of this Tile as a BufferedImage. 
   * @return BufferedImage the image of the Sprite. 
   */
  public BufferedImage tileImage()
  {
    return tile.image();
  }
  
  /**
   * This method changes the image value of the Sprite with the specified Sprite.
   * @param tile the Sprite that will be used as the Tile's image.
   */
  public void setSprite(Sprite tile)
  {
    this.tile = tile;
  } 
  /**
   * This method returns whether or not the Tile is an Obstacle.
   * @return true if the Tile is an Obstacle. 
   */
  public boolean isObstacle ()
  {
    return isObstacle;
  }
  /**
   * This method returns whether or not the Tile is interactive.
   * @return true if the Tile is interactive.
   */
  public boolean isInteract ()
  {
    return isInteract;
  }
  
  /**
   * This method returns whether or not the Tile is animated.
   * @return true if the Tile is animated.
   */
  public boolean isAnim ()
  {
    return isAnim;
  }
  
  /**
   * This returns the current frame as a double value.
   * @return current frame as a double.
   */
  public double currentFrame()
  {
    return currentFrame;
  }
  /**
   * This returns the Sprite at the current frame.
   * @return Sprite the Sprite at the current frame.
   */
  public Sprite retrieveFrame()
  {
    return anim[(int)currentFrame];
  }
  
  /**
   * This method returns the number of frames in the Tile's animation.
   * @return int the number of frames in the Tile's animation.
   */
  public int animationLength()
  {
    return anim.length;
  }
  
  /**
   * This retrieves the next frame of the Tile's animation.
   */
  public void nextFrame()
  {
    currentFrame +=FRAME_LENGTH;
    if (currentFrame >= anim.length)
      currentFrame =0;
  }
  /**
   * This retrieves the previous frame of the Tile's animation.
   */
  public void previousFrame()
  {
    currentFrame -= FRAME_LENGTH;
    if (currentFrame <= anim.length)
      currentFrame = anim.length - FRAME_LENGTH;
  }
  
  /**
   * This is an abstract method to be implemented in the subclasses which will be called when the Tile is interacted with.
   * @param panel This is the OpenWorldPanel that will be used during the interaction.
   */
  public abstract void interact (OpenWorldPanel panel);

  /**
   * This abstract method returns the tile type as an integer.
   * @return int the tile type as an integer.
   */ 
  public abstract int getTileType ();
}