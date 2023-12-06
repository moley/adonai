package org.adonai.fx.viewer;

import org.adonai.fx.TextRenderer;
import org.adonai.fx.editcontent.KeyType;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.junit.Assert;
import org.junit.Test;

public class TextRendererTest {

  private TextRenderer textRenderer = new TextRenderer();

  @Test
  public void normalLine () {
    Song song = SongBuilder.instance().withPart(SongPartType.REFRAIN).withLine().withLinePart("Hello ", "A").withLinePart("sun", "D").get();
    SongPart songPart = song.getFirstPart();
    String renderedText = textRenderer.getRenderedText(songPart, KeyType.CURRENT);
    String [] lines = renderedText.split("\n");
    Assert.assertEquals ("A     D  ", lines[0]);
    Assert.assertEquals ("Hello sun", lines[1]);


  }

  @Test
  public void twoChordsSpace () {
    Song song = SongBuilder.instance().withPart(SongPartType.REFRAIN).withLine().withLinePart("Hello ", "A").withLinePart("", "D").withLinePart("sun", "G").get();
    SongPart songPart = song.getFirstPart();
    String renderedText = textRenderer.getRenderedText(songPart, KeyType.CURRENT);
    String [] lines = renderedText.split("\n");
    Assert.assertEquals ("A     D G  ", lines[0]);
    Assert.assertEquals ("Hello   sun", lines[1]);

  }

  /**
   * A            F#m          D              Esus E
   * Shout to the Lord all the earth, let us  si   ng
   */
  @Test
  public void space () {
    Song song = SongBuilder.instance().withPart(SongPartType.REFRAIN).withLine().withLinePart("let us ", "A").withLinePart("", "").withLinePart("si", "Esus").withLinePart("ng", "E").get();
    SongPart songPart = song.getFirstPart();
    String renderedText = textRenderer.getRenderedText(songPart, KeyType.CURRENT);
    String [] lines = renderedText.split("\n");
    Assert.assertEquals ("A      Esus E ", lines[0]);
    Assert.assertEquals ("let us si   ng", lines[1]);

  }
}
