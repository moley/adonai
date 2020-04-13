package org.adonai;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

public class MidiTester {

  public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {

    ShortMessage myMsg = new ShortMessage();
    // Start playing the note Middle C (60),
    // moderately loud (velocity = 93).
    myMsg.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);

    MidiSystem.getTransmitter().setReceiver(MidiSystem.getReceiver());

    MidiDevice device;
    MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
    for (int i = 0; i < infos.length; i++) {

      try {
        device = MidiSystem.getMidiDevice(infos[i]);
        device.open();
        System.out.println (device.getDeviceInfo().getName() + "-" + device.getReceivers().size());
        //does the device have any recievers?
        if (device.getReceivers().size() > 0) {
          //if it does, add it to the device list
          for (Receiver next: device.getReceivers()) {
            System.out.println ("Device: " + device.getDeviceInfo().getName());
            next.send(myMsg, 100);

          }

        }

        device.close();
      } catch (MidiUnavailableException e) {}
    }

    System.out.println ("Done");


  }
}
