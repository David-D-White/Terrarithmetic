import java.awt.*;
import java.io.*;
/**
 * The SplashScreen for the program.
 * <p>
 * SplashScreen
 * <p>
 * Date: 2016/06/2
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/2
 */ 
public class SplashScreen extends Screen
{
  /**This is the Entity used in the SplashScreen.*/
  private Entity player = new Entity ("Character3",10,10); 
  /**This holds the frameCount of the Entity.*/
  private int frameCount;
  /**This holds the x coordinate of the map.*/
  private int mapX;
  /**This holds the y coordinate of the map.*/
  private int mapY;
  /**This is a Sprite used for the splash screen.*/
  private Sprite ground;
  /**This is a Sprite used for the splash screen.*/
  private Sprite groundHorizontal;
  /**This is a Sprite used for the splash screen.*/
  private Sprite groundCorner1;
  /**This is a Sprite used for the splash screen.*/
  private Sprite groundCorner0;
  /**This is a Sprite used for the splash screen.*/
  private Sprite grass;
  /**This is a Sprite used for the splash screen.*/
  private Sprite modernHouse;
  /**This is a Sprite used for the splash screen.*/
  private Sprite blackHouse;
  /**This is a Sprite used for the splash screen.*/
  private Sprite magicBack;
  /**This is the Door that the Entity comes out of.*/
  private Sprite companyLogo;
  /**This is the Door that the Entity comes out of.*/
  private Sprite bushSet;
  /**This is the Door that the Entity comes out of.*/
  private Sprite bushSet2;
  /**This is the Door that the Entity comes out of.*/
  private Sprite lamp;
  /**This is the Door that the Entity comes out of.*/
  private Sprite sign;
  /**This is the Door that the Entity exits through every screen.*/
  private Door closeDoor;
  /**This is the Door that the Entity enters through every screen.*/
  private Door door;
  /**This stores the current map that the Entity is on.*/
  private int mapNum;
  /**This is used for positioning and delays for the final screen.*/
  private int y;
  /**This is used as delay for the company screen.*/
  private int delay;
  /**This is used as reference to TerrarithmeticApp to start the SplashScreen.*/
  private TerrarithmeticApp frame;
  
