package org.adonai;

import com.mpatric.mp3agic.ID3v1Genres;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v22Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.commons.io.FileUtils;
import org.adonai.model.Configuration;
import org.adonai.model.Song;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by OleyMa on 08.11.16.
 */
public class PlaylistExport {

  private static final Logger LOGGER = Logger.getLogger(PlaylistExport.class.getName());


  private void adaptMp3Tags (final File inputfile, final File outputfile, final Song song) {

    try {
      Mp3File mp3file = new Mp3File(inputfile);
      System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
      System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
      System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
      System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
      System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
      System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));

      if (mp3file.hasId3v1Tag())
        mp3file.removeId3v1Tag();
      if (mp3file.hasId3v2Tag())
        mp3file.removeId3v2Tag();
      ID3v2 id3v1Tag = new ID3v22Tag();
      id3v1Tag.setTrack("" + song.getId());
      id3v1Tag.setArtist("");
      id3v1Tag.setTitle(song.getTitle());
      id3v1Tag.setAlbum("Worship");
      id3v1Tag.setYear("");
      id3v1Tag.setGenre(ID3v1Genres.matchGenreDescription("Christian Rock"));
      id3v1Tag.setComment("Some comment");
      mp3file.setId3v1Tag(id3v1Tag);
      mp3file.save(outputfile.getAbsolutePath());

    } catch (IOException e) {
      throw new IllegalStateException(e);
    } catch (UnsupportedTagException e) {
      throw new IllegalStateException(e);
    } catch (InvalidDataException e) {
      throw new IllegalStateException(e);
    } catch (NotSupportedException e) {
      throw new IllegalStateException(e);
    }
  }

  public void export (final Configuration configuration, final File playlistpath) {
    if (playlistpath.exists())
      try {
        FileUtils.deleteDirectory(playlistpath);
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

    playlistpath.mkdirs();

    for (Song next: configuration.getSongBooks().get(0).getSongs()) {
      if (next.getAttachedSong() != null && ! next.getAttachedSong().isEmpty()) {
        LOGGER.info("Add " + next.getAttachedSong() + " to playlist");

        File inputFile = new File (next.getAttachedSong());
        String id = String.format("%03d", next.getId());
        File outputFile = new File (playlistpath, id + "-" + sonderzeichen(next.getTitle()) + ".mp3");

        adaptMp3Tags(inputFile, outputFile, next);
      }
      else
        LOGGER.info("Song " + next.getId() + " dies not have an attached mp3 file");
    }

  }

  private String sonderzeichen (final String withoutSonderzeichen){
    String filtered = withoutSonderzeichen.replaceAll("Ö", "OE");
    filtered = filtered.replace("Ä", "AE");
    filtered = filtered.replace("Ü", "UE");
    filtered = filtered.replace("Ü", "UE");
    filtered = filtered.replace("'", "");
    filtered = filtered.replace("[^a-zA-Z0-9 ]+","");
    return filtered;

  }
}
