import java.io.*;
import java.util.*;
/**
 * The HighScoreList class is used to access and add to the list of HighScores.
 * <p>
 * HighScoreList
 * <p>
 * Date: 2016/06/04
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/04
 */ 
public class HighScoreList 
{
   /**This is the ArrayList of HighScores that stores the scores in descending order from highest to lowest. */
  private ArrayList <HighScore> scores = new ArrayList <HighScore>();
   /**This stores the total amount of scores. */
  private int numScores;
   /**This stores the maximum number of scores that can be printed or displayed. */
  private final int MAX_SCORES = 10;
   /**This is file's header. */
  private final String HEADER = "PIXELS";
  
  /**
   * This constructor reads through the data file and assigns the scores to the arraylist.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>in </b> This is used as reference to the BufferedReader class when reading from files.
   * @throws NullPointerException when the file is null.
   * @throws NumberFormatException when parsing between data values.
   */ 
  public HighScoreList ()
  {
    try
    {
      BufferedReader in = new BufferedReader (new FileReader (".//resources/highscores/Data.pi"));
      if (in.readLine ().equals (HEADER))
      {
        numScores = Integer.parseInt (in.readLine());
        for (int x = 0; x < numScores ;x++)
        {
          scores.add (new HighScore (in.readLine(), Integer.parseInt (in.readLine()), in.readLine()));
        }
      }
      in.close();
    }
    catch (NullPointerException e)
    {
    }
    catch (NumberFormatException e)
    {
    }
    catch (IOException e)
    {
    }
  }
  
  /**
   * This method is used to add a HighScore to the list of scores.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>y </b> This is used when sorting the values in the array.
   * <p>
   * <b>out </b> This is used as reference to the PrintWriter class when writing to a file.
   * @param score This is the new score that will be added to the list.
   */ 
  public void addScore(HighScore score)
  {
    int y;
    for (y = 0; y < numScores; y++)
    {
      if (scores.get (y).getScore () <= score.getScore ())
      {
        if (numScores == MAX_SCORES)
          scores.remove (MAX_SCORES-1);
        scores.add (y, score);
        break;
      }
    }   
    if (y == numScores)
      scores.add (score);
    if (numScores != MAX_SCORES)
      numScores ++;
    try
    {
      PrintWriter out = new PrintWriter (new FileWriter (".//resources/highscores/Data.pi"));
      out.println (HEADER);
      out.println (numScores);
      for (int x = 0; x < scores.size(); x++)
      {
        out.println (scores.get(x).getName().trim());
        out.println (scores.get(x).getScore());
        out.println (scores.get(x).getLevel());
      }
      out.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
   /**
   * This method returns the scores with the correct placement.
   * @return ArrayList the ArrayList of sorted scores.
   */ 
  public ArrayList<HighScore> getScores ()
  {
    return scores;
  }
}
    