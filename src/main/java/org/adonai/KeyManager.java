package org.adonai;

import java.util.ArrayList;
import java.util.Collection;

public class KeyManager {

  private Collection<Key> incrementingModes = new ArrayList<Key>();
  private Collection<Key> decrementingModes = new ArrayList<Key>();

  public KeyManager() {
    incrementingModes.add(Key.C);
    incrementingModes.add(Key.G);
    incrementingModes.add(Key.D);
    incrementingModes.add(Key.A);
    incrementingModes.add(Key.E);
    incrementingModes.add(Key.H);
    incrementingModes.add(Key.Fis);
    incrementingModes.add(Key.Cis);
    incrementingModes.add(Key.Gis);
    incrementingModes.add(Key.Dis);

    decrementingModes.add(Key.F);
    decrementingModes.add(Key.Bb);
    decrementingModes.add(Key.Eb);
    decrementingModes.add(Key.Ab);
    decrementingModes.add(Key.Db);
    decrementingModes.add(Key.Gb);
    decrementingModes.add(Key.Cb);

  }

  public NoteEntryType getType (Key mode) {

    if (incrementingModes.contains(mode))
      return NoteEntryType.INCREMENT;
    else if (decrementingModes.contains(mode))
      return NoteEntryType.DECREMENT;
    else
      throw new IllegalStateException("Could not find " + mode + " in modes");


  }
}
