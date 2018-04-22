import java.io.*;
/**
 * The Player class stores all the information relative to the player.
 * <p>
 * Player
 * <p>
 * Date: 2016/05/8
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/8
 */ 
public class Player extends Entity
{
  /**This stores the player's deck. */
  private Deck deck;
  /**This stores the number of wins the player has. */
  private int wins;
  /**This stores the number of losses the player has. */
  private int losses;
  /**This stores the player's name. */
  private String name;
  
  /**
   * This constructor creates an instance of the Player class based on the specified file path.
   * @param character the file path used for creating the character.
   * @param name the name given to the character, used for highscores.
   * @param x the x coordinate of the Entity.
   * @param y the y coordinate of the Entity.
   * @param deck the player's deck.
   */
  public Player (String character, String name, int x, int y, Deck deck) 
  {
    super (character, x, y);
    this.name = name;
    this.deck = deck;
  }
  
   /**
   * This method returns the player's name.
   * @return String the player's name.
   */
  public String name()
  {
    return name;
  }
  
  /**
   * This adds a loss to the player's total losses.
   */
  public void addLoss()
  {
    losses ++;
  }
  
  /**
   * This adds a win to the player's total wins.
   */
  public void addWin()
  {
    wins ++;
  }
  
  /**
   * This method returns the total number of wins the player has.
   * @return int the total number of wins the player has.
   */
  public int wins()
  {
    return wins;
  }
  
  /**
   * This method returns the total number of losses the player has.
   * @return int the total number of losses the player has.
   */
  public int losses()
  {
    return losses;
  }
  
  /**
   * This method adds a Card to the player's Deck.
   * @param card the new Card to be added.
   */
  public void addCard(Card card)
  {
    deck.add(card);
  }
  
  /**
   * This method returns the player's deck.
   * @return Deck the player's deck.
   */
  public Deck deck()
  {
    return deck;
  }
}