import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.AffineTransform;
import java.awt.*;
/**
 * The Sprite class is load and hold values of images as BufferedImages.
 * <p>
 * Sprite
 * <p>
 * Date: 2016/05/15
 * @author David White and Sepehr Hosseini 
 * @version 2 2016/05/15
 */ 
public class Sprite
{
  /**This holds the BufferedImage value of the Sprite.*/
  private BufferedImage image;
  /**This holds the file name of the image.*/
  private String imageName;
  /**This holds the BufferedImage value of the modified Sprite.*/
  private BufferedImage modifiedImage;
  /**This is used as reference for modifying the BufferedImage.*/
  private AffineTransform at = new AffineTransform();
  /**
   * This constructs an instance of the Sprite class based on String input as a file name.
   * @param fileName the file name used to read the image.
   * @throws IOException when reading an image. 
   */
  public Sprite (String fileName) throws java.io.IOException
  {
    image = ImageIO.read(new File(".//resources/" + fileName));
    imageName = fileName;
    modifiedImage = ImageIO.read (new File (".//resources/"+fileName));
  }
  
  /**
   * This method scales the image.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>g </b> Reference to the Graphics class which will be used to draw images onto the screen.
   * @param x the x ratio used to scale the image.
   * @param y the y ratio used to scale the image. 
   */
  public void scaleImage(double x, double y)
  {
    modifiedImage = new BufferedImage ((int)(image.getWidth()*x+0.5), (int)(image.getHeight()*y +0.5), BufferedImage.TYPE_INT_ARGB);
    at.scale (x, y);
    Graphics2D g = modifiedImage.createGraphics();
    g.setBackground (new Color (0,0,0,0));
    g.clearRect (0,0,modifiedImage.getWidth(), modifiedImage.getHeight());
    g.drawImage (image, at, null);
  }
  
  /**
   * This method returns the file name of the image.
   * @return String holds the file name of the image.
   */
  public String imageName()
  {
    return imageName;
  }
  
  /**
   * This method returns the BufferedImage value of the Sprite.
   * @return BufferedImage the BufferedImage value of the Sprite. 
   */
  public BufferedImage image()
  {
    return modifiedImage;
  }    
}