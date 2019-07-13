package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartDescriptorStrategy;

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

    preview += (realPart != null ? realPart.getSongPartTypeLabel(): "INVALID PART");

    if (realPart == null)
      return preview;

    Line firstLine = realPart.getFirstLine();

    if (firstLine == null)
      return preview;

    if (! firstLine.getPreview().trim().isEmpty())
      preview += " (" + firstLine.getPreview() + ")";
    return preview;
  }

  public String getStructure (final Song song, final SongPart songPart, final SongPartDescriptorStrategy songPartDescriptorStrategy) {


    String quantityString = songPart.getQuantity() != null ? songPart.getQuantity().trim(): "";
    if (! quantityString.endsWith("x") && ! quantityString.isEmpty())
      quantityString = quantityString + "x";

    SongPart shownPart = songPart;
    if (songPart.getReferencedSongPart() != null)
      shownPart = song.findSongPartByUUID(songPart.getReferencedSongPart());

    if (shownPart.getSongPartType() == null)
      return "";

    if (songPartDescriptorStrategy.equals(SongPartDescriptorStrategy.SHORT))
      return (shownPart.getSongPartType().name().substring(0, 1) + " " + quantityString).trim();
    else if (songPartDescriptorStrategy.equals(SongPartDescriptorStrategy.LONG))
      return (shownPart.getSongPartType().name() + " " + quantityString).trim();
    else
      return "";

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
