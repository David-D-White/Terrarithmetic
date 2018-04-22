/**
 * The SetPiece class is used to load a range of tiles from a file.
 * <p>
 * SetPiece
 * <p>
 * Date: 2016/05/10
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/10
 */ 
import java.util.*;
import java.io.*;
public class SetPiece
{
  /**This 3d array holds the Tile values that make up the SetPiece.*/
  private Tile [][][] piece;
  /**This stores the height of the array.*/
  private int height;
  /**This stores the width of the array.*/
  private int width;
  
  /**
   * This constructor loads the set of Tiles from a folder/files based on the specified file path width the String parameter.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>s </b> This is used to point to the Scanner class.
   * @param piece the file/folder to read from.
   * @throws IOException when reading from a file.
   */
  public SetPiece (String piece) throws java.io.IOException
  {
    Scanner s = new Scanner (new File(".//resources/SetPieces/"+piece+"/"+piece+".txt"));
    int tileType = -1;
    width = s.nextInt();
    height = s.nextInt();
    this.piece = new Tile [width] [height] [Map.MAX_LAYERS];
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y <height; y++)
      {
        tileType = s.nextInt();
        if (tileType == Map.OBSTACLE)
          this.piece [x] [y] [s.nextInt()] = new Obstacle (new Sprite ("SetPieces/" + piece + "/" + piece + s.nextInt () + ".png"));
        else if (tileType == Map.BACK_TILE)
          this.piece [x] [y] [s.nextInt()] = new BackTile (new Sprite ("SetPieces/" + piece + "/" + piece + s.nextInt () + ".png"));
        else if (tileType == Map.SIGN)
        {
          this.piece [x] [y] [s.nextInt()] = new Sign (new Sprite ("SetPieces/" + piece + "/" + piece + s.nextInt () + ".png"), new Sprite(s.next()), s.nextLine());
        }
        else
        {
          if (tileType == Map.DOOR)
          {
            if (s.next().equals("true"))
            {
              if (s.next().equals("true"))
                this.piece[x][y] [s.nextInt()] = new Door (Tile.loadAnim (s.next()), s.next(), s.nextInt (), s.nextInt(), new Sprite (s.next()), s.next());
              else
                this.piece[x][y] [s.nextInt()] = new Door (Tile.loadAnim (s.next()), s.next(), s.nextInt (), s.nextInt());
            }
            else
              this.piece[x][y] [s.nextInt()] = new Door (new Sprite ("SetPieces/" + piece + "/" + piece + s.nextInt () + ".png"), s.next(), s.nextInt (), s.nextInt()); 
          } 
        }
      }
    }
  }
  /**
   * This method checks whether or not the Tile location x,y,layer in the SetPiece is not null. 
   * @param x the x coordinate of the Tile location.
   * @param y the y coordinate of the Tile location.
   * @param layer the layer of the Tile location.
   * @return true if the tile location x,y,layer in the SetPiece is not null. 
   */
  public boolean isFilled (int x, int y, int layer)
  {
    return !(piece [x] [y] [layer] == null);
  }
  /**
   * This method returns the Tile at the specified x and y coordinates at the specified layer. 
   * @param x the x coordinate of the Tile location.
   * @param y the y coordinate of the Tile location.
   * @param layer the layer of the Tile location.
   * @return Tile at the value at the x,y coordinate at the specified layer. 
   */
  public Tile retrieveTile (int x, int y, int layer)
  {
    return piece [x] [y] [layer];
  }
  /**
   * This method returns the height of the SetPiece.
   * @return int the height of the SetPiece.
   */
  public int height ()
  {
    return height;
  }
  /**
   * This method returns the width of the SetPiece.
   * @return int the width of the SetPiece.
   */
  public int width()
  {
    return width;
  }
}