/**
 * The InformationNPC class is created so that NPCs can be added to the game which the player can talk to if they are lonely.
 * <p>
 * InformationNPC
 * <p>
 * Date: 2016/06/01
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/01
 */ 
public class InformationNPC extends NPC
{
   /**This is the text that will be shown when the character interacts with this NPC. */
  private String [] dialogueText;
   /**This is the Sprite that the text will be drawn on. */
  private Sprite dialogueBack;
   /**This is the currentLine of text that will be shown */
  private int currentLine;
  
  /**
   * This constructs an instance of the InformationNPC class with a specified character model at the specified x and y coordinate with the specified dialogue image and text. 
   * @param character the character model of this NPC.
   * @param x the x coordinate that this NPC will be created at.
   * @param y the y coordinate that this NPC will be created at.
   * @param dialogueBack the Sprite that the text will be drawn on.
   * @param dialogueText the text that will be shown when the character interacts with this NPC.
   */ 
  public InformationNPC (String character, int x, int y, Sprite dialogueBack, String dialogueText)
  {
    super (character, x, y);
    this.dialogueBack = dialogueBack;
    this.dialogueText = dialogueText.split("%");
  }
  
  /**
   * This method changes the text that will be shown when the NPC is interacted with.
   * @param dialogueText the text that will be shown when the character interacts with this NPC.
   */ 
  public void setText (String dialogueText)
  {
    this.dialogueText = dialogueText.split("%");
  }
  
  /**
   * This method increments to the next line of text.
   */ 
  public void nextLine ()
  {
    currentLine ++;
    if (currentLine >= dialogueText.length)
      currentLine = 0;
  }
  
    /**
   * This method returns true if the text is finished.
   * @return true if the text is finished.
   */ 
  public boolean finished()
  {
    return currentLine == dialogueText.length - 1;
  }
  
   /**
   * This method returns the current line of text.
   * @return String the current line of text.
   */ 
  public String dialogueText()
  {
    return dialogueText [currentLine];
  }
  
  /**
   * This method returns the fully unedited InformationNPC text.
   * @return String the fully unedited InformationNPC text.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>tempString </b> This is a temporary String used to piece together all the Strings in the separate pages.
   */ 
  public String fullText()
  {
    String tempString = dialogueText[0];
    for (int x = 1; x < dialogueText.length; x++)
    {
      tempString+= "%" + dialogueText[x];
    }
    return tempString;
  }
  
  /**
   * This method returns the Sprite used to display the text.
   * @return Sprite the background image for the text.
   */ 
  public Sprite dialogueBack ()
  {
    return dialogueBack;
  }
  
  /**
   * This is an overriden method which will be called when the NPC is interacted with.
   * @param panel This is the OpenWorldPanel that will be used during the interaction.
   */
  public void interact (OpenWorldPanel panel)
  {
    currentLine = 0;
    panel.npcDialogue(this);
  }
  
  /**
   * This method is used to return the value of the information npc type.
   * @return int the value used to store information npcs.
   */
  public int getType()
  {
    return Map.INFO_NPC;
  }
}