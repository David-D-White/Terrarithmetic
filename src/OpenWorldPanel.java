import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
 * This class serves as a driver class for the visuals of the open world aspect of this game.
 * <p>
 * OpenWorldPanel
 * <p>
 * Date: 2016/05/26
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/26
 */
public class OpenWorldPanel extends Screen
{
  /**This defines the size of all game components and pixels. */
  private final int TILE_SIZE = 64;
  /**This defines the speed of the NPC characters in the game.*/
  private final int NPC_SPEED = 1;
  /**This holds the processing value of the openDoor method. */
  private final int DOOR = 4;
  /**This holds the processing value of the signDialogue method. */
  private final int SIGN = 5; 
  /**This holds the processing value of the chest method. */
  private final int CHEST = 6;
  /**This holds the processing value of the NPCDialogue method. */
  private final int NPC_CHAT = 7;
  /**This holds the processing value of the startGame method. */
  private final int CARD_GAME = 8;
  /**This holds the processing value of the pauseMenu method. */
  private final int PAUSE = 9;
  /**This defines the movement speed of the character. */
  private final int PLAYER_SPEED = 4;
  /**This holds the x value that tracks the player's movement and modifies the drawing in the refresh method. */
  private int modX = 0;
  /**This holds the y value that tracks the player's movement and modifies the drawing in the refresh method. */
  private int modY = 0;
  /**This tracks the player's frame. */
  private int playerMovement = 0;
  /**This differenciates what is currently processing. */
  private int processing = -1;
  /**The Tile that is currently being interacted with. */
  private Tile interactingTile;
  /**The NPC that is currently being interacted with. */
  private NPC interactingNPC;
  /**The current item in the pause menu. */
  private int currentSelection =1;
  /**This defines whether or not the player is currently movng.*/
  private boolean moving;
  /**An array of buffers which will be drawn in the refresh method.*/
  private Buffer buffers [] = new Buffer [4];
  /**The player object to be drawn on this map.*/
  private Player player;
  /**The array of computer characters to be drawn on this map.*/
  private NPC [] computerCharacters;
  /**An array of values to track the movement of the computer characters loaded. */
  private int [] npcMovement;
  /**The map to be drawn. */
  private Map map;
  /**An image which holds the dialogue which will be drawn in the refresh method.*/
  private BufferedImage dialogue = new BufferedImage (1000,600,BufferedImage.TYPE_INT_ARGB);
  /**This is the frame that the this will be displayed on.*/ 
  private TerrarithmeticApp frame;
  
  /**
   * This constructs an instance of the OpenWorldPanel with the specified frame.
   * @param frame the TerrarithmeticApp object that will be used.
   */
  public OpenWorldPanel (TerrarithmeticApp frame)
  {
    super ("OpenWorldPanel");
    this.frame = frame;
  }
  
  /**
   * This method saves all the data currently loaded by this class into a save.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>out </b> Reference to the PrintWriter class when writing to a file.
   */
  public void saveGame()
  {
    try
    {
      map.mapToFile("Save/"+map.mapName());
      player.deck().deckToFile("Save/PlayerDeck.txt");
      PrintWriter out = new PrintWriter (new FileWriter(".//resources/Save/player.txt"));
      out.println("true");
      out.println(player.characterName() + " " + player.name());
      out.println (player.getX() + " " + player.getY());
      out.println (map.mapName());
      out.println (player.wins() + " " + player.losses());
      out.close();
    }
    catch (IOException e)
    {
    }
  }
  
