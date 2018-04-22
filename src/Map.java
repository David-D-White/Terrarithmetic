import java.io.*;
import java.util.*;
/**
 * This class holds an array of Tiles which serve as a map for the main game. 
 * <p>
 * Map
 * <p>
 * Date: 2016/05/18
 * @author David White and Sepehr Hosseini 
 * @version 3 2016/05/18
 */ 
public class Map 
{
  /**This is an array of Tiles that serves as a reference for the map. */
  private Tile map [] [] [];
  /**This is a boolean array of which map locations have been claimed by an Entity. */
  private boolean [] [] claimed;
  /**This defines the height of the map.*/
  private int height;
  /**This defines the width of the map.*/
  private int width;
  /**This defines the numerical value for an Obstacle.*/
  static final int OBSTACLE = 0;
  /**This defines the numerical value for a BackTile.*/
  static final int BACK_TILE = 1;
  /**This defines the numerical value for a Sign.*/
  static final int SIGN = 2;
  /**This defines the numerical value for a Door.*/
  static final int DOOR = 3;
  /**This defines the numerical value for a Chest.*/
  static final int CHEST = 4;
  /**This defines the numerical value for a CardGameNPC.*/
  static final int CARD_NPC = 0;
  /**This defines the numerical value for an InformationNPC.*/
  static final int INFO_NPC = 1;
  /**This defines the total number of layers in the map.*/
  static final int MAX_LAYERS = 3;
  /**This defines the numerical value for the bottom layer.*/
  static final int BOTTOM = 0;
  /**This defines the numerical value for the middle layer.*/
  static final int MIDDLE = 1;
  /**This defines the numerical value for the top layer.*/
  static final int TOP = 2;
  /**This ArrayList holds the values of all the different Tiles in the Map class.*/
  private ArrayList <Tile> tileTypes = new ArrayList <Tile> ();
  /**This ArrayList holds the values of all the different NPCs on this map.*/
  private ArrayList <NPC> computerCharacters = new ArrayList <NPC> ();
  /**This variable is used for save states to track the file name so it knows which file to save in.*/
  private String mapName;

  /**
   * This constructor creates an instance of the Map class of the specified width and height.
   * @param width the width of the map.
   * @param height the height of the map.
   * @param mapName tracks the file name for the save states.
   */ 
  public Map (int width, int height, String mapName)
  {
    this.height = height;
    this.width = width;
    this.mapName = mapName;
    map = new Tile [width] [height] [MAX_LAYERS];
    claimed = new boolean [width] [height];
  }
  
  /**
   * This method returns the file name for the save states.
   * @return String the file name for the save states.
   */ 
  public String mapName()
  {
    return mapName;
  }
  
  /**
   * This method returns the height of the map.
   * @return int returns the height of the map.
   */ 
  public int height ()
  {
    return height;
  }
  /**
   * This method returns the width of the map.
   * @return int returns the width of the map.
   */ 
  public int width ()
  {
    return width;
  }
  /**
   * This calls the nextFrame method on all the animated Tiles in the tileTypes array.
   */ 
  public void incrementFrame()
  {
    for (int x = 0; x < tileTypes.size(); x++)
    {
      if (tileTypes.get(x).isAnim() && tileTypes.get(x).animating())
        tileTypes.get(x).nextFrame();
    }
  }
  
