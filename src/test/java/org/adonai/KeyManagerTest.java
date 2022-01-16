package org.adonai;

import org.junit.Assert;
import org.junit.Test;

public class KeyManagerTest {

  @Test
  public void getNoteEntryType () {
    KeyManager keyManager = new KeyManager();
    Assert.assertEquals (NoteEntryType.INCREMENT, keyManager.getType(Key.A));
    Assert.assertEquals (NoteEntryType.INCREMENT, keyManager.getType(Key.D));

    Assert.assertEquals (NoteEntryType.DECREMENT, keyManager.getType(Key.F));

  }


}