  /**
   * This method loads all the necessary data for this class from a save.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>s </b> Reference to the Scanner class when reading from a file.
   */
  public void loadGame ()
  {
    try
    {
      Scanner s = new Scanner (new File (".//resources/Save/player.txt"));
      s.next();
      player = new Player (s.next(),s.nextLine(),s.nextInt(),s.nextInt(),Deck.deckFromFile("/Save/PlayerDeck.txt"));
      setMap (Map.mapFromFile("Save/"+ s.next()),player.getX(),player.getY());
      for (int x = s.nextInt(); x > 0; x--)
        player.addWin();
      for (int x = s.nextInt(); x > 0; x--)
        player.addLoss();
      s.close();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
  
  /**
   * This method initializes the buffers.
   */
  private void initializeBuffers()
  {
    buffers [0] = new TileBuffer (map.retrieveLayer (0), TerrarithmeticApp.WINDOW_WIDTH, TerrarithmeticApp.WINDOW_HEIGHT , TILE_SIZE);
    buffers [1] = new TileBuffer (map.retrieveLayer (1), TerrarithmeticApp.WINDOW_WIDTH, TerrarithmeticApp.WINDOW_HEIGHT , TILE_SIZE);
    buffers [2] = new EntityBuffer (computerCharacters);
    buffers [3] = new TileBuffer (map.retrieveLayer (2), TerrarithmeticApp.WINDOW_WIDTH, TerrarithmeticApp.WINDOW_HEIGHT , TILE_SIZE);
    for (int x = 0; x < buffers.length; x ++)
    {
      buffers[x].centerView(player.getX(), player.getY());
    }
  }
  
  /**
   * This method udpates the buffers.
   */
  private void updateBuffers ()
  {
    for (int x = 0; x < buffers.length; x ++)
      buffers[x].update();
  }
  
  /**
   * This method handles the movement of the player.
   * @param direction the direction that the player will move.
   */
  private void move (int direction)
  {
    if (playerMovement ==0)
    {
      playerMovement +=PLAYER_SPEED;
      player.changeDirection(direction);
      processing = direction;
      if (!map.isOccupied (player.getX(), player.getY()-1) && direction == Entity.NORTH && player.getY() - 1 >= 0 + buffers[0].renderHeight()/2)
      {
        map.setClaimed (player.getX(), player.getY() - 1, true);
        modY+=PLAYER_SPEED;
        moving = true;
      }
      else if (!map.isOccupied (player.getX(), player.getY()+1) && direction == Entity.SOUTH && player.getY() + 1 < map.height() - buffers[0].renderHeight()/2)
      {
        map.setClaimed (player.getX(), player.getY() + 1, true);
        modY-=PLAYER_SPEED;
        moving = true;
      }
      else if (!map.isOccupied (player.getX() + 1, player.getY()) && direction == Entity.EAST && player.getX() + 1 < map.width() - buffers[0].renderWidth()/2 )
      {
        map.setClaimed (player.getX() + 1, player.getY(), true);
        modX-=PLAYER_SPEED;
        moving = true;
      } 
      else
      {
        if (!map.isOccupied (player.getX() - 1, player.getY()) && direction == Entity.WEST && player.getX() - 1 >= 0 + buffers[0].renderWidth()/2)
        {
          map.setClaimed (player.getX() - 1, player.getY(), true);
          modX+=PLAYER_SPEED;
          moving = true;
        }
      }
    }
    else if (playerMovement > 0 && playerMovement < TILE_SIZE)
    {
      playerMovement+=PLAYER_SPEED;
      if (playerMovement % (TILE_SIZE/4) == 0)
        player.nextFrame();
      if (moving)
      {
        if (direction == Entity.NORTH)
          modY+=PLAYER_SPEED;
        else if (direction == Entity.SOUTH)
          modY-=PLAYER_SPEED;
        else if (direction == Entity.EAST)
          modX-=PLAYER_SPEED;
        else
        {
          if (direction == Entity.WEST)
            modX+=PLAYER_SPEED;
        }
      }
    }
    else
    {
      if (modY != 0 || modX != 0)
      {
        map.setClaimed(player.getX(), player.getY(), false);
        player.move();
        for (int x = 0; x < buffers.length; x ++)
          buffers[x].centerView(player.getX(),player.getY());
        modY =0;
        modX =0;
      }
      processing = -1;
      playerMovement = 0;
      moving = false;
    }
  }
  
  /**
   * This method returns the index of the character at the specified coordinates or -1 if it does not exist.
   * @param x the x coordinate to be checked.
   * @param y the y coordinate to be checked.
   * @return int the index of the character at the specified coordinates or -1 if it does not exist.
   */
  private int findCharacterAt (int x, int y)
  {
    for (int z = 0; z < computerCharacters.length; z++)
    {
      if (computerCharacters[z].getX() == x && computerCharacters[z].getY() == y)
      {
        return z;
      }
    }
    return -1;
  }
  
  /**
   * This method handles the interaction of the player in the specified direction.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>x </b> Variable for the for loop and used to tell whether or not the method should check for AI interaction.
   * @param direction of the player.
   */
  public void interact (int direction)
  {
    int x;
    if (direction == Entity.NORTH)
    {
      for (x = 0; x < Map.MAX_LAYERS; x++)
      {
        if(map.isFilled(player.getX(), player.getY() - 1, x) && map.retrieveTile(player.getX(), player.getY() - 1, x).isInteract())
        {
          map.retrieveTile(player.getX(), player.getY() - 1, x).interact(this);
          break;
        }
      }
      if (x == Map.MAX_LAYERS && findCharacterAt(player.getX(), player.getY() - 1) != -1 && npcMovement [findCharacterAt(player.getX(), player.getY() - 1)] == 0)
        computerCharacters[findCharacterAt(player.getX(), player.getY() - 1)].interact(this);
    }
    else if (direction == Entity.SOUTH)
    {
      
      for (x = 0; x < Map.MAX_LAYERS; x++)
      {
        if(map.isFilled(player.getX(), player.getY() + 1, x) && map.retrieveTile(player.getX(), player.getY() + 1, x).isInteract())
        {
          map.retrieveTile(player.getX(), player.getY() + 1, x).interact(this);
          break;
        }
      }
      if (x == Map.MAX_LAYERS && findCharacterAt(player.getX(), player.getY() + 1) != -1 && npcMovement [findCharacterAt(player.getX(), player.getY() + 1)] == 0)
        computerCharacters[findCharacterAt(player.getX(), player.getY() + 1)].interact(this);
    }
    else if (direction == Entity.EAST)
    {
      for (x = 0; x < Map.MAX_LAYERS; x++)
      {
        if(map.isFilled(player.getX() + 1, player.getY(), x) && map.retrieveTile(player.getX()  + 1, player.getY(), x).isInteract())
        {
          map.retrieveTile(player.getX()  + 1, player.getY(), x).interact(this);
          break;
        }
      }
      if (x == Map.MAX_LAYERS && findCharacterAt(player.getX()  + 1, player.getY()) != -1 && npcMovement [findCharacterAt(player.getX() + 1, player.getY())] == 0)
        computerCharacters[findCharacterAt(player.getX() + 1, player.getY())].interact(this);
    }
    else
    {
      if (direction == Entity.WEST)
      {
        for (x = 0; x < Map.MAX_LAYERS; x++)
        {
          if(map.isFilled(player.getX() - 1, player.getY(), x) && map.retrieveTile(player.getX()  - 1, player.getY(), x).isInteract())
          {
            map.retrieveTile(player.getX()  - 1, player.getY(), x).interact(this);
            break;
          }
        }
        if (x == Map.MAX_LAYERS && findCharacterAt(player.getX()  - 1, player.getY()) != -1 && npcMovement [findCharacterAt(player.getX() - 1, player.getY())] == 0)
          computerCharacters[findCharacterAt(player.getX() - 1, player.getY())].interact(this);
      }
    }
  }
  
  /**
   * This method checks whether or not the Tile at the specified x, y coordinate in the specified direction is available to be claimed by NPCs.
   * @param x the x coordinate to be checked.
   * @param y the y coordinate to be checked.
   * @param direction the direction to be checked.
   * @return true if the specified direction is available to be claimed by NPCs.
   */
  private boolean isFreeNPCSpace (int x, int y, int direction)
  {
    if (direction == Entity.NORTH && !map.isOccupied (x, y - 1) && (y - 1 != player.getY() || x != player.getX()))
      return true;
    if (direction == Entity.SOUTH && !map.isOccupied (x, y + 1) && (y + 1 != player.getY() || x != player.getX()))
      return true;
    if (direction == Entity.EAST && !map.isOccupied (x + 1, y) && (y != player.getY() || x + 1 != player.getX()))
      return true;
    if (direction == Entity.WEST && !map.isOccupied (x - 1, y) && (y != player.getY() || x - 1 != player.getX()))
      return true;
    return false;
  }
  
  /**
   * This method handles the movement of the NPC objects.
   * @param x the index of the NPC object to be moved.
   */
  private void moveNPC (int x)
  {
    if (npcMovement [x] == 0)
    {
      computerCharacters[x].changeDirection();
      if (isFreeNPCSpace(computerCharacters[x].getX(), computerCharacters[x].getY(), computerCharacters [x].direction()))
      {
        npcMovement [x] += NPC_SPEED; 
        if (computerCharacters[x].direction() == Entity.NORTH)
          map.setClaimed (computerCharacters [x].getX(), computerCharacters[x].getY() - 1, true);
        if (computerCharacters[x].direction() == Entity.SOUTH)
          map.setClaimed (computerCharacters [x].getX(), computerCharacters[x].getY() + 1, true);
        if (computerCharacters[x].direction() == Entity.EAST)
          map.setClaimed (computerCharacters [x].getX() + 1, computerCharacters[x].getY(), true);
        if (computerCharacters[x].direction() == Entity.WEST)
          map.setClaimed (computerCharacters [x].getX() - 1, computerCharacters[x].getY(), true);
        ((EntityBuffer)buffers[2]).moveCharacter(x);
      }
    }
    else if (npcMovement [x] > 0 && npcMovement [x] < TILE_SIZE)
    {
      npcMovement [x] += NPC_SPEED;
      if (npcMovement[x] % (TILE_SIZE/4) == 0)
        computerCharacters[x].nextFrame();
      ((EntityBuffer)buffers[2]).moveCharacter(x);
    }
    else
    {
      ((EntityBuffer)buffers[2]).resetCharacter(x);
      map.setClaimed(computerCharacters[x].getX(), computerCharacters[x].getY(), false);
      computerCharacters [x].move();
      npcMovement [x] = 0;
    }
  }
  /**
   * This method sets the map to the specified map and the player at the specified x and y coordinates.
   * @param map the map to be loaded.
   * @param x the player's x coordinate.
   * @param y the player's y coordinate.
   */
  public void setMap(Map map, int x, int y)
  {
    this.map = map;
    player.setX (x);
    player.setY (y);
    computerCharacters = new NPC [map.getComputerCharacters().size()];
    npcMovement = new int [computerCharacters.length];
    for (int z = 0; z < computerCharacters.length; z++)
      computerCharacters [z] = map.getComputerCharacters ().get(z);
    initializeBuffers();
  }
  
  /**
   * This method handles the opening of doors.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>difficulty </b> The String difficulty used to display on the highscores.
   * @param door the door that will be opened.
   */
  public void openDoor (Door door)
  {
    if (door.locked() && map.cleared())
      door.setLocked(false);
    if (!door.locked())
    {
      if (door.currentFrame() == 0 && !door.animating())
      {
        door.setAnimating(true);
        processing = DOOR;
        interactingTile = door;
      }
      if (door.isOpen())
      {
        door.setAnimating(false);
        processing = -1;
        interactingTile = null;
        try
        {
          String difficulty;
          if (Board.difficulty ==0)
            difficulty = "Easy";
          else if (Board.difficulty ==1)
            difficulty = "Medium";
          else
            difficulty = "Hard";
          if (!door.isWinGate())
          {
            saveGame();
            setMap (Map.mapFromFile("Save/"+door.mapName()), door.startX(),door.startY());
            saveGame();
          }
          else
          {
            TerrarithmeticApp.highscores.addScore(new HighScore (player.name(), player.wins(), difficulty));
            frame.credits();
          }
        }
        catch (IOException e)
        {
        }
      }
    }
    else
    {
      if (processing == -1)
      {
        processing = DOOR;
        setDialogue(door.background(), door.lockText());
        interactingTile = door;
      }
      else
      {
        if (TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_SPACE) && TerrarithmeticApp.bk.hasNewInput())
        {
          clearDialogue ();
          processing = -1;
          interactingTile = null;
        } 
      } 
    }
  }
  
  /**
   * This method handles the sign dialogues.
   * @param sign the sign to be displayed.
   */
  public void signDialogue (Sign sign)
  {
    if (processing == -1)
    {
      processing = SIGN;
      setDialogue(sign.background(),sign.text());
      interactingTile = sign;
    }
    else
    {
      if (TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_SPACE) && TerrarithmeticApp.bk.hasNewInput())
      {
        if (!sign.finished())
        {
          sign.nextPage();
          setDialogue(sign.background(),sign.text());
        }
        else
        {
          clearDialogue ();
          processing = -1;
          interactingTile = null;
        }
      }
    }
  }
  
