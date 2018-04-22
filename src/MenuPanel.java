import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
/**
 * This is the MenuPanel that includes all the menu options in the game. 
 * <p>
 * MenuPanel
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */ 
public class MenuPanel extends JPanel 
{
  /**This is used as reference to the driver class when starting certain menus. */
  TerrarithmeticApp frame;
   /**This is the Font used for titles in the menus. */
  Font title;
  /**This is the Font used for bodies in the menus. */
  Font body;
  /**This holds all the different menu screens. */
  CardLayout menus;
  /**This holds a final String value for the main menu. */
  static final String MAIN_MENU = "main";
  /**This holds a final String value for a new game. */
  static final String NEW_GAME = "new";
  /**This holds a final String value for the options. */
  static final String OPTIONS = "options";
  /**This holds a final String value for the instructions.*/
  static final String INSTRUCTIONS = "instructions";
  /**This holds a final String value for the highscores. */
  static final String HIGHSCORES = "highscores";
  /**This is used as reference to the MainMenu. */
  MainMenu mainMenu;
  /**This is used as reference to the NewGame. */
  NewGame newGame;
  /**This is used as reference to the Options. */
  Options options;
  /**This is used as reference to the Instructions. */
  Instructions instructions;
  /**This is used as reference to the HighscoreMenu. */
  HighscoreMenu highscores;

  /**
   * This constructs an instance of the MainMenu.
   * @param frame used as reference when started panels in TerrarithmeticApp.
   */ 
  public MenuPanel(TerrarithmeticApp frame)
  {
    this.frame = frame;
    title = new Font("Minecraftia", Font.PLAIN, 30);
    body = new Font("Minecraftia", Font.PLAIN, 20);
    menus  = new CardLayout ();
    setLayout (menus);
    
    mainMenu = new MainMenu ();
    newGame = new NewGame ();
    options = new Options ();
    instructions = new Instructions();
    highscores = new HighscoreMenu ();
    
    add (mainMenu, MAIN_MENU);
    add (newGame, NEW_GAME);
    add (options, OPTIONS);
    add (instructions, INSTRUCTIONS);
    add (highscores, HIGHSCORES);
    
    menus.show (this, MAIN_MENU);
  }
  
