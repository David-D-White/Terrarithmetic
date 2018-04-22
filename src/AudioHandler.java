import java.io.*;
import javax.sound.sampled.*;
import java.util.*;
/**
 * This class is used to handle audio output and manage currently playing tracks.
 * <p>
 * AudioHandler
 * <p>
 * Date: 2016/06/07
 * @author David White and Sepehr Hosseini 
 * @version 1 2016/06/07
 */
public class AudioHandler
{
  /**This is an ArrayList which holds all the currently playing audio tracks.*/
  private ArrayList <Clip> musicTracks = new ArrayList <Clip>();
  /**This is an ArrayList of Strings to serve as a way of referencing the currrently playing audio tracks. */
  private ArrayList <String> musicTags = new ArrayList <String> ();
  /**This is a boolean variable which tracks whether or not the audio is currently enabled. */
  private boolean enabled = true;
  
  /**
   * This method adds an audio clip to the array of tracks if it does not already exist.
   * <p>
   * <b>Local variables: </b>
   * <p>
   * <b>file </b> The music File.
   * <p>
   * <b>stream </b> Reference to the AudioInputStream class.
   * <p>
   * <b>music </b> Clip that will be stored and manipulated by this class.  
   * @param fileName The file that the music will be loaded from, also serves as a tag for reference.
   */
  public void addTrack(String fileName)
  {
    if (!musicTags.contains(fileName))
    {
      try 
      {
        File file = new File(".//resources/Sound/" + fileName + ".wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        Clip music = AudioSystem.getClip();
        music.loop(Clip.LOOP_CONTINUOUSLY);
        music.open(stream);
        musicTracks.add(music);
        musicTags.add(fileName);
      } 
      catch (UnsupportedAudioFileException e) 
      {
      }
      catch (LineUnavailableException e) 
      {
      }
      catch (IOException e) 
      {
      }
    }
  }
   
   /**
   * This method returns a boolean value which indicates whether or not the specified track is playing
   * @param tag the tag fot the music track to be checked
   * @return true if the corresponding audio track is running
   */ 
  public boolean playing (String tag)
  {
    if (musicTags.contains(tag))
      return musicTracks.get(musicTags.indexOf(tag)).isRunning();
    return false;
  }
  
  /**
   * This method is used to return the position of the specified track in microseconds.
   * @param tag the tag of the specified track.
   * @return long the position of the specified track in microseconds.
   */
  public long trackPosition (String tag)
  {
    if (musicTags.contains(tag))
      return musicTracks.get(musicTags.indexOf(tag)).getMicrosecondPosition();
    return -1L;
  }
  
  /**
   * This method is used to restart a specified track.
   * @param tag the tag of the specified track.
   */
  public void restart (String tag)
  {
    if (musicTags.contains(tag) && enabled)
      musicTracks.get(musicTags.indexOf(tag)).setFramePosition(1);
  }
  
  /**
   * This method is used to start a specified track.
   * @param tag the tag of the specified track.
   */
  public void start(String tag)
  {
    if (musicTags.contains(tag) && enabled)
    {
      musicTracks.get(musicTags.indexOf(tag)).start();
      musicTracks.get(musicTags.indexOf(tag)).loop(Clip.LOOP_CONTINUOUSLY);
    }
  }
  
  /**
   * This method is used to stop a specified track.
   * @param tag the tag of the specified track.
   */
  public void stop (String tag)
  {
   if (musicTags.contains(tag))
      musicTracks.get(musicTags.indexOf(tag)).stop();
  }
  
   /**
   * This method halts all audio files being run my this class.
   */ 
  public void stopAll()
  {
    for (int x = 0; x < musicTracks.size(); x++)
      musicTracks.get(x).stop();
  }
  
   /**
   * This method enables the audio from this class
   */ 
  public void enable ()
  {
    enabled = true;
  }
  
   /**
   * This method disables the audio from this class
   */ 
  public void disable()
  {
    enabled = false;
    stopAll();
  }
}