  /**
   * This method handles the npc dialogue.
   * @param computerCharacter the NPC whose dialogue will be displayed.
   */
  public void npcDialogue (InformationNPC computerCharacter)
  {
    if (processing == -1)
    {
      processing = NPC_CHAT;
      setDialogue(computerCharacter.dialogueBack(), computerCharacter.dialogueText());
      interactingNPC = computerCharacter;
      if (player.direction() == Entity.NORTH)
        computerCharacter.changeDirection (Entity.SOUTH);
      else if (player.direction() == Entity.SOUTH)
        computerCharacter.changeDirection (Entity.NORTH);
      else if (player.direction() == Entity.EAST)
        computerCharacter.changeDirection (Entity.WEST);
      else
      {
        if (player.direction() == Entity.WEST)
          computerCharacter.changeDirection (Entity.EAST);
      }
    }
    else
    {
      if (TerrarithmeticApp.bk.keyCodeEquals (KeyEvent.VK_SPACE) && TerrarithmeticApp.bk.hasNewInput())
      {
        if (!computerCharacter.finished())
        {
          computerCharacter.nextLine();
          setDialogue(computerCharacter.dialogueBack(),computerCharacter.dialogueText());
        }
        else
        {
          clearDialogue ();
          processing = -1;
          interactingNPC = null;
        }
      }
    }
  }
  
