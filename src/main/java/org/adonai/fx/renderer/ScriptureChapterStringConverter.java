package org.adonai.fx.renderer;

import javafx.util.StringConverter;
import org.adonai.bibles.Chapter;

public class ScriptureChapterStringConverter extends StringConverter<Chapter> {

  @Override public String toString(Chapter item) {
    if (item == null){
      return null;
    } else {
      return item.getNumber() + " (" + item.getVerses().size() + ")";
    }
  }

  @Override public Chapter fromString(String string) {
    return null;
  }
}
