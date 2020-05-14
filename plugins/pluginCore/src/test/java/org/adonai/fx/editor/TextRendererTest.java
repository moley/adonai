package org.adonai.fx.editor;

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
    String renderedText = textRenderer.getRenderedText(songPart);
    String [] lines = renderedText.split("\n");
    Assert.assertEquals ("A     D  ", lines[0]);
    Assert.assertEquals ("Hello sun", lines[1]);


  }

  @Test
  public void twoChordsSpace () {
    Song song = SongBuilder.instance().withPart(SongPartType.REFRAIN).withLine().withLinePart("Hello ", "A").withLinePart("", "D").withLinePart("sun", "G").get();
    SongPart songPart = song.getFirstPart();
    String renderedText = textRenderer.getRenderedText(songPart);
    String [] lines = renderedText.split("\n");
    Assert.assertEquals ("A     D G  ", lines[0]);
    Assert.assertEquals ("Hello   sun", lines[1]);

  }
}
