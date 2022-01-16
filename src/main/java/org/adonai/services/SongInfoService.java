package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartDescriptorStrategy;

import java.util.ArrayList;
import java.util.Collection;
import org.adonai.model.SongStructItem;

public class SongInfoService {

  public String getPreview (final Song song, final SongStructItem songStructItem) {

    String preview = "";

    SongPart songPart = song.findSongPart(songStructItem);
    if (! songStructItem.isFirstOccurence()) {
      preview = "Copy of ";
    }

    preview += (songPart != null ? songPart.getSongPartTypeLabel(): "INVALID PART");

    if (songPart == null)
      return preview;

    Line firstLine = songPart.getFirstLine();

    if (firstLine == null)
      return preview;

    if (! firstLine.getPreview().trim().isEmpty())
      preview += " (" + firstLine.getPreview() + ")";
    return preview;
  }

  public String getStructure (final Song song, final SongStructItem songStructItem, final SongPartDescriptorStrategy songPartDescriptorStrategy) {


    String quantityString = songStructItem.getQuantity() != null ? songStructItem.getQuantity().trim(): "";
    if (! quantityString.endsWith("x") && ! quantityString.isEmpty())
      quantityString = quantityString + "x";

    if (songStructItem.getShorttext() == null)
      throw new IllegalStateException("SongStructItem with part id" + songStructItem.getPartId() + " in song " + song.getId() + " has no shorttext");

    if (songStructItem.getText() == null)
      throw new IllegalStateException("SongStructItem with part id" + songStructItem.getPartId() + " in song " + song.getId() + " has no text");



    if (songPartDescriptorStrategy.equals(SongPartDescriptorStrategy.SHORT))
      return (songStructItem.getShorttext()+ " " + quantityString).trim();
    else if (songPartDescriptorStrategy.equals(SongPartDescriptorStrategy.LONG))
      return (songStructItem.getText() + " " + quantityString).trim();
    else
      return "";

  }

  public Collection<SongStructItem> getRealSongPartsWithoutCurrent (final Song song, final SongStructItem songStructItem) {
    Collection<SongStructItem> filtered = new ArrayList<SongStructItem>();
    for (SongStructItem nextSongPart: song.getStructItems()) {
      if (nextSongPart.isFirstOccurence() && ! nextSongPart.getPartId().equals(songStructItem.getPartId()))

      filtered.add(nextSongPart);
    }

    filtered.remove(songStructItem);

    return filtered;

  }

}