  /**
 * This class is used to create the MainMenu as a MenuPanel.
 * <p>
 * MainMenu
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */ 
  private class MainMenu extends JPanel implements ActionListener
  {
    /**This is used as reference to SpringLayout. */
    SpringLayout spring;
    /**
   * This constructs an instance of the MainMenu.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>image </b> This is used to draw the menu background.
   * <p>
   * <b>imageLabel </b> This is used to draw the menu background.
   * <p>
   * <b>newGame </b> This is the button used to start the new game.
   * <p>
   * <b>continueGame </b> This is the button used to continue a game.
   * <p>
   * <b>highscore </b> This is the button used to check the highscores.
   * <p>
   * <b>options </b> This is the button used to display the options.
   * <p>
   * <b>instructions </b> This is the button used to display the instructions.
   * <p>
   * <b>quit </b> This is the button used to quit the game.
   */ 
    public MainMenu ()
    {
      spring = new SpringLayout ();
      setLayout (spring);
      ImageIcon image = new ImageIcon(".//resources/menuBack.png");
      JLabel imageLabel = new JLabel(image);
      /*---------------------Create Buttons---------------------*/
      JButton newGame = new JButton ("New Game");
      formatButton (newGame);
      spring.putConstraint(SpringLayout.NORTH, newGame, 50, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, newGame, 50, SpringLayout.WEST, this);
      spring.putConstraint(SpringLayout.EAST, newGame, 280, SpringLayout.WEST, newGame);
      spring.putConstraint(SpringLayout.SOUTH, newGame, 45, SpringLayout.NORTH, newGame);
      JButton continueGame = new JButton ("Continue Game");
      formatButton (continueGame);
      spring.putConstraint(SpringLayout.NORTH, continueGame, 82, SpringLayout.NORTH, newGame);
      spring.putConstraint(SpringLayout.WEST, continueGame, 0, SpringLayout.WEST, newGame);
      spring.putConstraint(SpringLayout.EAST, continueGame, 280, SpringLayout.WEST, continueGame);
      spring.putConstraint(SpringLayout.SOUTH, continueGame, 45, SpringLayout.NORTH, continueGame);
      JButton highscore = new JButton ("Highscores");
      formatButton (highscore);
      spring.putConstraint(SpringLayout.NORTH, highscore, 82, SpringLayout.NORTH, continueGame);
      spring.putConstraint(SpringLayout.WEST, highscore, 0, SpringLayout.WEST, continueGame);
      spring.putConstraint(SpringLayout.EAST, highscore, 280, SpringLayout.WEST, highscore);
      spring.putConstraint(SpringLayout.SOUTH, highscore, 45, SpringLayout.NORTH, highscore);
      JButton options = new JButton ("Options");
      formatButton (options);
      spring.putConstraint(SpringLayout.NORTH, options, 82, SpringLayout.NORTH, highscore);
      spring.putConstraint(SpringLayout.WEST, options, 0, SpringLayout.WEST, highscore);
      spring.putConstraint(SpringLayout.EAST, options, 280, SpringLayout.WEST, options);
      spring.putConstraint(SpringLayout.SOUTH, options, 45, SpringLayout.NORTH, options);
      JButton instructions = new JButton ("Instructions");
      formatButton (instructions);
      spring.putConstraint(SpringLayout.NORTH, instructions, 82, SpringLayout.NORTH, options);
      spring.putConstraint(SpringLayout.WEST, instructions, 0, SpringLayout.WEST, options);
      spring.putConstraint(SpringLayout.EAST, instructions, 280, SpringLayout.WEST, instructions);
      spring.putConstraint(SpringLayout.SOUTH, instructions, 45, SpringLayout.NORTH, instructions);
      JButton quit = new JButton ("Quit");
      formatButton (quit);
      spring.putConstraint(SpringLayout.NORTH, quit, 82, SpringLayout.NORTH, instructions);
      spring.putConstraint(SpringLayout.WEST, quit, 0, SpringLayout.WEST, instructions);
      spring.putConstraint(SpringLayout.EAST, quit, 280, SpringLayout.WEST, quit);
      spring.putConstraint(SpringLayout.SOUTH, quit, 45, SpringLayout.NORTH, quit);
      //Add Buttons
      add (newGame);
      add (continueGame);
      add (highscore);
      add (options);
      add (instructions);
      add (quit);
      add (imageLabel);
      
      newGame.addActionListener(this);
      continueGame.addActionListener(this);
      highscore.addActionListener (this);
      options.addActionListener(this);
      instructions.addActionListener(this);
      quit.addActionListener(this);
    }
    
    /**
   * This method is used for determining what should take place after certain actions.
   * @param ae This is used as reference to ActionEvent
   */
    public void actionPerformed (ActionEvent ae)
    {
      if (ae.getActionCommand().equals("New Game"))
        newGame();
      else if (ae.getActionCommand().equals("Continue Game"))
      {
        mainMenu();
        frame.loadGame();
      }
      else if (ae.getActionCommand().equals("Highscores"))
        highscores();
      else if (ae.getActionCommand().equals("Options"))
        options();
      else if (ae.getActionCommand().equals("Instructions"))
        instructions();
      else
        System.exit(0);
    }
  }
  
   /**
 * This class is used to create a NewGame as a MenuPanel.
 * <p>
 * NewGame
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */ 
  private class NewGame extends JPanel implements ActionListener
  {
    /**This is used to detect mouse input. */
    BufferedMouse mouse;
    /**This is used as reference to SpringLayout. */
    SpringLayout spring;
    /**This is used for the background image. */
    Sprite background;
    /**This holds the array of characters. */
    Sprite [] [] characters;
    /**This holds the array of character names. */
    String [] characterNames;
    /**This specifies which characters in the array is selected. */
    int selected = 0;
    /**This tracks the frame timing for the animation. */
    int currentFrame = 0;
    /**This tracks which frame is currently being shown. */
    int animPosition = 0;
    /**This stores the system time in milliseconds to track frames for animation. */
    long oldTime = System.currentTimeMillis ();
    /**The textfield to input the character name. */
    JTextField characterName;
    
