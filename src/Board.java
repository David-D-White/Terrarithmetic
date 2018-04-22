import java.io.*;
import java.util.*;
/**
 * The Board class is used for creating the Board that the game will be played on.
 * <p>
 * Board
 * <p>
 * Date: 2016/05/24
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/24
 */
public class Board
{
  
  /**This is an integer value representing the Player.*/
  static final int PLAYER = 0;
  /**This is an integer value representing the Computer.*/
  static final int COMPUTER = 1;
  /**This is an integer value representing the easy level.*/
  static final int EASY = 0;
  /**This is an integer value representing the medium level.*/
  static final int MEDIUM = 1;
  /**This is an integer value representing the hard level.*/
  static final int HARD = 2;
  /**This is an integer value representing difficulty.*/
  public static int difficulty = 0;
  /**This is the index of the current number shown in the player's hand. */
  private int currentNumber = 0;
  /**This is the index of the current operator shown in the player's hand. */
  private int currentOperator = 0;
  /**This is an integer variable that tracks whether it is the computers or players turn.*/
  private int turnNum;
  /** this is an integer variable that tracks the round number.*/
  private int roundNum;
  /**This is an array which holds the values Deck classes for both the player and computer. */
  private Deck [] decks;
  /**This is a 2d array which holds the cards on the board for both the player and computer.*/
  private Card [] [] boardCards;
  /**This is a 2d array which holds the values of the operators in the hands of both the player and the computer.*/
  private Card [] [] handOperators;
  /**This is a 2d array which holds the values of the numbers in the hands of both the player and computer. */
  private Card [] [] handNumbers;
  /**This is an array which holds the point values for both the player and the computer. */
  private int [] points;
  /**This is used to display the time left in a move. */
  private int timerCount=10;
  /**This is used to start and stop the timer. */
  private boolean timerEnabled = true;
  /**This is the timer used to countdown the time left during the player's move. */
  private Timer timer;
  
  /**
   * This constructer creates a new Board which uses the player's deck.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>task </b> Reference to the TimerTask class.
   * @param playerDeck the player's deck that will be used for the entire game on the Board.
   * @param computerDeck the computer's deck that will be used for the entire game on the Board.
   */ 
  public Board(Deck playerDeck, Deck computerDeck)
  {
    decks = new Deck [2];
    boardCards = new Card [2] [3];
    handOperators = new Card [2] [2];
    handNumbers = new Card [2] [3];
    points = new int [2];
    decks[PLAYER] = playerDeck;
    decks[COMPUTER] = computerDeck;
    nextRound();
    dealHand();
    timer = new Timer ();
    TimerTask task = new  TimerTask()
    {
      public void run()
      {
        if (timerCount == 0)
        {
          timerCount = 10;
          points [COMPUTER]++;
        }
        if (timerEnabled)
          timerCount--;
      }
    };
    timer.scheduleAtFixedRate(task, 0, 1000);
  }
  
  
  /**
   * This method is used to return the timer value
   * @return int the current timer value
   */ 
  public int timerValue()
  {
    return timerCount;
  }
  
  /**
   * This method is used to pause the timer.
   */ 
  public void pauseTimer()
  {
    timerEnabled = false;
  }
  
  /**
   * This method is used to resume the timer.
   */ 
  public void resumeTimer()
  {
    timerEnabled = true;
  }
  
  /**
   * This method is used to reset the timer.
   */ 
  public void resetTimer()
  {
    timerCount = 10;
  }
  
  /**
   * This method is used to stop the timer.
   */ 
  public void stopTimer ()
  {
    timer.cancel();
  }
  
  /**
   * This method is used to perform the computer's actions when it is the computer's turn.
   */
  public void computerTurn()
  {
    if (turnNum == COMPUTER)
      computerAttack();
    else
      computerDefend();
  }
 
  /**
   * This method finds an index of the specified operator and returns it or -1 if it cannot find the specified number.
   * @param operator the specified operator to be serached for.
   * @return int the index of the specified operator or -1 if it cannot find the specified number.
   */
  private int findOperator (String operator)
  {
    for (int x = 0; x < handOperators[COMPUTER].length; x++)
    {
      if (handOperators[COMPUTER] [x] != null && handOperators[COMPUTER] [x].getOperator().equals (operator))
        return x;
    }
    return -1;
  }
  