  /**
   * This method returns all the tiles on the specified layer.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>temp </b> The tile array to be returned.
   * @param layer the layer of which the tiles will be retrieved from.
   * @return Tile[][] the specified layer of tiles.
   */ 
  public Tile [] [] retrieveLayer(int layer)
  {
    Tile [] [] temp = new Tile [width] [height];
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        temp [x] [y] = map [x] [y] [layer];
      }
    }
    return temp;
  }
  
  /**
   * This method changes the Tile at the specified x and y coordinate with the specified layer to the specified Tile.
   * @param tile the tile that is going to be inputted at the specified location. 
   * @param x the specified x coordinate.
   * @param y the specified y coordinate.
   * @param layer the specified layer.
   */ 
  public void setTile (Tile tile, int x, int y, int layer) 
  {
    map [x] [y] [layer] = tile;
    if (!tileTypes.contains(tile))
      tileTypes.add(tile);   
  }
  /**
   * This method retrives the Tile at the specified x and y coordinate with the specified layer.
   * @param x the specified x coordinate.
   * @param y the specified y coordinate.
   * @param layer the specified layer.
   * @return Tile the Tile at the specified x and y coordinated with the specified layer.
   */ 
  public Tile retrieveTile (int x, int y, int layer)
  {
    return map [x] [y] [layer];
  }
  /**
   * This method removes the Tile at the specified x and y coordinate with the specified layer.
   * @param x the specified x coordinate.
   * @param y the specified y coordinate.
   * @param layer the specified layer.
   */ 
  public void removeTile (int x, int y, int layer)
  {
    Tile temp = map [x] [y] [layer];
    map [x][y][layer] = null;
    if (getOccurrences (temp) == 0)
      tileTypes.remove(temp);
  }
  /**
   * This method checks whether there is a Tile at the specified x and y coordinate at the specified layer. 
   * @param x the specified x coordinate.
   * @param y the specified y coordinate.
   * @param layer the specified layer.
   * @return true if the Tile at the specified x and y coordinate at the specified layer is not null.
   */ 
  public boolean isFilled (int x, int y, int layer)
  {
    return !(map [x] [y] [layer] == null);
  }
  
    /**
   * This method sets whether or not a certain location has been claimed by an entity.
   * @param x the specified x coordinate.
   * @param y the specified y coordinate.
   * @param isClaimed whether or not the map location is claimed.
   */ 
  public void setClaimed (int x, int y, boolean isClaimed)
  {
    claimed [x] [y] = isClaimed;
  }
  
  
  /**
   * This method checks whether or not any of the Tiles at the specified x and y coordinate are obstacles.
   * @param x the specified x coordinate.
   * @param y the specified y coordinate.
   * @return true if neither of Tiles on the three layers at the specified x and y coordinates are obstacles. 
   */ 
  public boolean isOccupied (int x, int y)
  {
    for (int z = 0; z < MAX_LAYERS; z ++)
    {
      if (map [x] [y] [z] != null && map [x] [y] [z].isObstacle())
        return true;
    }
    for (int z = 0; z < computerCharacters.size(); z++)
    {
      if (x == computerCharacters.get(z).getX() && y == computerCharacters.get(z).getY())
        return true;
    }
    if (claimed [x] [y])
      return true;
    return false;
  }
  
  /**
   * This method checks all the CardGameNPCs in the map have been faced.
   * @return true if all the CardGameNPCs in the map have been faced.
   */ 
  public boolean cleared ()
  {
    for (int x  = 0; x < computerCharacters.size(); x++)
    {
      if (computerCharacters.get(x).getType() == CARD_NPC && !((CardGameNPC)computerCharacters.get(x)).battled())
        return false;
    }
    return true;
  }
  
  /**
   * This method returns the number of occurences of the specified Tile in this class.
   * <b>Local variables: </b>
   * <p>
   * <b>occurences </b> The number of occurences of a specified tile.
   * @param tile holds the Tile to be searched for.
   * @return int the number of occurences.
   */ 
  public int getOccurrences (Tile tile)
  {
    int occurrences = 0;
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height;y++)
      {
        for (int z = 0; z < MAX_LAYERS; z++)
        {
          if (isFilled(x,y,z) && retrieveTile(x,y,z).equals(tile))
            occurrences ++;
        }
      }
    }
    return occurrences;
  }
  /**
   * This method loads a map from a file.
   * <b>Local variables: </b>
   * <p>
   * <b>length </b> The length of the map.
   * <p>
   * <b>s </b> This is used as a reference to the Scanner class.
   * <p>
   * <b>map </b> The new map which the file will load on.
   * <p>
   * <b>npcType </b> Finds the NPC type from file to be checked.
   * <p>
   * <b>tile </b> This is the Tile which will be specified in the file when setting Tiles on the map.
   * @param fileName the file name that will be used to load the map from a file.
   * @return Map the new map which the file has loaded.
   * @throws IOException when there is an error loading the file.
   */ 
  public static Map mapFromFile (String fileName) throws java.io.IOException
  {
    Scanner s = new Scanner (new File (".//resources/" + fileName));
    int length = s.nextInt(), height = s.nextInt(), tileType = 0;
    Map map = new Map (length,height, fileName.substring(fileName.indexOf("/") + 1));
    Tile tile = null;
    int npcType;
    for (int x = s.nextInt(); x > 0; x --)
    {
      tileType = s.nextInt();
      if (tileType == OBSTACLE)
      {
        if (s.next().equals("false"))
          tile = new Obstacle (new Sprite(s.next()));
        else
        {
          tile = new Obstacle (Tile.loadAnim(s.next()));
        }
      }
      else if (tileType == BACK_TILE)
      {
        if (s.next().equals("false"))
          tile = new BackTile (new Sprite (s.next()));
        else
          tile = new BackTile (Tile.loadAnim(s.next()));
      }
      else if (tileType == SIGN)
      {
        tile = new Sign (new Sprite(s.next()),new Sprite(s.next()),s.nextLine());
      }
      else if (tileType == CHEST)
      {
        if (s.next().equals("true"))
          tile = new Chest (Tile.loadAnim (s.next()), new Sprite (s.next()), new Card (s.nextInt(),new Sprite (s.next())));
        else
          tile = new Chest (Tile.loadAnim (s.next()), new Sprite (s.next()), new Card (s.next(),new Sprite (s.next()))); 
      }
      else
      {
        if (tileType == Map.DOOR)
        {
          if (s.next().equals("true"))
          {
            if (s.next().equals("true"))
              tile = new Door (Tile.loadAnim (s.next()), s.next(), s.nextInt (), s.nextInt(), new Sprite (s.next()), s.nextLine());
            else
              tile = new Door (Tile.loadAnim (s.next()), s.next(), s.nextInt (), s.nextInt());
          }
          else
            tile = new Door (new Sprite (s.next()), s.next(), s.nextInt (), s.nextInt()); 
        }
        if (s.next().equals("true"))
          ((Door) tile).setWinGate();
      } 
      for (int y = s.nextInt(); y > 0; y--)
      {
        map.setTile(tile,s.nextInt(), s.nextInt(),s.nextInt());
      }
    }
    for (int x = s.nextInt(); x > 0; x--)
    {
      npcType = s.nextInt();
      if (npcType == CARD_NPC)
        map.addComputerCharacter(new CardGameNPC (s.next(), s.nextInt(), s.nextInt(), Deck.deckFromFile(s.next()), new Sprite (s.next()), s.nextLine()));
      else
      {
        map.addComputerCharacter(new InformationNPC (s.next(), s.nextInt(), s.nextInt(), new Sprite (s.next()), s.nextLine()));
      } 
    }
    s.close();
    return map;
  }
    /**
   * This method writes a map to a file.
   * <b>Local variables: </b>
   * <p>
   * <b>p </b> This is used as reference to the PrintWriter class.
   * @param fileName the file name that the map will be written on.
   * @throws IOException when there is an error loading the file.
   */ 
  public void mapToFile (String fileName) throws java.io.IOException
  {
    PrintWriter p = new PrintWriter (new FileWriter(".//resources/" + fileName));
    p.println(width + " " + height);
    p.println(tileTypes.size());
    for (int x = 0; x < tileTypes.size(); x ++)
    {
      p.print (tileTypes.get(x).getTileType() + " ");
      if (tileTypes.get(x).getTileType() == OBSTACLE || tileTypes.get (x).getTileType() == BACK_TILE)
      {
        if (tileTypes.get(x).isAnim())
          p.println("true " + tileTypes.get(x).retrieveFrame().imageName().substring(15,tileTypes.get(x).retrieveFrame().imageName().substring(15).indexOf("/")+15));
        else
          p.println("false " + tileTypes.get(x).getSprite().imageName());
      }
      else if (tileTypes.get(x).getTileType() == SIGN)
      {
        p.println(tileTypes.get(x).getSprite().imageName() + " " + ((Sign)tileTypes.get(x)).background().imageName() + " " + ((Sign)tileTypes.get(x)).textFull());
      }
      else if (tileTypes.get(x).getTileType() == CHEST)
      {
        if (!((Chest)tileTypes.get(x)).newCard().isOperator())
        {
          p.println ("true " + tileTypes.get(x).retrieveFrame().imageName().substring(15,tileTypes.get(x).retrieveFrame().imageName().substring(15).indexOf("/")+15) + " " + ((Chest)tileTypes.get(x)).background().imageName() + " " + ((Chest)tileTypes.get(x)).newCard().getValue() + " " + ((Chest)tileTypes.get(x)).newCard().cardImage().imageName());
        }
        else
          p.println ("false " + tileTypes.get(x).retrieveFrame().imageName().substring(15,tileTypes.get(x).retrieveFrame().imageName().substring(15).indexOf("/")+15) + " " + ((Chest)tileTypes.get(x)).background().imageName() + " " + ((Chest)tileTypes.get(x)).newCard().getOperator() + " " + ((Chest)tileTypes.get(x)).newCard().cardImage().imageName()); 
      }
      else
      {
        if (tileTypes.get(x).getTileType() == Map.DOOR)
        {
          if (tileTypes.get(x).isAnim())
          {
            if (((Door)tileTypes.get(x)).locked())
              p.println ("true true " + tileTypes.get(x).retrieveFrame().imageName().substring(15,tileTypes.get(x).retrieveFrame().imageName().substring(15).indexOf("/")+15) + " " + ((Door)tileTypes.get(x)).mapName() + " " + ((Door)tileTypes.get(x)).startX() + " " + ((Door)tileTypes.get(x)).startY() + " " + ((Door)tileTypes.get(x)).background().imageName() + " " + ((Door)tileTypes.get(x)).lockText());
            else
              p.println ("true false " + tileTypes.get(x).retrieveFrame().imageName().substring(15,tileTypes.get(x).retrieveFrame().imageName().substring(15).indexOf("/")+15) + " " + ((Door)tileTypes.get(x)).mapName() + " " + ((Door)tileTypes.get(x)).startX() + " " + ((Door)tileTypes.get(x)).startY());
          }
          else
            p.println ("false " + tileTypes.get(x).getSprite().imageName() + " " + ((Door)tileTypes.get(x)).mapName() + " " + ((Door)tileTypes.get(x)).startX() + " " + ((Door)tileTypes.get(x)).startY()); 
        }
        if (((Door)tileTypes.get(x)).isWinGate())
          p.println("true");
        else
          p.println("false");
      } 
      p.println(getOccurrences(tileTypes.get(x)));
      for (int a = 0; a < width; a++)
      {
        for (int b = 0; b < height; b++)
        {
          for (int c = 0; c <MAX_LAYERS; c++)
          {
            if (isFilled(a,b,c) && retrieveTile(a,b,c).equals(tileTypes.get(x)))
              p.print (a + " " + b + " " + c + " ");
          }
        }
      }
      p.println();
    }
    p.println(computerCharacters.size());
    for (int x = 0; x < computerCharacters.size(); x++)
    {
      if (computerCharacters.get(x).getType() == CARD_NPC && !((CardGameNPC) computerCharacters.get(x)).battled())
        p.println(CARD_NPC + " " + computerCharacters.get(x).characterName() + " " + computerCharacters.get(x).getX()+ " " + computerCharacters.get(x).getY()+ " " + ((CardGameNPC)computerCharacters.get(x)).deck().deckName()+ " " + ((CardGameNPC)computerCharacters.get(x)).dialogueBack().imageName()+ " " + ((CardGameNPC)computerCharacters.get(x)).fullText());
      else
        p.println(INFO_NPC + " " + computerCharacters.get(x).characterName() + " " + computerCharacters.get(x).getX() + " " + computerCharacters.get(x).getY()+ " " +  ((InformationNPC)computerCharacters.get(x)).dialogueBack().imageName()+ " " + ((InformationNPC)computerCharacters.get(x)).fullText());
    }
    p.close();
  }
  
    /**
   * This method modifies a range of tiles based on a specified SetPiece.
   * @param set the specified SetPiece used to modify a range of tiles.
   * @param startX the specified x coordinate (top left).
   * @param startY the specified y coordinate (top left).
   */ 
  public void drawSet(SetPiece set, int startX, int startY)
  {
    for (int x = 0; x < set.width(); x++)
    {
      for (int y = 0; y < set.height(); y++)
      {
        for (int z = 0; z < MAX_LAYERS; z++)
        {
          if (set.isFilled(x,y,z))
            setTile (set.retrieveTile(x,y,z), startX + x, startY + y, z);
        }
      }
    }
  }
  
  /**
   * This method adds a computer character to the map.
   * @param character the specified NPC to be added.
   */ 
  public void addComputerCharacter (NPC character)
  {
    if (character.getX() >=0 && character.getX() < width && character.getY() >=0 && character.getY() < height && !isOccupied (character.getX(), character.getY()))
    {
      computerCharacters.add(character);
      setClaimed(character.getX(),character.getY(),true);
    }
  }
  
  /**
   * This method returns a list of all the NPC characters in this map.
   * @return ArrayList the NPC characters in this map.
   */ 
  public ArrayList <NPC> getComputerCharacters ()
  {
    return computerCharacters;
  }
}