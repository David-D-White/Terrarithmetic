import java.awt.*;
import java.io.*;
/**
 * This is the main menu that will initiate the game.
 * <p>
 * EndScreen
 * <p>
 * Date: 2016/06/04
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/04
 */
public class EndScreen extends Screen
{
  /**This is the Entity used in the SplashScreen.*/
  private Entity player = new Entity ("Character3",10,10); 
  /**This holds the frameCount of the Entity.*/
  private int frameCount;
  /**This holds the x coordinate of the map.*/
  private int mapX=448;
  /**This holds the y coordinate of the map.*/
  private int mapY;
  /**This is the door that will be closed in the End Screen.*/
  private Door closeDoor;
  /**This is a door that will be opened in the End Screen.*/
  private Door door;
  /**This is a Sprite used for the end screen.*/
  private Sprite magicBack;
  /**This is a Sprite used for the end screen.*/
  private Sprite ground;
  /**This is a Sprite used for the end screen.*/
  private Sprite groundHorizontal;
  /**This is a Sprite used for the end screen.*/
  private Sprite groundCorner1;
  /**This is a Sprite used for the end screen.*/
  private Sprite groundCorner0;
  /**This is used for reference to methods in the SplashScreen class.*/
  private SplashScreen splashScreen;
  /**This boolean is used to determine when to show the credits screen.*/ 
  private boolean credits;
  /**This boolean is used as reference to TerrarithmeticApp.*/ 
  private TerrarithmeticApp frame;
  
   /**
   * This constructs the EndScreen. 
   * @param frame used as reference to TerrarithmeticApp to start the EndScreen.
   */
  public EndScreen (TerrarithmeticApp frame)
  {
    super ("EndScreen");
    this.frame = frame;
    splashScreen = new SplashScreen(frame);
    try
    {
      magicBack = new Sprite ("magicback.png");
      ground = new Sprite ("ground.png");
      groundCorner0 = new Sprite ("groundcorner0.png");
      groundHorizontal = new Sprite ("groundhorizontal.png");
      groundCorner1 = new Sprite ("groundcorner1.png");
      door = new Door (Tile.loadAnim ("moderndoor"), null,10,10);
      closeDoor = new Door (Tile.loadAnim ("splashdoor"), null,10,10);
    }
    catch (IOException e)
    {
    }
  }
  
  /**
   * This methods starts the EndScreen.
   */
  public void start()
  {
    frameCount = 0;
    mapX=448;
    mapY = 0;
    player.changeDirection(Entity.SOUTH);
    credits = false;
    super.start();
  }
  
  /**
   * This method controls the processing in the EndScreen.
   */
  public void tick ()
  {
    if (!credits)
    {
      if (mapY == -200)
      {
        mapY = 700;
        credits = true;
      }
      else if (mapY <= -100 && mapY > -200)
      {
        if (door.currentFrame() != 0)
          door.previousFrame();
        mapY --;
      }
      else
      {
        frameCount++;
        if (frameCount % 10 == 0)
          player.nextFrame();
        if (frameCount == 60)
          frameCount = 0;
        if (player.direction()== Entity.EAST)
          mapX++;
        else if (player.direction() == Entity.SOUTH)
          mapY++;
        else if (player.direction() == Entity.NORTH)
          mapY--;
        else 
        {
          if (player.direction() == Entity.WEST)
            mapX--;
        }
        if (mapY == 100)
        {
          player.changeDirection (Entity.WEST);
          if (!closeDoor.isOpen())
            closeDoor.nextFrame();
        }
        if (mapX == 0  && mapY <= 100 )
        {
          player.changeDirection (Entity.NORTH);
          if (!door.isOpen())
            door.nextFrame();
        }
      }
    }
    else
      mapY--;
  }
  
  /**
   * This method is used to draw the credits screen.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>font </b> The font that will be used in the credits screen.
   * @param g2D used as reference to the Graphics2D class for drawing.
   */
  private void creditScreen (Graphics2D g2D)
  {
    Font font = new Font("Minecraftia", Font.PLAIN, 30);
    int y = mapY;
    g2D.setFont (font);
    g2D.setBackground (Color.BLACK);
    g2D.setColor (Color.WHITE);
    g2D.drawString ("---GAME MAKERS---", x("---GAME MAKERS---",font,g2D), y);
    g2D.drawString ("David White", x("David White",font,g2D), y+50);
    g2D.drawString ("Sepehr Hosseini", x("Sepehr Hosseini",font,g2D), y+100);
    g2D.drawString ("---GAME LOGO DESIGNER---", x("---GAME LOGO DESIGNER---", font, g2D), y+200);
    g2D.drawString ("Conributing Designers:", x("Conributing Designers:", font, g2D), y+250);
    g2D.drawString ("Michael Wu", x("Michael Wu", font, g2D), y+300);
    g2D.drawString ("---SPRITES---", x("---SPRITES---", font, g2D), y+400);
    g2D.drawString ("Conributing Artists:", x("Conributing Artists:", font, g2D), y+450);
    g2D.drawString ("Kaliser", x("Kaliser", font, g2D), y+500);
    g2D.drawString ("RedKnightX", x("RedKnightX", font, g2D), y+550);
    g2D.drawString ("LightPA/Niv'", x("LightPA/Niv'", font, g2D), y+600);
    g2D.drawString ("ChaoticCherryCake", x("ChaoticCherryCake", font, g2D), y+650);
    g2D.drawString ("Alistair", x("Alistair", font, g2D), y+700);
    g2D.drawString ("zetavares852", x("zetavares852", font, g2D), y+750);
    g2D.drawString ("WesleyFG", x("WesleyFG", font, g2D), y+800);
    g2D.drawString ("---MUSIC---", x("---MUSIC---", font, g2D), y+900);
    g2D.drawString ("Conributing Musicians:", x("Conributing Musicians:", font, g2D), y+950);
    g2D.drawString ("Toni Leys", x("Toni Leys", font, g2D), y+1000);
    g2D.drawString ("Rikky", x("Rikky", font, g2D), y+1050);
    g2D.dispose();
    if (y == -1050)
      frame.mainMenu(TerrarithmeticApp.CREDITS);
  }
  
  /**
   * This method is used to center the x coordinate of the text.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>screen </b> This is used to create a new Rectangle that specifies the screen dimensions.
   * <p>
   * <b>metrics </b> This is used to obtain the centered x coordinates on the screen.
   * @param g2D used as reference to the Graphics2D class for drawing.
   * @param text the text that will be displayed.
   * @param font the font that is being used.
   */
  private int x(String text, Font font, Graphics2D g2D)
  {
    Rectangle screen = new Rectangle (1000,600);
    FontMetrics metrics = g2D.getFontMetrics(font);
    return (screen.width - metrics.stringWidth(text)) / 2;
  }
  
  /**
   * This method serves as a drawing component and refreshes the screen.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   * @param g used for drawing images onto the screen.
   */ 
  public void refresh(Graphics g)
  {
    Graphics2D g2D = (Graphics2D) g;
    g2D.setBackground (new Color (0,0,0,0));
    g2D.clearRect(0,0,1000,600);
    if (!credits)
    {
      splashScreen.screen1 (g2D);
      g2D.drawImage (closeDoor.retrieveFrame ().image(), 448,0,null);
      g2D.drawImage (player.retrieveFrame().image(), mapX,mapY,null);
      g2D.drawImage (door.retrieveFrame().image(), 0,0,null);
    }
    else
      creditScreen (g2D);
  }
}