  /**
   * This method searches for a non-null operator.
   * @return int the index of the first non-null operator or -1 if there is no operators.
   */
  private int findOperator ()
  {
    for (int x = 0; x < handOperators[COMPUTER].length; x++)
    {
      if (handOperators[COMPUTER] [x] != null)
        return x;
    }
    return -1;
  }
  
  /**
   * This method returns the index of the highest number.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>max </b> Holds the value of the highest number to be returned.
   * @return int the index of the highest number.
   */
  private int findMaxNum ()
  {
    int max = findNum();
    for (int x = 0; x < handNumbers[COMPUTER].length && max != -1; x++)
    {
      if (handNumbers[COMPUTER] [x] != null && handNumbers[COMPUTER] [x].getValue() > handNumbers[COMPUTER] [max].getValue())
        max = x;
    }
    return max;
  }
  
  /**
   * This method returns the index of the smallest number.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>min </b> Holds the value of the smallest number to be returned.
   * @return int the index of the smallest number.
   */
  private int findMinNum ()
  {
    int min = findNum();
    for (int x = 0; x < handNumbers[COMPUTER].length && min != -1; x++)
    {
      if (handNumbers[COMPUTER] [x] != null && handNumbers[COMPUTER] [x].getValue() < handNumbers[COMPUTER] [min].getValue())
        min = x;
    }
    return min;
  }
  
  /**
   * This method returns the index of the first non-null number or -1 no numbers can be found.
   * @return int the index of the first non-null number or -1 if no numbers can be found.
   */
  private int findNum ()
  {
    for (int x = 0; x < handNumbers[COMPUTER].length; x++)
    {
      if (handNumbers[COMPUTER] [x] != null)
        return x;
    }
    return -1;
  } 
  
  /**
   * This method assigns the cards that the computer will play on the board from the computer's hand when it's attacking.
   */ 
  public void computerAttack()
  {
    if (difficulty != EASY && findOperator ("x") != -1)
      playOperator(COMPUTER,findOperator("x"));
    else if (difficulty != EASY && findOperator ("+") != -1)
      playOperator(COMPUTER,findOperator("+"));
    else
      playOperator(COMPUTER,findOperator());
    
    if (difficulty == HARD)
    {
      playNumber(COMPUTER, findMaxNum(), 0);
      playNumber(COMPUTER, findMaxNum(), 1);
    }
    else
    {
      playNumber(COMPUTER, findNum(), 0);
      playNumber(COMPUTER, findNum(), 1);
    }
  }
  
  /**
   * This method assigns the cards that the computer will play on the board from the computer's hand when it's defending.
   */ 
  public void computerDefend()
  {
    if (difficulty != EASY && findOperator ("/") != -1)
      playOperator(COMPUTER,findOperator("/"));
    else if (difficulty != EASY && findOperator ("-") != -1)
      playOperator(COMPUTER,findOperator("-"));
    else
      playOperator(COMPUTER,findOperator());
    
    if (difficulty == HARD)
      playNumber(COMPUTER, findMinNum(), 1);
    else
      playNumber(COMPUTER, findNum(), 1);
  }
  
  /**
   * This method returns a Deck class based on the integer parameter passed in.
   * @param side this determines if it is the computer's or player's deck.
   * @return Deck the specified deck
   */ 
  public Deck getDeck (int side)
  {
    if (side == COMPUTER || side == PLAYER)
      return decks[side];
    return null;
  }
  
  
  /**
   * This method deals the hands of the player and computer by dealing operators and numbers from their respective decks.
   */ 
  public void dealHand()
  {
    for (int x = 0; x <decks.length; x++)
    {
      handOperators[x] = decks [x].dealOperators(2);
      handNumbers [x] = decks [x].dealNumbers(3);
    }
  }
  
  /**
   * This method returns the integer representation of the current turn.
   * @return int the integer representation of the current turn.
   */ 
  public int turnNum()
  {
    return turnNum;
  }
  