  /**
   * This constructs the SplashScreen. 
   * @param frame used as reference to TerrarithmeticApp to start the SplashScreen.
   */
  public SplashScreen (TerrarithmeticApp frame)
  {
    super ("SplashScreen");
    this.frame = frame;
    try
    {
      sign = new Sprite ("splashSign.png");
      lamp = new Sprite ("lampy.png");
      bushSet2 = new Sprite ("bushset2.png");
      bushSet = new Sprite ("bushset.png");
      companyLogo = new Sprite ("Company Logo.png");
      magicBack = new Sprite ("magicback.png");
      blackHouse = new Sprite ("bighouse.png");
      modernHouse = new Sprite ("modernHouse.png");
      grass = new Sprite ("grass3.png");
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
   * This method controls player's speed.
   * @return int the speed of the player frame.
   */
  private int playerSpeed (int map,int direction)
  {
    if (mapNum==0 || mapNum==3)
      return map + direction;
    return map+ direction*4;
  }
  /**
   * This method controls the processing in the SplashScreen.
   */
  public void tick ()
  {
    if (delay >=750)
    {
      if (mapNum == 3 && mapY ==300 && y < 300)
        y++;
      if (mapNum!=3 || mapNum ==3 && mapY >300)
      {
        frameCount++;
        if (mapNum >0 && mapNum < 3)
        {
          if (frameCount % 10 == 0)
            player.nextFrame();
        }
        else
        {
          if (frameCount %15==0)
            player.nextFrame();
        }
        if (frameCount == 60)
          frameCount = 0;
        if (player.direction()== Entity.EAST)
          mapX = playerSpeed (mapX, 1);
        else if (player.direction() == Entity.SOUTH)
        {
          mapY = playerSpeed (mapY,1);
        }
        else if (player.direction() == Entity.NORTH)
          mapY = playerSpeed (mapY, -1);
        else 
        {
          if (player.direction() == Entity.WEST)
            mapX = playerSpeed (mapX, -1);
        }
        if (mapY == 100)
        {
          player.changeDirection (Entity.EAST);
          
          if (!closeDoor.isOpen())
            closeDoor.nextFrame();
        }
        if (mapX == 448  && mapY <= 100 )
        {
          player.changeDirection (Entity.NORTH);
          if (!door.isOpen())
            door.nextFrame();
        }
        if (mapY < -20)
        {
          mapNum++;
          mapX= 448;
          mapY = 536;
          try
          {
            door = new Door (Tile.loadAnim ("moderndoor"), null,10,10);
          }
          catch (IOException e)
          {
          }
        }
      }
    }
    delay+=2;
  }
  
  /**
   * The first screen in the splash screen.
   * @param g2D used as reference to Graphics2D for drawing images.
   */
  public void screen1(Graphics2D g2D)
  {
    for (int x = 0; x < 7; x++)
    {
      g2D.drawImage (groundHorizontal.image(), 64*x, 128, null);
      if (x<2)
        g2D.drawImage (ground.image(), 0, 64*x, null);
    }
    g2D.drawImage (groundCorner0.image(), 0, 128, null);
    g2D.drawImage (groundCorner1.image(), 448, 128, null);
    g2D.drawImage (ground.image(), 448, 64, null);
    g2D.drawImage (ground.image(), 448, 0, null);
    g2D.drawImage (magicBack.image(), 0,0,null);
    g2D.drawImage (magicBack.image(), 448, 0, null);
  }
  
  /**
   * The second screen in the splash screen.
   * @param g2D used as reference to Graphics2D for drawing images.
   */
  private void screen2 (Graphics2D g2D)
  {
    for (int x = 0; x < 17; x++)
    {
      for (int y = 0; y < 10; y++)
      {
        g2D.drawImage (grass.image(), 64*x, 64*y,null);
      }
    }
    sideWalk (g2D);
    g2D.drawImage (bushSet.image(), 550,50,null);
    g2D.drawImage (bushSet2.image(), 26,50,null);
  }
  
  /**
   * The third screen in the splash screen.
   * @param g2D used as reference to Graphics2D for drawing images.
   */
  private void screen3 (Graphics2D g2D)
  {
    for (int x = 0; x < 17; x++)
    {
      for (int y = 0; y < 10; y++)
      {
        g2D.drawImage (grass.image(), 64*x, 64*y,null);
      }
    }
    sideWalk (g2D);
    for (int x = 0; x< 10; x+=4)
    {
      g2D.drawImage (lamp.image(), 384 ,30+(64*x), null);
      g2D.drawImage (lamp.image(), 512 ,30+(64*x), null);
    }
    g2D.drawImage (modernHouse.image(), 50,100, null);
    g2D.drawImage (blackHouse.image(), 600,200,null);
    g2D.drawImage (sign.image(), 620, 490,null);
  }
  
  /**
   * The fourth screen in the splash screen.
   * @param g2D used as reference to Graphics2D for drawing images.
   */
  private void screen4 (Graphics2D g2D)
  {
    font (g2D, 75);
    g2D.drawString ("Terrarithmetic", 150,0+y);
    if (frame.audio.trackPosition("MenuTheme") >= 40500000)
      frame.mainMenu(TerrarithmeticApp.SPLASH_SCREEN);
  }
  
  /**
   * The first screen (company screen) in the splash screen.
   * @param g2D used as reference to Graphics2D for drawing images.
   */
  private void companyScreen (Graphics2D g2D)
  {
    font (g2D, 30);
    g2D.drawImage (companyLogo.image(), 400,200,null);
    if (delay <350)
    {
      if (delay>=250)
        g2D.drawString ("A Pixelinx Project...", 550, 550);
      else
        g2D.drawString ("A Pixelinx Project...", 800-delay, 550);
    }
    else
    {
      if (delay >=350)
      {
        if (delay <=600)
        g2D.drawString ("Presenting...", 1000-(delay-350),550);
        else
          g2D.drawString ("Presenting...", 750,550);
      }
    }
  }
  
  /**
   * This method is used to set the Font of the game.
   * @param g2D used as reference to Graphics2D for drawing images.
   * @param size the size of the Font.
   */
  private void font (Graphics2D g2D, int size)
  {
    Font font = new Font("Minecraftia", Font.PLAIN, size);
    g2D.setFont(font);
    g2D.setColor (Color.WHITE);
  }
  
  /**
   * This side walk used in every screen.
   * @param g2D used as reference to Graphics2D for drawing images.
   */
  private void sideWalk (Graphics2D g2D)
  {
    for (int x = 0; x < 10; x++)
    {
      g2D.drawImage (ground.image(), 448, 64*x, null);
    }
    g2D.drawImage (ground.image(), 448, 0, null);
    g2D.drawImage (magicBack.image(), 448, 0, null);
  }
  
  /**
   * This method refreshes and updates the graphics on the screen.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   * @param g used as reference to Graphics for drawing images.
   */
  public void refresh (Graphics g)
  {
    Graphics2D g2D = (Graphics2D) g;
    g2D.setBackground (new Color (0,0,0,0));
    g2D.clearRect(0,0,1000,600);
    if (delay <750)
    {
      companyScreen (g2D);
    }
    else if (mapNum == 0)
    {
      screen1 (g2D);
      g2D.drawImage (closeDoor.retrieveFrame ().image(), 0,0,null);
    }
    else if (mapNum == 1)
      screen2 (g2D);
    else if (mapNum ==2)
      screen3 (g2D);
    else
    {
      if (mapNum ==3)
      {
        screen4 (g2D);
      }
    }
    if (delay >= 750)
    {
      g2D.drawImage (player.retrieveFrame().image(), mapX,mapY,null);
      if (mapNum < 3)
        g2D.drawImage (door.retrieveFrame().image(), 448,0,null);
    }
  }
}