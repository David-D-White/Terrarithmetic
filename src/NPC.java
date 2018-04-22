/**
 * This is an abstract class that defines the generic attributes and behaviours of all NPCs.
 * <p>
 * NPC
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */ 
public abstract class NPC extends Entity
{
   /**
   * This constructs an instance of the NPC class with a specified character model at the specified x and y coordinate. 
   * @param character the character model of this NPC.
   * @param x the x coordinate that this NPC will be created at.
   * @param y the y coordinate that this NPC will be created at.
   */ 
  public NPC(String character, int x, int y) 
  {
    super (character, x, y);
  }
  /**
   * This method randomly changes the direction of the NPC.
   */ 
  public void changeDirection ()
  {
    changeDirection((int) (Math.random() * 4));
  }
  /**
   * This is an abstract method to be implemented in the subclasses which will be called when the NPC is interacted with.
   * @param panel This is the OpenWorldPanel that will be used during the interaction.
   */
  public abstract void interact (OpenWorldPanel panel);
  /**
   * This is an abstract method to be implemented in the subclasses which returns the type of the NPC.
   * @return int the type of the NPC.
   */
  public abstract int getType ();
}