package org.adonai;

import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SongStruct {

  private List<SongStructItem> items = new ArrayList<SongStructItem>();
  private Song song;

  public SongStruct(final Song file) {
    readStruct(file);
    this.song = file;
  }

  public SongStructItem getItem(final SongPart part) {
    for (SongStructItem nextItem : getItems()) {
      if (nextItem.getPart().equals(part))
        return nextItem;
    }

    return null;
  }

  /**
   * checks the last items with the same type
   * and gets the first that is shown
   * If no shown item with the given type is found directly above, than <code>null</code> is returned
   * If no suitable item is found, than <code>null</code> is returned either
   *
   * @return item or <code>null</code>
   */
  private SongStructItem getLastShownItem(SongPart contentPart) {
    SongStructItem markedItem = null;
    for (int i = getItems().size() - 1; i >= 0; i--) {
      SongStructItem prevItem = getItems().get(i);

      if (true)
        throw new IllegalStateException("NYI");

      SongPart contentPartCompare = getUsedSongPart(prevItem.getPart());

      if (!contentPartCompare.equals(contentPart))
        return markedItem;
      else
        markedItem = prevItem;
    }
    return null;
  }

  private SongPart getUsedSongPart(final SongPart originPath) {
    if (originPath.getReferencedSongPart() != null)
      return song.findSongPartByUUID(originPath.getReferencedSongPart());
    else
      return originPath;
  }


  /**
   * reads the structure of a song to give additionals infos to show
   *
   * @param file file
   * @return structitems
   */
  private void readStruct(final Song file) {
    HashMap<SongPartType, Integer> partcountsCreated = new HashMap<SongPartType, Integer>();
    for (SongPart nextPart : file.getSongParts()) {

      SongPart contentPart = getUsedSongPart(nextPart);
      SongStructItem item = getItem(contentPart);

      if (item == null) { //if current part was not analyzed, then the current part is a new one, so increment the counter
        Integer integer = partcountsCreated.get(nextPart.getSongPartType());
        if (integer == null)
          integer = new Integer(1);
        else
          integer = new Integer(integer.intValue() + 1);
        partcountsCreated.put(nextPart.getSongPartType(), integer);
      }

      SongStructItem newItem = new SongStructItem();
      newItem.setPart(nextPart); //the part itself, references are not resolved
      newItem.setType(nextPart.getSongPartType());
      newItem.setIndex(item != null ? item.getIndex() : partcountsCreated.get(nextPart.getSongPartType())); //REFRAIN2...
      newItem.setContentShown(nextPart.getReferencedSongPart() == null);

      //.... REFRAIN 3x
      SongStructItem lastShownItem = getLastShownItem(contentPart);
      if (lastShownItem != null) {
        newItem.setContentShown(false);
        newItem.setVisible(false);
        lastShownItem.increaseCumulation();
      }

      getItems().add(newItem);
    }

    for (SongStructItem nextItem : getItems()) {
      Integer count = partcountsCreated.get(nextItem.getType());
      if (count == 1)
        nextItem.setIndex(null);
    }
  }

  public List<SongStructItem> getItems() {
    return items;
  }
}