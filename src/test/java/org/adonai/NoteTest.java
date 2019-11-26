package org.adonai;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class NoteTest {

  @Test
  public void orderedNoteLabels () {
    Collection<NoteEntry> notes = Note.getLengthOrderedNotes();
    Collection<Integer> lengths = new ArrayList<Integer>();
    for (NoteEntry nextOne: notes) {
      System.out.println (nextOne.getLabel());
      lengths.add(nextOne.getLabel().length());
    }

    Integer last = new Integer(4);

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