    /**
   * This constructs an instance of the NewGame.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>back </b> This goes back to the main menu.
   * <p>
   * <b>newGame </b> This starts a new game.
   */ 
    public NewGame ()
    {
      spring = new SpringLayout();
      setLayout (spring);
      characterNames = new String [] {"Violet","Blue","Green","Red"};
      characters = new Sprite [characterNames.length] [3];
      mouse = new BufferedMouse();
      addMouseListener(mouse);
      try
      {
        background = new Sprite("menuBlank.png");
        for (int x = 0; x < characters.length; x++)
        {
          for (int y = 0; y < characters [x].length; y++)
          {
            characters[x] [y] = new Sprite ("/Characters/"+characterNames[x]+"/Down" + y + ".png");
            characters [x] [y].scaleImage(3,3);
          }
        }
      }
      catch(IOException e)
      {
        e.printStackTrace();
      }
      characterName = new JTextField (15);
      characterName.setFont(body);
      characterName.setText("New Player");
      spring.putConstraint(SpringLayout.NORTH, characterName, 145, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, characterName, 310, SpringLayout.WEST, this);
      spring.putConstraint(SpringLayout.EAST, characterName, 300, SpringLayout.WEST, characterName);
      add(characterName);
      JButton back = new JButton ("Back");
      formatButton (back);
      spring.putConstraint(SpringLayout.NORTH, back, 470, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, back, 70, SpringLayout.WEST, this);      
      spring.putConstraint(SpringLayout.EAST, back, 180, SpringLayout.WEST, back);
      spring.putConstraint(SpringLayout.SOUTH, back, 40, SpringLayout.NORTH, back);
      JButton newGame = new JButton ("New Game");
      formatButton (newGame);
      spring.putConstraint(SpringLayout.NORTH, newGame, 470, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, newGame, 740, SpringLayout.WEST, this);      
      spring.putConstraint(SpringLayout.EAST, newGame, 180, SpringLayout.WEST, newGame);
      spring.putConstraint(SpringLayout.SOUTH, newGame, 40, SpringLayout.NORTH, newGame);
      add (newGame);
      add (back);
      back.addActionListener(this);
      newGame.addActionListener(this);
    }
    
     /**
   * This method is used for determining what should take place after certain actions.
   * @param ae This is used as reference to ActionEvent
   */
    public void actionPerformed (ActionEvent ae)
    {
      if (ae.getActionCommand().equals("Back"))
        mainMenu();
      else 
      {
        if (ae.getActionCommand().equals("New Game"))
        {
          characterName.setText(characterName.getText().trim());
          if (characterName.getText().equals(""))
            characterName.setText("New Player");
          else if (characterName.getText().length() > 15)
            characterName.setText(characterName.getText().substring(0,15));
          else
          {
            mainMenu();
            frame.newGame(characterNames[selected],characterName.getText());
          }
        }
      }
    }
    
    /**
     * This method is used to draw on the screen.
     * <p>
     * <b>Local variables: </b>
     * <p>
     * <b>g2D </b> This is used as reference to the Graphics2D class.
     * @param g used as reference to the Graphics class for drawing.
     */
    public void paintComponent (Graphics g)
    {
      if (System.currentTimeMillis() >= oldTime + 200)
      {
        oldTime = System.currentTimeMillis();
        animPosition++;
        if (animPosition > characters [selected].length)
          animPosition = 0;
        if (animPosition == 1)
          currentFrame = 1;
        else if (animPosition == 3)
          currentFrame = 2;
        else
          currentFrame = 0;
      }
      Graphics2D g2D = (Graphics2D) g;
      g2D.setColor(Color.BLACK);
      g2D.drawImage(background.image(),0,0,null);
      g2D.setFont (title);
      g2D.drawString ("Character Selection", (new Rectangle (1000,600).width - g2D.getFontMetrics (title).stringWidth("Character Selection")) / 2 ,110);
      g2D.setFont(body);
      g2D.drawString("Character Name:",100,180);
      
      for (int x = 0; x < characters.length; x++)
      {
        if (mouse.xCoord() >= 88+ x* 225 && mouse.xCoord() <= 223 + x*225 && mouse.yCoord() >= 250 && mouse.yCoord() <= 430)
          selected = x;
        if (x == selected)
          g2D.drawImage(characters [x] [currentFrame].image(),60 + x* 225,230,null);
        else
          g2D.drawImage(characters [x] [0].image(),60 + x* 225,230,null);
      }
      mouse.refreshMouse();
      repaint();
    }
  }
  
