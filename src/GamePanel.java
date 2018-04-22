import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
/**
 * The GamePanel class is the driver class for the card game.
 * <p>
 * GamePanel
 * <p>
 * Date: 2016/05/24
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/24
 */ 
public class GamePanel extends Screen
{ 
  /**This is the Sprite that will be used in the background of the card game. */
  private Sprite background;
  /**This is the Sprite that will be pressed in order to place the first number onto the board. */
  private Sprite placeNumber;
  /**This is the Sprite that will be pressed in order to place the operator onto the board. */
  private Sprite placeOperator;
  /**This is the Sprite that will be pressed in order to place the second number onto the board. */
  private Sprite placeNumber2;
  /**This is the Sprite that will be pressed in order to submit the cards played onto the board. */
  private Sprite enter;
  /**This is the Sprite used when there are no remaining cards in the player's hand. */
  private Sprite blank;
  /**This is the Sprite that will be used to block off the first number when the player is in a defending position during the card game.*/
  private Sprite block;
  /**This is used as a pointer to the BufferedMouse.*/
  private BufferedMouse bm;
  /**This is the frame that the GamePanel will be displayed on.*/
  private TerrarithmeticApp frame;
  /**A font used to draw Strings.*/
  private Font font;
  /**A font used to draw Strings.*/
  private Font fontLarge;
  /**A font used to draw Strings.*/
  private Font fontXL;
  /**Reference to the specified Player.*/
  private Player player;
  /**Reference to the specified CardGameNPC.*/
  private CardGameNPC opponent;
   /**This variable stores a Sprite used behind text.*/
  private Sprite textBack;
  /**This variable stores a Sprite used behind text.*/
  private Sprite textBack2;
  
  /**This is the board that the game will be played on. */
  private Board board;
  /**This variable stores whether or not the Board has been cleared. */
  private boolean cleared = true;
  /**This variable stores whether or not the game is over. */
  private boolean gameOver;
  
  /**
   * This constructor is used as a driver class for the card game, which creates a new Board that the game will be played on, and add MouseListener to the class.
   * @param frame the frame that the GamePanel will be displayed on.
   */ 
  public GamePanel(TerrarithmeticApp frame)
  {
    super("CardGame");
    this.frame = frame;
    font = new Font("Minecraftia", Font.PLAIN, 16);
    fontLarge = new Font("Minecraftia", Font.PLAIN, 22);
    fontXL = new Font("Minecraftia", Font.PLAIN, 80);
    bm = new BufferedMouse();
    addMouseListener(bm);
    try{
      textBack2 = new Sprite ("textBack2.png");
      textBack = new Sprite ("textBack.png");
      block = new Sprite ("block.png");
      background = new Sprite ("back.png");
      placeNumber = new Sprite ("placenumber.png");
      placeOperator = new Sprite ("placeoperator.png");
      enter = new Sprite ("enter.png");
      blank = new Sprite ("blank.png");
    }
    catch (IOException e)
    {
    }
    background.scaleImage(0.45, 0.48);
  }
  
  /**
   *This method starts the game with the specified Player and Computer.
   * @param player reference to the Player.
   * @param opponent reference to the opponent.
   */ 
  public void start (Player player, CardGameNPC opponent)
  {
    this.player = player;
    this.opponent = opponent;
    board = new Board (player.deck(), opponent.deck());
    cleared = true;
    gameOver = false;
    start();
  }
  
  /**
   *This method stops the card game.
   */ 
  public void stop ()
  {
    board.stopTimer();
    opponent.setBattled(true);
    if (board.getPoints(Board.COMPUTER) > board.getPoints (Board.PLAYER))
      player.addLoss();
    else
      player.addWin();
    this.player = null;
    this.opponent = null;
    super.stop();
  }
  
  /**
   *This method does the processing of the game.
   */ 
  public void tick()
  {
    if (cleared)
    {
      if (bm.xCoord () >= 860 && bm.xCoord () <= 988 && bm.yCoord() >=20 && bm.yCoord() <=148)
        board.nextNumber();
      else if (bm.xCoord () >= 860 && bm.xCoord () <= 988 && bm.yCoord() >=160 && bm.yCoord() <=288)
        board.nextOperator();
      else if (bm.xCoord () >= 860 && bm.xCoord () <= 988 && bm.yCoord() >=300 && bm.yCoord() <=376)
      {
        if (board.turnNum() == Board.PLAYER)
          board.playNumber (Board.PLAYER,board.currentNumber(), 0);
      }
      else if (bm.xCoord () >= 860 && bm.xCoord () <= 988 && bm.yCoord() >=388 && bm.yCoord() <=464)
      {
        if (board.operatorsInHand(Board.PLAYER))
          board.playOperator(Board.PLAYER, board.currentOperator());
      }
      else if (bm.xCoord () >= 860 && bm.xCoord () <= 988 && bm.yCoord() >=476 && bm.yCoord() <=552)
      {
        if (board.numbersInHand(Board.PLAYER))
          board.playNumber (Board.PLAYER, board.currentNumber(),1);
      }
      else if (bm.xCoord () >= 680 && bm.xCoord () <= 808 && bm.yCoord() >=400 && bm.yCoord() <=528 && board.isValidMove())
      {
        cleared = false;
        board.computerTurn();
        board.updateScore();
        board.pauseTimer();
        board.resetTimer();
      }
    }
    else
    {
      if (bm.xCoord () >= 680 && bm.xCoord () <= 808 && bm.yCoord() >=400 && bm.yCoord() <=528)
      {
        if (gameOver)
          frame.switchOpenWorld(TerrarithmeticApp.GAME_PANEL);
        else
        {
          if (board.getPoints (Board.PLAYER) >= 50 || board.getPoints (Board.COMPUTER) >= 50)
            gameOver = true;
          else
          {
            board.nextTurn();
            board.resumeTimer();
            cleared = true;
          }
        }
      }
    }
    bm.refreshMouse();
  }
  
