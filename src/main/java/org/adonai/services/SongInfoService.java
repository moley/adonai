package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

import java.util.ArrayList;
import java.util.Collection;

public class SongInfoService {

  public String getPreview (final Song song, final SongPart songPart) {

    String preview = "";

    SongPart realPart = songPart;
    if (songPart.getReferencedSongPart() != null) {
      realPart = song.findSongPartByUUID(songPart.getReferencedSongPart());
      preview = "Copy of ";
    }

    preview += realPart.getSongPartTypeLabel();

    Line firstLine = realPart.getFirstLine();

    if (firstLine == null)
      return preview;

    if (! firstLine.getPreview().trim().isEmpty())
      preview += " (" + firstLine.getPreview() + ")";
    return preview;
  }

  public Collection<SongPart> getRealSongPartsWithoutCurrent (final Song song, final SongPart songPart) {
    Collection<SongPart> filtered = new ArrayList<>();
    for (SongPart nextSongPart: song.getSongParts()) {
      if (nextSongPart.getReferencedSongPart() == null)
        filtered.add(nextSongPart);
    }

    filtered.remove(songPart);

    return filtered;

  }

}