  /**
   * This method returns the current round number.
   * @return int the current round number.
   */ 
  public int roundNum()
  {
    return roundNum;
  }
  
  /**
   * This method prepares the Board for the next turn and changes the turn number.
   */ 
  public void nextTurn()
  {
    clearBoard();
    if (!numbersInHand(PLAYER) && !numbersInHand(COMPUTER) && !operatorsInHand(PLAYER) && !operatorsInHand(COMPUTER))
    {
      nextRound();
      return;
    }
    if (turnNum == PLAYER)
      turnNum = COMPUTER;
    else
      turnNum = PLAYER;
  }
  
  /**
   * This method prepares the Board for the next round and increments the round number.
   */ 
  private void nextRound()
  {
    dealHand();
    turnNum = (int) (Math.random() * 2);
    roundNum ++;
  }
  
  /**
   * This method clears the Board by setting the values of the Cards on the Board to null.
   */ 
  public void clearBoard()
  {
    for (int x = 0; x < boardCards.length;x++)
    {
      for (int y = 0; y < boardCards[1].length; y++)
        boardCards [x] [y] = null;
    }
  }
  
  /**
   * This method returns the points of the specified side.
   * @param side whether it is the computer or player.
   * @return int the points of the specified side.
   */ 
  public int getPoints(int side)
  {
    if (side == COMPUTER || side == PLAYER)
      return points [side];
    return -1;
  }
  
  
  /**
   * This method updates the score of the computer and the player when a set of cards are entered. 
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>score </b> This variable holds the resulting score change for the round. 
   */
  public void updateScore ()
  {
    int score = 0;
    if (turnNum == PLAYER)
    {
      score = getDefend (boardCards[PLAYER], boardCards[COMPUTER]);
      if (score < 0 )
        points[COMPUTER] += score*-1;
      else
        points[PLAYER]+=score;
    }
    else
    {
      score = getDefend(boardCards[COMPUTER], boardCards[PLAYER]);
      if (score < 0 )
        points[PLAYER] += score*-1;
      else
        points [COMPUTER]+=score;
    }
  }
  
  /**
   * This method calculates the value of the player or computer's attack.
   * @param board this is used to distinguish between the cards of the player or the computer when attacking. 
   * @return int the amount of points that results from the exchange.
   */
  private int getAttack(Card[]board)
  {
    if (board[1].getOperator().equals ("+"))
      return board[0].getValue() + board[2].getValue();
    else if (board[1].getOperator().equals ("-"))
      return board[0].getValue() - board[2].getValue();
    else if (board[1].getOperator().equals ("x"))
      return board[0].getValue() * board[2].getValue();
    else
      return board [0].getValue() / board [2].getValue();
  }
  
  /**
   * This method calculates the value of the player or computer's defend.
   * @param attackBoard this is used to distinguish between the cards of the player or the computer when attacking. 
   * @param defendBoard this is used to distinguish between the cards of the player or the computer when defending. 
   * @return int the amount of points that results from the exchange.
   */
  public int getDefend (Card[]attackBoard, Card[]defendBoard)
  {
    if (defendBoard[1].getOperator().equals ("+"))
      return getAttack(attackBoard) + defendBoard[2].getValue();
    else if (defendBoard [1].getOperator().equals ("-"))
      return getAttack(attackBoard) - defendBoard[2].getValue();
    else if (defendBoard[1].getOperator().equals ("x"))
      return getAttack(attackBoard)* defendBoard [2].getValue();
    else
      return getAttack (attackBoard)/defendBoard [2].getValue();
  }
  
  
  /**
   * This method is used to play an operator onto the Board from the current Card in the player's or computer's hand.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>temp</b> This variable is used to set the index of the current operator in the hand back to the value of the Cards on the board after a new board value has been set.
   * @param side whether it is the computer or player.
   * @param index the index to be played at.
   */ 
  public void playOperator(int side, int index)
  {
    Card temp = boardCards [side] [1];
    boardCards [side] [1] = handOperators [side] [index];
    handOperators [side] [index] = temp;
    nextOperator ();
  }
  
