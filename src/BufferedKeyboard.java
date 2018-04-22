import java.awt.event.*;
import java.awt.*;
/**
 * The BufferedKeyboard class uses an array when listening for key input to smoothen the transition between the keys that are pressed.
 * <p>
 * BufferedKeyboard
 * <p>
 * Date: 2016/05/31
 * @author David White and Sepehr Hosseini 
 * @version 2 2016/05/31
 */ 
public class BufferedKeyboard implements KeyListener
{
  /**This is an array of integers that serves as a key buffer for the class. */
  private int [] keyBuffer = new int [] {-1,-1}; 
  /**This boolean represents whether or not a new key has been pressed different from the last returned key. */
  private boolean newInput = true;
  
  /**
   * This method returns the first value in the key buffer. 
   * @return int returns the first value in the key buffer.
   */ 
  public int getKeyCode ()
  {
    return keyBuffer [0];
  }
  
  /**
   * This method returns a boolean value representative of whether or not there is a key currently being pressed.
   * @return true if the keyboard has input stored.
   */ 
  public boolean hasInput()
  {
    return keyBuffer [0] != -1;
  }
  
  /**
   * This method returns a boolean value representative of whether or not there is a new key being pressed.
   * @return true if there is currently a key being pressed which is different than the key being pressed the last time this method was called.
   */ 
  public boolean hasNewInput()
  {
    if (newInput && keyBuffer [0] != -1)
    {
      newInput = false;
      return true;
    }
    return false;
  }
  
  /**
   * This method is intended to compare the specified integer to the first key code in the buffer. 
   * @param x the integer to be compared with the key code. 
   * @return true if the specified integer matches the first key code in the buffer.
   */ 
  public boolean keyCodeEquals (int x)
  {
    return keyBuffer [0] == x;
  }
  
  /**
   * This method overrides the keyPressed method in the KeyListener class and adds the pressed keys into the key buffer.
   * @param ke holds the value of the key that is currently being pressed.
   */ 
  @Override
  public void keyPressed (KeyEvent ke)
  {
    if (keyBuffer [0] == -1)
      keyBuffer [0] = ke.getKeyCode();
    else if (ke.getKeyCode () != keyBuffer [0])
      keyBuffer [1] = ke.getKeyCode();
  }
  /**
   * This method overrides the keyReleased method in the KeyListener class and removes the first key from the buffer and replaces it with the second value.
   * @param ke holds the value of the key that has been released. 
   */ 
  @Override
  public void keyReleased (KeyEvent ke)
  {
    newInput = true;
    keyBuffer [0] = keyBuffer [1];
    keyBuffer [1] = -1;
  }
  /**
   * This method overrides the keyTyped method in the KeyListener class.
   * @param ke holds the value of the key that is currently being pressed.
   */
  @Override
  public void keyTyped (KeyEvent ke)
  {  
  }
}