/**
 * The Chest class creates a Chest that the player can recieve new Cards from.
 * <p>
 * Chest
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */
public class Chest extends Obstacle
{
  /**This is the new Card that will be added to the Deck. */
  private Card newCard;
  /**This is the Deck that the new Card will be added to. */
  private Sprite background;
  
  /**
   * This constructor creates a Card that will be a number with a value and image.
   * @param animChest[] the Sprite array of Chest images.
   * @param newCard the Card that will be added to the Deck.
   * @param background the Sprite that the text will be displayed on.
   */ 
  public Chest (Sprite [] animChest, Sprite background, Card newCard)
  {
    super (animChest,true);
    this.background = background;
    this.newCard = newCard;
  }
  
  /**
   * This method is used to return the text shown when the Chest is interacted with.
   * @return String the text shown when the Chest is interacted with.
   */ 
  public String chestText()
  {
    if (newCard.isOperator())
      return ("Congratulations! New card found: ["+newCard.getOperator()+ "]. This card has been added to your deck...");
    else
      return ("Congratulations! New card found: ["+newCard.getValue()+ "]. This card has been added to your deck...");
  }
  
  /**
   * This method returns the Sprite that the text will be displayed on.
   * @return Sprite the Sprite that the text will be displayed on.
   */ 
  public Sprite background()
  {
    return background;
  }
  
  /**
   * This method returns the new Card that will be added to the player's deck.
   * @return Card the new Card that will be added to the player's deck.
   */ 
  public Card newCard ()
  {
    return newCard;
  }
  
  /**
   * This method returns the Tile type as a Chest.
   * @return int the Tile type as a Chest.
   */
  @Override
  public int getTileType ()
  {
    return Map.CHEST;
  }
  
  /**
   * This method is used to determine what will happen when the Chest is interacted with.
   * @param panel used as reference to OpenWorldPanel.
   */ 
  public void interact(OpenWorldPanel panel)
  {
    panel.chestFound(this);
  }
  
}