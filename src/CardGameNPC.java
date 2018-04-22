/**
 * The CardGameNPC class is created so that NPCs can be added to the game which the player can play a Card game against.
 * <p>
 * CardGameNPC
 * <p>
 * Date: 2016/06/01
 * @author David White
 * @version 1 2016/06/01
 */ 
public class CardGameNPC extends InformationNPC
{
  /**This is the Deck that the NPC will play with. */
  private Deck deck;
   /**This is a boolean value representative of whether or not the player has played against this NPC.*/
  private boolean battled;
  
  /**
   * This constructs a new instance of the Card game NPC class with a specified character model, the specified x and y coordinates, and the specified Deck.
   * @param character the model the be loaded.
   * @param x the x coordinate to be started at.
   * @param y the y coordinate to be started at.
   * @param deck the Deck that the NPC will play with.
   * @param textBack the Sprite used behind the displayed text. 
   * @param afterGameText the text that the NPC will say after they have been played against.
   */ 
  public CardGameNPC (String character, int x, int y, Deck deck, Sprite textBack, String afterGameText)
  {
    super (character, x, y , textBack, afterGameText);
    this.deck = deck;
  }
  
   /**
   * This method is used to set whether or not the NPC has been played against.
   * @param battled sets whether or not the NPC has been played against.
   */ 
  public void setBattled (boolean battled)
  {
    this.battled = battled;
  }
  
  /**
   * This method returns the value of the battled variable.
   * @return true if the NPC has been played against.
   */ 
  public boolean battled ()
  {
    return battled;
  }
  
  /**
   * This method returns this NPC's deck.
   * @return Deck the NPC's deck.
   */ 
  public Deck deck()
  {
    return deck;
  }
  
   /**
   * This is an overriden method which will be called when the NPC is interacted with.
   * @param panel This is the OpenWorldPanel that will be used during the interaction.
   */
  @Override 
  public void interact(OpenWorldPanel panel)
  {
    if (!battled)
      panel.startGame(this);
    else
      super.interact(panel);
  }
  
  /**
   * This method returns the type of the NPC as an integer.
   * @return int the type of the NPC as an integer.
   */
  @Override 
  public int getType()
  {
    return Map.CARD_NPC;
  }
}