  /**
 * This class is used to create the Options as a MenuPanel.
 * <p>
 * Options
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */ 
  private class Options extends JPanel implements ActionListener
  {
    /**This is used as reference to the SpringLayout class. */
    SpringLayout spring;
    /**This button is used to play the music. */
    JButton music;
    /**This button is used to change the difficulty. */
    JButton difficulty;
    
    /**
   * This constructs an instance of the Options.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>image </b> This is used to draw the menu background.
   * <p>
   * <b>imageLabel </b> This is used to draw the menu background.
   * <p>
   * <b>header </b> This displays the header.
   * <p>
   * <b>music </b> This displays label that labels the music button.
   * <p>
   * <b>difficultyLabel </b> This displays label that labels the difficulty/gamelevels button.
   * <p>
   * <b>back </b> This displays the back button.
   */ 
    public Options ()
    {
      spring = new SpringLayout ();
      setLayout (spring);
      ImageIcon image = new ImageIcon(".//resources/menuBlank.png");
      JLabel imageLabel = new JLabel(image);
      JLabel header = new JLabel ("Options");
      header.setFont(title);
      spring.putConstraint(SpringLayout.HORIZONTAL_CENTER,header,0, SpringLayout.HORIZONTAL_CENTER,this);
      spring.putConstraint(SpringLayout.NORTH,header,50, SpringLayout.NORTH,this);
      music = new JButton ("Music: On");
      formatButton (music);
      spring.putConstraint(SpringLayout.NORTH, music, 180, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, music, 520, SpringLayout.WEST, this);      
      spring.putConstraint(SpringLayout.EAST, music, 180, SpringLayout.WEST, music);
      spring.putConstraint(SpringLayout.SOUTH, music, 40, SpringLayout.NORTH, music);
      JLabel musicLabel = new JLabel ("Toggle music:");
      musicLabel.setFont(body);
      spring.putConstraint(SpringLayout.VERTICAL_CENTER,musicLabel,0, SpringLayout.VERTICAL_CENTER,music);
      spring.putConstraint(SpringLayout.EAST,musicLabel,-50, SpringLayout.WEST,music);
      difficulty = new JButton ("Difficulty: Easy");
      formatButton (difficulty);
      spring.putConstraint(SpringLayout.NORTH, difficulty, 100, SpringLayout.NORTH, music);
      spring.putConstraint(SpringLayout.WEST, difficulty, 0, SpringLayout.WEST, music);      
      spring.putConstraint(SpringLayout.EAST, difficulty, 180, SpringLayout.WEST, difficulty);
      spring.putConstraint(SpringLayout.SOUTH, difficulty, 40, SpringLayout.NORTH, difficulty);
      JLabel difficultyLabel = new JLabel ("Difficulty:");
      difficultyLabel.setFont(body);
      spring.putConstraint(SpringLayout.VERTICAL_CENTER,difficultyLabel,0, SpringLayout.VERTICAL_CENTER,difficulty);
      spring.putConstraint(SpringLayout.EAST,difficultyLabel,-50, SpringLayout.WEST,difficulty);
      JButton back = new JButton ("Back");
      formatButton (back);
      spring.putConstraint(SpringLayout.NORTH, back, 470, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, back, 70, SpringLayout.WEST, this);      
      spring.putConstraint(SpringLayout.EAST, back, 180, SpringLayout.WEST, back);
      spring.putConstraint(SpringLayout.SOUTH, back, 40, SpringLayout.NORTH, back);
      
      add (difficultyLabel);
      add (difficulty);
      add (musicLabel);
      add (music);
      add (header);
      add (back);
      add (imageLabel);
      difficulty.addActionListener(this);
      music.addActionListener(this);
      back.addActionListener(this);
    }
    
