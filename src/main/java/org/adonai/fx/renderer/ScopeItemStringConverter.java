package org.adonai.fx.renderer;

import javafx.util.StringConverter;
import org.adonai.fx.main.ScopeItem;

public class ScopeItemStringConverter extends StringConverter<ScopeItem> {

  @Override public String toString(ScopeItem scopItem) {
    if (scopItem == null){
      return null;
    } else {
      return scopItem.getName();
    }
  }

  @Override public ScopeItem fromString(String string) {
    return null;
  }
}