  /**
   * This method is used to play a number onto the Board from the current Card in the player's hand depending on whether it is the first number or the second number in the sequence.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>temp</b> This variable is used to set the index of the current number in the hand back to the value of the Cards on the board after a new board value has been set.
   * @param location this determines whether it is the first or second number in the sequence to be placed onto the Board.
   * @param side whether it is the computer or player.
   * @param index the index to be played at.
   */
  public void playNumber (int side, int index, int location)
  {
    Card temp;
    if (location == 0)
    {
      temp = boardCards [side] [0];
      boardCards [side] [0] = handNumbers [side] [index];
      handNumbers [side] [index] = temp;
      nextNumber ();
    }
    else if (location == 1)
    {
      temp = boardCards [side] [2];
      boardCards [side] [2] = handNumbers [side] [index];
      handNumbers [side] [index] = temp;
      nextNumber ();
    }
  }
  
  /**
   * This method returns the cards on the board for the specified side.
   * @param side the specified side to retrieve the cards from.
   * @return Card[] the cards on the specified side's Board.
   */
  public Card [] getBoard (int side)
  {
    return boardCards [side];
  }
  
  /**
   * This method is used to return a Card on a specified location on the specified side's board based on it's index.
   * @param location the index used to determine the location of the Card on the specified side.
   * @param side the specified side to retrieve the Card from.
   * @return Card the Card at the specified location.
   */
  public Card getBoardCard (int side, int location)
  {
    if (location >= boardCards[side].length)
      return null;
    return boardCards [side] [location];
  }
  
  /**
   * This method is used to return the index of the current number.
   * @return int the index of the current number card.
   */
  public int currentNumber()
  {
    return currentNumber;
  }
  
  /**
   * This method is used to return the index of the current operator.
   * @return int the current operator.
   */
  public int currentOperator ()
  {
    return currentOperator;
  }
  
  /**
   * This method is used to return the current operator.
   * @param side whether it is the computer or player.
   * @param index the index to be played at.
   * @return Card the current operator.
   */
  public Card getOperator (int side, int index)
  {
    if (index > handOperators [side].length)
      return null;
    return handOperators [side] [index];
  }
  
  /**
   * This method is used to return the current number.
   * @param side whether it is the computer or player.
   * @param index the index to be played at.
   * @return Card the current number.
   */
  public Card getNumber(int side, int index)
  {
    if (index > handNumbers [side].length)
      return null;
    return handNumbers [side] [index];
  }
  
  /**
   * This method is used to determine whether or not the specified side has any numbers in their hand.
   * @param side whether it is the computer or player.
   * @return true if the specified side has any number cards in their hand
   */
  public boolean numbersInHand (int side)
  {
    for (int x = 0; x < handNumbers[side].length; x++)
    {
      if (handNumbers [side] [x] != null)
        return true;
    }
    return false;
  }
  
  /**
   * This method is used to determine whether or not the specified side has any operators in their hand.
   * @param side whether it is the computer or player.
   * @return true if the specified side has any operators in their hand.
   */
  public boolean operatorsInHand(int side)
  {
    for (int x = 0; x < handOperators[side].length; x++)
    {
      if (handOperators [side] [x] != null)
        return true;
    }
    return false;
  }
  
  /**
   * This method is used to determine whether or not the player has all the essential Cards on the Board before pressing enter.
   * @return true if the player has all the essential Cards on the Board.
   */
  public boolean isValidMove ()
  {
    for (int x = turnNum; x < boardCards [PLAYER].length; x++)
    {
      if (boardCards [PLAYER] [x] == null)
        return false;
    }
    return true;
  }
  
  /**
   * This method is used to move through the numbers in the player's hand.
   */
  public void nextNumber()
  {
    if (currentNumber < handNumbers[PLAYER].length -1)
      currentNumber++;
    else
      currentNumber = 0;
    if (handNumbers [PLAYER] [currentNumber] == null && numbersInHand(PLAYER))
      nextNumber();
  }
  
  /**
   * This method is used to move through the operators in the player's hand.
   */
  public void nextOperator()
  {
    if (currentOperator < handOperators [PLAYER].length -1)
      currentOperator++;
    else
      currentOperator = 0;
    if (handOperators [PLAYER] [currentOperator] == null && operatorsInHand(PLAYER))
      nextOperator();
  }
}