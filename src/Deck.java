import java.util.*;
import java.io.*;
/**
 * The Deck class is used to handle a set of cards that will be either operators or numbers.
 * <p>
 * Deck
 * <p>
 * Date: 2016/05/24
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/24
 */
public class Deck
{
  /**This is the list of numbers that will be used in the Deck. */
  private ArrayList <Card> numbers;
  /**This is the list of operators that will be used in the Deck. */
  private ArrayList <Card> operators;
  /**This is a temporary list of operators that will be used in the Deck when determining which operators have already been dealt. */
  private ArrayList <Card> tempOperators;
  /**This is a temporary list of numbers that will be used in the Deck when determining which numbers have already been dealt. */
  private ArrayList <Card> tempNumbers;
  /**This is the String that will be used to represent the addition operator. */
  private final String ADDITION = "+";
  /**This is the String that will be used to represent the subtraction operator. */
  private final String SUBTRACTION = "-";
  /**This is the name of the file that the deck was loaded from. */
  private final String deckName;
  
  /**
   * This constructor creates a Deck which contains a set of Cards that are either numbers or operators.
   * @param deckName the name of the file that is associated with the Deck.
   */ 
  public Deck (String deckName)
  {
    numbers = new ArrayList <Card>();
    operators = new ArrayList <Card>();
    tempNumbers = new ArrayList <Card>();
    tempOperators = new ArrayList <Card>();
    this.deckName = deckName;
    if (deckName.indexOf(".txt") != deckName.length() - 4)
      deckName += ".txt";
  }
  
  /**
   * This method returns the name of the file that this Deck is associated with.
   * @return String the name of the file that this Deck is associated with.
   */ 
  public String deckName ()
  {
    return deckName;
  }
  
  /**
   * This method is used to read a Deck from a file.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>s </b> This variable is used as reference to the Scanner class.
   * <p>
   * <b>numCards </b> The number of Cards in the Deck in the file.
   * <p>
   * <b>deck </b> The Deck that will be created from the file.
   * @param fileName the name of the file that contains the Deck.
   * @return Deck the Deck that is read from the file.
   * @throws IOException when there is a problem reading from a file.
   */ 
  public static Deck deckFromFile (String fileName) throws java.io.IOException
  {
    Scanner s = new Scanner (new File (".//resources/"+fileName));
    int numCards = s.nextInt();
    Deck deck = new Deck (fileName);
    for (int x = 0; x < numCards; x++)
      deck.add (new Card (s.nextInt(),new Sprite(s.next())));
    numCards = s.nextInt();
    for (int x = 0; x < numCards; x++)
      deck.add (new Card (s.next(), new Sprite (s.next())));
    s.close();
    return deck;
  }
  
  /**
   * This method is used to write a Deck to a file.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>out </b> This variable is used as reference to the PrintWriter class used to write to a file.
   * @param fileName the name of the file that this method will write to.
   * @throws IOException when there is a problem writing to the file.
   */ 
  public void deckToFile (String fileName) throws java.io.IOException
  {
    PrintWriter out = new PrintWriter (new FileWriter(new File (".//resources/"+fileName)));
    out.println(numbers.size());
    for (int x = 0; x < numbers.size(); x++)
      out.println(numbers.get(x).getValue() + " " + numbers.get(x).cardImage().imageName());
    out.println(operators.size());
    for (int x = 0; x < operators.size(); x++)
      out.println(operators.get(x).getOperator() + " " + operators.get(x).cardImage().imageName());
    out.close();
  }
  
  /**
   * This method is used to deal a specified number of Cards that are operators.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>hand </b> The random Cards that will be dealt in the player's hand from the Deck. 
   * <p>
   * <b>rand </b> A random number that will be used to randomly choose an index from the Deck for the player's hand.
   * @param numCards the number of Cards that will be dealt.
   * @return Card[] the array of Cards to be dealt that are operators.
   */ 
  public Card [] dealOperators (int numCards)
  {
    Card [] hand = new Card [numCards];
    int rand;
    
    if (numCards > tempOperators.size())
    {
      tempOperators.clear();
      for (int x = 0; x < operators.size(); x++)
        tempOperators.add(operators.get(x));
    }
    
    for (int x = 0; x < numCards && x < operators.size(); x++)
    {
      rand = (int)(Math.random()*(tempOperators.size()));
      hand [x] = (tempOperators.get(rand));
      tempOperators.remove (rand);
    }
    return hand;
  }
  
  /**
   * This method is used to deal a specified number of Cards that are numbers.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>hand </b> The random Cards that will be dealt in the player's hand from the Deck. 
   * <p>
   * <b>rand </b> A random number that will be used to randomly choose an index from the Deck for the player's hand.
   * @param numCards the number of Cards that will be dealt.
   * @return Card[] the array of Cards to be dealt that are numbers.
   */ 
  public Card [] dealNumbers (int numCards)
  {
    Card [] hand = new Card [numCards];
    int rand;
    
    if (numCards > tempNumbers.size())
    {
      tempNumbers.clear();
      for (int x = 0; x < numbers.size(); x++)
        tempNumbers.add(numbers.get(x));
    }
    
    for (int x = 0; x < numCards && x < numbers.size(); x++)
    {
      rand = (int)(Math.random()*(tempNumbers.size()));
      hand [x] = tempNumbers.get(rand);
      tempNumbers.remove(rand);
    }
    return hand;
  }
  
  /**
   * This method is used to count the number of occurences of the Cards of a specified value.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>count </b> The number of occurences of Cards of a specified value.
   * @param value the value to be searched for.
   * @return int the number of occurences of the specified Ccard.
   */ 
  public int countNumbers (int value)
  {
    int count = 0;
    for (int x = 0; x < numbers.size(); x ++)
    {
      if (numbers.get(x).getValue() == value)
        count ++;
    }
    return count;
  }
  
  /**
   * This method is used to count the number of occurences of the Cards of a specified operator.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>count </b> The number of occurences of Cards of a specified operator.
   * @param operator the operator to be searched for.
   * @return int the number of occurences of the specified operator.
   */ 
  public int countOperators (String operator)
  {
    int count = 0;
    for (int x = 0; x < operators.size(); x ++)
    {
      if (operators.get(x).getOperator().equals(operator))
        count ++;
    }
    return count;
  }
  
  /**
   * This method is used to return the numbers that are available in the Deck.
   * @return ArrayList the list of Cards that are numbers.
   */ 
  public ArrayList <Card> getNumbers ()
  {
    return tempNumbers;
  }
  
  /**
   * This method is used to return the operators that are available in the Deck.
   * @return ArrayList the list of Cards that are operators.
   */ 
  public ArrayList <Card> getOperators ()
  {
    return tempOperators;
  }
  
  /**
   * This method is used to add a card to the Deck.
   * @param card the Card that will be added to the Deck.
   */ 
  public void add (Card card)
  {
    if (!card.isOperator())
    {
      numbers.add (card);
      tempNumbers.add (card);
    }
    else 
    {
      operators.add (card);
      tempOperators.add (card);
    }
  }
  
  /**
   * This method returns the operators and values of the Deck as a String.
   * @return String the values and operators of the Deck.
   */ 
  @Override
  public String toString()
  {
    return "Numbers: "+ getNumbers() + " Operators: "+getOperators();
  }
  
}