  /**
   * This method handles the interaction with chests.
   * @param chest the chest to be interacted with.
   */
  public void chestFound (Chest chest)
  {
    if (processing == -1)
    {
      processing = CHEST;
      setDialogue(chest.background(), chest.chestText());
      player.addCard(chest.newCard());
    }
    else
    {
      if (TerrarithmeticApp.bk.keyCodeEquals (KeyEvent.VK_SPACE) && TerrarithmeticApp.bk.hasNewInput())
      {
        for (int x = 0; x < Map.MAX_LAYERS; x++)
        {
          if (player.direction() == Entity.NORTH && map.isFilled(player.getX(), player.getY()-1, x) && map.retrieveTile(player.getX(), player.getY()-1, x).isInteract())
            map.removeTile (player.getX(), player.getY()-1, x);
          else if (player.direction() == Entity.SOUTH && map.isFilled(player.getX(), player.getY()+1, x) && map.retrieveTile(player.getX(), player.getY()+1, x).isInteract())
            map.removeTile (player.getX(), player.getY()+1, x);
          else if (player.direction() == Entity.EAST && map.isFilled(player.getX()+ 1, player.getY(), x) && map.retrieveTile(player.getX() + 1, player.getY(), x).isInteract())
            map.removeTile (player.getX()+1, player.getY(), x);
          else
          {
            if (player.direction() == Entity.WEST && map.isFilled(player.getX()- 1, player.getY(), x) && map.retrieveTile(player.getX()- 1, player.getY(), x).isInteract())
              map.removeTile (player.getX()-1, player.getY(), x);
          }
          if (x == 2)
          {
            ((TileBuffer)buffers[3]).setBuffer (map.retrieveLayer(x));
            ((TileBuffer)buffers[3]).redraw();
          }
          else
          {
            ((TileBuffer)buffers[x]).setBuffer (map.retrieveLayer(x));
            ((TileBuffer)buffers[x]).redraw();
          }
        }
        clearDialogue ();
        processing = -1;
      }
    }
  }
  
