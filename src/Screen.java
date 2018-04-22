import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/**
 * The Screen class aids in the processing and drawing of the game.
 * <p>
 * Screen
 * <p>
 * Date: 2016/05/26
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/05/26
 */ 
public abstract class Screen extends Canvas implements Runnable
{
  /**This defines the number of times the Screen class will update per second. */
  private final int FPS = 60;
  /**This tracks whether or not the Screen class should continue running. */
  private boolean running;
  /**This is the Thread that the Screen class will be run on.*/
  private Thread thread;
  /**The name that will be given to the Thread. */
  private String threadName;
  /*Counts ticks for the wait method. */
  private int ticks = 0;
  
  /**
   * This creates a new instance of the Screen class with the specified thread name.
   * @param threadName the name that will be given to the Thread.
   */
  public Screen (String threadName)
  {
    this.threadName = threadName;
    setFocusable(false);
  }
  
   /**
   * This programs halts processing for a specified number of ticks
   * @param ticks the number of ticks that should be waited before resuming processing.
   */
  public void wait (int ticks)
  {
    this.ticks -= ticks;
  }
  
  /**
   * This method is intended to hold the processing of all implementing classes.
   */
  public abstract void tick();
  
  /**
   * This method is intended to hold the drawing of all implementing classes.
   * @param g the Graphics object to be drawn on.
   */
  public abstract void refresh (Graphics g);
  
  /**
   * This method refreshes the graphics of the Screen class.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>bs </b> This is used as reference to BufferStrategy.
   * <p>
   * <b>g </b> Graphics object that is used as reference to Graphics.
   * @throws IllegalStateException when the program attempts to create a BufferStrategy before the screen is visible.
   */
  public void refresh ()
  {
    BufferStrategy bs = getBufferStrategy ();
    if (bs == null)
    {
      try
      {
        createBufferStrategy (2);
      }
      catch (IllegalStateException e)
      {
      }
      return;
    }
    Graphics g = bs.getDrawGraphics();
    refresh (g);
    g.dispose();
    bs.show();
  }
  
  /**
   * This method handles the processing and drawing of the Screen class and ensures that processing only occus 60 times per second.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>timeOld </b> This holds the previous nanosecond value of the computer clock.
   * <p>
   * <b>timeNew </b> This holds the current nanosecond value of the computer clock.
   * <p>
   * <b>frameRate </b> This holds the value of how long each update shouls last.
   */
  public void run()
  {
    long timeOld = System.nanoTime();
    long timeNew;
    double frameRate = 1000000000 / FPS;
    while (running)
    {
      timeNew = System.nanoTime();
      if (timeNew - timeOld >= frameRate)
      {
        timeOld = timeNew;
        if (ticks == 0)
          tick();
        else 
          ticks ++;
      }  
      refresh();
    }
    stop();
  }
  
   /**
   * This method starts the Screen.
   */
  public void start ()
  {
    running = true;
    thread = new Thread (this, threadName);
    thread.start();
  }
  
  /**
   * This method stops the Screen.
   */
  public void stop ()
  {
    running = false;
    try
    {
      thread.join();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}