    /**
   * This method is used for determining what should take place after certain actions.
   * @param ae This is used as reference to ActionEvent
   */
    public void actionPerformed (ActionEvent ae)
    {
      if (ae.getActionCommand().equals("Difficulty: Easy"))
      {
        difficulty.setText("Difficulty: Medium");
        Board.difficulty = Board.MEDIUM;
      }
      else if (ae.getActionCommand().equals("Difficulty: Medium"))
      {
        difficulty.setText("Difficulty: Hard");
        Board.difficulty = Board.HARD;
      } 
      else if (ae.getActionCommand().equals("Difficulty: Hard"))
      {
        difficulty.setText("Difficulty: Easy");
        Board.difficulty = Board.EASY;
      }
      else if (ae.getActionCommand().equals("Music: On"))
      {
        music.setText("Music: Off");
        frame.audio.disable();
      }
      else if (ae.getActionCommand().equals("Music: Off"))
      {
        music.setText("Music: On");
        frame.audio.enable();
        frame.audio.start("MenuTheme");
      }
      else
      {
        mainMenu();
      }
    }
  }
  
  /**
 * This class is used to create the Instructions as a MenuPanel.
 * <p>
 * Instructions
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */ 
  private class Instructions extends JPanel implements ActionListener
  {
    /**This is the Sprite used to draw the menu background. */
    private Sprite menuBack;
    /**This is a Sprite used in the Instructions panel. */
    private Sprite arrowKeys;
    /**This is a Sprite used in the Instructions panel. */
    private Sprite placeNumber;
    /**This is a Sprite used in the Instructions panel. */
    private Sprite placeOperator;
    /**This boolean determines when to go to the next page. */
    private boolean page2;
    /**This is used as reference to the SpringLayout class. */
    private SpringLayout spring;
    /**This button is used to go to the next page. */
    private JButton next;
    
