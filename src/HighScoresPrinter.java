import java.awt.*;
import javax.swing.*;
import java.awt.print.*;
import java.util.ArrayList;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
/**
 * This class is used to print the highscores.
 * <p>
 * HighScorePrinter
 * <p>
 * Date: 2016/06/04
 * @author David White and Sepehr Hosseini (with help from Salar Hosseini)
 * @version 1 2016/06/04
 */ 
 
public class HighScoresPrinter implements Printable
{

  /**
   * This method is used to sort the printed highscores.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>d </b> This is used as reference to the Dimension class when calculating the screen dimensions.
   * <p>
   * <b>x2 </b> Used for positioning of the high scores.
   * <p>
   * <b>hs </b> Reference to HighScoreList when listing the scores.
   * <p>
   * <b>scores </b> The list of highscores used when printing.
   * <p>
   * <b>pic </b> This variable is used to draw the Company Logo.
   * <p>
   * <b>current </b> The current HighScore.
   * @param g This object references Graphics  to draw all of the text and images that will be displayed on the page that will be printed.
   * @param pf This stores the format of the page that will be printed.
   * @param page This stores the index of the page.
   */ 
  public int print(Graphics g, PageFormat pf, int page) throws PrinterException
  {
        
    if (page > 0) 
      return NO_SUCH_PAGE;
    
    Dimension d = new Dimension ((int) (pf.getImageableWidth()), (int) (pf.getImageableHeight()));
    
    drawText (g, Font.BOLD, 18, "Terrarithmetic High Scores", 50, d);
    drawText (g, Font.PLAIN, 15, "Created by Sepehr Hosseini & David White", 68, d);
    drawText (g, Font.PLAIN, 15, "Resource reference: Data.pi", 86, d);
    int x2 = drawText (g, Font.BOLD, 13, "Rank                 Name                Level                Score", 140, d);
    
    BufferedImage pic;
    try 
    {
      pic = ImageIO.read(new File(".//resources/Company Logo.png"));
      g.drawImage (pic, 420, 300, null);
    }
    catch (IOException e)
    {}
    
    g.setFont (new Font("Minecraftia",Font.PLAIN,12));
    
    HighScoreList hs = new HighScoreList ();
    ArrayList <HighScore> scores = hs.getScores ();
    HighScore current;
    
    for (int x = 0, y = 160; x<10; x++, y += 15)
    {
      try
      {
        current = scores.get (x);
        g.drawString ("" + (x+1), x2, y);
        g.drawString(current.getName (), 210, y);
        g.drawString(current.getLevel (), 313, y);
        g.drawString("" + current.getScore (), 425, y);
      }
      catch (IndexOutOfBoundsException e)
      {
        g.drawString ("" + (x+1), x2, y);
        g.drawString("None", 210, y);
        g.drawString("None", 313, y);
        g.drawString("None", 425, y);            
      }
    }
    return PAGE_EXISTS;
  }
  
  /**
   * This private method draws the text that will be printed and centered.
   * @param g Reference to Graphics when drawing Strings on the page.
   * @param fontStyle stores the style of font that will be used when printing.
   * @param fontSize stores the font size.
   * @param printString stores the String that will be printed.
   * @param yLoc stores the y position that the text will be printed at.
   * @param d stores the Dimensions of the page that will be printed.
   * @param x2 the x position of the Strings that will be printed.
   * @return integer x position of drawn text.
   */ 
  private int drawText (Graphics g, int fontStyle, int fontSize, String printString, int yLoc, Dimension d)
  {
    g.setFont (new Font ("Minecraftia", fontStyle, fontSize));
    int x2 = d.width/2 - g.getFontMetrics().stringWidth (printString)/2;
    g.drawString (printString, x2, yLoc);
    return x2;
  }
  
  /**
   * This method starts printing the highscores.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>job </b> reference to the PrinterJob that is used to print the top 10 sorted highscores.
   */ 
  public void printHighscores () 
  {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(this);
    if (job.printDialog()) //if ok to start printing
    {
      try 
      {
        job.print();
      } 
      catch (PrinterException e) 
      {
        JOptionPane.showMessageDialog (null, "An Error has occured. Please make sure that you are connected to your printer, and it is ready to print.");
      }
    }
  }
}