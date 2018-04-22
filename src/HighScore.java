/**
 * The HighScore class stores the player'name, score and level.
 * <p>
 * HighScore
 * <p>
 * Date: 2016/06/04
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/04
 */ 
public class HighScore
{
  /**This is the String that holds the player's name. */
  private String name;
  /**This is the value of the player's score. */
  private int score;
  /**This is the String that stores the level/difficulty that the player selected. */
  private String level;
  
  /**
   * This constructor assigns the values specified to a new HighScore which will be used in the HighScoreList.
   * @param name the player's name as a String.
   * @param score the player's score value. 
   * @param level the player's level selection.
   */ 
  public HighScore (String name, int score,  String level)
  {
    this.name = name;
    this.score = score;
    this.level = level;
  }
  
  /**
   *This is used to access the player's name.
   * @return String the player's name.
   */
  public String getName()
  {
    return name;
  }
  /**
   *This is used to access the player's score.
   * @return int the player's score.
   */
  public int getScore()
  {
    return score;
  }
  /**
   *This is used to access the player's level/difficulty.
   * @return int the player's level/difficulty.
   */
  public String getLevel()
  {
    return level;
  }
}