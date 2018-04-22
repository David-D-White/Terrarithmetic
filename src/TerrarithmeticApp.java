import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.util.*;
/**
 * The TerrarithmeticApp class serves as a driver class for the game. 
 * <p>
 * TerrarithmeticApp
 * <p>
 * Date: 2016/05/6
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/6
 */ 
public class TerrarithmeticApp extends JFrame implements ActionListener
{
  /**This is a CardLayout which holds all of the panels involved in the game.*/
  private CardLayout panels;
  /**This is the JPanel which is used to show the CardLayout.*/
  private JPanel screenView;
  /**This is the OpenWorldPanel which handles the open world aspect of the game.*/
  private OpenWorldPanel openWorld;
  /**This is the GamePanel which handles the card game aspect of the game.*/
  private GamePanel gamePanel;
  /**This is the MenuPanel which handles the main menu.*/
  private MenuPanel menuPanel;
  /**This is the SplashScreen which handles the introduction animation.*/
  private SplashScreen splash;
  /**This is the EndScreen which handles the credits.*/
  private EndScreen end;
  /**This is the BufferedKeyboard object which handles keyboard input.*/
  static BufferedKeyboard bk;
  /**This handles audio output.*/
  static AudioHandler audio;
  /**This is a HighScoreList class which handles the sorting of highscores.*/
  static HighScoreList highscores;
  /**This is the width of the window in pixels.*/
  static final int WINDOW_WIDTH = 1000;
  /**This is the height of the window in pixels.*/
  static final int WINDOW_HEIGHT = (int) (WINDOW_WIDTH * 0.62);
  /**This is the String value used to reference the OpenWorldPanel.*/
  static final String OPEN_WORLD = "OpenWorld";
  /**This is the String value used to reference the GamePanel.*/
  static final String GAME_PANEL = "GamePanel";
  /**This is the String value used to reference the MenuPanel.*/
  static final String MENU_PANEL = "MenuPanel";
  /**This is the String value used to reference the SplashScreen.*/
  static final String SPLASH_SCREEN = "SplashScreen";
  /**This is the String value used to reference the credits.*/
  static final String CREDITS = "credits";
  
  
  /**
   * This constructor creates all the components of the game, links them together, and adds them to the main card layout.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>quitItem </b> This is used to create a quit item in the menu bar.
   * <p>
   * <b>aboutItem </b> This is used to create an about item in the menu bar.
   * <p>
   * <b>fileMenu </b> This is used to create the file menu bar.
   * <p>
   * <b>helpMenu </b>  This is used to create the help menu bar.
   * <p>
   * <b>myMenus </b>  This is used to create the menu bar.
   */
  public TerrarithmeticApp ()
  {
    super ("Terrarithmetic");
    audio = new AudioHandler();
    audio.addTrack("MenuTheme");
    audio.addTrack("OpenWorld");
    audio.addTrack("CardGame");
    highscores = new HighScoreList();
    try 
    {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(".//Resources/Minecraftia-Regular.ttf")));
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
    } 
    catch(FontFormatException e) 
    {
      e.printStackTrace();
    }
    
    bk = new BufferedKeyboard ();
    addKeyListener (bk);
    
    panels = new CardLayout ();
    screenView = new JPanel (panels);
    
    gamePanel = new GamePanel (this);
    menuPanel = new MenuPanel (this);
    openWorld = new OpenWorldPanel (this);
    splash = new SplashScreen (this);
    end = new EndScreen (this);    
    
    screenView.add(openWorld,OPEN_WORLD);
    screenView.add(gamePanel, GAME_PANEL);
    screenView.add(menuPanel, MENU_PANEL);
    screenView.add(splash, SPLASH_SCREEN);
    screenView.add(end, CREDITS);
    
    try
    {
      setIconImage ((new Sprite ("Terrarithmetic.png")).image());
    }
    catch (IOException e)
    {
    }           
    //-------------------------------MENU BAR----------------------------------------------- 
    JMenuItem quitItem = new JMenuItem ("Quit");
    JMenuItem aboutItem = new JMenuItem ("About");
    JMenuItem helpFile = new JMenuItem ("Help File");
    
    JMenu fileMenu = new JMenu ("File");
    JMenu helpMenu = new JMenu ("Help");
    
    fileMenu.add (quitItem);
    helpMenu.add (aboutItem);
    helpMenu.add (helpFile);
    
    JMenuBar myMenus = new JMenuBar ();
    
    myMenus.add (fileMenu);  
    myMenus.add (helpMenu);
    
    setJMenuBar (myMenus);
    
    quitItem.addActionListener (this);
    aboutItem.addActionListener (this);
    helpFile.addActionListener (this);
    //-------------------------------WINDOW FORMAT-----------------------------------------------
    setSize (WINDOW_WIDTH,WINDOW_HEIGHT);
    setResizable (false);
    setLocationRelativeTo (this);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    panels.show(screenView, SPLASH_SCREEN);
    splash.start();
    add(screenView);
    setVisible (true);
    audio.start("MenuTheme");
  }
  
  /**
   * This method switches the GamePanel to be in view and starts a new card game.
   * @param player the player who will be playing in the game.
   * @param opponent the computer who will be playing in the game.
   */
  public void switchGamePanel (Player player, CardGameNPC opponent)
  {
     if (!audio.playing("CardGame"))
    {
      audio.stopAll();
      audio.restart("CardGame");
    }
    audio.start("CardGame");
    gamePanel.start(player, opponent);
    panels.show(screenView, GAME_PANEL);
    openWorld.stop();
  }
  
  /**
   * This method switches the focus of the screen to the main menu.
   * @param stop the String reference of which panel in the CardLayout will be stopped.
   */
  public void mainMenu(String stop)
  {
   if (!audio.playing("MenuTheme"))
    {
      audio.stopAll();
      audio.restart("MenuTheme");
    }
    audio.start("MenuTheme");
    panels.show(screenView, MENU_PANEL);
    if (stop == OPEN_WORLD)
    {
      openWorld.saveGame();
      openWorld.stop();
    }
    else if (stop == SPLASH_SCREEN)
      splash.stop();
    else
    {
      if (stop == CREDITS)
        end.stop();
    } 
  }
  
  /**
   * This method switches the focus of the screen to the OpenWorld panel.
   * @param stop the String reference of which panel in the CardLayout will be stopped.
   */
  public void switchOpenWorld(String stop)
  {
    if (!audio.playing("OpenWorld"))
    {
      audio.stopAll();
      audio.restart("OpenWorld");
    }
    audio.start("OpenWorld");
    openWorld.start();
    panels.show(screenView, OPEN_WORLD);
    if (stop == GAME_PANEL)
      gamePanel.stop();
  }
  
  /**
   * This method handles the loading of an already started game.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>s </b> This is a reference to the scanner class used to read the player save.
   */
  public void loadGame()
  {
    try
    {
    Scanner s = new Scanner (new File(".//resources/Save/player.txt"));
    if (s.next().equals("true"))
    {
      requestFocus();
      openWorld.loadGame();
      openWorld.start();
      panels.show(screenView, OPEN_WORLD);
      if (!audio.playing("OpenWorld"))
      {
        audio.stopAll();
        audio.restart("OpenWorld");
      }
      audio.start("OpenWorld");
    }
    }
    catch (IOException e)
    {
    }
  }
  
  /**
   * This method handles the creation of a new game.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>out </b> This is used as reference to the PrintWriter class to write the data to the save state.
   * @param character the character to be used in the new save.
   * @param saveName the name to be displayed on the leaderboard.
   */
  public void newGame (String character, String saveName)
  {
   if (!audio.playing("OpenWorld"))
    {
      audio.stopAll();
      audio.restart("OpenWorld");
    }
    audio.start("OpenWorld");
    try
    {
      copyFile("maps/Town1/Town1.txt","Save/Town1/Town1.txt");
      copyFile("maps/Town1/Indoor.txt","Save/Town1/Indoor.txt");
      copyFile("maps/Town2/Town2.txt","Save/Town2/Town2.txt");
      copyFile("maps/Town2/Indoor2.txt","Save/Town2/Indoor2.txt");
      copyFile("maps/Town3/Town3.txt","Save/Town3/Town3.txt");
      copyFile("maps/Town3/Indoor3.txt","Save/Town3/Indoor3.txt");
      copyFile("decks/PlayerDeck.txt","Save/PlayerDeck.txt");
      PrintWriter out = new PrintWriter (new FileWriter(".//resources/Save/player.txt"));
      out.println("true");
      out.println(character + " " + saveName);
      out.println ("11 25 ");
      out.println ("/Town1/Town1.txt");
      out.println ("0 0");
      out.close();
      loadGame();
    }
    catch (IOException e)
    {
    }
  }
  
   /**
   * This method copies the contents of one file to another.
   * @param in the input file.
   * @param out the output file.
   */
  public void copyFile (String in, String out)
  {
    FileInputStream instream = null;
    FileOutputStream outstream = null;
    
    try{
      File infile =new File(in);
      File outfile =new File(out);
      
      instream = new FileInputStream(".//resources/"+infile);
      outstream = new FileOutputStream(".//resources/"+outfile);
      
      byte[] buffer = new byte[1024];
      
      int length;

      while ((length = instream.read(buffer)) > 0){
        outstream.write(buffer, 0, length);
      }
      
      instream.close();
      outstream.close();

    }catch(IOException ioe){
      ioe.printStackTrace();
    }
  }
  
  /**
   * This method swaps the focus of the screen to the credits screen and displays the animation.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>out </b> This is used as reference to the PrintWriter class to write the data to the save state.
   */
  public void credits ()
  {
    if (!audio.playing("MenuTheme"))
    {
      audio.stopAll();
      audio.restart("MenuTheme");
    }
    audio.start("MenuTheme");
    try
    {
      PrintWriter out = new PrintWriter (new FileWriter(".//resources/Save/player.txt"));
      out.println("false");
      out.close();
    }
    catch (IOException e)
    {
    }
    end.start();
    panels.show(screenView, CREDITS);
    openWorld.stop();
  }
  
  /**
   * This method is used for determining what should take place after certain actions.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>x </b> This stores the JOptionPane input that the user enters.
   * <p>
   * <b>label </b> The label used to display the About dialog.
   * <p>
   * <b>myDialog </b> The box that will display the About dialog text.
   * <p>
   * <b>button </b> The button that will close the dialog.
   * @param ae This is used as reference to ActionEvent
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getActionCommand().equals ("Quit"))
    {
      int x = JOptionPane.showConfirmDialog((Component) null, "Are you sure you want to quit?","Warning", JOptionPane.YES_NO_OPTION);
      if (x==JOptionPane.YES_OPTION)
        System.exit (0);
      else
        return;
    }
    else if (ae.getActionCommand().equals ("About"))
    {
      JLabel label = new JLabel ("<html><p>+ Terrarithmetic is a very enjoyable and exciting game, with many different things to explore.<p>+ This game is intended for children between the ages 3 and 5. <p>+ This game was developped by Pixelinx Interactive, and is the first of many user friendly and fun games for all ages.<p>+ This is a open-world aspect game which allows the user to select the character they wish to use, as they encounter many obstacles <p>and opponents, as well as many hidden secrets which may aid them in their quest.</p></html>");
      JDialog myDialog = new JDialog (this, "About");
      myDialog.setSize (800,150);
      myDialog.add (label);
      myDialog.setResizable (false);
      myDialog.setLayout (new FlowLayout());
      JButton button = new JButton ("Close");
      button.addActionListener (new ActionListener ()
                                  {
        public void actionPerformed (ActionEvent e)
        {
          myDialog.dispose ();
        }
      });
      myDialog.add (button);
      myDialog.setLocationRelativeTo (this);
      myDialog.setVisible (true);    
    }
    else
    {
      try
      {
        Runtime.getRuntime().exec("hh.exe .//CHM Folder/Help File.chm");
      }
      catch (IOException e)
      {
      }
    }
  }
  
  /** Description of main(String [] args)
    * This method calls the DatabaseApp constructor to
    * create the application.
    * 
    * @param args [ ]  String array that allows command line
    * parameters to be used when executing the program.
    */ 
  public static void main (String [] args)
  {
    TerrarithmeticApp t = new TerrarithmeticApp();
  }
}