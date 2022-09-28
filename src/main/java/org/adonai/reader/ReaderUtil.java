package org.adonai.reader;

import org.adonai.model.SongPartType;

public class ReaderUtil {

  public SongPartType extractTypeOrDefault(final String typeAsString) {
    SongPartType extractedType = extractType(typeAsString);
    return extractedType != null ? extractedType : SongPartType.ZWISCHENSPIEL;
  }

  public SongPartType extractType(final String typeAsString) {
    for (SongPartType next : SongPartType.values()) {
      if (next.getDisplayname().equalsIgnoreCase(typeAsString))
        return next;
    }

    switch (typeAsString.toUpperCase()) {
    case "CHORUS":
      return SongPartType.REFRAIN;
    case "SCHLUSS":
      return SongPartType.EXTRO;
    default:
      return null;
    }
  }
}
