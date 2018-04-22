import java.awt.image.*;
/**
 * The Buffer class is used for taking information from game components and formatting it into a display.
 * <p>
 * Buffer
 * <p>
 * Date: 2016/05/26
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/26
 */
public abstract class Buffer
{
  /**This is a BufferedImage which is a visual representation of the buffer. */
  protected BufferedImage view;
  /**This defines the render width of the buffer class in number of game components. */
  private int renderWidth;
   /**This defines the render height of the buffer class in number of game components. */ 
  private int renderHeight;
  /**This is the specified size of the game components in pixels. */
  protected int tileSize;
  
  /**
   * This constructor creates a buffer of the specified width and height in pixels with the specified game component size.
   * @param width this is the width of the screen this class is buffering for in pixels.
   * @param height this is the height of the screen this class is buffering for in pixels.
   * @param tileSize the specified size of the game components in pixels.
   */ 
  public Buffer (int width, int height, int tileSize)
  {
    this.tileSize = tileSize;
    renderWidth = (int)((double) width/tileSize + 0.5) + 2;
    renderHeight = (int) ((double) height/tileSize + 0.5) + 2;
    view = new BufferedImage (renderWidth * tileSize, renderHeight * tileSize, BufferedImage.TYPE_INT_ARGB);
  }
  
  /**
   * This method is intended to update the buffer image based on the game components.
   */ 
  public abstract void update();
  
  /**
   * This method is intended to center the view of the buffer around the specified x and y coordinates in relation to the game components passed into it.
   * @param x the x coordinate.
   * @param y the y coordinate.
   */
  public abstract void centerView(int x, int y);
  
  /**
   * This method returns the buffer's image.
   * @return BufferedImage the buffer's image.
   */
  public BufferedImage getView()
  {
    return view;
  }
   
  /**
   * This method returns the render width of the buffer in number of game components.
   * @return int the render width of the buffer in number of game components.
   */
  public int renderWidth ()
  {
    return renderWidth;
  }
  
  /**
   * This method returns the render height of the buffer in number of game components.
   * @return int the render height of the buffer in number of game components.
   */
  public int renderHeight()
  {
    return renderHeight;
  }
}