  /**
   * This method serves as a drawing component for the GamePanel class and refreshes the screen.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   * @param g used for drawing images onto the screen.
   */ 
  public void refresh (Graphics g)
  {
    Graphics2D g2D = (Graphics2D) g;
    g2D.setBackground(Color.BLACK);
    g2D.clearRect ( 0, 0, 1000,600);
    g2D.drawImage (background.image(), 0, 0, null);
    g2D.drawImage (textBack.image(), 230,215,null);
    g2D.setFont(font);
    g2D.setColor(Color.WHITE);
    g2D.drawString("Timer:"+board.timerValue(),700,400);
    g2D.drawString("Your Score:"+board.getPoints(Board.PLAYER),680,60);
    g2D.drawString("Rival Score:"+board.getPoints(Board.COMPUTER),680,80);
    g2D.drawString("Round:"+board.roundNum(),680,100);
    if (board.timerValue() == 0)
    {
      g2D.setColor(Color.RED);
      g2D.drawString("+1",820,80);
    }
    g2D.setFont(fontLarge);
    g2D.setColor(Color.WHITE);
    if (cleared)
    {
      g2D.setColor(Color.BLACK);
      if (board.turnNum() == Board.COMPUTER)
        g2D.drawString("Rival Turn! Defend!",295,300);
      else
        g2D.drawString("Your Turn! Attack!",300,300);
    }
    else if (gameOver)
    {
      g2D.setColor(Color.BLACK);
      g2D.setFont(fontXL);
      if (board.getPoints(Board.COMPUTER) > board.getPoints(Board.PLAYER))
      {
        g2D.drawImage (textBack2.image(), 170,215,null);
        g2D.drawString("Rival wins!",195,360);
      }
      else 
        g2D.drawString("You win!",255,360);
    }
    else
    {
      g2D.setColor(Color.BLACK);
      int points = 0;
      if (board.turnNum() == Board.COMPUTER)
        points = board.getDefend(board.getBoard(Board.COMPUTER),board.getBoard(Board.PLAYER));
      else
        points = board.getDefend(board.getBoard(Board.PLAYER),board.getBoard(Board.COMPUTER));
      if (board.turnNum() == Board.COMPUTER && points >= 0 || board.turnNum() == Board.PLAYER && points < 0)
        g2D.drawString("Rival gained " + Math.abs(points) + " point(s)!",275,300);
      else 
        g2D.drawString("You gained " + Math.abs(points) + " point(s)!",275,300);
    }
    if (!board.numbersInHand(Board.PLAYER))
      g2D.drawImage (blank.image(),860,20,null);
    else
      g2D.drawImage (board.getNumber(Board.PLAYER, board.currentNumber()).cardImage().image(), 860,20,null);
    if (!board.operatorsInHand(Board.PLAYER))
      g2D.drawImage (blank.image(),860,160,null);
    else
      g2D.drawImage (board.getOperator(Board.PLAYER, board.currentOperator()).cardImage().image(), 860,160,null);
    g2D.drawImage (placeNumber.image(), 860, 300, null);
    g2D.drawImage (placeOperator.image(), 860,388,null);
    g2D.drawImage (placeNumber.image(), 860, 476, null);
    g2D.drawImage (enter.image(), 680,400,null);
    if (board.turnNum() == Board.COMPUTER)
      g2D.drawImage (block.image(), 50,400,null);
    for (int x = 0; x < 3; x++)
    {
      if (board.getBoardCard (Board.PLAYER,x) != null)
        g2D.drawImage(board.getBoardCard(Board.PLAYER,x).cardImage().image(),50 + 210*x,400,null);
    }
    for (int x = 0; x < 3; x++)
    {
      if (board.getBoardCard (Board.COMPUTER,x) != null)
        g2D.drawImage(board.getBoardCard(Board.COMPUTER,x).cardImage().image(),50 + 210*x,50,null);
    }
  }
}
