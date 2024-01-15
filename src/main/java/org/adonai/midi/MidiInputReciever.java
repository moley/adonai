package org.adonai.midi;

import javafx.application.Platform;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import org.adonai.fx.SongSelector;

public class MidiInputReciever implements Receiver {

  private final SongSelector songSelector;
  private final String name;

  public MidiInputReciever (final SongSelector songSelector, final String name) {
    this.songSelector = songSelector;
    this.name = name;
  }

  @Override public void send(MidiMessage message, long timeStamp) {

    if (message instanceof ShortMessage) {
      ShortMessage shortMessage = (ShortMessage) message;
      System.out.println ("shortMessage: " + shortMessage.getChannel() + "-" + shortMessage.getCommand() + "-" + shortMessage.getData1() + "-" + shortMessage.getData2());
      if (shortMessage.getData1() == 62 && shortMessage.getData2() == 127) {
        System.out.println("Step to next song");
        Platform.runLater(() -> songSelector.selectNextSong());
      }

      if (shortMessage.getData1() == 61 && shortMessage.getData2() == 127) {
        System.out.println("Step to previous song");
        Platform.runLater(() -> songSelector.selectPrevSong());
      }
    }

  }

  @Override public void close() {

  }
}