    /**
   * This constructs an instance of the Instructions.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>back </b> This is used to draw the back button.
   */ 
    public Instructions()
    {
      spring = new SpringLayout ();
      setLayout(spring);
      next = new JButton ("Next Page");
      formatButton (next);
      spring.putConstraint(SpringLayout.NORTH, next, 480, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, next, 750, SpringLayout.WEST, this);      
      spring.putConstraint(SpringLayout.EAST, next, 180, SpringLayout.WEST, next);
      spring.putConstraint(SpringLayout.SOUTH, next, 40, SpringLayout.NORTH, next);
      JButton back = new JButton ("Back");
      formatButton (back);
      spring.putConstraint(SpringLayout.NORTH, back, 480, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, back, 60, SpringLayout.WEST, this);      
      spring.putConstraint(SpringLayout.EAST, back, 180, SpringLayout.WEST, back);
      spring.putConstraint(SpringLayout.SOUTH, back, 40, SpringLayout.NORTH, back);
      add (next);
      add (back);
      next.addActionListener (this);
      back.addActionListener (this);
      setVisible (true);
      try
      {
        placeNumber = new Sprite ("placenumber.png");
        placeOperator = new Sprite ("placeoperator.png");
        arrowKeys = new Sprite ("arrowKeys.png");
        menuBack = new Sprite ("menuBlank.png");
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      placeNumber.scaleImage (2,2);
      placeOperator.scaleImage (2,2);
    }
    
    /**
   * This method is used for determining what should take place after certain actions.
   * @param ae This is used as reference to ActionEvent
   */
    public void actionPerformed (ActionEvent ae)
    {
      if (ae.getActionCommand().equals ("Next Page") && !page2)
      {
        page2 = true;
        next.setText("Main Menu");
      }
      else if (ae.getActionCommand().equals("Back") && page2)
      {
        page2 = false;
        next.setText("Next Page");
      }
      else
      {
        next.setText("Next Page");
        page2 = false;
        mainMenu();
      }
    }
    
    /**
   * This method is used to draw the text that explains the card game.
   * @param g2D This is used as reference to the Graphics2D class for drawing Strings.
   */
    private void cardGame (Graphics2D g2D)
    {
      g2D.setFont (title);
      g2D.drawString ("Card Game Instructions", (new Rectangle (1000,600).width - g2D.getFontMetrics (title).stringWidth("Card Game Instructions")) / 2 ,90);
      g2D.drawString ("Objective", 50, 140);
      g2D.drawString ("Controls: Mouse Clicks", 50, 340);
      g2D.setFont (body);
      g2D.drawString ("The first person to obtain 50 or more points will win.", 50,190);
      g2D.drawString ("Greatest possible sum = more points (ATTACKING).", 50,230);
      g2D.drawString ("Lowest possible number = more points (DEFENDING).", 50,270);
      
      g2D.drawString ("You can navigate through your numbers/operators.", 50,390);
      g2D.drawString ("Pressing the green button will place a number.", 50,430);
      g2D.drawString ("Pressing the green button will place an operator.", 50,470);
      g2D.drawImage (placeNumber.image(), 690,140,null);
      g2D.drawImage (placeOperator.image(), 690,310,null);
    }
    
    /**
   * This method is used to draw the text that explains the controls.
   * @param g2D This is used as reference to the Graphics2D class for drawing Strings.
   */
    private void controls (Graphics2D g2D)
    {
      g2D.setFont (title);
      g2D.drawString ("Instructions", (new Rectangle (1000,600).width - g2D.getFontMetrics (title).stringWidth("Instructions")) / 2 ,90);
      g2D.drawString ("Controls", 50, 140);
      g2D.drawString ("Objective", 50, 340);
      g2D.setFont (body);
      g2D.drawString ("Use the arrow keys (UP, DOWN, LEFT, RIGHT) to move your character.", 50,190);
      g2D.drawString ("Use the key (SPACE BAR) to interact with items.", 50,230);
      g2D.drawString ("Use the key (P) to open to pause menu.", 50, 270);
      g2D.drawString ("Play against all the NPC characters.", 50, 390);
      g2D.drawString ("Advance through towns to reach the final gate.", 50, 430);
      g2D.drawString ("Find strong cards in hidden chests across the map.", 50, 470);
      g2D.drawImage (arrowKeys.image(), 630,200,null);
    }
    
    /**
     * This method is used to draw on the screen.
     * <p>
     * <b>Local variables: </b>
     * <p>
     * <b>g2D </b> This is used as reference to the Graphics2D class.
     * @param g used as reference to the Graphics class for drawing.
     */
    public void paintComponent(Graphics g)
    {
      Graphics2D g2D = (Graphics2D) g;
      g2D.setBackground (new Color (255,255,255));
      g2D.clearRect(0,0,1000,600);
      g2D.drawImage (menuBack.image(), 0,0,null);
      if (page2)
      {
        cardGame (g2D);
      }
      else
        controls (g2D);
      repaint();
    }
  }  
  