  /**
   * This method sets the dialogue on the screen with the specified text and background.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   * <p>
   * <b>font </b> The font that the String will be drawn with.
   * <p>
   * <b>count </b> This is used for spacing on the dialogue.
   * <p>
   * @param back the Sprite that will be used as the dialogue background.
   * @param text the text to be set on the dialogue.
   */
  public void setDialogue (Sprite back, String text)
  {
    Graphics2D g2D = dialogue.createGraphics();
    g2D.drawImage(back.image(),20,20,null);
    g2D.setColor(new Color(0,0,0));
    Font font = new Font("Minecraftia", Font.PLAIN, 18);
    g2D.setFont(font);
    int count = 80;
    for (String line : text.split("~"))
    {
      g2D.drawString(line,55,count);
      count += 25;
    }
    g2D.dispose();
  }
  
  /**
   * This method clears all images on the dialogue layer.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   */
  public void clearDialogue ()
  {
    Graphics2D g2D = dialogue.createGraphics();
    g2D.setBackground(new Color(0,0,0,0));
    g2D.clearRect (0,0,dialogue.getWidth(),dialogue.getHeight());
    g2D.dispose();
  }
  
  /**
   * This method displays the options on the pause menu.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   * <p>
   * <b>font </b> The Font used to display text.
   */
  private void menuText()
  {
    Graphics2D g2D = dialogue.createGraphics();
    try
    {
      g2D.drawImage (new Sprite ("PauseBack.png").image(), 785,10,null);
      g2D.drawImage (new Sprite ("border.png").image(), 820,40 + (currentSelection-1)*60,null);
    }
    catch (IOException e)
    {
    }
    Font font = new Font("Minecraftia", Font.PLAIN, 18);
    g2D.setColor (Color.BLACK);
    g2D.setFont(font);
    g2D.drawString ("Stats", 842,85);
    g2D.drawString ("Quit", 842,145);
    g2D.drawString ("Cancel", 842,205);
    g2D.dispose();
  }
  
