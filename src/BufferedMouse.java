import java.awt.event.*;
import java.util.*;
/**
 * The BufferedMouse class takes and stores input from the mouse so that other classes can access and remove the information when needed.
 * <p>
 * BufferedMouse
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */ 
public class BufferedMouse implements MouseListener
{
  /**This is the list of x coordinate values. */
  ArrayList <Integer> xList = new ArrayList <Integer> ();
  /**This is the listo f y coordinate values. */
  ArrayList <Integer> yList = new ArrayList <Integer> ();
  
  /**
   *This method returns the first x coordinate in the list. 
   * @return int the first x coordinate in the list.
   */
  public int xCoord()
  {
    if (xList.size() > 0)
      return xList.get(0);
    return -1;
  }
  
  /**
   *This method returns the first y coordinate in the list. 
   * @return int the first y coordinate in the list.
   */
  public int yCoord()
  {
    if (yList.size() > 0)
      return yList.get(0);
    return -1;
  }
  
  /**
   *This method removes the first values of the x and y coordinate lists.
   */
  public void refreshMouse()
  {
    if (xList.size() > 0 && yList.size() >0)
    {
      xList.remove(0);
      yList.remove(0);
    }
  }
  
  /**
   *This method is used to determine what will happen when the mouse is pressed.
   * @param e used as reference to MouseEvent.
   */
  public void mousePressed (MouseEvent e)
  {
    xList.add(new Integer (e.getX()));
    yList.add(new Integer (e.getY()));
  }
  
  /**
   *This method is used to determine what will happen when the mouse is exited.
   * @param e used as reference to MouseEvent.
   */
  public void mouseExited (MouseEvent e)
  {
  }
  /**
   *This method is used to determine what will happen when the mouse is released.
   * @param e used as reference to MouseEvent.
   */
  public void mouseReleased (MouseEvent e)
  {
  }
  /**
   *This method is used to determine what will happen when the mouse is clicked.
   * @param e used as reference to MouseEvent.
   */
  public void mouseClicked (MouseEvent e)
  {
  }
  /**
   *This method is used to determine what will happen when the mouse is entered.
   * @param e used as reference to MouseEvent.
   */
  public void mouseEntered (MouseEvent e)
  {
  }
}