   /**
 * This class is used to create the HighscoreMenu as a MenuPanel.
 * <p>
 * HighscoreMenu
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */ 
  private class HighscoreMenu extends JPanel implements ActionListener
  {
    /**This is used as reference to the SpringLayout class for positioning. */
    SpringLayout spring;
    /**This stores all the Highscores. */
    HighScore [] scores;
     /**
   * This constructs an instance of the HighscoreMenu.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>back </b> This is used to draw the back button.
   * <p>
   * <b>print </b> This is used to print the Highscores.
   */ 
    public HighscoreMenu ()
    {
      spring = new SpringLayout ();
      setLayout (spring);
      JButton back = new JButton ("Back");
      formatButton (back);
      spring.putConstraint(SpringLayout.NORTH, back, 470, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, back, 70, SpringLayout.WEST, this);      
      spring.putConstraint(SpringLayout.EAST, back, 180, SpringLayout.WEST, back);
      spring.putConstraint(SpringLayout.SOUTH, back, 40, SpringLayout.NORTH, back);
      JButton print = new JButton ("Print");
      formatButton (print);
      spring.putConstraint(SpringLayout.NORTH, print, 470, SpringLayout.NORTH, this);
      spring.putConstraint(SpringLayout.WEST, print, 740, SpringLayout.WEST, this);      
      spring.putConstraint(SpringLayout.EAST, print, 180, SpringLayout.WEST, print);
      spring.putConstraint(SpringLayout.SOUTH, print, 40, SpringLayout.NORTH, print);
      add (print);
      add (back);
      back.addActionListener(this);
      print.addActionListener(this);
    }
    
    /**
   * This method is used for determining what should take place after certain actions.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>HighScoresPrinter </b> This is used as reference to HighScoresPrinter.
   * @param ae This is used as reference to ActionEvent
   */
    public void actionPerformed (ActionEvent ae)
    {
      if (ae.getActionCommand().equals("Print"))
      {
        HighScoresPrinter p = new HighScoresPrinter();
        p.printHighscores();
      }
      else
        mainMenu();
    }
    
    /**
     * This method is used to draw on the screen.
     * <p>
     * <b>Local variables: </b>
     * <p>
     * <b>g2D </b> This is used as reference to the Graphics2D class.
     * <p>
     * <b>logo </b> The Sprite for the company logo.
     * @param g used as reference to the Graphics class for drawing.
     */
    public void paintComponent(Graphics g)
    {
      scores = new HighScore [TerrarithmeticApp.highscores.getScores().size()];
      for (int x = 0; x < scores.length; x++)
        scores [x] = TerrarithmeticApp.highscores.getScores().get(x);
      Graphics2D g2D = (Graphics2D) g;
      try
      {
      g2D.drawImage (new Sprite("menuBlank.png").image(),0,0,null);
      Sprite logo = new Sprite ("CompanyLogo.png");
      logo.scaleImage(0.5,0.5);
      g2D.drawImage (logo.image(),690,475,null);
      }
      catch (IOException e)
      {
      }
      g2D.setFont(title);
      g2D.drawString ("Highscores", (new Rectangle (1000,600).width - g2D.getFontMetrics (title).stringWidth("Instructions")) / 2 ,90);
      g2D.drawString("Name",220,140);
      g2D.drawString("Wins",450,140);
      g2D.drawString("Level",680,140);
      g2D.setFont(body);
      for (int x = 0; x < scores.length; x++)
      {
        g2D.drawString(scores [x].getName().trim(), 180,180+x*30);
        g2D.drawString("" + scores [x].getScore(),470,180+x*30);
        g2D.drawString(scores[x].getLevel(),695,180+x*30);
      }
      g2D.drawString(".//resources/highscores/data.pi",260,510);
    }
  }
  /**
   * This method customizes buttons and spacing between eachother.
   * @param b the button to be formatted.
   */
  private void formatButton (JButton b)
  {
    b.setFocusPainted (false);
    b.setBackground (new Color (59,89,182));
    b.setForeground (Color.white);
    b.setFont(new Font("Minecraftia", Font.BOLD, 12));
  }
  
  /**
   * This method shows highscores.
   */ 
  public void highscores()
  {
    menus.show (this, HIGHSCORES);
  }
  
  /**
   * This method shows the main menu.
   */ 
  public void mainMenu()
  {
    menus.show(this, MAIN_MENU);
  }
  
  /**
   * This method shows the new game.
   */ 
  public void newGame()
  {
    menus.show(this, NEW_GAME);
  }
  
  /**
   * This method shows the options.
   */ 
  public void options ()
  {
    menus.show(this,OPTIONS);
  }
  
  /**
   * This method shows the instructions.
   */ 
  public void instructions ()
  {
    menus.show(this,INSTRUCTIONS);
  }
}