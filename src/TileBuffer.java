import java.awt.image.*;
import java.awt.*;
/**
 * The TileBuffer class is used for taking information from the Tile components of the game and formatting it into a display.
 * <p>
 * TileBuffer
 * <p>
 * Date: 2016/05/26
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/26
 */
public class TileBuffer extends Buffer
{
  /**Tiles to be rendered by this class.*/
  private Tile [] [] tiles;
  /**Numerical x value for the top left corner of this buffer in relation to Tiles. */
  private int tileX;
  /**Numerical y value for the top left corner of this buffer in relation to Tiles. */
  private int tileY;

    /**
   * This constructs an instance of the TileBuffer class with the specified Tile array, the specified width and height, and the specified Tile size.
   * @param tiles[][] the array of Tiles to be rendered by the class. 
   * @param width this is the width of the screen this class is buffering for in pixels.
   * @param height this is the height of the screen this class is buffering for in pixels.
   * @param tileSize the specified size of the game components in pixels.
   */
  public TileBuffer (Tile [] [] tiles, int width, int height,int tileSize)
  {
    super(width, height, tileSize);
    this.tiles = tiles;
    redraw();
  }
  
  /**
   * This method centers the view of the buffer around the specified x and y coordinates in relation to the Tile components.
   * @param x the x coordinate.
   * @param y the y coordinate.
   */
  public void centerView (int x, int y)
  {
    tileX = x - 8;
    tileY = y - 5;
    redraw();
  }

  /**
   * This method redraws all the Tiles from the Tile array.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   */
  public void redraw ()
  {
    Graphics2D g2D = view.createGraphics();
    g2D.setBackground (new Color (0,0,0,0));
    g2D.clearRect(0,0,view.getWidth(),view.getHeight());
    for (int x = 0; x < renderWidth(); x++)
    {
      for (int y = 0; y < renderHeight(); y++)
      {
        if (tiles [tileX + x] [tileY + y] != null)
        {
          if (tiles [tileX + x] [tileY + y].isAnim() && tiles [tileX + x][tileY+y].animating())
          {
          g2D.drawImage(tiles [tileX + x] [tileY + y].retrieveFrame().image(), x * tileSize , y * tileSize, null);
          }
          else
          {
            g2D.drawImage(tiles [tileX + x] [tileY + y].tileImage(), x * tileSize , y * tileSize, null);
          }
        }
      }
    }
    g2D.dispose();
  }
  
  /**
   * This method resets the buffer to buffer tiles from the specified Tile array.
   * @param tiles[][] the specified Tile array. 
   */
  public void setBuffer (Tile [] [] tiles)
  {
    this.tiles = tiles;
  }
  
  /**
   * This method redraws only the animated Tiles from the Tile array.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g2D </b> Reference to the Graphics2D class which will be used to draw images onto the screen.
   */
  public void update()
  {
    Graphics2D g2D = view.createGraphics();
    for (int x = 0; x < renderWidth(); x++)
    {
      for (int y = 0; y < renderHeight(); y++)
      {
        if (tiles [tileX + x] [tileY + y] != null)
        {
          if (tiles [tileX + x] [tileY + y].isAnim() && tiles[tileX+x][tileY+y].animating())
          {
          g2D.drawImage(tiles [tileX + x] [tileY + y].retrieveFrame().image(), x * tileSize , y * tileSize, null);
          }
        }
      }
    }
    g2D.dispose();
  }
}