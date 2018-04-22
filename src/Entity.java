import java.io.*;
/**
 * The Entity class stores all the information relative to any characters.
 * <p>
 * Entity
 * <p>
 * Date: 2016/05/30
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/30
 */ 
public class Entity
{
  /**Numerical value for the south direction. */
  static final int SOUTH = 2;
  /**Numerical value for the west direction. */
  static final int WEST = 3;
  /**Numerical value for the north direction. */
  static final int NORTH = 0;
  /**Numerical value for the east direction. */
  static final int EAST = 1;
  /**Numerical value for the index of the standing image of the character. */
  protected final int STANDING =0;
  /**Numerical value for the index of the right foot forward image of the character. */
  protected final int RIGHT = 1;
  /**Numerical value for the index of the left foot forward image of the character. */
  protected final int LEFT = 2; 
  /**Number of directions that the player can face. */
  protected final int DIRECTIONS = 4;
  /**Number of frames in the walking animation of the character.*/ 
  protected final int STEPS = 3;
  /**This stores the array of sprites for the walking animation of the character.*/ 
  protected Sprite [] [] animationSprites = new Sprite [DIRECTIONS][STEPS];
  /**Stores an integer value to track which sprite should be returned by the nextStep() method*/ 
  private int animationPosition = 0;
  /**Stores the direction that the player is currently facing.*/ 
  private int direction= SOUTH;
  /**Stores the x coordinate of this character.*/ 
  private int entityX;
  /**Stores the y coordinate of this character.*/ 
  private int entityY;
  /**Stores the character name.*/ 
  private String characterName;
  
  /**
   * This constructor creates an instance of the Entity class based on the specified file path.
   * @param character the file path used for creating the character.
   * @param entityX the x coordinate of this character.
   * @param entityY the y coordinate of this character.
   */
  public Entity (String character, int entityX, int entityY) 
  {
    this.entityX = entityX;
    this.entityY = entityY;
    characterName = character;
    try
    {
      for (int x = 0; x < STEPS; x++)
      {
        animationSprites [NORTH] [x] = new Sprite ("Characters/" + character + "/Up" + x + ".png");
        animationSprites [SOUTH] [x] = new Sprite ("Characters/" + character + "/Down" + x + ".png");
        animationSprites [EAST] [x] = new Sprite ("Characters/" + character + "/Right" + x + ".png");
        animationSprites [WEST] [x] = new Sprite ("Characters/" + character + "/Left" + x + ".png");
      }
    }
    catch (IOException e)
    {
    }
  }
  
  /**
   * This method returns the character name.
   * @return String the character name.
   */
  public String characterName()
  {
    return characterName;
  }
  
  /**
   * This method goes to the next frame in the walking animation based on the direction of the player. 
   */
  public void nextFrame()
  {
    animationPosition ++;
    if (animationPosition > 3)
      animationPosition = 0;
  }
  
  /**
   * This method retrieves the current frame of the character based on the direction.
   * @return Sprite the image of the character based on the direction. 
   */
  public Sprite retrieveFrame ()
  {
    if (animationPosition == 1)
      return animationSprites [direction] [1];
    else if (animationPosition == 3)
      return animationSprites [direction] [2];
    else
      return animationSprites [direction] [0];
  }
  
  /**
   * This method returns the direction that the character is facing.
   * @return int direction that the character is facing.
   */
  public int direction ()
  {
    return direction;
  }
  /**
   * This method sets the player's direction to be the specified direction.
   * @param direction the specified direction that will replace the player's current direction.
   */
  public void changeDirection(int direction)
  {
    if (direction >= NORTH && direction <= WEST)
      this.direction = direction;
  }
  
  /**
   * This method returns the x coordinate of this character.
   * @return int the x coordinate of this character.
   */
  public int getX ()
  {
    return entityX;
  }
  
  /**
   * This method returns the y coordinate of this character.
   * @return int the y coordinate of this character.
   */
  public int getY ()
  {
    return entityY;
  }
  
  /**
   * This method sets the x coordinate of this character.
   * @param x the x coordinate that will be set for this character.
   */
  public void setX (int x)
  {
    entityX = x;
  }
  
   /**
   * This method sets the y coordinate of this character.
   * @param y the y coordinate that will be set for this character.
   */
  public void setY (int y)
  {
    entityY = y;
  }
  
   /**
   * This method increments the x or y coordinate based on character direction.
   */
  public void move ()
  {
    if (direction == NORTH)
      entityY --;
    else if (direction == SOUTH)
      entityY ++;
    else if (direction == EAST)
      entityX ++;
    else if (direction == WEST)
      entityX --;
  }
}