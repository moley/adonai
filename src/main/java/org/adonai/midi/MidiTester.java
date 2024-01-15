package org.adonai.midi;

import java.util.List;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import org.adonai.fx.SongSelector;

public class MidiTester {

  public void configure (final SongSelector songSelector)  {

    MidiDevice device;

    MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
    for (int i = 0; i < infos.length; i++) {
      try {
        device = MidiSystem.getMidiDevice(infos[i]);
        System.out.println(infos[i]);

        List<Transmitter> transmitters = device.getTransmitters();

        for (int j = 0; i < transmitters.size(); j++) {
          transmitters.get(j).setReceiver(new MidiInputReciever(songSelector, device.getDeviceInfo().toString()));
        }

        device.open();

        Transmitter transmitter  = device.getTransmitter();
        transmitter.setReceiver(new MidiInputReciever(songSelector, device.getDeviceInfo().toString()));

        System.out.println(device.getDeviceInfo() + " as opened");
      } catch (MidiUnavailableException e) {
        System.out.println ("Error on " + infos[i].getName() + ":" + e.getLocalizedMessage());
      }
    }

    System.out.println ("Finished");

  }
}
