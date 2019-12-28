package org.adonai;

import org.junit.Assert;
import org.junit.Test;

public class NoteEntryTest {

  @Test
  public void equalsAndHashCode () {

    NoteEntry noteEntry = new NoteEntry("C", Note.C, NoteEntryType.INCREMENT);
    NoteEntry noteEntry2 = new NoteEntry("C", Note.C, NoteEntryType.INCREMENT);
    NoteEntry noteEntry3 = new NoteEntry("D", Note.C, NoteEntryType.INCREMENT);

    Assert.assertEquals ("Same equals", noteEntry, noteEntry);
    Assert.assertEquals ("Same content equals", noteEntry, noteEntry2);
    Assert.assertNotEquals ("Different content not equals", noteEntry, noteEntry3);

    Assert.assertEquals ("Same hashcode", noteEntry.hashCode(), noteEntry.hashCode());
    Assert.assertEquals ("Same content hashcode", noteEntry.hashCode(), noteEntry2.hashCode());
    Assert.assertNotEquals ("Different content not hashcode", noteEntry.hashCode(), noteEntry3.hashCode());

  }
}
