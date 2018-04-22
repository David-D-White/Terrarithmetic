import java.awt.image.*;
/**
 * The Card class is used to construct cards that will be either operators or numbers in a Deck.
 * <p>
 * Card
 * <p>
 * Date: 2016/05/20
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/20
 */
public class Card 
{
  /**This is the String that will store the operator. */
  private String operator;
  /**This is the int that will store the value of a Card that is a number. */
  private int value;
  /**This is the Sprite that each individual Card will be drawn with. */
  private Sprite cardImage;
  
  /**
   * This constructor creates a Card that will be a number with a value and image.
   * @param value the value that will be given to a specified Card.
   * @param cardImage the Sprite that will be given to a specified Card.
   */ 
  public Card (int value, Sprite cardImage)
  {
    this.value = value;
    this.cardImage = cardImage;
  }
  /**
   * This constructor creates a Card that will be an operator with an operator and image.
   * @param operator the String that will store the value of the operator.
   * @param cardImage the Sprite that will be given to a specified Card.
   */ 
  public Card (String operator, Sprite cardImage)
  {
    this.operator = operator;
    this.cardImage = cardImage;
  }
  /**
   * This method returns the value of a Card that is a number.
   * @return int the value of a Card that is a number.
   */ 
  public int getValue()
  {
    return value;
  }
  /**
   * This method returns the value of a Card that is an operator as a String.
   * @return String the String value of a Card that is an operator.
   */ 
  public String getOperator()
  {
    return operator;
  }
  
  /**
   * This method is used to determine whether the Card is an operator or a number.
   * @return true if the Card is an operator.
   */ 
  public boolean isOperator()
  {
    return (operator != null);
  }
  
  /**
   * This method returns the operator and value of the Card as a String.
   * @return String the value and operator of the Card.
   */ 
  @Override
  public String toString ()
  {
    return operator + " "+ value;
  }
  /**
   * This method returns the Sprite of a Card which will be used when drawing Sprites.
   * @return Sprite the Sprite value of the Card.
   */ 
  public Sprite cardImage()
  {
    return cardImage;
  }
}