package org.adonai;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class NoteTest {

  @Test
  public void orderedNoteLabels () {
    Collection<NoteEntry> notes = Note.getLengthOrderedNotes();
    Collection<Integer> lengths = new ArrayList<Integer>();
    for (NoteEntry nextOne: notes) {
      lengths.add(nextOne.getLabel().length());
    }

    Integer last = 4;

    for (Integer next: lengths) {

      Assert.assertTrue ("Sorting incorrect (next: " + next + ", last: " + last + ")", next <= last);
      last = next;
    }
  }

  @Test
  public void fis () {
    Key key = Key.Fis;
    Note note = Note.from(key);

  }
}