  /**
   * This method creates the pause menu during the game.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   */
  public void pauseMenu()
  {
    if (processing == -1)
    {
      processing = PAUSE;
      currentSelection = 1;
      menuText();
    }
    if (TerrarithmeticApp.bk.hasNewInput())
    {
      clearDialogue();
      if (TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_UP))
        currentSelection --;
      else if (TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_DOWN))
        currentSelection ++;
      else if (TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_P))
      {
        processing = -1;
      }
      else
      {
        if (TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_SPACE))
        {
          if (currentSelection == 1)
          {
            try
            {
              Graphics2D g2D = dialogue.createGraphics();
              g2D.setColor (Color.BLACK);
              g2D.setFont(new Font("Minecraftia", Font.BOLD, 24));
              g2D.drawImage (new Sprite("PauseDisplay.png").image(),90,10,null);
              g2D.drawString("My Deck",330,70);
              Sprite [] numberCards = new Sprite [10];
              for (int x = 1; x <= numberCards.length; x++)
              {
                numberCards [x-1] = new Sprite("cards/numbers" + x + ".png");
                numberCards [x-1].scaleImage(0.5,0.5);
              }
              Sprite [] operatorCards = new Sprite [4];
              for (int x = 0; x < operatorCards.length; x++)
              {
                operatorCards [x] = new Sprite ("cards/operators" + x + ".png");
                operatorCards [x].scaleImage(0.5,0.5);
              }
              g2D.setFont(new Font("Minecraftia", Font.PLAIN, 18));
              for (int x = 0; x < 5; x++)
              {
                g2D.drawImage(numberCards [x].image(), 130 + 113 * (x), 80, null);
                g2D.drawString("x" + player.deck().countNumbers(x + 1),150 + 113* (x),175);
                g2D.drawImage(numberCards [x+5].image(),130 + 113 * (x), 180, null);
                g2D.drawString("x" + player.deck().countNumbers(x + 6),150 + 113* (x),275);
                if (x < 4)
                  g2D.drawImage(operatorCards [x].image(),130 + 113 * (x), 280, null);
              }
              g2D.drawString("x" + player.deck().countOperators("+"),150,375);
              g2D.drawString("x" + player.deck().countOperators("-"),263,375);
              g2D.drawString("x" + player.deck().countOperators("x"),376,375);
              g2D.drawString("x" + player.deck().countOperators("/"),489,375);
              g2D.drawString("Wins: " + player.wins(),570,320);
              g2D.drawString("Loss: " + player.losses(),570,360);
              g2D.dispose();
            }
            catch (IOException e)
            {
              e.printStackTrace();
              System.out.println(e.getMessage());
            }
          }
          else if (currentSelection == 2)
          {
            processing = -1;
            frame.mainMenu(TerrarithmeticApp.OPEN_WORLD);
          }
          else
          {
            processing = -1;
          }
        }
      }
      if (currentSelection < 1)
        currentSelection = 3;
      else
      {
        if (currentSelection > 3)
          currentSelection = 1;
      }
      if (processing != -1)
        menuText();
    }
  }
  
  /**
   * This method starts the card game.
   @param opponent the NPC that will initiate the card game.
   */
  public void startGame (CardGameNPC opponent)
  {
    if (processing == -1)
    {
      processing = CARD_GAME;
      interactingNPC = opponent; 
      if (player.direction() == Entity.NORTH)
        opponent.changeDirection (Entity.SOUTH);
      else if (player.direction() == Entity.SOUTH)
        opponent.changeDirection (Entity.NORTH);
      else if (player.direction() == Entity.EAST)
        opponent.changeDirection (Entity.WEST);
      else
      {
        if (player.direction() == Entity.WEST)
          opponent.changeDirection (Entity.EAST);
      }
      frame.switchGamePanel(player, opponent);
    }
    else
    {
      processing = -1;
      interactingNPC = null;
    }
  }
  
  /**
   * This method controls what events will take place.
   */
  public void tick ()
  {
    if (processing == -1)
    {
      if(TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_UP))
      {
        move(Entity.NORTH);
      }
      else if(TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_DOWN))
        move(Entity.SOUTH);
      else if(TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_RIGHT))
        move(Entity.EAST);
      else if(TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_LEFT))
        move(Entity.WEST);
      else if(TerrarithmeticApp.bk.keyCodeEquals(KeyEvent.VK_SPACE) && TerrarithmeticApp.bk.hasNewInput())
        interact(player.direction());
      else if (TerrarithmeticApp.bk.keyCodeEquals (KeyEvent.VK_P) && TerrarithmeticApp.bk.hasNewInput())
        pauseMenu();
    }
    else
    {
      if(processing == Entity.NORTH || processing == Entity.SOUTH || processing == Entity.EAST || processing == Entity.WEST)
        move (processing);
      else if (processing == DOOR)
        openDoor ((Door) interactingTile);
      else if (processing == SIGN)
        signDialogue ((Sign) interactingTile);
      else if (processing == PAUSE)
        pauseMenu();
      else if (processing == NPC_CHAT)
        npcDialogue ((InformationNPC) interactingNPC);
      else if (processing == CHEST)
        chestFound ((Chest) interactingTile);
      else
      {
        if (processing == CARD_GAME)
          startGame((CardGameNPC) interactingNPC);
      }
    }
    
    for (int x = 0; x < computerCharacters.length; x++)
    {
      if (npcMovement [x] !=0)
      {
        moveNPC (x);
      }
      else
      {
        if (npcMovement [x] == 0 && (int) (Math.random() * 100) == 1 && !computerCharacters[x].equals(interactingNPC))
        {
          moveNPC (x);
        }
      }
    }
    map.incrementFrame();
    updateBuffers();
  }
  
  /**
   * This method serves as a drawing component for the OpenWorldPanel class and refreshes the screen.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g </b> Reference to the Graphics class which will be used to draw images onto the screen.
   * @param g used for drawing images onto the screen.
   */ 
  public void refresh (Graphics g)
  {
    for (int x = 0; x < buffers.length; x++)
    {
      if (x ==2)
        g.drawImage(player.retrieveFrame().image(), 7 * TILE_SIZE, 4 * TILE_SIZE - TILE_SIZE/4, null);
      g.drawImage(buffers [x].getView(),modX - TILE_SIZE, modY - TILE_SIZE, null);
    }
    g.drawImage(dialogue, 0,0,null);
  }
}